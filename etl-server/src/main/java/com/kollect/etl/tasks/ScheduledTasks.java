package com.kollect.etl.tasks;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.MailClientService;
import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import com.kollect.etl.service.yyc.YycQuerySequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    /*Required services*/
    private PbkLumpSumPaymentService pbklSumPayServ;
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private YycQuerySequenceService yycQuerySequenceService;
    private MailClientService mailClientService;
    private BatchHistoryService batchHistoryService;
    private IReadWriteServiceProvider iRWProvider;
    private ComponentProvider componentProvider;

    /*Values coming in application.properties*/
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    @Value("${app.datasource_kv_production}")
    private String prodDataSource;
    private @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> yycDataSource;
    private @Value("#{'${app.datasource_all}'.split(',')}")
    List<String> pbkDataSource;
    private @Value("${app.datasource_pelita_test}")
    List<String> pelitaDataSource;
    private @Value("${app.datasource_ictzone}")
    List<String> ictZoneDataSource;
    /*This empty list is used to replace the prodStats query since Pelita is not on production yet.*/
    private List<Object> emptyList = new ArrayList<>();

    /*The constructor for the class to inject the necessary services*/
    @Autowired
    public ScheduledTasks(PbkLumpSumPaymentService pbklSumPayServ,
                          AsyncBatchExecutorService asyncBatchExecutorService,
                          UpdateDataDateService updateDataDateService,
                          YycQuerySequenceService yycQuerySequenceService,
                          MailClientService mailClientService,
                          BatchHistoryService batchHistoryService,
                          IReadWriteServiceProvider iRWProvider,
                          ComponentProvider componentProvider){
        this.pbklSumPayServ = pbklSumPayServ;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.mailClientService = mailClientService;
        this.batchHistoryService = batchHistoryService;
        this.iRWProvider = iRWProvider;
        this.componentProvider = componentProvider;
    }

    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void runYycBatches() {
        this.asyncBatchExecutorService.execute(63, yycDataSource,
                "getYycInvoiceStatus",
                "updateYycInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(64, yycDataSource,
                "getYycAgeInvoice", "updateYycAgeInvoice",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(65, yycDataSource,
                "getYycInAging", "updateYycInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(66,
                yycDataSource, "yycUpdateDataDate");
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(recipient,
                "YYC - Daily Batch Report", this.batchHistoryService.viewYycAfterSchedulerUat(),
                this.batchHistoryService.viewYycAfterSchedulerProd());
    }

    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void runPbkBatches() {
        this.asyncBatchExecutorService.execute(3, pbkDataSource,
                "getPbkAgeInvoice",
                "updatePbkAgeInvoice",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(53, pbkDataSource, "pbkUpdateDataDate");
        this.componentProvider.taskSleep();
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(recipient,
                "PBK - Daily Batch Report",
                this.batchHistoryService.viewPbkAfterSchedulerUat(),
                this.batchHistoryService.viewPbkAfterSchedulerProd());
    }

    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void runPelitaBatches() {
        this.asyncBatchExecutorService.execute(57, pelitaDataSource,
                "getPelitaInvoiceAmountAfterTax",
                "updatePelitaInvoiceAmountAfterTax",
                "COMPUTE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(58, pelitaDataSource,
                "getPelitaInvoiceStatus",
                "updatePelitaInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(56,
                pelitaDataSource, "getPelitaInAging",
                "updatePelitaInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(59,
                pelitaDataSource, "getPelitaAgeInvoices",
                "updatePelitaAgeInvoices",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(60,
                pelitaDataSource, "pelitaUpdateDataDate");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(61,
                pelitaDataSource, "getPelitaDebitAmountAfterTax",
                "updatePelitaDebitAmountAfterTax",
                "COMPUTE_DEBIT");
        this.mailClientService.sendAfterBatch(recipient,
                "Pelita - Daily Batch Report",
                this.batchHistoryService.viewPelitaAfterSchedulerUat(), emptyList);
    }

    @Scheduled(cron = "${app.scheduler.runat6am}")
    public void runIctZoneBatches(){
        this.asyncBatchExecutorService.execute(68,
                ictZoneDataSource,
                "getIctZoneInvoiceAmountAfterTax",
                "updateIctZoneInvoiceAmountAfterTax",
                "COMPUTE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(69,
                ictZoneDataSource, "getIctZoneInvoiceStatus",
                "updateIctZoneInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(67,
                ictZoneDataSource, "getIctZoneInAging",
                "updateIctZoneInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(70, ictZoneDataSource,
                "getIctZoneAgeInvoices",
                "updateIctZoneAgeInvoices", "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(71, ictZoneDataSource,
                "ictZoneUpdateDataDate");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(72, ictZoneDataSource,
                "getIctZoneDebitAmountAfterTax",
                "updateIctZoneDebitAmountAfterTax", "COMPUTE_DEBIT");
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(
                recipient+",syazman@kollect.my,biman@kollect.my",
                "ICT Zone - Daily Batch Report",
                this.batchHistoryService.viewIctZoneAfterSchedulerUat(), emptyList);
    }

    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runYycSequences(){
        this.yycQuerySequenceService.runYycSequenceQuery(62);
        this.componentProvider.taskSleep();
        this.mailClientService.sendAfterBatch(recipient,
                "YYC - Daily Batch Report" ,
                this.batchHistoryService.viewYycSeqAfterSchedulerUat(),
                this.batchHistoryService.viewYycSeqAfterSchedulerProd());
    }

    @Scheduled(fixedDelay = 600000)
    public void runKeepConnectionAliveHack(){
        this.iRWProvider.executeQuery(prodDataSource,
                "getUpdateDataDateToKeepConnectionOpen", null);
    }
}