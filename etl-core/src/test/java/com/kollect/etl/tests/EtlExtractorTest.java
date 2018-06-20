package com.kollect.etl.tests;

import org.junit.Test;

import com.kollect.etl.dataextractor.EtlExtractor;
import com.kollect.etl.service.email.IEmailService;
import com.kollect.etl.service.email.impl.EmailService;

public class EtlExtractorTest {
  
  public IEmailService service;
  public EtlExtractor extractor;
  
  
  public EtlExtractorTest() {
    service = new EmailService();
    extractor = new EtlExtractor();
  }
  
  
  @Test
  public void sendEmail() {
    String[] emailCred = {"datareceived@kollect.my", "", "mail.automanage.biz", "Some subject", "Testing","datareceived@kollect.my"};
    extractor.sendEmailNotification(emailCred, 587, new String[] {"nwankwo.joshua@gmail.com","joshua@kollect.my"}, service, false);
    
  }

}
