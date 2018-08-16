package com.kollect.etl.notification.service;

import com.kollect.etl.notification.entity.Email;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IEmailClient {
  String execute(Email mail);

  void sendAdhocEmail(String fromEmail, String recipient, String title, String body, MultipartFile attachment,
      File logFile, IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog);

  void sendAutoEmail(String fromEmail, String recipient, String title, String body, File logFile,
      IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog);

  String sendAndSetStatus(MimeMessagePreparator messagePreparator);

  String getSendTime();
}
