package com.kollect.etl.tasks;

import com.kollect.etl.service.PbkAgeInvoiceService;
import com.kollect.etl.service.PbkLumpSumPaymentService;
import com.kollect.etl.service.PbkUpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private PbkLumpSumPaymentService pbkLumpSumPaymentService;
    @Autowired
    private PbkAgeInvoiceService pbkAgeInvoiceService;
    @Autowired
    private PbkUpdateDataDateService pbkUpdateDataDateService;

    @Scheduled(cron = "0 0 5 * * *")
    public void runBatches() {
        this.pbkLumpSumPaymentService.combinedLumpSumPaymentService(2);
        this.pbkAgeInvoiceService.combinedAgeInvoiceService(3);
        this.pbkUpdateDataDateService.runupdateDataDate(53);
    }
}