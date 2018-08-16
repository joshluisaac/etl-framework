package com.kollect.etl.notification.service;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.multipart.MultipartFile;

import com.kollect.etl.notification.entity.Email;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IEmailClient {
  String execute(Email mail);

  void sendAdhocEmail(String fromEmail, String recipient, String title, String body, MultipartFile attachment,
      File logFile, IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog);

  void sendAutoEmail(String fromEmail, String recipient, String title, String body, File logFile,
      IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog);

  void sendExtractLoadEmail(String fromEmail, String recipient, String title, List<String> stats,
      IEmailContentBuilder emailContentBuilder, String templateName, String pathToEmailLog);

  Map<String, String> sendBatchEmailUpdate(String fromEmail, String recipient, String title,
      IEmailContentBuilder emailContentBuilder, String templateName, List<Object> uatStats, List<Object> prodStats);

  String sendAndSetStatus(MimeMessagePreparator messagePreparator);

  String getSendTime();
}
