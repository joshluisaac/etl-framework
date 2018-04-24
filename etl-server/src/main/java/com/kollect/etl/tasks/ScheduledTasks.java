package com.kollect.etl.tasks;

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
    private String recipient;
    private String intro ="This is an Automated Notification for KollectValley Batch Statistics for " + sdf.format(today) + ":";
    private String message = "Batch Summary & Statistics";

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
                          @Value("${spring.mail.properties.batch.autoupdate.recipients}") String recipient){
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
        this.recipient = recipient;
    }

    @Scheduled(cron = "0 0 19 * * *")
    public void runYycBatches(){
        this.yycQuerySequenceService.runYycSequenceQuery(62);
        this.mailClientService.sendAfterBatch(recipient,"YYC - Daily Batch Report" ,intro,
                message, this.batchHistoryService.viewYycAfterSchedulerUat(), this.batchHistoryService.viewYycAfterSchedulerProd());
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void runPelitaBatches() {
        this.pelitaInvoiceStatusEvaluationServicePelita.combinePelitaInvoiceStatusEvaluation(58);
        this.pelitaComputeInvoiceAmountAfterTaxServicePelita.combinedPelitaComputeInvoiceAmountAfterTax(57);
        this.pelitaInAgingServicePelita.combinedPelitaAgeInvoiceService(56);
        this.pelitaAgeInvoiceService.combinedAgeInvoiceService(59);
        this.pelitaUpdateDataDateService.runupdateDataDate(60);
        this.pelitaComputeDebitAmountAfterTaxService.combinedPelitaComputeDebitAmountAfterTax(61);
        this.mailClientService.sendAfterBatch(recipient, "Pelita - Daily Batch Report",intro,
                message, this.batchHistoryService.viewPelitaAfterSchedulerUat(), null);
    }

    @Scheduled(cron = "0 30 22 * * *")
    public void runPbkBatches() {
        this.pbklSumPayServ.combinedLumpSumPaymentService(2);
        this.pbkageInvServ.combinedAgeInvoiceService(3);
        this.pbkupdDataDateServ.runupdateDataDate(53);
        this.mailClientService.sendAfterBatch(recipient, "PBK - Daily Batch Report",intro,
                message, this.batchHistoryService.viewPbkAfterSchedulerUat(), this.batchHistoryService.viewPbkAfterSchedulerProd());
    }

}