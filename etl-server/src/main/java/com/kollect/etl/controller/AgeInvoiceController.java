package com.kollect.etl.controller;

import com.kollect.etl.service.AgeInvoiceService;
import com.kollect.etl.service.PelitaAgeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class AgeInvoiceController {
    @Autowired
    private AgeInvoiceService ageInvoiceService;


    @Autowired
    private PelitaAgeInvoiceService pelitaAgeInvoiceService;
    /**
     * POST method to get the tenant_id from user to run the age invoice batch
     * @param tenant_id
     *                  necessary to differentiate different tenants when running a batch
     * @return
     *          returns the number of rows updated by the batch
     */

    @PostMapping(value = "/ageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id){
        return this.ageInvoiceService.combinedAgeInvoiceService(tenant_id, batch_id);
        }


    @PostMapping(value = "/pelitaageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object pelitaAgeInvoice (@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id){
        return this.pelitaAgeInvoiceService.combinedPelitaAgeInvoiceService(tenant_id, batch_id);
    }

}
