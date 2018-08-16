package com.kollect.etl.config;

import com.kollect.etl.notification.config.EmailConfig;
import com.kollect.etl.notification.config.IEmailConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {
    @Bean
    public JavaMailSender javaMailService(){
        final IEmailConfig emailConfig = new EmailConfig();
        return emailConfig.setEmailSettings("mail.automanage.biz", 587, "datareceived@kollect.my",
                "K@L#eKT#12", "true", "false", "false");
    }
}
