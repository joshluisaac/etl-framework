import com.kollect.etl.notification.config.EmailConfig;
import com.kollect.etl.notification.config.IEmailConfig;
import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.entity.EmailConfigEntity;
import com.kollect.etl.notification.service.EmailClient;
import com.kollect.etl.notification.service.IEmailClient;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

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
  
//  public void assembleEmailConfigEntity(EmailConfigEntity configEntity) {
//    configEntity.setHost("168.8.1.151");
//    configEntity.setPort(25);
//    configEntity.setUsername("MBSBDOM\\powerkollect");
//    configEntity.setPassword("password@456");
//    configEntity.setSmtpAuth("true");
//    configEntity.setStartTls("true");
//    configEntity.setDebug("true");
//    
//  }
  
  public void assembleEmailConfigEntity(EmailConfigEntity configEntity) {
    configEntity.setHost("smtp.gmail.com");
    configEntity.setPort(587);
    configEntity.setUsername("MBSBDOM\\powerkollect");
    configEntity.setPassword("powerintegrator@k88");
    configEntity.setSmtpAuth("true");
    configEntity.setStartTls("true");
    configEntity.setDebug("true");
    
  }
  
  
  
  
  @Test
  public void execute_email_test() {
    Email mail = new Email("powerintegrator@gmail.com","joshua@kollect.my","Testing etl notification","This is the content2", null, null );
    emailClient.execute(mail);
  }

  
}
