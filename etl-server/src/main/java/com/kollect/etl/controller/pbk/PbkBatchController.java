package com.kollect.etl.controller.pbk;

import com.kollect.etl.service.pbk.PbkAgeInvoiceService;
import com.kollect.etl.service.pbk.PbkCalcOutstandingService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import com.kollect.etl.service.pbk.PbkUpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PbkBatchController {
    private PbkCalcOutstandingService pbkCalcOutstandingService;
    private PbkAgeInvoiceService pbkAgeInvoiceService;
    private PbkLumpSumPaymentService pbkLumpSumPaymentService;
    private PbkUpdateDataDateService pbkUpdateDataDateService;

    @Autowired
    public PbkBatchController(PbkCalcOutstandingService pbkCalcOutstandingService,
                              PbkAgeInvoiceService pbkAgeInvoiceService,
                              PbkLumpSumPaymentService pbkLumpSumPaymentService,
                              PbkUpdateDataDateService pbkUpdateDataDateService) {
        this.pbkCalcOutstandingService = pbkCalcOutstandingService;
        this.pbkAgeInvoiceService = pbkAgeInvoiceService;
        this.pbkLumpSumPaymentService = pbkLumpSumPaymentService;
        this.pbkUpdateDataDateService = pbkUpdateDataDateService;
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

    @PostMapping(value = "/calcoutstanding", produces="application/json")
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

    @PostMapping(value = "/ageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (@RequestParam Integer batch_id){
        return this.pbkAgeInvoiceService.combinedAgeInvoiceService(batch_id);
    }

    @PostMapping(value ="/lumpSumPayment")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object selectLumSumPayment (@RequestParam Integer batch_id) {
        return pbkLumpSumPaymentService.combinedLumpSumPaymentService(batch_id);
    }

    @PostMapping("/updatedatadate")
    @ResponseBody
    public Object runUpdateDataDate(@RequestParam Integer batch_id){
        return this.pbkUpdateDataDateService.runupdateDataDate(batch_id);
    }
}
