package com.kollect.etl.controller.ictzone;

import com.kollect.etl.service.commonbatches.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IctZoneBatchController {
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private @Value("${app.datasource_kv_production}")
    List<String> dataSource;

    @Autowired
    public IctZoneBatchController(AsyncBatchExecutorService asyncBatchExecutorService,
                                  UpdateDataDateService updateDataDateService){
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
    }

    @PostMapping(value = "/ictzoneageinvoice", produces = "application/json")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id, dataSource,
                "getIctZoneAgeInvoices",
                "updateIctZoneAgeInvoices", "AGE_INV");
    }

    @PostMapping("/ictzonecomputedebit")
    @ResponseBody
    public Object computeDebit(@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id, dataSource,
                "getIctZoneDebitAmountAfterTax",
                "updateIctZoneDebitAmountAfterTax", "COMPUTE_DEBIT");
    }

    @PostMapping(value = "/ictzonecompinvamtafttax", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object computeInv (@RequestParam Integer batch_id) {
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource,
                "getIctZoneInvoiceAmountAfterTax",
                "updateIctZoneInvoiceAmountAfterTax",
                "COMPUTE_INV");
    }

    @PostMapping(value = "/ictzoneinaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getIctZoneInAging",
                "updateIctZoneInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/ictzoneinvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation (@RequestParam Integer batch_id) {
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getIctZoneInvoiceStatus",
                "updateIctZoneInvoiceStatus",
                "INV_STAT_EVAL");
    }

    @PostMapping("/ictzonedatadate")
    @ResponseBody
    public Object updateDataDate(@RequestParam Integer batch_id) {
        return this.updateDataDateService.runUpdateDataDate(batch_id, dataSource,
                "ictZoneUpdateDataDate");
    }
}
