package com.kollect.etl.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.kollect.etl.component.Utils;
import com.kollect.etl.notification.config.IEmailConfig;
import com.kollect.etl.notification.entity.EmailConfigEntity;


@Component
public class EmailConfigAssembler {
  
  @Value("${app.generalEmailJson}")
  private String generalEmailJsonPath;
  
  private Utils utils;
  private IEmailConfig config;
  
  private final Logger logger = LoggerFactory.getLogger(EmailConfigAssembler.class);
  
  @Autowired
  EmailConfigAssembler(Utils utils, IEmailConfig config){
    this.utils = utils;
    this.config = config;
  }
  
  @Bean
  public JavaMailSender javaMailService(EmailConfigEntity configEntity) {
    JavaMailSender mailer = config.setEmailSettings(configEntity);
    logger.info("Initialized and constructed JavaMailSender Bean");
    return mailer;
  }
  
  //new TypeToken<List<EmailConfigEntity>>(){}.getType()
  
  @Bean
  public EmailConfigEntity deSerializeConfig() {
    EmailConfigEntity configEntity = null;
    try (Reader reader = new FileReader(new File(generalEmailJsonPath))) {
      configEntity = utils.readJson(reader, EmailConfigEntity.class);
      logger.info("Initialized and constructed EmailConfigEntity Bean");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return configEntity;
  }
  

}
