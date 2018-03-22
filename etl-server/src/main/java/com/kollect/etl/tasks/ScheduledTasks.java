package com.kollect.etl.tasks;

import com.kollect.etl.service.AgeInvoiceService;
import com.kollect.etl.service.LumpSumPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private LumpSumPaymentService lumpSumPaymentService;
    @Autowired
    private AgeInvoiceService ageInvoiceService;

    @Scheduled(cron = "0 0 5 * * *")
    public void runBatches() {
        this.lumpSumPaymentService.combinedLumpSumPaymentService();
        this.ageInvoiceService.combinedAgeInvoiceService(63);
    }
}