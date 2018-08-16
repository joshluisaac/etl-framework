package com.kollect.etl.service.app;

import com.kollect.etl.component.EmailHelper;
import com.kollect.etl.config.MailConfig;
import com.kollect.etl.entity.ExtractionMetric;
import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.*;
import com.kollect.etl.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmailSenderService {
  private JsonUtils jsonUtils = new JsonUtils();
  private MailConfig mailConfig;
  private TemplateEngine templateEngine;
  
  private EmailLogService emailLogService;
  private EmailHelper emailHelper;

  @Value("${app.extractionEmailLogPath}")
  private String extractionEmailLogPath;
  private String fromEmail = "datareceived@kollect.my";
  @Value("${spring.mail.properties.batch.autoupdate.recipients}")
  String recipient;
  
  private final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

  @Autowired
  public EmailSenderService(
      TemplateEngine templateEngine, 
      MailConfig mailConfig, 
      EmailLogService emailLogService,
      EmailHelper emailHelper) {
    this.templateEngine = templateEngine;
    this.mailConfig = mailConfig;
    this.emailLogService = emailLogService;
    this.emailHelper = emailHelper;
  }

    private String getSendTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
        return sdf.format(new Date());
    }
  
  public void sendExtractionEmail(String manifestDirPath, String title) throws IOException {
    /*Get the JSON file*/
    List<String> prefixes = new ArrayList<>(Arrays.asList("stats_manifest_pelita_","stats_manifest_cco_","stats_manifest_yyc_","stats_manifest_pbk_"));
    List<String> diff = emailHelper.fetchNewManifestLog(new File(manifestDirPath), prefixes);
    
    if(diff.size() > 0) {
      String manifestLog = diff.get(0);
      String threadName = Thread.currentThread().getName();
      logger.info("Processing {} using {}", manifestLog,threadName);
      
      /*Deserialize the JSON file into Java objects*/
      ExtractionMetric[] extractionMetricArray = jsonUtils.fromJson(new FileReader(new File(manifestDirPath,manifestLog)), ExtractionMetric[].class);
      List<ExtractionMetric> metrics = new ArrayList<>(Arrays.asList(extractionMetricArray));

      final IEmailContentBuilder emailContentBuilder = new EmailContentBuilder(templateEngine);
      IEmailClient emailClient = new EmailClient(mailConfig.javaMailService(), new EmailLogger());
      final IEmailLogger emailLogger = new EmailLogger();
      /*Build email content*/
      String emailContent = emailContentBuilder.buildExtractLoadEmail("fragments/template_extract_load", metrics);
      /*Construct and assemble email object*/
      Email mail = new Email(fromEmail, recipient, title,
              emailContent, null, null);
      /*Send email*/
      String status = emailClient.execute(mail);
      /*write difference to cache*/
      emailHelper.persistToCache(manifestLog);
      /*persists to "emailLog/extractorEmailLog.csv"*/
      String[] logArray = {mail.getTo(), mail.getSubject(), getSendTime(), status};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)), extractionEmailLogPath);
    }
  }

  public void sendAfterBatch(String fromEmail, String recipient, String subject, List<Object> uatStats,
      List<Object> prodStats) {
    final IEmailContentBuilder emailContentBuilder = new EmailContentBuilder(templateEngine);
    IEmailClient emailClient = new EmailClient(mailConfig.javaMailService(), new EmailLogger());
    String emailContent = emailContentBuilder.buildBatchUpdateEmail
            ("fragments/template_batch_mail_template",
            uatStats, prodStats);
    Email mail = new Email(fromEmail, recipient, subject, emailContent, null, null);
      String status = emailClient.execute(mail);
      Map<Object, Object> emailLogMap = new HashMap<>();
      emailLogMap.put("recipient", recipient);
      emailLogMap.put("subject", subject);
      emailLogMap.put("status", status);
      emailLogService.logEmails(emailLogMap);
  }
}
