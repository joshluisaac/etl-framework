package com.kollect.etl.controller.pelita;

import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.pelita.UpdateInvoiceNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PelitaBatchController {
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private UpdateInvoiceNumber updateInvoiceNumber;
    private @Value("#{'${app.datasource_all3}'.split(',')}")
    List<String> dataSource;

    @Autowired
    public PelitaBatchController(AsyncBatchExecutorService asyncBatchExecutorService,
                                 UpdateDataDateService updateDataDateService,
                                 UpdateInvoiceNumber updateInvoiceNumber){
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
        this.updateInvoiceNumber=updateInvoiceNumber;
    }

    @PostMapping(value = "/pelitaageinvoice", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object ageInvoice (){
        return this.asyncBatchExecutorService.execute(59,
                dataSource, "getPelitaAgeInvoices",
                "updatePelitaAgeInvoices",
                "AGE_INV");
    }

    @PostMapping(value = "/pelitacompinvamtafttax", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object computeInv () {
        return this.asyncBatchExecutorService.execute(57,
                dataSource, "getPelitaInvoiceAmountAfterTax",
                "updatePelitaInvoiceAmountAfterTax",
                "COMPUTE_INV");
    }

    @PostMapping(value = "/pelitainaging", produces = "application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object inAging (){
        return this.asyncBatchExecutorService.execute(56,
                dataSource, "getPelitaInAging",
                "updatePelitaInAging",
                "IN_AGING");
    }

    @PostMapping(value = "/pelitainvstateval", produces="application/json")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Object invStatEvaluation () {
        return this.asyncBatchExecutorService.execute(58,
                dataSource, "getPelitaInvoiceStatus",
                "updatePelitaInvoiceStatus",
                "INV_STAT_EVAL");
    }

    @PostMapping("/pelitadatadate")
    @ResponseBody
    public Object runUpdateDataDate() {
        return this.updateDataDateService.runUpdateDataDate(60,
                dataSource, "pelitaUpdateDataDate");
    }

    @PostMapping("/pelitaupdateinvoiceno")
    @ResponseBody
    public Object updateInvoiceNo() {
        return this.updateInvoiceNumber.execute(80, "getPelitaInvoiceNumbers",
                "updatePelitaInvoiceNumbers");
    }

    @PostMapping("/pelitaupdatetrxcode")
    @ResponseBody
    public Object updateTrxCode() {
        return asyncBatchExecutorService.execute(85, dataSource, "getTrxCodeAndDesc",
                "updateTrxCode", "UPDATE_TRX_CODE_DESC");
    }

    @PostMapping("/pelitaupdatetrxdesc")
    @ResponseBody
    public Object updateTrxDesc() {
        return asyncBatchExecutorService.execute(86, dataSource, "getTrxCodeAndDesc",
                "updateTrxDesc", "UPDATE_TRX_CODE");
    }

    @PostMapping("/pelitacomputeinvoiceoutstanding")
    @ResponseBody
    public Object computeInvoiceOutstanding() {
        return asyncBatchExecutorService.execute(84, dataSource, "getPelitaOutstanding",
                "updatePelitaInvoiceOutstanding", "PELITA_INV_OUTSTANDING");
    }

    @PostMapping("/pelitacleandefault")
    @ResponseBody
    public Object cleanDefault() {
        Integer batch_id = 88;
        Integer updatedRows = asyncBatchExecutorService.execute(batch_id, dataSource, "getPelitaEmailsDefault",
                "deletePelitaEmailsDefault", "PELITA_DEF_EMAILS");
        updatedRows += asyncBatchExecutorService.execute(batch_id, dataSource, "getPelitaPhoneNosDefault",
                "deletePelitaPhoneNosDefault", "PELITA_DEF_PHONES");
        updatedRows += asyncBatchExecutorService.execute(batch_id, dataSource, "getPelitaPicDefault",
                "deletePelitaPicDefault", "PELITA_DEF_PIC");
        updatedRows += asyncBatchExecutorService.execute(batch_id, dataSource, "getPelitaAddressDefault",
                "deletePelitaAddressDefault", "PELITA_DEF_ADDR");
        return updatedRows;
    }
}