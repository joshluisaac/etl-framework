package com.kollect.etl.service.app;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class sends over emails from the client in case there was a mail server error.
 * It is also used to send out test emails.
 */
@Service
public class EmailUpdatesService {
    /* Necessary service dependencies */
    private BatchHistoryService batchHistoryService;
    private ComponentProvider componentProvider;
    private IReadWriteServiceProvider iRWProvider;
    private EmailSenderService emailSenderService;
    private static final Logger LOG = LoggerFactory.getLogger(EmailUpdatesService.class);

    /*Values coming in application.properties*/
    @Value("${app.datasource_uat_8}")
    private String dataSource;
    /*This empty list is used to replace the prodStats query since Pelita is not on production yet.*/
    private List<Object> emptyList = new ArrayList<>();

    @Autowired
    private EmailUpdatesService(BatchHistoryService batchHistoryService,
                                ComponentProvider componentProvider,
                                IReadWriteServiceProvider iRWProvider,
                                EmailSenderService emailSenderService) {
        this.batchHistoryService = batchHistoryService;
        this.componentProvider = componentProvider;
        this.iRWProvider = iRWProvider;
        this.emailSenderService=emailSenderService;

    }

    /**
     * This method checks if the email was sent the previous 24 hours,
     * returns the difference to be used in the sending methods
     * @param queryName
     *                  either BatchUpdate or TestUpdate queries
     * @return
     *          Long difference which can be used to check if email has been sent
     *          before
     */
    private Long checkIfEmailWasSentLast24Hours(String queryName){
        Long currentTime = System.currentTimeMillis();
        List<Map<String, Long>> getLastUpdate =
                this.iRWProvider.executeQuery(dataSource,
                        queryName, null);
        Map<String, Long> map = getLastUpdate.get(0);
        Long lastRunTime = map.get("last_run_time");
        return currentTime - lastRunTime;
    }

    /**
     * This method sends over the batch email updates manually in case the auto email sender fails after batch execution.
     * @param recipient
     *                  the recipient from client to whom the emails are sent.
     * @return
     *          returns the difference which is used by Ajax to display appropriate message to client.
     */
    public long resendEmail(String recipient) {
        Long difference = checkIfEmailWasSentLast24Hours("getLastRunBatchUpdate");
        if (difference >= 86400000) {
            emailSenderService.sendAfterBatch("datareceived@kollect.my",recipient,
                    "YYC - Daily Batch Report",
                    batchHistoryService.viewYycAfterSchedulerUat(),
                    batchHistoryService.viewYycAfterSchedulerProd());
            this.componentProvider.taskSleep();
            emailSenderService.sendAfterBatch("datareceived@kollect.my",
                    recipient,
                    "PBK - Daily Batch Report",
                    batchHistoryService.viewPbkAfterSchedulerUat(),
                    batchHistoryService.viewPbkAfterSchedulerProd());
            this.componentProvider.taskSleep();
            emailSenderService.sendAfterBatch("datareceived@kollect.my",
                    recipient,
                    "Pelita - Daily Batch Report",
                    batchHistoryService.viewPelitaAfterSchedulerUat(),
                    emptyList);
            this.componentProvider.taskSleep();
            emailSenderService.sendAfterBatch("datareceived@kollect.my",
                    recipient,
                    "ICT Zone - Daily Batch Report",
                    batchHistoryService.viewIctZoneAfterSchedulerUat(), emptyList);
            iRWProvider.insertQuery(dataSource, "updateLastRunBatchUpdate", null);
            LOG.info("All batch email updates sent successfully.");
        }
        else
            LOG.info("Emails not sent, please wait 24 hours. ");
        return difference;
    }

    /**
     * This method is used to carry out tests on the email settings. It is also useful to test out different templates.
     * @param recipient
     *                  recipient from client to whom the emails are sent.
     * @return
     *          returns the difference which is used by Ajax to display appropriate message to client.
     */
    public long sendTestEmail(String recipient) {
        Long difference = checkIfEmailWasSentLast24Hours
                ("getLastRunTestUpdate");
        if (difference >= 86400000) {
            emailSenderService.sendAfterBatch("datareceived@kollect.my",
                    recipient, "Test Email To Check Template",
                    batchHistoryService.viewYycAfterSchedulerUat(),
                    batchHistoryService.viewYycAfterSchedulerProd());
            this.iRWProvider.insertQuery(dataSource, "updateLastRunTestUpdate", null);
            LOG.info("Test email sent successfully.");
        }
        else
            LOG.info("Emails not sent, please wait 24 hours. ");
        return difference;
    }
}