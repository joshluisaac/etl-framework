package com.kollect.etl.controller;

import com.kollect.etl.service.InvoiceTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InvoiceTransactionController {
    @Autowired
    private InvoiceTransactionService Service;

    @PostMapping(value ="/updateInvoiceTransaction")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object selectLumSumPayment () {
        return Service.executeInvoiceTransactionService();
    }
}


