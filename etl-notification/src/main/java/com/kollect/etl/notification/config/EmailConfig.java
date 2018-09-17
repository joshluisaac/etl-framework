package com.kollect.etl.notification.config;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.kollect.etl.notification.entity.EmailConfigEntity;

import java.util.Properties;

/**
 * This class is an implementation of the JavaMailSender
 * Used when sending email. It sets the necessary information needed such as
 * username
 * password
 * host
 * port
 * protocol
 * DefaultEncoding
 * It also sets a few extra email properties such as tls, debug and authentication.
 * @author hashim
 */
@Component
public class EmailConfig implements IEmailConfig {
    public JavaMailSender setEmailSettings(EmailConfigEntity configEntity) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(configEntity.getHost());
        javaMailSender.setPort(configEntity.getPort());
        javaMailSender.setUsername(configEntity.getUsername());
        javaMailSender.setPassword(configEntity.getPassword());
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setJavaMailProperties(getMailProperties(configEntity));
        return javaMailSender;
    }
    private Properties getMailProperties(EmailConfigEntity configEntity) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", configEntity.getSmtpAuth());
        properties.setProperty("mail.smtp.starttls.enable", configEntity.getStartTls());
        properties.setProperty("mail.smtp.ssl.trust", "*");
        properties.setProperty("mail.debug", configEntity.getDebug());
        return properties;
    }
}
