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
                          DataConnectorNotification dcNotificationService){
        this.pbklSumPayServ = pbklSumPayServ;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.batchHistoryService = batchHistoryService;
        this.iRWProvider = iRWProvider;
        this.componentProvider = componentProvider;
        this.emailSenderService=emailSenderService;
        this.dcNotificationService = dcNotificationService;
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
        emailSenderService.sendAfterBatch(fromEmail,
                recipient, "YYC - Daily Batch Report",
                batchHistoryService.viewYycAfterSchedulerUat(),
                batchHistoryService.viewYycAfterSchedulerProd());
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
        emailSenderService.sendAfterBatch(fromEmail,
                recipient, "PBK - Daily Batch Report",
                batchHistoryService.viewPbkAfterSchedulerUat(),
                batchHistoryService.viewPbkAfterSchedulerProd());
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
        this.componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail,
                recipient, "Pelita - Daily Batch Report",
                batchHistoryService.viewPelitaAfterSchedulerUat(),
                emptyList);
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
        emailSenderService.sendAfterBatch(fromEmail,
                recipient+",syazman@kollect.my,biman@kollect.my",
                "ICT Zone - Daily Batch Report",
                batchHistoryService.viewIctZoneAfterSchedulerUat(),
                emptyList);
    }

    @Scheduled(cron = "${app.scheduler.runat7pm}")
    public void runYycSequences(){
        this.yycQuerySequenceService.runYycSequenceQuery(62);
        this.componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail,
                recipient,
                "YYC - Daily Sequence Batch Report",
                batchHistoryService.viewYycSeqAfterSchedulerUat(),
                batchHistoryService.viewYycSeqAfterSchedulerProd());
    }

    @Scheduled(fixedDelay = 600000)
    public void runKeepConnectionAliveHack(){
        this.iRWProvider.executeQuery(prodDataSource,
                "getUpdateDataDateToKeepConnectionOpen", null);
    }
    
    //@Scheduled(fixedDelay = 120000)
    public void sendPelitaExtractEmail() throws IOException {
      String title = "Pelita - Daily Extraction Metrics";
        logger.info("Extraction Email Scheduler Running...");
      emailSenderService.sendExtractionEmail(pelitaExtractionPath,title);
    }
    
    //@Scheduled(fixedDelay = 120000)
    public void sendDataConnectorStats() throws IOException {
      String title = "Pelita - Daily Data Loading";
      String context = "pelita";
      logger.info("DataConnector Email Notification Running...at {} using thread {}", System.currentTimeMillis(), Thread.currentThread().getName());
      dcNotificationService.execute(title, pelitaDcServerLogPath, context);
    }
    
    
    
    
}