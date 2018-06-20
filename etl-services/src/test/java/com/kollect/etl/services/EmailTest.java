package com.kollect.etl.services;

import org.junit.Test;

import com.kollect.etl.service.email.IEmailService;
import com.kollect.etl.service.email.impl.EmailService;

public class EmailTest {

  public IEmailService service;

  public EmailTest() {
    service = new EmailService();
    
  }

  @Test
  public void sendEmail() {
    service.sendEmail("datareceived@kollect.my", "", "mail.automanage.biz", "Some subject", "Testing",
        "datareceived@kollect.my", 587, new String[] { "nwankwo.joshua@gmail.com" }, false);
  }

}
