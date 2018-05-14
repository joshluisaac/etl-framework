package com.kollect.etl.controller.yyc;

import com.kollect.etl.service.yyc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class YycBatchController {
    private YycQuerySequenceService yycQuerySequenceService;
    private YycAgeInvoiceService yycAgeInvoiceService;
    private YycInAgingService yycInAgingService;
    private YycInvoiceStatusEvaluationService yycInvoiceStatusEvaluationService;
    private YycUpdateDataDateService yycUpdateDataDateService;

    @Autowired
    public YycBatchController (YycQuerySequenceService yycQuerySequenceService,
                               YycAgeInvoiceService yycAgeInvoiceService,
                               YycInAgingService yycInAgingService,
                               YycInvoiceStatusEvaluationService yycInvoiceStatusEvaluationService,
                               YycUpdateDataDateService yycUpdateDataDateService){
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.yycAgeInvoiceService = yycAgeInvoiceService;
        this.yycInAgingService = yycInAgingService;
        this.yycInvoiceStatusEvaluationService = yycInvoiceStatusEvaluationService;
        this.yycUpdateDataDateService = yycUpdateDataDateService;
    }

    @ResponseBody
    @PostMapping("/runyycsequence")
    public Object runYycSequence(@RequestParam Integer batch_id){
        return this.yycQuerySequenceService.runYycSequenceQuery(batch_id);
    }

    @ResponseBody
    @PostMapping("/yycinvstatevaluation")
    public Object yycInvStatEvaluation(@RequestParam Integer batch_id){
        return this.yycInvoiceStatusEvaluationService.combinedYycInvoiceStatusEvaluation(batch_id);
    }

    @ResponseBody
    @PostMapping("/yycageinvoice")
    public Object YycAgeInvoice(@RequestParam Integer batch_id){
        return this.yycAgeInvoiceService.combinedAgeInvoiceService(batch_id);
    }

    @ResponseBody
    @PostMapping("/yycinaging")
    public Object yycInAging(@RequestParam Integer batch_id){
        return this.yycInAgingService.combinedYycAgeInvoiceService(batch_id);
    }

    @ResponseBody
    @PostMapping("/yycupdatedatadate")
    public Object yycUpdateDataDate(@RequestParam Integer batch_id){
        return this.yycUpdateDataDateService.runupdateDataDate(batch_id);
    }
}
