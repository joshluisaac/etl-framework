package com.kollect.etl.controller.yyc;

import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.yyc.YycQuerySequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class YycBatchController {
    private YycQuerySequenceService yycQuerySequenceService;
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService yycUpdateDataDateService;
    private @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> dataSource;

    @Autowired
    public YycBatchController (YycQuerySequenceService yycQuerySequenceService,
                               AsyncBatchExecutorService asyncBatchExecutorService,
                               UpdateDataDateService yycUpdateDataDateService){
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.yycUpdateDataDateService = yycUpdateDataDateService;
    }

    @ResponseBody
    @PostMapping("/yycsequence")
    public Object runYycSequence(@RequestParam Integer batch_id){
        return this.yycQuerySequenceService.runYycSequenceQuery(batch_id, dataSource);
    }

    @ResponseBody
    @PostMapping("/yycinvstatevaluation")
    public Object invStatEvaluation(@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute
                (batch_id, dataSource, "getYycInvoiceStatus",
                        "updateYycInvoiceStatus",
                        "INV_STAT_EVAL");
    }

    @ResponseBody
    @PostMapping("/yycageinvoice")
    public Object ageInvoice(@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id, dataSource,
                "getYycAgeInvoice", "updateYycAgeInvoice",
                "AGE_INV");
    }

    @ResponseBody
    @PostMapping("/yycinaging")
    public Object inAging(@RequestParam Integer batch_id){
        return this.asyncBatchExecutorService.execute(batch_id, dataSource,
                "getYycInAging", "updateYycInAging",
                "IN_AGING");
    }

    @ResponseBody
    @PostMapping("/yycupdatedatadate")
    public Object yycUpdateDataDate(@RequestParam Integer batch_id){
        return this.yycUpdateDataDateService.runUpdateDataDate(batch_id,
                dataSource, "yycUpdateDataDate");
    }
}
