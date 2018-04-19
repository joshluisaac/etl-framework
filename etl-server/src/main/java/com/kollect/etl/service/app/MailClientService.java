package com.kollect.etl.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void sendAfterBatch(String recipient, String intro,
                               String message, List<Object> messageContent) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject("PowerETL - Batch Status");
            String content = builder.build(intro, message, messageContent);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            /*runtime exception; compiler will not force you to handle it*/
        }
    }
}
