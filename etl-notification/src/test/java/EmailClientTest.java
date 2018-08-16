import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

import com.kollect.etl.notification.config.EmailConfig;
import com.kollect.etl.notification.config.IEmailConfig;
import com.kollect.etl.notification.entity.Email;
import com.kollect.etl.notification.service.EmailClient;
import com.kollect.etl.notification.service.EmailLogger;
import com.kollect.etl.notification.service.IEmailClient;

public class EmailClientTest {
  IEmailConfig emailConfig;
  JavaMailSender mailSender;
  IEmailClient emailClient;
  
  
  public EmailClientTest() {
    emailConfig = new EmailConfig();
    mailSender = emailConfig.setEmailSettings("mail.automanage.biz", 587, "datareceived@kollect.my","K@L#eKT#12", "true", "false", "false");
    emailClient = new EmailClient(mailSender, new EmailLogger());
    
  }
  
  @Test
  public void execute_email_test() {
    Email mail = new Email("datareceived@kollect.my","joshua@kollect.my","Testing etl notification","This is the content2", null, null );
    emailClient.execute(mail);
  }

  
}
