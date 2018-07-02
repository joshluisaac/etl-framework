package com.kollect.etl.controller.pbk;

import com.kollect.etl.service.commonbatches.RunAsyncBatchService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.pbk.PbkCalcOutstandingService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PbkBatchController {
    private PbkCalcOutstandingService pbkCalcOutstandingService;
    private PbkLumpSumPaymentService pbkLumpSumPaymentService;
    private RunAsyncBatchService runAsyncBatchService;
    private UpdateDataDateService pbkUpdateDataDateService;
    private @Value("#{'${app.datasource_all}'.split(',')}")
    List<String> dataSource;

    @Autowired
    public PbkBatchController(PbkCalcOutstandingService pbkCalcOutstandingService,
                              PbkLumpSumPaymentService pbkLumpSumPaymentService,
                              UpdateDataDateService pbkUpdateDataDateService,
                              RunAsyncBatchService runAsyncBatchService) {
        this.pbkCalcOutstandingService = pbkCalcOutstandingService;
        this.pbkLumpSumPaymentService = pbkLumpSumPaymentService;
        this.pbkUpdateDataDateService = pbkUpdateDataDateService;
        this.runAsyncBatchService = runAsyncBatchService;
    }
    /**
     * HTTP POST request mapping to run the batch
     *
     * ResponseBody is used to return a json value to the URL needed as a result of the ajax
     *
     * @param tenant_id
     *            this is the tenant id that determines the client, e.g. 63 for PBK
     *
     * @return returns the number of rows updated as json
     */

    @PostMapping(value = "/pbkcalcoutstanding", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object calcOutstanding (@RequestParam Integer batch_id) {
        return this.pbkCalcOutstandingService.combinedCalcOutstanding(batch_id);
    }

    /**
     * POST method to get the tenant_id from user to run the age invoice batch
     * @param batch_id
     *                  used to run the batchhistoryservice for this batch
     * @return
     *          returns the number of rows updated by the batch
     */

    @PostMapping(value = "/pbkageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.runAsyncBatchService.execute(batch_id,
                dataSource, "getPbkAgeInvoice",
                "updatePbkAgeInvoice",
                "AGE_INV");
    }

    @PostMapping(value ="/pbklumpsumpayment")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object lumSumPayment (@RequestParam Integer batch_id) {
        return pbkLumpSumPaymentService.combinedLumpSumPaymentService(batch_id);
    }

    @PostMapping("/pbkdatadate")
    @ResponseBody
    public Object updateDataDate(@RequestParam Integer batch_id){
        return this.pbkUpdateDataDateService.runUpdateDataDate(batch_id,
                dataSource, "pbkUpdateDataDate");
    }
}
