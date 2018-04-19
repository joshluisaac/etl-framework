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

    public String build(String intro, String message, List<Object> messageContent) {
        Context context = new Context();
        context.setVariable("title", "Hi there,");
        context.setVariable("intro", intro);
        context.setVariable("message", message);
        context.setVariable("messageContent", messageContent);
        context.setVariable("salutation", "Regards,");
        context.setVariable("signOff", "PowerETL Auto Update");
        context.setVariable("footer", null);
        return templateEngine.process("fragments/template_batch_mail_template", context);
    }
}
