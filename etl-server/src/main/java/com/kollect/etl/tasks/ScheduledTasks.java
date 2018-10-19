package com.kollect.etl.tasks;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.DataConnectorNotification;
import com.kollect.etl.service.app.EmailSenderService;
import com.kollect.etl.service.commonbatches.AsyncBatchExecutorService;
import com.kollect.etl.service.commonbatches.UpdateDataDateService;
import com.kollect.etl.service.pbk.PbkLumpSumPaymentService;
import com.kollect.etl.service.pelita.UpdateInvoiceNumber;
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
    private UpdateInvoiceNumber updateInvoiceNumber;

    /*Values coming in application.properties*/
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    private @Value("${app.datasource_kv_production}")
    List<String> prodDataSource;
    private @Value("${app.datasource_kv_uat}")
    List<String> kvUat;
    @Value("#{'${app.datasource_all2}'.split(',')}")
    List<String> dataSourceAll2;
    @Value("#{'${app.datasource_all}'.split(',')}")
    List<String> dataSourceAll;


    @Value("${app.pelitaExtractionPath}")
    private String pelitaExtractionPath;
    @Value("${app.pelitaDcServerLogPath}")
    private String pelitaDcServerLogPath;


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
                          DataConnectorNotification dcNotificationService,
                          UpdateInvoiceNumber updateInvoiceNumber) {
        this.pbklSumPayServ = pbklSumPayServ;
        this.asyncBatchExecutorService = asyncBatchExecutorService;
        this.updateDataDateService = updateDataDateService;
        this.yycQuerySequenceService = yycQuerySequenceService;
        this.batchHistoryService = batchHistoryService;
        this.iRWProvider = iRWProvider;
        this.componentProvider = componentProvider;
        this.emailSenderService = emailSenderService;
        this.dcNotificationService = dcNotificationService;
        this.updateInvoiceNumber=updateInvoiceNumber;
    }

    private void runYycSequences(List<String> datasource) {
        this.yycQuerySequenceService.runYycSequenceQuery(62, datasource);
        this.componentProvider.taskSleep();
    }

    @Scheduled(cron = "${app.scheduler.runat1220am}")
    public void yycSequences() {
        runYycSequences(dataSourceAll2);
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
        this.componentProvider.taskSleep();
        asyncBatchExecutorService.execute(81, datasource,
                "getYycPhoneNosNotListed",
                "updateYycPhoneNosNotListed", "YYC_DEF_PHONE");
        this.componentProvider.taskSleep();
        asyncBatchExecutorService.execute(82, datasource,
                "getYycDefPicName", "updateYycPicName", "YYC_DEF_PIC");
        this.componentProvider.taskSleep();
        asyncBatchExecutorService.execute(91, datasource,
                "getYycDefEmails", "deleteYycDefEmails", "YYC_DEF_EMAILS");
    }

    @Scheduled(cron = "${app.scheduler.runat2am}")
    public void yycBatches() {
        runYycBatches(dataSourceAll2);
    }

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

    @Scheduled(cron = "${app.scheduler.runat215am}")
    public void pbkBatches() {
        runPbkBatches(dataSourceAll);
    }

    private void runPelitaBatches(List<String> dataSource) {
        this.asyncBatchExecutorService.execute(57, dataSource,
                "getPelitaInvoiceAmountAfterTax",
                "updatePelitaInvoiceAmountAfterTax",
                "COMPUTE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(84, dataSource, "getPelitaOutstanding",
                "updatePelitaInvoiceOutstanding", "PELITA_INV_OUTSTANDING");
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
        this.updateInvoiceNumber.execute(80, "getPelitaInvoiceNumbers",
                "updatePelitaInvoiceNumbers");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(85, dataSource, "getTrxCodeAndDesc",
                "updateTrxCode", "UPDATE_TRX_CODE");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(86, dataSource, "getTrxCodeAndDesc",
                "updateTrxDesc", "UPDATE_TRX_CODE");
        this.componentProvider.taskSleep();
        asyncBatchExecutorService.execute(88, dataSource, "getPelitaEmailsDefault",
                "deletePelitaEmailsDefault", "PELITA_DEF_EMAILS");
        asyncBatchExecutorService.execute(89, dataSource, "getPelitaPhoneNosDefault",
                "deletePelitaPhoneNosDefault", "PELITA_DEF_PHONES");
        asyncBatchExecutorService.execute(90, dataSource, "getPelitaPicDefault",
                "deletePelitaPicDefault", "PELITA_DEF_PIC");
    }

    @Scheduled(cron = "${app.scheduler.runat230am}")
    public void pelitaBatches() {
        runPelitaBatches(dataSourceAll2);
    }

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
    }

    @Scheduled(cron = "${app.scheduler.runat3am}")
    public void ictZoneBatches() {
        runIctZoneBatches(kvUat);
    }

    private void runCcoBatches(List<String> dataSource) {
        this.asyncBatchExecutorService.execute(76, dataSource,
                "getCcoAgeInvoices",
                "updateCcoAgeInvoices", "AGE_INV");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(73,
                dataSource, "getCcoInAging",
                "updateCcoInAging",
                "IN_AGING");
        this.componentProvider.taskSleep();
        this.asyncBatchExecutorService.execute(75,
                dataSource, "getCcoInvoiceStatus",
                "updateCcoInvoiceStatus",
                "INV_STAT_EVAL");
        this.componentProvider.taskSleep();
        this.updateDataDateService.runUpdateDataDate(77, dataSource,
                "ccoUpdateDataDate");
        this.componentProvider.taskSleep();
        asyncBatchExecutorService.execute(79, dataSource,
                "selectCcoCustomerEmailsWithDash",
                "updateCcoCustomerEmailsWithDash", "CCO_DEF_EMAILS");
        this.componentProvider.taskSleep();
        asyncBatchExecutorService.execute(87, dataSource, "getCcoZeroCreditTrx",
                "deleteCcoZeroCreditTrx", "DEL_ZERO_CREDIT");
    }

    @Scheduled(cron = "${app.scheduler.runat315am}")
    public void ccoBatches() {
        runCcoBatches(kvUat);
    }

        @Scheduled(cron = "${app.scheduler.runat7am}")
    public void sendBatchEmails() {
        String fromEmail = "datareceived@kollect.my";
        emailSenderService.sendAfterBatch(fromEmail, recipient, "YYC - Daily Sequence Batch Report",
                batchHistoryService.viewYycSeqAfterSchedulerUat(), batchHistoryService.viewYycSeqAfterSchedulerProd());
        emailSenderService.sendAfterBatch(fromEmail, recipient, "YYC - Daily Batch report",
                batchHistoryService.viewYycAfterSchedulerUat(), batchHistoryService.viewYycAfterSchedulerProd());
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail, recipient, "PBK - Daily Batch Report",
                batchHistoryService.viewPbkAfterSchedulerUat(), batchHistoryService.viewPbkAfterSchedulerProd());
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail, recipient, "Pelita - Daily Batch Report",
                batchHistoryService.viewPelitaAfterSchedulerUat(), batchHistoryService.viewPelitaAfterSchedulerProd());
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail, recipient + ",syazman@kollect.my,biman@kollect.my",
                "ICT Zone - Daily Batch Report", batchHistoryService.viewIctZoneAfterSchedulerUat(),
                emptyList);
        componentProvider.taskSleep();
        emailSenderService.sendAfterBatch(fromEmail, recipient, "Cheng&Co - Daily Batch Report", batchHistoryService.viewCcoAfterSchedulerUat(),
                emptyList);
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