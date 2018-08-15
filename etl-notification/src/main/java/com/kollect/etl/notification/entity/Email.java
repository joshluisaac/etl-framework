package com.kollect.etl.notification.entity;

import org.springframework.web.multipart.MultipartFile;

public class Email {
  
  String from, to, subject,content;
  MultipartFile attachment;
  
  
  
  
  
  public String getFrom() {
    return from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getTo() {
    return to;
  }
  public void setTo(String to) {
    this.to = to;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public MultipartFile getAttachment() {
    return attachment;
  }
  public void setAttachment(MultipartFile attachment) {
    this.attachment = attachment;
  }
  
  
  

}
