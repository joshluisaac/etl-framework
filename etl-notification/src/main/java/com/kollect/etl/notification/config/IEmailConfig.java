package com.kollect.etl.notification.config;

import org.springframework.mail.javamail.JavaMailSender;

import com.kollect.etl.notification.entity.EmailConfigEntity;

public interface IEmailConfig {
    JavaMailSender setEmailSettings(EmailConfigEntity config);
}
