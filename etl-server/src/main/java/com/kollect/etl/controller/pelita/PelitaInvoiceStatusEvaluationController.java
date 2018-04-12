package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.pelita.PelitaInvoiceStatusEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaInvoiceStatusEvaluationController {


    @Autowired
    private PelitaInvoiceStatusEvaluationService service;

    @PostMapping(value = "/pelitainvoicestatusevaluation", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object pelitaCalcOutstanding (@RequestParam Integer batch_id) {
        return this.service.combinePelitaInvoiceStatusEvaluation( batch_id);
    }
}
