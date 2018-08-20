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

    private void runYycBatches(List<String> datasource, List<Object> uatStats, List<Object> prodStats, String subject) {
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
        this.componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail,
                recipient, subject,
                uatStats,
                prodStats);
    }

    /*comment this out on production*/
    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void runUatYycBatches() {
        runYycBatches(yycUatDataSource, batchHistoryService.viewYycAfterSchedulerUat(), emptyList, "YYC - UAT Daily Batch Report");
    }

    /*comment this out on uat*/
    @Scheduled(cron = "${app.scheduler.runat430am}")
    public void runProdYycBatches() {
        runYycBatches(prodDataSource, emptyList, batchHistoryService.viewYycAfterSchedulerProd(), "YYC - Production Daily Batch Report");
    }

    private void runPbkBatches(List<String> dataSource, List<Object> uatStats, List<Object> prodStats, String subject) {
        this.asyncBatchExecutorService.execute(3, dataSource,
                "getPbkAgeInvoice",
                "updatePbkAgeInvoice",
                "AGE_INV");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(53, dataSource, "pbkUpdateDataDate");
        this.componentProvider.taskSleep();
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail,
                recipient, subject,
                uatStats,
                prodStats);
    }

    /*comment this out on production*/
    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void runUatPbkBatches() {
        runPbkBatches(pbkUatDataSource, batchHistoryService.viewPbkAfterSchedulerUat(), emptyList, "PBK - UAT Daily Batch Report");
    }

    /*comment this out on uat*/
    @Scheduled(cron = "${app.scheduler.runat5am}")
    public void runProdPbkBatches() {
        runPbkBatches(prodDataSource, emptyList, batchHistoryService.viewPbkAfterSchedulerProd(), "PBK - Production Daily Batch Report");
    }

    private void runPelitaBatches(List<String> dataSource, List<Object> uatStats, List<Object> prodStats, String subject) {
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
        emailSenderService.sendAfterBatch(fromEmail,
                recipient, subject,
                uatStats,
                prodStats);
    }

    /*comment this out on production*/
    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void runUatPelitaBatches() {
        runPelitaBatches(pelitaUatDataSource, batchHistoryService.viewPelitaAfterSchedulerUat(), emptyList, "Pelita - UAT Daily Batch Report");
    }

    /*comment this out on uat*/
/*    @Scheduled(cron = "${app.scheduler.runat530am}")
    public void runProdPelitaBatches() {
        runPelitaBatches(prodDataSource, emptyList, batchHistoryService.viewPelitaAfterSchedulerProd(), "Pelita - Production Daily Batch Report");
    }*/

    private void runIctZoneBatches(List<String> dataSource, List<Object> uatStats, List<Object> prodStats, String subject) {
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
        emailSenderService.sendAfterBatch(fromEmail,
                recipient + ",syazman@kollect.my,biman@kollect.my",
                subject,
                uatStats,
                prodStats);
    }

    /*comment this out on production*/
    @Scheduled(cron = "${app.scheduler.runat6am}")
    public void runUatIctZoneBatches() {
        runIctZoneBatches(ictZoneUatDataSource, batchHistoryService.viewIctZoneAfterSchedulerUat(), emptyList, "ICT Zone - UAT Daily Batch Report");
    }

    /*comment this out on uat*/
    @Scheduled(cron = "${app.scheduler.runat6am}")
    public void runProdIctZoneBatches() {
        runIctZoneBatches(prodDataSource, emptyList, batchHistoryService.viewIctZoneAfterSchedulerProd(), "ICT Zone - Production Daily Batch Report");
    }

    private void runYycSequences(List<String> datasource, List<Object> uatStats, List<Object> prodStats, String subject) {
        this.yycQuerySequenceService.runYycSequenceQuery(62, datasource);
        this.componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail,
                recipient,
                subject,
                uatStats,
                prodStats);
    }

    /*comment this out on production*/
    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runUatYycSequences() {
        runYycSequences(yycUatDataSource, batchHistoryService.viewYycSeqAfterSchedulerUat(), emptyList, "YYC - UAT Daily Sequence Batch Report");
    }

    /*comment this out on uat*/
    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runProdYycSequences() {
        runYycSequences(yycUatDataSource, emptyList, batchHistoryService.viewYycSeqAfterSchedulerProd(), "YYC - Production Daily Sequence Batch Report");
    }

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