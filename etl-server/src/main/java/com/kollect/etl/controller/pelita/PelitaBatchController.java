package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.commonbatches.RunAsyncBatchService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PelitaBatchController {
    private RunAsyncBatchService runAsyncBatchService;
    private UpdateDataDateService updateDataDateService;
    private @Value("${app.datasource_pelita_test}")
    List<String> dataSource;

    @Autowired
    public PelitaBatchController(RunAsyncBatchService runAsyncBatchService,
                                 UpdateDataDateService updateDataDateService){
        this.runAsyncBatchService = runAsyncBatchService;
        this.updateDataDateService = updateDataDateService;
    }

    @PostMapping(value = "/pelitaageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.runAsyncBatchService.execute(batch_id,
                dataSource, "getPelitaAgeInvoices",
                "updatePelitaAgeInvoices",
                "AGE_INV");
    }

    @PostMapping("/pelitacomputedebit")
    @ResponseBody
    public Object computeDebit(@RequestParam Integer batch_id){
        return this.runAsyncBatchService.execute(batch_id,
                dataSource, "getPelitaDebitAmountAfterTax",
                "updatePelitaDebitAmountAfterTax",
                "COMPUTE_DEBIT");
    }

    @PostMapping(value = "/pelitacompinvamtafttax", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object computeInv (@RequestParam Integer batch_id) {
        return this.runAsyncBatchService.execute(batch_id,
                dataSource, "getPelitaInvoiceAmountAfterTax",
                "updatePelitaInvoiceAmountAfterTax",
                "COMPUTE_INV");
    }

    @PostMapping(value = "/pelitainaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (@RequestParam Integer batch_id){
        return this.runAsyncBatchService.execute(batch_id,
                dataSource, "getPelitaInAging",
                "updatePelitaInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/pelitainvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation (@RequestParam Integer batch_id) {
        return this.runAsyncBatchService.execute(batch_id,
                dataSource, "getPelitaInvoiceStatus",
                "updatePelitaInvoiceStatus",
                "INV_STAT_EVAL");
    }

    @PostMapping("/pelitadatadate")
    @ResponseBody
    public Object runUpdateDataDate(@RequestParam Integer batch_id) {
        return this.updateDataDateService.runUpdateDataDate(batch_id,
                dataSource, "pelitaUpdateDataDate");
    }
}
