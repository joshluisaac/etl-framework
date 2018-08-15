package com.kollect.etl.service.app;

import com.kollect.etl.config.MailConfig;
import com.kollect.etl.notification.service.*;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmailSenderService {
    private JsonUtils jsonUtils = new JsonUtils();
    private MailConfig mailConfig;
    private TemplateEngine templateEngine;
    private Utils utils = new Utils();

    @Autowired
    public EmailSenderService(TemplateEngine templateEngine,
                              MailConfig mailConfig){
        this.templateEngine=templateEngine;
        this.mailConfig=mailConfig;
    }

    private String getDailyExtractStatsFile(String tenant){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        return "stats_manifest_"+tenant+"_"+sdf.format(new Date())+".json";
    }

    //still working on parsing this list into the email as a table.
    @SuppressWarnings("unchecked")
    private List<String> getDailyExtractStats(String tenant){
        return jsonUtils.fromJson(utils.readFile(new File(getDailyExtractStatsFile(tenant))).get(0), List.class);
    }

    public void sendExtractionEmail(String title, String tenant){
        final IEmailContentBuilder emailContentBuilder = new EmailContentBuilder(templateEngine);
        IEmailClient emailClient = new EmailClient( mailConfig.javaMailService(), new EmailLogger());
        emailClient.sendExtractLoadEmail("datareceived@kollect.my",
                "hashim@kollect.my", title,
                getDailyExtractStats(tenant), emailContentBuilder,
                "fragments/template_extract_load",
                "emailLog/extractorEmailLog.csv");

    }
}
