package com.kollect.etl.config;

import com.kollect.etl.notification.config.EmailConfig;
import com.kollect.etl.notification.config.IEmailConfig;
import com.kollect.etl.util.JsonToHashMap;
import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {
    private JsonToHashMap jsonToHashMap = new JsonToHashMap(new JsonUtils(), new Utils());
    @Value("${app.generalEmailJson}")
    private String generalEmailJsonPath;

    @Bean
    public JavaMailSender javaMailService(){
        final IEmailConfig emailConfig = new EmailConfig();
        return emailConfig.setEmailSettings(
                jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("host"),
                Integer.valueOf(
                        jsonToHashMap.toHashMapFromJson
                                (generalEmailJsonPath).get("port")),
                jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("username"),
                jsonToHashMap.toHashMapFromJson(generalEmailJsonPath).get("password"),
                "true",
                "false",
                "false");
    }
}
