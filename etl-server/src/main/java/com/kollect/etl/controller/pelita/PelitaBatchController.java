package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.pelita.UpdateInvoiceNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PelitaBatchController {
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private UpdateInvoiceNumber updateInvoiceNumber;
    private @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> dataSource;

    @Autowired
    public PelitaBatchController(AsyncBatchExecutorService asyncBatchExecutorService,
                                 UpdateDataDateService updateDataDateService,
                                 UpdateInvoiceNumber updateInvoiceNumber){
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
        this.updateInvoiceNumber=updateInvoiceNumber;
    }

    @PostMapping(value = "/pelitaageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getPelitaAgeInvoices",
                "updatePelitaAgeInvoices",
                "AGE_INV");
    }

    @PostMapping("/pelitacomputedebit")
    @ResponseBody
    public Object computeDebit(@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getPelitaDebitAmountAfterTax",
                "updatePelitaDebitAmountAfterTax",
                "COMPUTE_DEBIT");
    }

    @PostMapping(value = "/pelitacompinvamtafttax", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object computeInv (@RequestParam Integer batch_id) {
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getPelitaInvoiceAmountAfterTax",
                "updatePelitaInvoiceAmountAfterTax",
                "COMPUTE_INV");
    }

    @PostMapping(value = "/pelitainaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getPelitaInAging",
                "updatePelitaInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/pelitainvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation (@RequestParam Integer batch_id) {
        return this.asyncBatchExecutorService.execute(batch_id,
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

    @PostMapping("/pelitaupdateinvoiceno")
    @ResponseBody
    public Object updateInvoiceNo(@RequestParam Integer batch_id) {
        return this.updateInvoiceNumber.execute(batch_id, "getPelitaInvoiceNumbers",
                "updatePelitaInvoiceNumbers");
    }

    @PostMapping("/pelitaupdatetransactioninvno")
    @ResponseBody
    public Object updateTrxInvNo(@RequestParam Integer batch_id) {
        return asyncBatchExecutorService.execute(batch_id, dataSource, "getTrxCodeAndDesc",
                "updateTrxCodeAndDesc", "UPDATE_TRX_CODE_DESC");
    }

    @PostMapping("/pelitacomputeinvoiceoutstanding")
    @ResponseBody
    public Object computeInvoiceOutstanding(@RequestParam Integer batch_id) {
        return asyncBatchExecutorService.execute(batch_id, dataSource, "getPelitaOutstanding",
                "updatePelitaInvoiceOutstanding", "PELITA_INV_OUTSTANDING");
    }

}