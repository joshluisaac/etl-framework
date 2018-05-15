package com.kollect.etl.service.app;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This method sends over emails from the client in case there was a mail server error.
 * It is also used to send out test emails.
 */
@Service
public class EmailUpdatesService {
    /* Necessary service dependencies */
    private MailClientService mailClientService;
    private BatchHistoryService batchHistoryService;
    private ComponentProvider componentProvider;
    private IReadWriteServiceProvider iRWProvider;

    /* Necessary service variables */
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String intro = "This is an Automated Notification for KollectValley YYC Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";
    /*Values coming in application.properties*/
    @Value("${spring.mail.properties.batch.autoupdate.recipients}")
    private String recipient;
    @Value("${app.datasource_uat_8}")
    private String dataSource;
    private boolean lock;
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

    public long resendEmail() {
        Long currentTime = System.currentTimeMillis();
        List<Object> getLastRunTestUpdate = this.iRWProvider.executeQuery(dataSource, "getLastRunBatchUpdate", null);
        Map<String, Long> map = (Map) getLastRunTestUpdate.get(0);
        Long lastRunTime = map.get("last_run_time");
        Long difference = currentTime - lastRunTime;
        if (difference >= 86400000 && !lock) {
            lock = true;
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
            System.out.println("Emails sent successfully.");
        }
        return difference;
    }

    public long sendTestEmail() {
        Long currentTime = System.currentTimeMillis();
        List<Object> getLastRunTestUpdate = this.iRWProvider.executeQuery(dataSource, "getLastRunTestUpdate", null);
        Map<String, Long> map = (Map) getLastRunTestUpdate.get(0);
        Long lastRunTime = map.get("last_run_time");
        Long difference = currentTime - lastRunTime;
        if (difference >= 86400000 && !lock) {
            lock = true;
            String testRecipient = "hashim@kollect.my, joshua@kollect.my";
            this.mailClientService.sendAfterBatch(testRecipient, "YYC - Daily Batch Report", intro,
                    message, this.batchHistoryService.viewYycAfterSchedulerUat(), emptyList);
            this.iRWProvider.insertQuery(dataSource, "updateLastRunTestUpdate", null);
            System.out.println("Email sent successfully.");
        }
        return difference;
    }
}