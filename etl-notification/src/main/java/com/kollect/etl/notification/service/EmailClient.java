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

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.util.Preconditions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * This class is used to send out HTML emails using JavaMailSender and Thymeleaf
 * template engine There are various types of emails being sent out.
 *
 * @author hashim
 */
@Service
public class EmailClient implements IEmailClient {
  private JavaMailSender mailSender;
  private IEmailLogger emailLogger;
  private MimeMessagePreparator messagePrep;
  private final Logger logger = LoggerFactory.getLogger(EmailClient.class);

  @Autowired
  public EmailClient(JavaMailSender mailSender, IEmailLogger emailLogger) {
    this.mailSender = mailSender;
    this.emailLogger = emailLogger;
  }

    /**
     * This method does the sending of emails and sets a status depending on
     * the success or failure.
     * @param messagePreparator
     *                      needed to prepare the email message content.
     * @return
     *          returns a String status which is needed in the email logs.
     */
  @Override
  public String sendAndSetStatus(MimeMessagePreparator messagePreparator) {
    String mailStatus = "Failed";
    try {
      mailSender.send(messagePreparator);
      mailStatus = "Success";
      logger.info("Email has been sent successfully.");
    } catch (MailException e) {
      logger.error("An error occurred during email send." + e);
    }
    return mailStatus;
  }

  @Override
  public String getSendTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
    return sdf.format(new Date());
  }

    /**
     * Prepares the email message by settings the most needed variables.
     * @param email
     *              the email object
     * @return
     *          returns the whole message to be sent.
     */
  /*Let the client who's calling this method pass the email object*/
  private MimeMessagePreparator prepareEmail(Email email) {
    messagePrep = mimeMessage -> {
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(email.getFrom());
      helper.setTo(email.getTo().split(","));
      helper.setSubject(email.getSubject());
      helper.setText(email.getContent(), true);
      if (email.getAttachment() != null)helper.addAttachment(email.getAttachment().getOriginalFilename(), email.getAttachment());
      if (email.getFile() != null)helper.addAttachment(email.getFile().getName(), email.getFile());
    };
    return messagePrep;
  }

    /**
     * Executes the email sending. Starts by preparing the email using the email object
     * and messagePreparator, then sends email and returns the status.
     * @param email
     *              email object
     * @return
     *          returns the status.
     */
  @Override
  public String execute(Email email) {
    Preconditions.checkNotNull(email);
    messagePrep = prepareEmail(email);
      return sendAndSetStatus(messagePrep);
  }

  @Override
  public void sendAdhocEmail(String fromEmail, String recipient, String title, String body, MultipartFile attachment,
      File logFile, IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
      messageHelper.setFrom(fromEmail);
      messageHelper.setTo(recipient.split(","));
      messageHelper.setSubject(title);
      messageHelper.setText(emailContentBuilder.buildSimpleEmail(body, templateName), true);
      messageHelper.addAttachment(attachment.getOriginalFilename(), attachment);
      messageHelper.addAttachment(logFile.getName(), logFile);
    };
    String[] logArray = { recipient, title, logFile.getName(), getSendTime(),
        sendAndSetStatus(messagePreparator) };
    emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)), pathToEmailLog);
  }

  @Override
  public void sendAutoEmail(String fromEmail, String recipient, String title, String body, File logFile,
      IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
      messageHelper.setFrom(fromEmail);
      messageHelper.setTo(recipient.split(","));
      messageHelper.setSubject(title);
      messageHelper.setText(emailContentBuilder.buildSimpleEmail(body, templateName), true);
      messageHelper.addAttachment(logFile.getName(), logFile);
    };
    String[] logArray = { recipient, title, logFile.getName(), getSendTime(),
        sendAndSetStatus(messagePreparator) };
    emailLogger.persistLogToCsv(new ArrayList<>(Arrays.asList(logArray)), pathToEmailLog);
  }
}