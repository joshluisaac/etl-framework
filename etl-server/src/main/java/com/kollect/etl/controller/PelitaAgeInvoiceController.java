package com.kollect.etl.controller;

import com.kollect.etl.service.PelitaAgeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaAgeInvoiceController {

    private PelitaAgeInvoiceService service;

    @Autowired
    public PelitaAgeInvoiceController (PelitaAgeInvoiceService service){
        this.service = service;
    }


    @PostMapping(value = "/pelitageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.service.combinedAgeInvoiceService(batch_id);

    }

}


