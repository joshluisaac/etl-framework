package com.kollect.etl.controller.pbk;

import com.kollect.etl.service.pbk.PbkAgeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class PbkAgeInvoiceController {
    private PbkAgeInvoiceService pbkAgeInvoiceService;

    @Autowired
    public PbkAgeInvoiceController(PbkAgeInvoiceService pbkAgeInvoiceService){
        this.pbkAgeInvoiceService = pbkAgeInvoiceService;
    }

    /**
     * POST method to get the tenant_id from user to run the age invoice batch
     * @param batch_id
     *                  used to run the batchhistoryservice for this batch
     * @return
     *          returns the number of rows updated by the batch
     */

    @PostMapping(value = "/ageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.pbkAgeInvoiceService.combinedAgeInvoiceService(batch_id);
        }

}
