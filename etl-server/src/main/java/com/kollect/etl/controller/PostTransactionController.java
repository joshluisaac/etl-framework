package com.kollect.etl.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kollect.etl.service.PostTransactionService;

@Controller
public class PostTransactionController {
  
  @Autowired
  private PostTransactionService service;
  
  
  @PostMapping(value ="/updatePostTrx")
  @ResponseBody
  public Object updatePostTransaction() {
    
    String[] postJobs = {"updateIsDepositTransaction","updateInvoiceTransactionType","updatePaymentTransactionType",
        "updateInvoiceStatusId","updateInvoiceInAging"};
    for (String string : postJobs) {
      int updatedCount = service.updatePostTransaction(string,null);
    }
      
      return 0;
  }

}
