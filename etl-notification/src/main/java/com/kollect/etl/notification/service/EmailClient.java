package com.kollect.etl.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to send out HTML emails using JavaMailSender and Thymeleaf template engine
 * There are various types of emails being sent out.
 *
 * @author hashim
 */
@Service
public class EmailClient implements IEmailClient{
    private JavaMailSender mailSender;
    private static final Logger LOG = LoggerFactory.getLogger(EmailClient.class);

    @Autowired
    public EmailClient(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAdhocEmail(String fromEmail, String recipient,
                               String title, String body,
                               MultipartFile attachment, File logFile, IEmailContentBuilder emailContentBuilder,
                               String templateName) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            messageHelper.setText(emailContentBuilder.buildSimpleEmail(body, templateName), true);
            messageHelper.addAttachment(attachment.getOriginalFilename(), attachment);
            messageHelper.addAttachment(logFile.getName(), logFile);
        };
        try {
            mailSender.send(messagePreparator);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            LOG.error("An error occurred during email send." + e);
        }
    }

    @Override
    public void sendAutoEmail(String fromEmail, String recipient,
                              String title, String body,
                              File logFile, IEmailContentBuilder emailContentBuilder,
                              String templateName) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            messageHelper.setText(emailContentBuilder.buildSimpleEmail(body, templateName), true);
            messageHelper.addAttachment(logFile.getName(), logFile);
        };
        try {
            mailSender.send(messagePreparator);
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            LOG.error("An error occurred during email send." + e);
        }
    }

    @Override
    public Map<Object, Object> sendBatchEmailUpdate(String fromEmail, String recipient,
                                     String title, IEmailContentBuilder emailContentBuilder,
                                     String templateName,
                                     List<Object> uatStats, List<Object> prodStats){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            String content = emailContentBuilder.buildBatchUpdateEmail(templateName ,uatStats, prodStats);
            messageHelper.setText(content, true);
        };
        Map<Object, Object> arguments = new HashMap<>();
        arguments.put("subject",title);
        arguments.put("recipient",recipient);
        try {
            mailSender.send(messagePreparator);
            arguments.put("status", "Success");
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            arguments.put("status", "Failed");
            LOG.error("An error occurred during email send." + e);
        }
        return arguments;
    }
}