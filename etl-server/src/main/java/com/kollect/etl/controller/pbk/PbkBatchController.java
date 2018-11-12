package com.kollect.etl.controller.pbk;

import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
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
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService pbkUpdateDataDateService;
    private @Value("#{'${app.datasource_all}'.split(',')}")
    List<String> dataSource;

    @Autowired
    public PbkBatchController(PbkCalcOutstandingService pbkCalcOutstandingService,
                              PbkLumpSumPaymentService pbkLumpSumPaymentService,
                              UpdateDataDateService pbkUpdateDataDateService,
                              AsyncBatchExecutorService asyncBatchExecutorService) {
        this.pbkCalcOutstandingService = pbkCalcOutstandingService;
        this.pbkLumpSumPaymentService = pbkLumpSumPaymentService;
        this.pbkUpdateDataDateService = pbkUpdateDataDateService;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
    }
    /**
     * HTTP POST request mapping to run the batch
     *
     * ResponseBody is used to return a json value to the URL needed as a result of the ajax
     *
     * @return returns the number of rows updated as json
     */

    @PostMapping(value = "/pbkcalcoutstanding", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object calcOutstanding () {
        return this.pbkCalcOutstandingService.combinedCalcOutstanding(1);
    }

    /**
     * POST method to get the tenant_id from user to run the age invoice batch
     * @return
     *          returns the number of rows updated by the batch
     */

    @PostMapping(value = "/pbkageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (){
        return this.asyncBatchExecutorService.execute(3,
                dataSource, "getPbkAgeInvoice",
                "updatePbkAgeInvoice",
                "AGE_INV");
    }

    @PostMapping(value ="/pbklumpsumpayment")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object lumSumPayment () {
        return pbkLumpSumPaymentService.combinedLumpSumPaymentService(2);
    }

    @PostMapping("/pbkdatadate")
    @ResponseBody
    public Object updateDataDate(){
        return this.pbkUpdateDataDateService.runUpdateDataDate(53,
                dataSource, "pbkUpdateDataDate");
    }
}
