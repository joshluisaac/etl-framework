package com.kollect.etl.notification.service;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IEmailClient {
    void sendAdhocEmail(String fromEmail, String recipient,
                        String title, String body,
                        MultipartFile attachment, File logFile, IEmailContentBuilder emailContentBuilder,
                        String templateName, String pathToEmailLog);

    void sendAutoEmail(String fromEmail, String recipient,
                       String title, String body,
                       File logFile, IEmailContentBuilder emailContentBuilder,
                       String templateName, String pathToEmailLog);

    Map<String, String> sendBatchEmailUpdate(String fromEmail, String recipient,
                                             String title,
                                             IEmailContentBuilder emailContentBuilder,
                                             String templateName,
                                             List<Object> uatStats, List<Object> prodStats);

    String executeSendAndSetStatus(MimeMessagePreparator messagePreparator);

    String getSendTime();
}
