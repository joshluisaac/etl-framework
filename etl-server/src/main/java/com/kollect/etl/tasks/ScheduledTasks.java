package com.kollect.etl.tasks;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.DataConnectorNotification;
import com.kollect.etl.service.app.EmailSenderService;
import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import com.kollect.etl.service.yyc.YycQuerySequenceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    /*Required services*/
    private PbkLumpSumPaymentService pbklSumPayServ;
    private AsyncBatchExecutorService asyncBatchExecutorService;
    private UpdateDataDateService updateDataDateService;
    private YycQuerySequenceService yycQuerySequenceService;
    private BatchHistoryService batchHistoryService;
    private IReadWriteServiceProvider iRWProvider;
    private ComponentProvider componentProvider;
    private EmailSenderService emailSenderService;
    private DataConnectorNotification dcNotificationService;

    /*Values coming in application.properties*/
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    private @Value("${app.datasource_kv_production}")
    List<String> prodDataSource;
    private @Value("${app.datasource_pelita_test}")
    List<String> yycUatDataSource;
    private @Value("${app.datasource_pbk1}")
    List<String> pbkUatDataSource;
    private @Value("${app.datasource_pelita_test}")
    List<String> pelitaUatDataSource;
    private @Value("${app.datasource_ictzone}")
    List<String> ictZoneUatDataSource;
    @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> dataSourceAll2;
    @Value("#{'${app.datasource_all}'.split(',')}")
    List<String> dataSourceAll;


    @Value("${app.pelitaExtractionPath}")
    private String pelitaExtractionPath;
    @Value("${app.pelitaDcServerLogPath}")
    private String pelitaDcServerLogPath;


    private String fromEmail = "datareceived@kollect.my";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    /*This empty list is used to replace the prodStats query since Pelita is not on production yet.*/
    private List<Object> emptyList = new ArrayList<>();

    /*The constructor for the class to inject the necessary services*/
    @Autowired
    public ScheduledTasks(PbkLumpSumPaymentService pbklSumPayServ,
                          AsyncBatchExecutorService asyncBatchExecutorService,
                          UpdateDataDateService updateDataDateService,
                          YycQuerySequenceService yycQuerySequenceService,
                          BatchHistoryService batchHistoryService,
                          IReadWriteServiceProvider iRWProvider,
                          ComponentProvider componentProvider,
                          EmailSenderService emailSenderService,
                          DataConnectorNotification dcNotificationService) {
        this.pbklSumPayServ = pbklSumPayServ;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.batchHistoryService = batchHistoryService;
        this.iRWProvider = iRWProvider;
        this.componentProvider = componentProvider;
        this.emailSenderService = emailSenderService;
        this.dcNotificationService = dcNotificationService;
    }

    @Scheduled(cron = "${app.scheduler.runat7am}")
    public void sendBatchEmails(){
        emailSenderService.sendAfterBatch(fromEmail, recipient, "YYC - Daily Batch report",
                batchHistoryService.viewYycAfterSchedulerUat(), batchHistoryService.viewYycAfterSchedulerProd());
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail, recipient, "PBK - Daily Batch Report",
                batchHistoryService.viewPbkAfterSchedulerUat(), batchHistoryService.viewPbkAfterSchedulerProd());
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail, recipient, "Pelita - Daily Batch Report",
                batchHistoryService.viewPelitaAfterSchedulerUat(), batchHistoryService.viewPelitaAfterSchedulerProd());
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail,recipient + ",syazman@kollect.my,biman@kollect.my",
                "ICT Zone - Daily Batch Report", batchHistoryService.viewIctZoneAfterSchedulerUat(),
                emptyList);
        componentProvider.taskSleep();
        }

    private void runYycBatches(List<String> datasource) {
        this.asyncBatchExecutorService.execute(63, datasource,
                "getYycInvoiceStatus",
                "updateYycInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(64, datasource,
                "getYycAgeInvoice", "updateYycAgeInvoice",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(65, datasource,
                "getYycInAging", "updateYycInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(66,
                datasource, "yycUpdateDataDate");
    }

    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void yycBatches(){
        runYycBatches(dataSourceAll2);
    }

/*    *//*comment this out on production*//*
    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void runUatYycBatches() {
        runYycBatches(yycUatDataSource);
    }

    *//*comment this out on uat*//*
    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void runProdYycBatches() {
        runYycBatches(prodDataSource);
    }*/

    private void runPbkBatches(List<String> dataSource) {
        this.asyncBatchExecutorService.execute(3, dataSource,
                "getPbkAgeInvoice",
                "updatePbkAgeInvoice",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(53, dataSource, "pbkUpdateDataDate");
        this.componentProvider.taskSleep();
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.componentProvider.taskSleep();
    }

    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void pbkBatches(){
        runPbkBatches(dataSourceAll);
    }

/*    *//*comment this out on production*//*
    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void runUatPbkBatches() {
        runPbkBatches(pbkUatDataSource);
    }

    *//*comment this out on uat*//*
    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void runProdPbkBatches() {
        runPbkBatches(prodDataSource);
    }*/

    private void runPelitaBatches(List<String> dataSource) {
        this.asyncBatchExecutorService.execute(57, dataSource,
                "getPelitaInvoiceAmountAfterTax",
                "updatePelitaInvoiceAmountAfterTax",
                "COMPUTE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(58, dataSource,
                "getPelitaInvoiceStatus",
                "updatePelitaInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(56,
                dataSource, "getPelitaInAging",
                "updatePelitaInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(59,
                dataSource, "getPelitaAgeInvoices",
                "updatePelitaAgeInvoices",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(60,
                dataSource, "pelitaUpdateDataDate");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(61,
                dataSource, "getPelitaDebitAmountAfterTax",
                "updatePelitaDebitAmountAfterTax",
                "COMPUTE_DEBIT");
        this.componentProvider.taskSleep();
    }

    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void pelitaBatches(){
        runPelitaBatches(dataSourceAll2);
    }

/*    *//*comment this out on production*//*
    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void runUatPelitaBatches() {
        runPelitaBatches(pelitaUatDataSource);
    }

    *//*comment this out on uat*//*
    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void runProdPelitaBatches() {
        runPelitaBatches(prodDataSource);
    }*/

    private void runIctZoneBatches(List<String> dataSource) {
        this.asyncBatchExecutorService.execute(68,
                dataSource,
                "getIctZoneInvoiceAmountAfterTax",
                "updateIctZoneInvoiceAmountAfterTax",
                "COMPUTE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(69,
                dataSource, "getIctZoneInvoiceStatus",
                "updateIctZoneInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(67,
                dataSource, "getIctZoneInAging",
                "updateIctZoneInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(70, dataSource,
                "getIctZoneAgeInvoices",
                "updateIctZoneAgeInvoices", "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(71, dataSource,
                "ictZoneUpdateDataDate");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(72, dataSource,
                "getIctZoneDebitAmountAfterTax",
                "updateIctZoneDebitAmountAfterTax", "COMPUTE_DEBIT");
        this.componentProvider.taskSleep();
    }

    @Scheduled(cron = "${app.scheduler.runat6am}")
    public void ictZoneBatches(){
        runIctZoneBatches(ictZoneUatDataSource);
    }

/*    *//*comment this out on production*//*
    @Scheduled(cron = "${app.scheduler.runat6am}")
    public void runUatIctZoneBatches() {
        runIctZoneBatches(ictZoneUatDataSource);
    }

    *//*comment this out on uat*//*
    @Scheduled(cron = "${app.scheduler.runat6am}")
    public void runProdIctZoneBatches() {
        runIctZoneBatches(prodDataSource);
    }*/

    private void runYycSequences(List<String> datasource) {
        this.yycQuerySequenceService.runYycSequenceQuery(62, datasource);
        this.componentProvider.taskSleep();
    }

    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void yycSequences(){
        runYycSequences(dataSourceAll2);
        emailSenderService.sendAfterBatch(fromEmail, recipient,"YYC - Daily Sequence Batch Report",
                batchHistoryService.viewYycSeqAfterSchedulerUat(), batchHistoryService.viewYycSeqAfterSchedulerProd());
    }

/*    *//*comment this out on production*//*
    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runUatYycSequences() {
        runYycSequences(yycUatDataSource);
    }

    *//*comment this out on uat*//*
    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runProdYycSequences() {
        runYycSequences(yycUatDataSource);
    }*/

    @Scheduled(fixedDelay = 600000)
    public void runKeepConnectionAliveHack() {
        this.iRWProvider.executeQuery(prodDataSource.get(0),
                "getUpdateDataDateToKeepConnectionOpen", null);
    }

    //@Scheduled(fixedDelay = 120000)
    public void sendPelitaExtractEmail() throws IOException {
        String title = "Pelita - Daily Extraction Metrics";
        logger.info("Extraction Email Scheduler Running...");
        emailSenderService.sendExtractionEmail(pelitaExtractionPath, title);
    }

    //@Scheduled(fixedDelay = 120000)
    public void sendPelitaDataConnectorStatsEmail() throws IOException {
        String title = "Pelita - Daily Data Loading";
        String context = "pelita";
        logger.info("DataConnector Email Notification Running...at {} using thread {}", System.currentTimeMillis(), Thread.currentThread().getName());
        dcNotificationService.execute(title, pelitaDcServerLogPath, context);
    }


}