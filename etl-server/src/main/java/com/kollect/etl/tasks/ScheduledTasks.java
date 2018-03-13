package com.kollect.etl.tasks;

import com.kollect.etl.controller.AgeInvoiceController;
import com.kollect.etl.controller.CalculateOutstandingController;
import com.kollect.etl.controller.LumpSumPaymentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private LumpSumPaymentController lumpSumPaymentController;
    @Autowired
    private CalculateOutstandingController calculateOutstandingController;
    @Autowired
    private AgeInvoiceController ageInvoiceController;

    @Scheduled(cron = "0 0 5 * * *")
    public void runBatches (){
        this.lumpSumPaymentController.selectLumSumPayment();
        this.calculateOutstandingController.calcOutstanding(63);
        this.ageInvoiceController.ageInvoice(63);
    }
}