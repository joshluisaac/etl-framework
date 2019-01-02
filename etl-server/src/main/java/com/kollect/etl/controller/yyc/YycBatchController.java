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
    public YycBatchController(YycQuerySequenceService yycQuerySequenceService,
                              AsyncBatchExecutorService asyncBatchExecutorService,
                              UpdateDataDateService yycUpdateDataDateService) {
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.yycUpdateDataDateService = yycUpdateDataDateService;
    }

    @ResponseBody
    @PostMapping("/yycsequence")
    public Object runYycSequence() {
        return this.yycQuerySequenceService.runYycSequenceQuery(62, dataSource);
    }

    @ResponseBody
    @PostMapping("/yycinvstatevaluation")
    public Object invStatEvaluation() {
        return this.asyncBatchExecutorService.execute
                (63, dataSource, "getYycInvoiceStatus",
                        "updateYycInvoiceStatus",
                        "INV_STAT_EVAL");
    }

    @ResponseBody
    @PostMapping("/yycageinvoice")
    public Object ageInvoice() {
        return this.asyncBatchExecutorService.execute(64, dataSource,
                "getYycAgeInvoice", "updateYycAgeInvoice",
                "AGE_INV");
    }

    @ResponseBody
    @PostMapping("/yycinaging")
    public Object inAging() {
        return this.asyncBatchExecutorService.execute(65, dataSource,
                "getYycInAging", "updateYycInAging",
                "IN_AGING");
    }

    @ResponseBody
    @PostMapping("/yycupdatedatadate")
    public Object yycUpdateDataDate() {
        return this.yycUpdateDataDateService.runUpdateDataDate(66,
                dataSource, "yycUpdateDataDate");
    }

    @PostMapping("/yycupdatephonenos")
    @ResponseBody
    public Object updatePhoneNos() {
        return asyncBatchExecutorService.execute(81, dataSource,
                "getYycPhoneNosNotListed",
                "updateYycPhoneNosNotListed", "YYC_DEF_PHONE");
    }

    @PostMapping("/yycupdatepic")
    @ResponseBody
    public Object updatePicName() {
        return asyncBatchExecutorService.execute(82, dataSource,
                "getYycDefPicName", "updateYycPicName", "YYC_DEF_PIC");
    }

    @PostMapping("/yycupdatedefemails")
    @ResponseBody
    public Object updateDefEmails(@RequestParam Integer batch_id) {
        return asyncBatchExecutorService.execute(batch_id, dataSource,
                "getYycDefEmails", "deleteYycDefEmails", "YYC_DEF_EMAILS");
    }

    @PostMapping("/yycdeletedefaddresses")
    @ResponseBody
    public Object updateDefAddress() {
        return asyncBatchExecutorService.execute(93, dataSource,
                "getYycDefAddress", "deleteYycDefAddress", "YYC_DEF_ADDR");
    }

    @PostMapping("/yycdeletedummyinvoices")
    @ResponseBody
    public Object deleteDummyInvoices() {
        return this.asyncBatchExecutorService.execute(103, dataSource, "getOldYycDummyInvoices", "deleteYycDummyInvoices",
                "ICTZONE_DELETE_DUMMY_INV");
    }

    @PostMapping("/yycinsertdummyinvoices")
    @ResponseBody
    public Object insertDummyInvoices() {
        return this.asyncBatchExecutorService.execute(104, dataSource, "getYycTrxWithZeroInvId", "insertYycDummyInvoices",
                "ICTZONE_DUMMY_INV");
    }

    @PostMapping("/yycupdatetrxwith0invid")
    @ResponseBody
    public Object updateTrxWithZeroInvId() {
        return this.asyncBatchExecutorService.execute(105, dataSource, "getYycTrxWithZeroInvId", "updateYycTrxWithZeroInvId",
                "ICTZONE_UPDATE_TRX_ZERO_INV_ID");
    }
}
