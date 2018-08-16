package com.kollect.etl.notification.service;

import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


/**
 * This class is used to send out HTML emails using JavaMailSender and Thymeleaf
 * template engine There are various types of emails being sent out.
 *
 * @author hashim
 */
@Service
public class EmailClient implements IEmailClient {
  private JavaMailSender mailSender;
  private MimeMessagePreparator messagePrep;
  private final Logger logger = LoggerFactory.getLogger(EmailClient.class);

  @Autowired
  public EmailClient(JavaMailSender mailSender) {
    this.mailSender = mailSender;
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
}