package com.kollect.etl.service.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailClientService {
    private JavaMailSender mailSender;
    @Value("${spring.mail.properties.fromemail}")
    private String emailFrom;
    private MailContentBuilderService builder;
    private static final Logger LOG = LoggerFactory.getLogger(MailClientService.class);
    private EmailLogService emailLogService;

    @Autowired
    public MailClientService(JavaMailSender mailSender,
                             MailContentBuilderService builder,
                             EmailLogService emailLogService) {
        this.mailSender = mailSender;
        this.builder = builder;
        this.emailLogService =emailLogService;
    }

    public void sendAfterBatch(String recipient,String subject, List<Object> uatStats, List<Object> prodStats) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(subject);
            String content = builder.buildBatchEmail(uatStats, prodStats, "fragments/template_batch_mail_template");
            messageHelper.setText(content, true);
        };
        Map<Object, Object> arguments = new HashMap<>();
        arguments.put("subject",subject);
        arguments.put("recipient",recipient);
        try {
            mailSender.send(messagePreparator);
            arguments.put("status", "Success");
            this.emailLogService.logEmails(arguments);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            arguments.put("status","Failed");
            this.emailLogService.logEmails(arguments);
            LOG.error("An error occurred during email send." + e);
        }
    }
}
