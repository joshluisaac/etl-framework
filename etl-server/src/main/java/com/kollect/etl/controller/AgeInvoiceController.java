package com.kollect.etl.controller;

import com.kollect.etl.service.AgeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AgeInvoiceController {
    @Autowired
    private AgeInvoiceService ageInvoiceService;
    private boolean lock = false;
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
    public Object ageInvoice (@RequestParam(required = false) Integer tenant_id){
        int numberOfRows = -1;
        if (!lock) {
            lock = true;
            List<Object> ageInvoiceList = this.ageInvoiceService.getAgeInvoiceById(tenant_id);
            int numberOfRecords = ageInvoiceList.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) ageInvoiceList.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("invoice_due_date", map.get("invoice_due_date"));
                args.put("id", map.get("id"));
                args.put("tenant_id", tenant_id);
                this.ageInvoiceService.updateAgeInvoice(args);
            }
            lock = false;
            numberOfRows = numberOfRecords;
        }
        return numberOfRows;
    }

}
