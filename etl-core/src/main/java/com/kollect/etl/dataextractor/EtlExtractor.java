package com.kollect.etl.dataextractor;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kollect.etl.extractor.DataAccess;
import com.kollect.etl.extractor.DataExtractorUtils;
import com.kollect.etl.extractor.DataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.dataaccess.AbstractSqlSessionProvider;
import com.kollect.etl.pbk.CustomerFormatter;
import com.kollect.etl.pbk.Formattable;
import com.kollect.etl.pbk.InvoiceFormatter;
import com.kollect.etl.pbk.TransactionFormatter;
import com.kollect.etl.service.email.IEmailService;
import com.kollect.etl.service.email.impl.EmailService;
import com.kollect.etl.services.SftpClient;
import com.kollect.etl.util.ResourceUtils;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.PropertiesUtils;
import com.kollect.etl.util.DateUtils;

public final class EtlExtractor {
  
  private static final String FTP_PROPERTIES = "../config/ftp.properties";
  private static final Logger LOG = LoggerFactory.getLogger(EtlExtractor.class);
  protected static final Map<String, Properties> propertyMap = new HashMap<>();
  private static final String EXTRACTION_STAT_LOG = "../logs/extractionStats.log";
  private static final String SERV_PROP = "SERVER_PROPERTY";
  
  private Connection getDataSource( String dbName ) {
    return new DataSource().getDbConnection(dbName);
  }
  
  private void writeData (IExtractionContext ctx, final String dbName, final String dstDir, final String filePrefix, final String queryString ) {
    final FileUtils fu = new FileUtils();
    final long queryStart = System.currentTimeMillis();
    final List<String> data = ctx.executeQuery(getDataSource(dbName), queryString);
    final long queryEnd = System.currentTimeMillis();
    final long queryDiff = DataExtractorUtils.getDifference(queryStart, queryEnd);
    final String fileName = filePrefix + dbName + ".csv";
    final long writingStart = System.currentTimeMillis();
    final long[] writeStat = new DataWriter().writeFile(dstDir, fileName, data);
    final long writingEnd = System.currentTimeMillis();
    final long writeDiff = DataExtractorUtils.getDifference(writingStart, writingEnd);
    final Object[] messageFormatObject = new Object[] { dbName, fileName, queryDiff, writeDiff, writeStat[0], writeStat[1] };
    fu.writeTextFile(EXTRACTION_STAT_LOG, DataExtractorUtils.buildStatsMessage(messageFormatObject) + "\n", true);
  }
  
  private void writeData (final Formattable fmt, final String dbName, final String dstDir, final String filePrefix, final String queryName ) {
    DataAccess q1 = new DataAccess(new AbstractSqlSessionProvider(dbName));
    final FileUtils fu = new FileUtils();
    final long queryStart = System.currentTimeMillis();
    final List<String> data = fmt.format(q1.executeQuery(queryName, null));
    final long queryEnd = System.currentTimeMillis();
    final long queryDiff = DataExtractorUtils.getDifference(queryStart, queryEnd);
    final String fileName = filePrefix + dbName + ".csv";
    final long writingStart = System.currentTimeMillis();
    final long[] writeStat = new DataWriter().writeFile(dstDir, fileName, data);
    final long writingEnd = System.currentTimeMillis();
    final long writeDiff = DataExtractorUtils.getDifference(writingStart, writingEnd);
    final Object[] messageFormatObject = new Object[] { dbName, fileName, queryDiff, writeDiff, writeStat[0], writeStat[1] };
    fu.writeTextFile(EXTRACTION_STAT_LOG, DataExtractorUtils.buildStatsMessage(messageFormatObject) + "\n", true);
  }
  
  private static String getOutputDir( final String dirName ) {
    return "../out/" + dirName;
  }

  private String getDirectoryName() {
    return new DateUtils().getDateToString("yyyyMMdd", new Date());
  }

  public final void getAppConfigs() throws IOException {
    propertyMap.put(SERV_PROP, new ResourceUtils().getServerProps());
  }


  
  public final void sendEmailNotification(String[] credn, int port, String[] recipient,  IEmailService emailServ) {
    emailServ.sendEmail(credn[0],credn[1],credn[2],credn[3], credn[4], credn[5], port ,recipient);
  }

