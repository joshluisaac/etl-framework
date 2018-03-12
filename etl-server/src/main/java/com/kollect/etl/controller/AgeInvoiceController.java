package com.kollect.etl.controller;

import com.kollect.etl.service.AgeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/ageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam(required = false) Integer tenant_id){
        List<Object> ageInvoiceList = this.ageInvoiceService.getAgeInvoiceById(tenant_id);
        int numberOfRows = ageInvoiceList.size();
        for (int i=0;i<numberOfRows;i++) {
            Map<Object, Object> map = (Map<Object, Object>) ageInvoiceList.get(i);
            Map<Object, Object> args = new HashMap<>();
            args.put("invoice_due_date", map.get("invoice_due_date"));
            args.put("id", map.get("id"));
            this.ageInvoiceService.updateAgeInvoice(args);
        }
        return numberOfRows;
    }

}
