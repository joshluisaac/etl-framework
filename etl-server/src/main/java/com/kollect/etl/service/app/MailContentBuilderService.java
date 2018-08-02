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

    public String buildBatchEmail(List<Object> uatStats, List<Object> prodStats, String templateName) {
        Context context = new Context();
        context.setVariable("uatStats", uatStats);
        context.setVariable("prodStats", prodStats);
        return templateEngine.process(templateName, context);
    }
}
