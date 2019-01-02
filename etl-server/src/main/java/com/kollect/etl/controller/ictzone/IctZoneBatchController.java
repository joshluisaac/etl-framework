package com.kollect.etl.controller.ictzone;

import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IctZoneBatchController {
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> dataSource;

    @Autowired
    public IctZoneBatchController(AsyncBatchExecutorService asyncBatchExecutorService,
                                  UpdateDataDateService updateDataDateService){
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
    }

    @PostMapping(value = "/ictzoneageinvoice", produces = "application/json")
    @ResponseBody
    public Object ageInvoice (){
        return this.asyncBatchExecutorService.execute(70, dataSource,
                "getIctZoneAgeInvoices",
                "updateIctZoneAgeInvoices", "AGE_INV");
    }

    @PostMapping(value = "/ictzonecompinvamtafttax", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object computeInv () {
        return this.asyncBatchExecutorService.execute(68,
                dataSource,
                "getIctZoneInvoiceAmountAfterTax",
                "updateIctZoneInvoiceAmountAfterTax",
                "COMPUTE_INV");
    }

    @PostMapping(value = "/ictzoneinaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (){
        return this.asyncBatchExecutorService.execute(67,
                dataSource, "getIctZoneInAging",
                "updateIctZoneInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/ictzoneinvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation () {
        return this.asyncBatchExecutorService.execute(69,
                dataSource, "getIctZoneInvoiceStatus",
                "updateIctZoneInvoiceStatus",
                "INV_STAT_EVAL");
    }

    @PostMapping("/ictzonedatadate")
    @ResponseBody
    public Object updateDataDate() {
        return this.updateDataDateService.runUpdateDataDate(71, dataSource,
                "ictZoneUpdateDataDate");
    }

    @PostMapping("/ictzoneinvoiceoutstanding")
    @ResponseBody
    public Object updateInvoiceOutstanding() {
        return this.asyncBatchExecutorService.execute(94, dataSource, "getInvoiceOutstanding", "updateInvoiceOutstanding",
                "ICTZONE_OUTSTANDING_INVOICE");
    }

    @PostMapping("/ictzonedeletedummyinvoices")
    @ResponseBody
    public Object deleteDummyInvoices() {
        return this.asyncBatchExecutorService.execute(102, dataSource, "getOldIctDummyInvoices", "deleteIctDummyInvoices",
                "ICTZONE_DELETE_DUMMY_INV");
    }

    @PostMapping("/ictzoneinsertdummyinvoices")
    @ResponseBody
    public Object insertDummyInvoices() {
        return this.asyncBatchExecutorService.execute(100, dataSource, "getIctTrxWithZeroInvId", "insertIctDummyInvoices",
                "ICTZONE_DUMMY_INV");
    }

    @PostMapping("/ictzoneupdatetrxwith0invid")
    @ResponseBody
    public Object updateTrxWithZeroInvId() {
        return this.asyncBatchExecutorService.execute(101, dataSource, "getIctTrxWithZeroInvId", "updateIctTrxWithZeroInvId",
                "ICTZONE_UPDATE_TRX_ZERO_INV_ID");
    }
}
