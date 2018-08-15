package com.kollect.etl.service.app;

import com.kollect.etl.config.MailConfig;
import com.kollect.etl.entity.ExtractionMetric;
import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.*;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailSenderService {
  private JsonUtils jsonUtils = new JsonUtils();
  private MailConfig mailConfig;
  private TemplateEngine templateEngine;
  private Utils utils = new Utils();
  private EmailLogService emailLogService;

  @Autowired
  public EmailSenderService(TemplateEngine templateEngine, MailConfig mailConfig, EmailLogService emailLogService) {
    this.templateEngine = templateEngine;
    this.mailConfig = mailConfig;
    this.emailLogService = emailLogService;
  }

  private String getDailyExtractStatsFile(String tenant) {
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    return "stats_manifest_" + tenant + "_" + sdf.format(new Date()) + ".json";
  }

  private List<ExtractionMetric> getDailyExtractStats(String tenant) {
    ExtractionMetric[] extractionMetricArray = jsonUtils
        .fromJson(utils.readFile(new File(getDailyExtractStatsFile(tenant))).get(0), ExtractionMetric[].class);
    List<ExtractionMetric> extractionMetricList = new ArrayList<>(Arrays.asList(extractionMetricArray));
    return extractionMetricList;
  }

  public void sendExtractionEmail(String title, String tenant) {
    final IEmailContentBuilder emailContentBuilder = new EmailContentBuilder(templateEngine);
    IEmailClient emailClient = new EmailClient(mailConfig.javaMailService(), new EmailLogger());
    List<ExtractionMetric> metrics = getDailyExtractStats(tenant);
    String emailContent = emailContentBuilder.buildExtractLoadEmail("fragments/template_extract_load", metrics);
    /* set email object */
    Email mail = new Email("datareceived@kollect.my", "joshua@kollect.my", title, emailContent, null);
    emailClient.execute(mail);

//        emailClient.sendExtractLoadEmail("datareceived@kollect.my",
//                "hashim@kollect.my", title,
//                getDailyExtractStats(tenant), emailContentBuilder,
//                "fragments/template_extract_load",
//                "emailLog/extractorEmailLog.csv");

  }

  public void sendAfterBatch(String fromEmail, String recipient, String subject, List<Object> uatStats,
      List<Object> prodStats) {
    final IEmailContentBuilder emailContentBuilder = new EmailContentBuilder(templateEngine);
    IEmailClient emailClient = new EmailClient(mailConfig.javaMailService(), new EmailLogger());
    Map<Object, Object> emailLogMap = new HashMap<>(emailClient.sendBatchEmailUpdate(fromEmail, recipient, subject,
        emailContentBuilder, "fragments/template_batch_mail_template", uatStats, prodStats));
    emailLogService.logEmails(emailLogMap);
  }
}
