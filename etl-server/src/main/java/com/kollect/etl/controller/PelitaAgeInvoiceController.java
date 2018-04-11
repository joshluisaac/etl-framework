package com.kollect.etl.controller;

import com.kollect.etl.service.PelitaAgeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaAgeInvoiceController {
    @Autowired
    private PelitaAgeInvoiceService pelitaAgeInvoiceService;

    @PostMapping(value = "/pelitaageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object pelitaAgeInvoice (@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id){
        return this.pelitaAgeInvoiceService.combinedPelitaAgeInvoiceService(tenant_id, batch_id);
    }
}
