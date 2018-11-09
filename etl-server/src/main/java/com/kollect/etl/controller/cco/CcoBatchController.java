package com.kollect.etl.controller.cco;

import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CcoBatchController {
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private @Value("${app.datasource_kv_production}")
    List<String> dataSource;

    @Autowired
    public CcoBatchController(AsyncBatchExecutorService asyncBatchExecutorService,
                                  UpdateDataDateService updateDataDateService){
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
    }

    @PostMapping(value = "/ccoageinvoice", produces = "application/json")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id, dataSource,
                "getCcoAgeInvoices",
                "updateCcoAgeInvoices", "AGE_INV");
    }

    @PostMapping(value = "/ccoinaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getCcoInAging",
                "updateCcoInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/ccoinvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation (@RequestParam Integer batch_id) {
        return this.asyncBatchExecutorService.execute(batch_id,
                dataSource, "getCcoInvoiceStatus",
                "updateCcoInvoiceStatus",
                "INV_STAT_EVAL");
    }

    @PostMapping("/ccodatadate")
    @ResponseBody
    public Object updateDataDate(@RequestParam Integer batch_id) {
        return this.updateDataDateService.runUpdateDataDate(batch_id, dataSource,
                "ccoUpdateDataDate");
    }

    @PostMapping("/ccocleandefault")
    @ResponseBody
    public Object cleanDefault(@RequestParam Integer batch_id) {
        Integer updatedSize = asyncBatchExecutorService.execute(batch_id, dataSource,
                "selectCcoCustomerEmailsWithDash",
                "updateCcoCustomerEmailsWithDash", "CCO_DEF_EMAILS");
        updatedSize += asyncBatchExecutorService.execute(batch_id, dataSource,
                "getCcoPhoneNosDefault",
                "deleteCcoPhoneNosDefault", "CCO_DEF_PHONES");
        updatedSize += asyncBatchExecutorService.execute(batch_id, dataSource,
                "getCcoPicDefault",
                "deleteCcoPicDefault", "CCO_DEF_PIC");
        updatedSize += asyncBatchExecutorService.execute(batch_id, dataSource,
                "getCcoAddressDefault",
                "deleteCcoAddressDefault", "CCO_DEF_ADDRESS");

        return updatedSize;
    }

    @PostMapping("/ccodeletezerocredittrx")
    @ResponseBody
    public Object deleteZeroCredit(@RequestParam Integer batch_id) {
        return asyncBatchExecutorService.execute(batch_id, dataSource, "getCcoZeroCreditTrx",
                "deleteCcoZeroCreditTrx", "DEL_ZERO_CREDIT");
    }
}
