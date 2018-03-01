package com.kollect.etl.service.email.impl;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.service.email.IEmailService;


public class AbstractEmailService  implements IEmailService {
  
  private static final Logger LOG = LoggerFactory.getLogger(AbstractEmailService.class);
  
  public boolean sendEmail(String user, String password, String host, String subject, String htmlContent, String userAuth, int port, String[] recipient) {
    HtmlEmail email = new HtmlEmail();
    email.setHostName(host);
    email.setSmtpPort(port);
    email.setSSLOnConnect(true);
    email.setAuthentication(userAuth, password);
    try {
      email.setFrom(user);
      email.addTo(recipient);
      email.setSubject(MessageFormat.format(subject, new Object[]{new Date(),new Date()} ));
      //email.setHtmlMsg(buildHtmlTemplate(content));
      email.setHtmlMsg(htmlContent);
      email.send();
      LOG.info("Email Notification Sent!");
    } catch (EmailException e) {
      LOG.error(e.getMessage());
      e.printStackTrace();
    }
    return false;
  }

}
