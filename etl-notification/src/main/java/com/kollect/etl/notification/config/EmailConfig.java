package com.kollect.etl.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

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
    @Bean
    public JavaMailSender setEmailSettings(String host, Integer port, String username,
                                           String password, String smtpAuth, String startTls, String debug) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setJavaMailProperties(getMailProperties(smtpAuth, startTls, debug));
        return javaMailSender;
    }
    private Properties getMailProperties(String smtpAuth, String startTls, String debug) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", smtpAuth);
        properties.setProperty("mail.smtp.starttls.enable", startTls);
        properties.setProperty("mail.debug", debug);
        return properties;
    }
}
