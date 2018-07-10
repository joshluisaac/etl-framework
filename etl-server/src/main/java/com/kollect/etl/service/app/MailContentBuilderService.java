package com.kollect.etl.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class MailContentBuilderService {
    private TemplateEngine templateEngine;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String intro ="This is an Automated Notification for KollectValley Batch Statistics for " + sdf.format(today) + ".";

    @Autowired
    public MailContentBuilderService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildBatchEmail(List<Object> uatStats, List<Object> prodStats) {
        Context context = new Context();
        context.setVariable("title", "Hi there,");
        context.setVariable("intro", intro);
        context.setVariable("message", "Batch Summary & Statistics:");
        context.setVariable("uatStats", uatStats);
        context.setVariable("prodStats", prodStats);
        context.setVariable("salutation", "Regards,");
        context.setVariable("signOff", "PowerETL Auto Update");
        context.setVariable("footer", null);
        return templateEngine.process("fragments/template_batch_mail_template", context);
    }
}
