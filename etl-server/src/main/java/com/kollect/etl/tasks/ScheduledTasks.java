package com.kollect.etl.tasks;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.MailClientService;
import com.kollect.etl.service.pbk.PbkAgeInvoiceService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import com.kollect.etl.service.pbk.PbkUpdateDataDateService;
import com.kollect.etl.service.pelita.*;
import com.kollect.etl.service.yyc.YycQuerySequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

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
    private YycQuerySequenceService yycQuerySequenceService;
    private MailClientService mailClientService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private BatchHistoryService batchHistoryService;
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    private String intro ="This is an Automated Notification for KollectValley Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";
    private IReadWriteServiceProvider irwprovider;
    @Value("${app.datasource_kv_production}")
    private String dataSource;

    @Autowired
    public ScheduledTasks(PbkLumpSumPaymentService pbklSumPayServ,
                          PbkAgeInvoiceService pbkageInvServ,
                          PbkUpdateDataDateService pbkupdDataDateServ,
                          PelitaUpdateDataDateService pelitaUpdateDataDateService,
                          PelitaAgeInvoiceService pelitaAgeInvoiceService,
                          PelitaComputeInvoiceAmountAfterTaxService pelitaComputeInvoiceAmountAfterTaxServicePelita,
                          PelitaInAgingService pelitaInAgingServicePelita,
                          PelitaInvoiceStatusEvaluationService pelitaInvoiceStatusEvaluationServicePelita,
                          PelitaComputeDebitAmountAfterTaxService pelitaComputeDebitAmountAfterTaxService,
                          YycQuerySequenceService yycQuerySequenceService,
                          MailClientService mailClientService,
                          BatchHistoryService batchHistoryService,
                          IReadWriteServiceProvider irwprovider){
        this.pbklSumPayServ = pbklSumPayServ;
        this.pbkageInvServ = pbkageInvServ;
        this.pbkupdDataDateServ = pbkupdDataDateServ;
        this.pelitaUpdateDataDateService = pelitaUpdateDataDateService;
        this.pelitaAgeInvoiceService = pelitaAgeInvoiceService;
        this.pelitaComputeInvoiceAmountAfterTaxServicePelita = pelitaComputeInvoiceAmountAfterTaxServicePelita;
        this.pelitaInAgingServicePelita = pelitaInAgingServicePelita;
        this.pelitaInvoiceStatusEvaluationServicePelita = pelitaInvoiceStatusEvaluationServicePelita;
        this.pelitaComputeDebitAmountAfterTaxService = pelitaComputeDebitAmountAfterTaxService;
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.mailClientService = mailClientService;
        this.batchHistoryService = batchHistoryService;
        this.irwprovider = irwprovider;
    }

    private void taskSleep(){
        try {
            System.out.println("Rejuvenating for ten seconds...");
            TimeUnit.SECONDS.sleep(10);
        }catch (Exception e){
            System.out.println("An error occured during thread sleep." +e);
        }
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void runPbkBatches() {
        this.pbkageInvServ.combinedAgeInvoiceService(3);
        this.taskSleep();
        this.pbkupdDataDateServ.runupdateDataDate(53);
        this.taskSleep();
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.taskSleep();
        this.mailClientService.sendAfterBatch(recipient, "PBK - Daily Batch Report",intro,
                message, this.batchHistoryService.viewPbkAfterSchedulerUat(), this.batchHistoryService.viewPbkAfterSchedulerProd());
    }

    @Scheduled(cron = "0 30 5 * * *")
    public void runPelitaBatches() {
        this.pelitaInvoiceStatusEvaluationServicePelita.combinePelitaInvoiceStatusEvaluation(58);
        this.taskSleep();
        this.pelitaComputeInvoiceAmountAfterTaxServicePelita.combinedPelitaComputeInvoiceAmountAfterTax(57);
        this.taskSleep();
        this.pelitaInAgingServicePelita.combinedPelitaAgeInvoiceService(56);
        this.taskSleep();
        this.pelitaAgeInvoiceService.combinedAgeInvoiceService(59);
        this.taskSleep();
        this.pelitaUpdateDataDateService.runupdateDataDate(60);
        this.taskSleep();
        this.pelitaComputeDebitAmountAfterTaxService.combinedPelitaComputeDebitAmountAfterTax(61);
        this.mailClientService.sendAfterBatch(recipient, "Pelita - Daily Batch Report",intro,
                message, this.batchHistoryService.viewPelitaAfterSchedulerUat(), null);
    }

    @Scheduled(cron = "0 0 19 * * *")
    public void runYycBatches(){
        this.yycQuerySequenceService.runYycSequenceQuery(62);
        this.taskSleep();
        this.mailClientService.sendAfterBatch(recipient,"YYC - Daily Batch Report" ,intro,
                message, this.batchHistoryService.viewYycAfterSchedulerUat(), this.batchHistoryService.viewYycAfterSchedulerProd());
    }

    @Scheduled(fixedDelay = 600000)
    public void runKeepConnectionAliveHack(){
        this.irwprovider.executeQuery(dataSource, "getUpdateDataDateToKeepConnectionOpen", null);
    }
}