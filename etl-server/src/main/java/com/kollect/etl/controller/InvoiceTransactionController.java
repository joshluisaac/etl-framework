package com.kollect.etl.controller;

import com.kollect.etl.service.AsyncBatchService;
import com.kollect.etl.service.InvoiceTransactionService;
import com.kollect.etl.service.ReadWriteServiceProvider;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InvoiceTransactionController {
    @Autowired
    private InvoiceTransactionService Service;
    @Autowired
    private ReadWriteServiceProvider rwProvider;
    @Autowired
    private AsyncBatchService asyncService;

    @PostMapping(value ="/updateInvoiceTransaction")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object selectLumSumPayment () {
        return Service.executeInvoiceTransactionService();
    }
    
    
    @PostMapping(value ="/loadInv")
    @ResponseBody
    public Object loadInv() {
      Iterator<Object> mQueryResults = rwProvider.executeQueryItr("getInvoiceTransaction", null);
      asyncService.execute(mQueryResults, "insertInvoiceTransaction", 10, 100);
        return 0;
    }
}


