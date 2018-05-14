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

import java.util.List;

@Service
public class MailClientService {
    private JavaMailSender mailSender;
    @Value("${spring.mail.properties.fromemail}")
    private String emailFrom;
    private MailContentBuilderService builder;
    private static final Logger LOG = LoggerFactory.getLogger(MailClientService.class);

    @Autowired
    public MailClientService(JavaMailSender mailSender, MailContentBuilderService builder) {
        this.mailSender = mailSender;
        this.builder = builder;
    }

    public void sendAfterBatch(String recipient,String subject, String intro,
                               String message, List<Object> uatStats, List<Object> prodStats) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(subject);
            String content = builder.buildBatchEmail(intro, message, uatStats, prodStats);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            LOG.error("An error occurred during email send." + e);
        }
    }
}
