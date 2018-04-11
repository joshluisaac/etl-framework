package com.kollect.etl.tasks;

import com.kollect.etl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private PbkLumpSumPaymentService pbkLumpSumPaymentService;
    private PbkAgeInvoiceService pbkAgeInvoiceService;
    private PbkUpdateDataDateService pbkUpdateDataDateService;
    private PelitaUpdateDataDateService pelitaUpdateDataDateService;
    private PelitaAgeInvoiceService pelitaAgeInvoiceService;
    private PelitaComputeInvoiceAmountAfterTaxService pelitaComputeInvoiceAmountAfterTaxService;
    private PelitaInAgingService pelitaInAgingService;

    @Autowired
    public ScheduledTasks(PbkLumpSumPaymentService pbkLumpSumPaymentService, PbkAgeInvoiceService pbkAgeInvoiceService,
                          PbkUpdateDataDateService pbkUpdateDataDateService, PelitaUpdateDataDateService pelitaUpdateDataDateService,
                          PelitaAgeInvoiceService pelitaAgeInvoiceService,
                          PelitaComputeInvoiceAmountAfterTaxService pelitaComputeInvoiceAmountAfterTaxService,
                          PelitaInAgingService pelitaInAgingService){
        this.pbkLumpSumPaymentService = pbkLumpSumPaymentService;
        this.pbkAgeInvoiceService = pbkAgeInvoiceService;
        this.pbkUpdateDataDateService = pbkUpdateDataDateService;
        this.pelitaUpdateDataDateService = pelitaUpdateDataDateService;
        this.pelitaAgeInvoiceService = pelitaAgeInvoiceService;
        this.pelitaComputeInvoiceAmountAfterTaxService = pelitaComputeInvoiceAmountAfterTaxService;
        this.pelitaInAgingService = pelitaInAgingService;
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void runPbkBatches() {
        this.pbkLumpSumPaymentService.combinedLumpSumPaymentService(2);
        this.pbkAgeInvoiceService.combinedAgeInvoiceService(3);
        this.pbkUpdateDataDateService.runupdateDataDate(53);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void runPelitaBatches() {
        this.pelitaAgeInvoiceService.combinedAgeInvoiceService(59);
        this.pelitaComputeInvoiceAmountAfterTaxService.combinedPelitaComputeInvoiceAmountAfterTax(57);
        this.pelitaInAgingService.combinedPelitaAgeInvoiceService(56);
        this.pelitaUpdateDataDateService.runupdateDataDate(60);
    }

}