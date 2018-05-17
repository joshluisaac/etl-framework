package com.kollect.etl.service.app;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private MailClientService mailClientService;
    private BatchHistoryService batchHistoryService;
    private ComponentProvider componentProvider;
    private IReadWriteServiceProvider iRWProvider;
    private static final Logger LOG = LoggerFactory.getLogger(EmailUpdatesService.class);

    /* Necessary service variables */
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String intro = "This is an Automated Notification for KollectValley YYC Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";
    /*Values coming in application.properties*/
    @Value("${app.datasource_uat_8}")
    private String dataSource;
    /*This empty list is used to replace the prodStats query since Pelita is not on production yet.*/
    private List<Object> emptyList = new ArrayList<>();

    @Autowired
    private EmailUpdatesService(MailClientService mailClientService,
                                BatchHistoryService batchHistoryService,
                                ComponentProvider componentProvider,
                                IReadWriteServiceProvider iRWProvider) {
        this.mailClientService = mailClientService;
        this.batchHistoryService = batchHistoryService;
        this.componentProvider = componentProvider;
        this.iRWProvider = iRWProvider;

    }

    /**
     * This method sends over the batch email updates manually in case the auto email sender fails after batch execution.
     * @param recipient
     *                  the recipient from client to whom the emails are sent.
     * @return
     *          returns the difference which is used by Ajax to display appropriate message to client.
     */
    public long resendEmail(String recipient) {
        Long currentTime = System.currentTimeMillis();
        List<Map<String, Long>> getLastRunTestUpdate = this.iRWProvider.executeQuery(dataSource, "getLastRunBatchUpdate", null);
        Map<String, Long> map = getLastRunTestUpdate.get(0);
        Long lastRunTime = map.get("last_run_time");
        Long difference = currentTime - lastRunTime;
        if (difference >= 86400000) {
            this.mailClientService.sendAfterBatch(recipient, "YYC - Daily Batch Report", intro,
                    message, this.batchHistoryService.viewYycAfterSchedulerUat(), emptyList);
            this.componentProvider.taskSleep();
            this.mailClientService.sendAfterBatch(recipient, "PBK - Daily Batch Report", intro,
                    message, this.batchHistoryService.viewPbkAfterSchedulerUat(),
                    this.batchHistoryService.viewPbkAfterSchedulerProd());
            this.componentProvider.taskSleep();
            this.mailClientService.sendAfterBatch(recipient, "Pelita - Daily Batch Report", intro,
                    message, this.batchHistoryService.viewPelitaAfterSchedulerUat(), emptyList);
            this.iRWProvider.insertQuery(dataSource, "updateLastRunBatchUpdate", null);
            LOG.info("All batch email updates sent successfully.");
        }
        else
            LOG.info("Emails not sent, please wait 24 hours. ");
        return difference;
    }

    /**
     * This method is used to carry out tests on the email settings. It is also useful to test out different templates.
     * @param recipient
     *                  the recipient from client to whom the emails are sent.
     * @return
     *          returns the difference which is used by Ajax to display appropriate message to client.
     */
    public long sendTestEmail(String recipient) {
        Long currentTime = System.currentTimeMillis();
        List<Map<String, Long>> getLastRunTestUpdate = this.iRWProvider.executeQuery(dataSource, "getLastRunTestUpdate", null);
        Map<String, Long> map = getLastRunTestUpdate.get(0);
        Long lastRunTime = map.get("last_run_time");
        Long difference = currentTime - lastRunTime;
        if (difference >= 86400000) {
            this.mailClientService.sendAfterBatch(recipient, "YYC - Daily Batch Report", intro,
                    message, this.batchHistoryService.viewYycAfterSchedulerUat(), emptyList);
            this.iRWProvider.insertQuery(dataSource, "updateLastRunTestUpdate", null);
            LOG.info("Test email sent successfully.");
        }
        else
            LOG.info("Emails not sent, please wait 24 hours. ");
        return difference;
    }
}