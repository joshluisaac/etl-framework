package com.kollect.etl.tasks;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.MailClientService;
import com.kollect.etl.service.pbk.PbkAgeInvoiceService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import com.kollect.etl.service.pbk.PbkUpdateDataDateService;
import com.kollect.etl.service.pelita.*;
import com.kollect.etl.service.yyc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    /*Required services*/
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
    private BatchHistoryService batchHistoryService;
    private IReadWriteServiceProvider irwprovider;
    private YycInvoiceStatusEvaluationService yycInvoiceStatusEvaluationService;
    private YycAgeInvoiceService yycAgeInvoiceService;
    private YycInAgingService yycInAgingService;
    private YycUpdateDataDateService yycUpdateDataDateService;
    private ComponentProvider componentProvider;

    /*Required variables*/
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String intro ="This is an Automated Notification for KollectValley Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";

    /*Values coming in application.properties*/
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    @Value("${app.datasource_kv_production}")
    private String dataSource;
    /*This empty list is used to replace the prodStats query since Pelita is not on production yet.*/
    private List<Object> emptyList = new ArrayList<>();

    /*The constructor for the class to inject the necessary services*/
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
                          IReadWriteServiceProvider irwprovider,
                          YycInvoiceStatusEvaluationService yycInvoiceStatusEvaluationService,
                          YycAgeInvoiceService yycAgeInvoiceService,
                          YycInAgingService yycInAgingService,
                          YycUpdateDataDateService yycUpdateDataDateService,
                          ComponentProvider componentProvider){
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
        this.yycInvoiceStatusEvaluationService = yycInvoiceStatusEvaluationService;
        this.yycAgeInvoiceService = yycAgeInvoiceService;
        this.yycInAgingService = yycInAgingService;
        this.yycUpdateDataDateService = yycUpdateDataDateService;
        this.componentProvider = componentProvider;
    }

    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void runYycBatches() {
        this.yycInvoiceStatusEvaluationService.combinedYycInvoiceStatusEvaluation(63);
        this.componentProvider.taskSleep();
        this.yycAgeInvoiceService.combinedAgeInvoiceService(64);
        this.componentProvider.taskSleep();
        this.yycInAgingService.combinedYycAgeInvoiceService(65);
        this.componentProvider.taskSleep();
        this.yycUpdateDataDateService.runupdateDataDate(66);
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(recipient, "YYC - Daily Batch Report",intro,
                message, this.batchHistoryService.viewYycAfterSchedulerUat(), emptyList);
    }

    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void runPbkBatches() {
        this.pbkageInvServ.combinedAgeInvoiceService(3);
        this.componentProvider.taskSleep();
        this.pbkupdDataDateServ.runupdateDataDate(53);
        this.componentProvider.taskSleep();
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(recipient, "PBK - Daily Batch Report",intro,
                message, this.batchHistoryService.viewPbkAfterSchedulerUat(), this.batchHistoryService.viewPbkAfterSchedulerProd());
    }

    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void runPelitaBatches() {
        this.pelitaComputeInvoiceAmountAfterTaxServicePelita.combinedPelitaComputeInvoiceAmountAfterTax(57);
        this.componentProvider.taskSleep();
        this.pelitaInvoiceStatusEvaluationServicePelita.combinePelitaInvoiceStatusEvaluation(58);
        this.componentProvider.taskSleep();
        this.pelitaInAgingServicePelita.combinedPelitaInAgingService(56);
        this.componentProvider.taskSleep();
        this.pelitaAgeInvoiceService.combinedAgeInvoiceService(59);
        this.componentProvider.taskSleep();
        this.pelitaUpdateDataDateService.runupdateDataDate(60);
        this.componentProvider.taskSleep();
        this.pelitaComputeDebitAmountAfterTaxService.combinedPelitaComputeDebitAmountAfterTax(61);
        this.mailClientService.sendAfterBatch(recipient, "Pelita - Daily Batch Report",intro,
                message, this.batchHistoryService.viewPelitaAfterSchedulerUat(), emptyList);
    }

    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runYycSequences(){
        this.yycQuerySequenceService.runYycSequenceQuery(62);
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(recipient,"YYC - Daily Batch Report" ,intro,
                message, this.batchHistoryService.viewYycSeqAfterSchedulerUat(),
                this.batchHistoryService.viewYycSeqAfterSchedulerProd());
    }

    @Scheduled(fixedDelay = 600000)
    public void runKeepConnectionAliveHack(){
        this.irwprovider.executeQuery(dataSource, "getUpdateDataDateToKeepConnectionOpen", null);
    }
}