  public static final void main( String[] args ) throws Throwable {
    final EtlExtractor ext = new EtlExtractor();
    ext.getAppConfigs();
    Properties ftpProp = new PropertiesUtils().loadPropertiesFile(FTP_PROPERTIES);
    final String ftpServer = ftpProp.getProperty("upload.server");
    final int ftpPort = Integer.parseInt(ftpProp.getProperty("upload.port"));
    final String ftpUserName = ftpProp.getProperty("upload.user");
    final String ftpPassword = ftpProp.getProperty("upload.pass");
    final String ftpDstDir = ftpProp.getProperty("upload.remoteDir");
    final SftpClient sftpClient = new SftpClient(ftpServer, ftpPort, ftpUserName, ftpPassword, ftpDstDir);
    final FileUtils fUtils = new FileUtils();
    final String dirName = ext.getDirectoryName();
    final String outDir = getOutputDir(dirName);
    final File outFile = new File(outDir);
    fUtils.deleteFile(outFile);
    fUtils.createDirIfNotExists(outFile);
    final Properties servProp = propertyMap.get(SERV_PROP);
    final String[] dbList = servProp.getProperty("db.sources").split(",");
    new FileUtils().deleteFile(new File(EXTRACTION_STAT_LOG));
    for (int i = 0; i < dbList.length; i++) {
      LOG.info("Extracting from {}", dbList[i]);
      if(args[0].equals("legacy")) {
        ext.writeData(new ExtractionContext(new ExtractCustomer()),dbList[i], outDir, "CUSTOMER_", QueryStringFetcher.getCustomerQuery(dbList[i]));
        ext.writeData(new ExtractionContext(new ExtractInvoice()),dbList[i], outDir, "INVOICE_", QueryStringFetcher.getInvoiceQuery(dbList[i]));
        ext.writeData(new ExtractionContext(new ExtractCreditNote()),dbList[i], outDir, "CREDIT_NOTE_", QueryStringFetcher.getCreditNoteQuery(dbList[i]));
        ext.writeData(new ExtractionContext(new ExtractPayment()),dbList[i], outDir, "PAYMENT_", QueryStringFetcher.getPaymentQuery(dbList[i])); 
      } else {
        ext.writeData(new CustomerFormatter(), dbList[i], outDir, "CUSTOMER_", "getCustomer");
        ext.writeData(new InvoiceFormatter(), dbList[i], outDir, "INVOICE_", "getCustomerInvoice");
        ext.writeData(new InvoiceFormatter(), dbList[i], outDir, "DEBIT_NOTE_", "getCustomerDebitNote");
        ext.writeData(new InvoiceFormatter(), dbList[i], outDir, "CREDIT_NOTE_", "getCustomerCreditNote");
        ext.writeData(new TransactionFormatter(), dbList[i], outDir, "TRANSACTION_", "getCustomerTransaction");
        ext.writeData(new TransactionFormatter(), dbList[i], outDir, "TRANSACTION_LUMP_", "getCustomerTransactionLumpSumPayment");
      }

    }
    final List<String> fileList = fUtils.getFileList(outFile);
    final String finalFtpDst = ftpDstDir + "/" + dirName;
    final boolean sendSftp = Boolean.parseBoolean(servProp.getProperty("ftp.send"));
    final boolean sendEmail = Boolean.parseBoolean(servProp.getProperty("email.send"));
    if (sendSftp) {
      try {
        sftpClient.connect();
        sftpClient.createDir(dirName);
        for (int i = 0; i < fileList.size(); i++) {
          LOG.info("SFTP transferring {}", fileList.get(i));
          final String fileName = fileList.get(i);
          sftpClient.uploadFile(outDir + "/" + fileName, fileName, finalFtpDst);
        }
      } finally {
        sftpClient.disconnect();
      }
    }
    fUtils.writeTextFile("../config/extraction_history.control", dirName + "\r", true);
    
    String user = servProp.getProperty("email.user");
    String userAuth = servProp.getProperty("email.userAuthentication");
    String password = servProp.getProperty("email.pass");
    String host = servProp.getProperty("email.host");
    String subject = servProp.getProperty("email.subject");
    String emailMsg = servProp.getProperty("email.msg");
    int port = Integer.parseInt(servProp.getProperty("email.port"));
    String[] recipient = servProp.getProperty("email.recipient").split("\\s*,\\s*");
    String[] emailCred = {user,password,host,subject,emailMsg,userAuth}; 
    if(sendEmail) ext.sendEmailNotification(emailCred, port, recipient, new EmailService());
  }

}
