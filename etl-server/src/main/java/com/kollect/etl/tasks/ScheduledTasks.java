package com.kollect.etl.tasks;

import com.kollect.etl.service.pbk.*;
import com.kollect.etl.service.pelita.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private PbkLumpSumPaymentService pbklSumPayServ;
    private PbkAgeInvoiceService pbkageInvServ;
    private PbkUpdateDataDateService pbkupdDataDateServ;
    private PelitaUpdateDataDateService pelitaUpdateDataDateService;
    private PelitaAgeInvoiceService pelitaAgeInvoiceService;
    private PelitaComputeInvoiceAmountAfterTaxService pelitaComputeInvoiceAmountAfterTaxServicePelita;
    private PelitaInAgingService pelitaInAgingServicePelita;
    private PelitaInvoiceStatusEvaluationService pelitaInvoiceStatusEvaluationServicePelita;
    private PelitaComputeDebitAmountAfterTaxService pelitaComputeDebitAmountAfterTaxService;

    @Autowired
    public ScheduledTasks(PbkLumpSumPaymentService pbklSumPayServ,
                          PbkAgeInvoiceService pbkageInvServ,
                          PbkUpdateDataDateService pbkupdDataDateServ,
                          PelitaUpdateDataDateService pelitaUpdateDataDateService,
                          PelitaAgeInvoiceService pelitaAgeInvoiceService,
                          PelitaComputeInvoiceAmountAfterTaxService pelitaComputeInvoiceAmountAfterTaxServicePelita,
                          PelitaInAgingService pelitaInAgingServicePelita,
                          PelitaInvoiceStatusEvaluationService pelitaInvoiceStatusEvaluationServicePelita,
                          PelitaComputeDebitAmountAfterTaxService pelitaComputeDebitAmountAfterTaxService){
        this.pbklSumPayServ = pbklSumPayServ;
        this.pbkageInvServ = pbkageInvServ;
        this.pbkupdDataDateServ = pbkupdDataDateServ;
        this.pelitaUpdateDataDateService = pelitaUpdateDataDateService;
        this.pelitaAgeInvoiceService = pelitaAgeInvoiceService;
        this.pelitaComputeInvoiceAmountAfterTaxServicePelita = pelitaComputeInvoiceAmountAfterTaxServicePelita;
        this.pelitaInAgingServicePelita = pelitaInAgingServicePelita;
        this.pelitaInvoiceStatusEvaluationServicePelita = pelitaInvoiceStatusEvaluationServicePelita;
        this.pelitaComputeDebitAmountAfterTaxService = pelitaComputeDebitAmountAfterTaxService;
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void runPbkBatches() {
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.pbkageInvServ.combinedAgeInvoiceService(3);
        this.pbkupdDataDateServ.runupdateDataDate(53);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void runPelitaBatches() {
        this.pelitaInvoiceStatusEvaluationServicePelita.combinePelitaInvoiceStatusEvaluation(58);
        this.pelitaComputeInvoiceAmountAfterTaxServicePelita.combinedPelitaComputeInvoiceAmountAfterTax(57);
        this.pelitaInAgingServicePelita.combinedPelitaAgeInvoiceService(56);
        this.pelitaAgeInvoiceService.combinedAgeInvoiceService(59);
        this.pelitaUpdateDataDateService.runupdateDataDate(60);
        this.pelitaComputeDebitAmountAfterTaxService.combinedPelitaComputeDebitAmountAfterTax(61);
    }

}