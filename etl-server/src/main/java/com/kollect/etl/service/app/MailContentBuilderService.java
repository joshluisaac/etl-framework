package com.kollect.etl.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class MailContentBuilderService {
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilderService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildBatchEmail(String intro, String message, List<Object> uatStats, List<Object> prodStats) {
        Context context = new Context();
        context.setVariable("title", "Hi there,");
        context.setVariable("intro", intro);
        context.setVariable("message", message);
        context.setVariable("uatStats", uatStats);
        context.setVariable("prodStats", prodStats);
        context.setVariable("salutation", "Regards,");
        context.setVariable("signOff", "PowerETL Auto Update");
        context.setVariable("footer", null);
        return templateEngine.process("fragments/template_batch_mail_template", context);
    }
}
