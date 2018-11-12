package com.kollect.etl.controller.nationwide;

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
public class NationwideBatchController {
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private @Value("${app.datasource_kv_production}")
    List<String> dataSource;

    @Autowired
    public NationwideBatchController(AsyncBatchExecutorService asyncBatchExecutorService,
                                  UpdateDataDateService updateDataDateService){
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
    }

    @PostMapping(value = "/nationwideageinvoice", produces = "application/json")
    @ResponseBody
    public Object ageInvoice (){
        return this.asyncBatchExecutorService.execute(97, dataSource,
                "getNationwideAgeInvoices",
                "updateNationwideAgeInvoices", "AGE_INV");
    }

    @PostMapping(value = "/nationwideinaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (){
        return this.asyncBatchExecutorService.execute(95,
                dataSource, "getNationwideInAging",
                "updateNationwideInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/nationwideinvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation () {
        return this.asyncBatchExecutorService.execute(96,
                dataSource, "getNationwideInvoiceStatus",
                "updateNationwideInvoiceStatus",
                "INV_STAT_EVAL");
    }

    @PostMapping("/nationwidedatadate")
    @ResponseBody
    public Object updateDataDate() {
        return this.updateDataDateService.runUpdateDataDate(98, dataSource,
                "nationwideUpdateDataDate");
    }
}
