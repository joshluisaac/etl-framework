package com.kollect.etl.notification.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class Email {
    private String from, to, subject, content;
    private MultipartFile attachment;
    private File file;

    public Email(String from, String to, String subject,
                 String content, MultipartFile attachment,
                 File file) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
        this.file=file;
    }

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
      return "Email [from=" + from + ", to=" + to + ", subject=" + subject + ", content=" + content + ", attachment="
          + attachment + ", file=" + file + "]";
    }
    
    
    
    
}
