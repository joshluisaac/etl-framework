package com.kollect.etl.notification.service;

import com.kollect.etl.notification.entity.Email;
import org.springframework.mail.javamail.MimeMessagePreparator;

public interface IEmailClient {
  String execute(Email mail);

  String sendAndSetStatus(MimeMessagePreparator messagePreparator);
}
