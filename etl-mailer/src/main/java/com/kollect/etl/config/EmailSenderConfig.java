package main.java.service;

import com.kollect.etl.notification.config.EmailConfig;
import com.kollect.etl.notification.config.IEmailConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderConfig {
    private IEmailConfig emailConfig = new EmailConfig();

    @Primary
    @Bean
    public JavaMailSender javaMailService() {
        return emailConfig.setEmailSettings("mail.automanage.biz", 587, "datareceived@kollect.my",
                "K@L#eKT#12", "true", "false", "false");
    }
}
