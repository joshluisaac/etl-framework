package com.kollect.etl.service.email.impl;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.service.email.IEmailService;


public abstract class AbstractEmailService  implements IEmailService {
  
  private static final Logger LOG = LoggerFactory.getLogger(AbstractEmailService.class);
  
  @Override
  public boolean sendEmail(String user, String password, String host, String subject, String content, String userAuth,
      int port, String[] recipient, boolean enableSsl) {
    HtmlEmail email = new HtmlEmail();
    email.setHostName(host);
    email.setSmtpPort(port);
    email.setSSLOnConnect(enableSsl);
    email.setAuthentication(userAuth, password);
    try {
      email.setFrom(user);
      email.addTo(recipient);
      email.setSubject(MessageFormat.format(subject, new Object[]{new Date(),new Date()} ));
      email.setHtmlMsg(content);
      email.send();
      LOG.info("Email Notification Sent!");
      return true;
    } catch (EmailException e) {
      LOG.error(e.getMessage());
      e.printStackTrace();
    }
    return false;
  }
  
  @Override
  public boolean sendEmail(String user, String password, String host, String subject, String content, String userAuth,
      int port, String[] recipient) {
    
    return sendEmail(user, password, host, subject, content, userAuth, port, recipient, true);
  }
  
  
  
  

}
