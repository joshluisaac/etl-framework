package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.app.MailClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
    private Timestamp today = new Timestamp(System.currentTimeMillis());
    private String recipient = "hashim@kollect.my, joshua@kollect.my";
    private String intro = "This is an Automated Notification for KollectValley PBK Batch Statistics for " + sdf.format(today) + ".";
    private String message = "Batch Summary & Statistics:";

    @GetMapping("/runbatch")
    public Object allBatches() {
/*        this.mailClientService.sendAfterBatch(recipient, "PBK - Daily Batch Report",intro,
                message, this.service.viewPbkAfterSchedulerUat(), this.service.viewPbkAfterSchedulerProd());
        */return "runBatch";
    }
}
