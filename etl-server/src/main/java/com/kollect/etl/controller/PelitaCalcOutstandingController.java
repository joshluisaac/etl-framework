package com.kollect.etl.controller;

import com.kollect.etl.service.PelitaCalcOutstandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaCalcOutstandingController {
    @Autowired
    private PelitaCalcOutstandingService pelitaCalcOutStandingService;

    @PostMapping(value = "/pelitacalcoutstanding", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object pelitaCalcOutstanding (@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id) {
        return this.pelitaCalcOutStandingService.combinedPelitaCalcOutstanding(tenant_id, batch_id);
    }
}
