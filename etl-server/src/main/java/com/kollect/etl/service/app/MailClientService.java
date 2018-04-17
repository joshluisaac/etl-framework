package com.kollect.etl.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailClientService {
    private JavaMailSender mailSender;
    private String emailFrom = "datareceived@kollect.my";
    private MailContentBuilderService builder;

    @Autowired
    public MailClientService(JavaMailSender mailSender, MailContentBuilderService builder) {
        this.mailSender = mailSender;
        this.builder = builder;
    }

    public void sendAfterBatch(String recipient, String title, String message, String salutation, String footer) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject("PowerETL - Sample HTML Email");
            String content = builder.build(title, message, salutation,footer);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            /*runtime exception; compiler will not force you to handle it*/
        }
    }
}
