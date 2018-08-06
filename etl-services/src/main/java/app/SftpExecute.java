package app;

import java.io.File;
import java.util.List;
import java.util.Properties;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.PropertiesUtils;
import com.powerapps.services.Sftp;
import com.powerapps.services.SftpService;

public class SftpExecute {
  private static final String FTP_PROPERTIES = "../config/ftp.properties";

  List<String> getListOfFiles(File f) {
    return new FileUtils().getFileList(f);
  }

  public static void main(String[] args) throws Throwable {
    SftpExecute sftpExe = new SftpExecute();
    Properties ftpProp = new PropertiesUtils().loadPropertiesFile(FTP_PROPERTIES);
    
    final String ftpServer = ftpProp.getProperty("upload.server");
    final int ftpPort = Integer.parseInt(ftpProp.getProperty("upload.port"));
    final String ftpUserName = ftpProp.getProperty("upload.user");
    final String ftpPassword = ftpProp.getProperty("upload.pass");
    final String ftpDstDir = ftpProp.getProperty("upload.remoteDir");
    final Sftp sftp  = new Sftp(new SftpService(), ftpServer, ftpPort, ftpUserName, ftpPassword, ftpDstDir);
    final String dirName = "diffOut";
    final String srcDir = "../diffOut";
    final File srcFile = new File(srcDir);
    final List<String> srcFileList = sftpExe.getListOfFiles(srcFile);
    final String destDir = sftp.getDstDir() + "/" + dirName;
    sftp.execute(srcFileList, srcDir, destDir);
  }

}
