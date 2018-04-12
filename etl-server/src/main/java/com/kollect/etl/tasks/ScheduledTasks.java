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
    private PelitaUpdateDataDateService pelitaPelitaUpdateDataDateService;
    private PelitaAgeInvoiceService pelitaPelitaAgeInvoiceService;
    private PelitaComputeInvoiceAmountAfterTaxService pelitacomputeInvoiceAmountAfterTaxServicePelita;
    private PelitaInAgingService pelitainAgingServicePelita;
    private PelitaInvoiceStatusEvaluationService pelitainvoiceStatusEvaluationServicePelita;

    @Autowired
    public ScheduledTasks(PbkLumpSumPaymentService pbklSumPayServ,
                          PbkAgeInvoiceService pbkageInvServ,
                          PbkUpdateDataDateService pbkupdDataDateServ,
                          PelitaUpdateDataDateService pelitaPelitaUpdateDataDateService,
                          PelitaAgeInvoiceService pelitaPelitaAgeInvoiceService,
                          PelitaComputeInvoiceAmountAfterTaxService pelitacomputeInvoiceAmountAfterTaxServicePelita,
                          PelitaInAgingService pelitainAgingServicePelita,
                          PelitaInvoiceStatusEvaluationService pelitainvoiceStatusEvaluationServicePelita){
        this.pbklSumPayServ = pbklSumPayServ;
        this.pbkageInvServ = pbkageInvServ;
        this.pbkupdDataDateServ = pbkupdDataDateServ;
        this.pelitaPelitaUpdateDataDateService = pelitaPelitaUpdateDataDateService;
        this.pelitaPelitaAgeInvoiceService = pelitaPelitaAgeInvoiceService;
        this.pelitacomputeInvoiceAmountAfterTaxServicePelita = pelitacomputeInvoiceAmountAfterTaxServicePelita;
        this.pelitainAgingServicePelita = pelitainAgingServicePelita;
        this.pelitainvoiceStatusEvaluationServicePelita = pelitainvoiceStatusEvaluationServicePelita;
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void runPbkBatches() {
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.pbkageInvServ.combinedAgeInvoiceService(3);
        this.pbkupdDataDateServ.runupdateDataDate(53);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void runPelitaBatches() {
        this.pelitainvoiceStatusEvaluationServicePelita.combinePelitaInvoiceStatusEvaluation(58);
        this.pelitaPelitaAgeInvoiceService.combinedAgeInvoiceService(59);
        this.pelitacomputeInvoiceAmountAfterTaxServicePelita.combinedPelitaComputeInvoiceAmountAfterTax(57);
        this.pelitainAgingServicePelita.combinedPelitaAgeInvoiceService(56);
        this.pelitaPelitaUpdateDataDateService.runupdateDataDate(60);
    }

}