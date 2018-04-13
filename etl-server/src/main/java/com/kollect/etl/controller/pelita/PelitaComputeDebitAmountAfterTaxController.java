package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.pelita.PelitaComputeDebitAmountAfterTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaComputeDebitAmountAfterTaxController {
    @Autowired
    private PelitaComputeDebitAmountAfterTaxService service;

    @PostMapping("/pelitacomputedebit")
    @ResponseBody
    public Object pelitaComputeDebit(@RequestParam Integer batch_id){
        return this.service.combinedPelitaComputeDebitAmountAfterTax(batch_id);
    }
}
