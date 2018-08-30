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
    service.sendEmail("powerkollect@mbsbbank.com", "password@456", "mail.mbsbbank.com", "Some subject", "Testing",
        "MBSBDOM\\powerkollect", 25, new String[] { "joshua@kollect.my","hashim@kollect.my" }, false);
  }

}
