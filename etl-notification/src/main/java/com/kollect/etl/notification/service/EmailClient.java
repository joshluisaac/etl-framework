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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is used to send out HTML emails using JavaMailSender and Thymeleaf template engine
 * There are various types of emails being sent out.
 *
 * @author hashim
 */
@Service
public class EmailClient implements IEmailClient{
    private JavaMailSender mailSender;
    private IEmailLogger emailLogger;
    private static final Logger LOG = LoggerFactory.getLogger(EmailClient.class);

    @Autowired
    public EmailClient(JavaMailSender mailSender,
                       IEmailLogger emailLogger) {
        this.mailSender = mailSender;
        this.emailLogger = emailLogger;
    }

    @Override
    public String executeSendAndSetStatus(MimeMessagePreparator messagePreparator){
        Map<String, String> logMap = new HashMap<>();
        try {
            mailSender.send(messagePreparator);
            logMap.put("status", "Success");
            LOG.info("Email has been sent successfully.");
        } catch (MailException e) {
            logMap.put("status", "Failed");
            LOG.error("An error occurred during email send." + e);
        }
        return logMap.get("status");
    }

    @Override
    public String getSendTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
        return sdf.format(new Date());
    }

    @Override
    public void sendAdhocEmail(String fromEmail, String recipient,
                               String title, String body,
                               MultipartFile attachment, File logFile, IEmailContentBuilder emailContentBuilder,
                               String templateName, String pathToEmailLog) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            messageHelper.setText(emailContentBuilder.buildSimpleEmail(body, templateName), true);
            messageHelper.addAttachment(attachment.getOriginalFilename(), attachment);
            messageHelper.addAttachment(logFile.getName(), logFile);
        };
        String[] logArray= {recipient, title, logFile.getName(), getSendTime(), executeSendAndSetStatus(messagePreparator)};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)), pathToEmailLog);
    }

    @Override
    public void sendAutoEmail(String fromEmail, String recipient,
                              String title, String body,
                              File logFile, IEmailContentBuilder emailContentBuilder,
                              String templateName, String pathToEmailLog) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            messageHelper.setText(emailContentBuilder.buildSimpleEmail(body, templateName), true);
            messageHelper.addAttachment(logFile.getName(), logFile);
        };
        String[] logArray= {recipient, title, logFile.getName(), getSendTime(), executeSendAndSetStatus(messagePreparator)};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)), pathToEmailLog);
    }

    @Override
    public void sendExtractLoadEmail(String fromEmail, String recipient,
                                  String title, List<String> stats,
                                  IEmailContentBuilder emailContentBuilder,
                                  String templateName, String pathToEmailLog){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(recipient.split(","));
            messageHelper.setSubject(title);
            messageHelper.setText(emailContentBuilder.buildExtractLoadEmail(templateName, stats), true);
        };
        String[] logArray= {recipient, title, getSendTime(), executeSendAndSetStatus(messagePreparator)};
        emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)), pathToEmailLog);
    }

    //this method hasn't been implemented yet to PowerETL.
    @Override
    public Map<String, String> sendBatchEmailUpdate(String fromEmail, String recipient,
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
        return this.emailLogger.saveEmailLog(recipient, title, executeSendAndSetStatus(messagePreparator));
    }
}