package com.kollect.etl.notification.config;

import org.springframework.mail.javamail.JavaMailSender;

public interface IEmailConfig {
    JavaMailSender setEmailSettings(String host, Integer port, String username,
                                    String password, String smtpAuth, String startTls, String debug);
}
