package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.pelita.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PelitaBatchController {
    private PelitaAgeInvoiceService ageInvService;
    private PelitaComputeDebitAmountAfterTaxService compDebitAmtAfterTax;
    private PelitaComputeInvoiceAmountAfterTaxService compInvAmtAfterTax;
    private PelitaInAgingService inAgingService;
    private PelitaInvoiceStatusEvaluationService invoiceStatusEvaluationService;
    private PelitaUpdateDataDateService updateDataDateService;

    @Autowired
    public PelitaBatchController(PelitaAgeInvoiceService ageInvService,
                                 PelitaComputeDebitAmountAfterTaxService compDebitAmtAfterTax,
                                 PelitaComputeInvoiceAmountAfterTaxService compInvAmtAfterTax,
                                 PelitaInAgingService inAgingService,
                                 PelitaInvoiceStatusEvaluationService invoiceStatusEvaluationService,
                                 PelitaUpdateDataDateService updateDataDateService){
        this.ageInvService = ageInvService;
        this.compDebitAmtAfterTax = compDebitAmtAfterTax;
        this.compInvAmtAfterTax = compInvAmtAfterTax;
        this.inAgingService = inAgingService;
        this.invoiceStatusEvaluationService = invoiceStatusEvaluationService;
        this.updateDataDateService = updateDataDateService;
    }

    @PostMapping(value = "/pelitageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.ageInvService.combinedAgeInvoiceService(batch_id);
    }

    @PostMapping("/pelitacomputedebit")
    @ResponseBody
    public Object computeDebit(@RequestParam Integer batch_id){
        return this.compDebitAmtAfterTax.combinedPelitaComputeDebitAmountAfterTax(batch_id);
    }

    @PostMapping(value = "/pelitacomputeinvoiceamountaftertax", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object computeInv (@RequestParam Integer batch_id) {
        return this.compInvAmtAfterTax.combinedPelitaComputeInvoiceAmountAfterTax(batch_id);
    }

    @PostMapping(value = "/pelitainaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInv (@RequestParam Integer batch_id){
        return this.inAgingService.combinedPelitaInAgingService(batch_id);
    }

    @PostMapping(value = "/pelitainvoicestatusevaluation", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation (@RequestParam Integer batch_id) {
        return this.invoiceStatusEvaluationService.combinePelitaInvoiceStatusEvaluation( batch_id);
    }

    @PostMapping("/pelitaupdatedatadate")
    @ResponseBody
    public Object runUpdateDataDate(@RequestParam Integer batch_id) {
        return this.updateDataDateService.runupdateDataDate(batch_id);
    }
}
