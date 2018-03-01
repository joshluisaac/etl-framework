package com.kollect.etl.service.email.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.service.email.IEmailService;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.StringUtils;

public class EmailService implements IEmailService {
  
  private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
  
  public boolean sendEmail(String user, String password, String host, String subject, String content, String userAuth, int port, String[] recipient) {
    HtmlEmail email = new HtmlEmail();
    email.setHostName(host);
    email.setSmtpPort(port);
    email.setSSLOnConnect(true);
    email.setAuthentication(userAuth, password);
    try {
      email.setFrom(user);
      email.addTo(recipient);
      email.setSubject(MessageFormat.format(subject, new Object[]{new Date(),new Date()} ));
      email.setHtmlMsg(buildHtmlTemplate(content));
      email.send();
      LOG.info("Email Notification Sent!");
    } catch (EmailException e) {
      LOG.error(e.getMessage());
      e.printStackTrace();
    }
    return false;
  }
  
  
  private List<String> getExtractionStat(){
    try {
      return new FileUtils().readFile("../logs/extractionStats.log");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public String buildHtmlTemplate(String content) {
    String message = MessageFormat.format(content, new Object[]{new Date(),new Date()});
    StringBuilder template = new StringBuilder();
    template.append("<!DOCTYPE html>");
    template.append("<html lang=\"en\">");
    template.append("<head>");
    //template.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\"/>");
    template.append("</head>");
    template.append("<body>");
    template.append("<h3>" + message + "</h3>");
    template.append("<body>");
    template.append("<table align=\"left\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"1000\">");
    template.append("<tr>"
        + "<th>DB Name</th>"
        + "<th>File Name</th><th>Query & formatting (ms)</th>"
        + "<th> Wrting to disk (ms)</th>"
        + "<th>Data Size (Bytes)</th><th>Number of Records</th>"
        + "</tr>");

    List<String> l = getExtractionStat();
    StringUtils su = new StringUtils();
    for (int i = 0; i < l.size(); i++) {
      Object[] row = su.splitString(l.get(i), "\\|");
      String rowTag = MessageFormat.format("<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><tr>", row);
      template.append(rowTag);
    }
    template.append("</body></html>");
    return template.toString();
  }
 
  
  public static URL getRootUrl(){
    URL url = null;
    try {
      url =  new URL("http://localhost:8088");
      
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }

}
