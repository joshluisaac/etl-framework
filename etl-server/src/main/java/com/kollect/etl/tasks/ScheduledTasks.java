package com.kollect.etl.tasks;

import com.kollect.etl.service.PbkAgeInvoiceService;
import com.kollect.etl.service.LumpSumPaymentService;
import com.kollect.etl.service.UpdateDataDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private LumpSumPaymentService lumpSumPaymentService;
    @Autowired
    private PbkAgeInvoiceService pbkAgeInvoiceService;
    @Autowired
    private UpdateDataDateService updateDataDateService;

    @Scheduled(cron = "0 0 5 * * *")
    public void runBatches() {
        this.lumpSumPaymentService.combinedLumpSumPaymentService(2);
        this.pbkAgeInvoiceService.combinedAgeInvoiceService(3);
        this.updateDataDateService.runupdateDataDate(53);
    }
}