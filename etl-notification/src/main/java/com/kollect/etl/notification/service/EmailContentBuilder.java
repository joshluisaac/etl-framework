package com.kollect.etl.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * This class is used to prepare the email template using thymeleaf.
 * The different types of emails can be used by different modules
 *
 * @author hashim
 */
@Service
public class EmailContentBuilder implements IEmailContentBuilder{
    private TemplateEngine templateEngine;

    @Autowired
    public EmailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildSimpleEmail(String body, String templateName) {
        Context context = new Context();
        context.setVariable("body", body);
        return templateEngine.process(
                templateName, context);
    }

    @Override
    public String buildBatchUpdateEmail(String templateName,
                                        List<Object> uatStats, List<Object> prodStats) {
        Context context = new Context();
        context.setVariable("uatStats", uatStats);
        context.setVariable("prodStats", prodStats);
        return templateEngine.process(
                templateName, context);
    }

    public <T> String buildExtractLoadEmail(String templateName, List<T> stats){
        Context context = new Context();
        context.setVariable("stats", stats);
        return templateEngine.process(
                templateName, context);
    }
}
