package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.pelita.PelitaInAgingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaInAgingController {
    @Autowired
    private PelitaInAgingService pelitaInAgingService;

    @PostMapping(value = "/pelitainaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object pelitaAgeInvoice (@RequestParam Integer batch_id){
        return this.pelitaInAgingService.combinedPelitaAgeInvoiceService(batch_id);
    }
}
