package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.MailClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RunBatchController {
    @Autowired
    private MailClientService mailClientService;
    @Autowired
    private BatchHistoryService service;
    /**
     * HTTP GET request to retrieve all batches
     *
     * @return runbatch - used to return the HTML for first time visit.
     */
/*    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String recipient = "hashim.kollect@gmail.com";
    private String intro = "This is an Automated Notification for KollectValley Pelita Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";*/

    @GetMapping("/runbatch")
    public Object allBatches() {
/*        this.mailClientService.sendAfterBatch(recipient, "Pelita - Daily Batch Report",intro,
                message, this.service.viewPelitaAfterSchedulerUat(), null);*/
        return "runBatch";
    }
}
