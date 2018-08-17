import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

import com.kollect.etl.notification.config.EmailConfig;
import com.kollect.etl.notification.config.IEmailConfig;
import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.notification.service.EmailClient;
import com.kollect.etl.notification.service.IEmailClient;

public class EmailClientTest {
  IEmailConfig emailConfig;
  JavaMailSender mailSender;
  IEmailClient emailClient;
  
  
  public EmailClientTest() {
    emailConfig = new EmailConfig();
    EmailConfigEntity configEntity = new EmailConfigEntity();
    assembleEmailConfigEntity(configEntity);
    mailSender = emailConfig.setEmailSettings(configEntity);
    emailClient = new EmailClient(mailSender);
    
  }
  
  public void assembleEmailConfigEntity(EmailConfigEntity configEntity) {
    configEntity.setHost("mail.automanage.biz");
    configEntity.setPort(587);
    configEntity.setUsername("datareceived@kollect.my");
    configEntity.setPassword("K@L#eKT#12");
    configEntity.setSmtpAuth("true");
    configEntity.setStartTls("false");
    configEntity.setDebug("false");
    
  }
  
  @Test
  public void execute_email_test() {
    Email mail = new Email("datareceived@kollect.my","joshua@kollect.my","Testing etl notification","This is the content2", null, null );
    emailClient.execute(mail);
  }

  
}
