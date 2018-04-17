package com.kollect.etl.controller.app;

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
    /**
     * HTTP GET request to retrieve all batches
     *
     * @return runbatch - used to return the HTML for first time visit.
     */

    @GetMapping("/runbatch")
    public Object allBatches() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        this.mailClientService.sendAfterBatch("hashim.kollect@gmail.com", "Hi there,", "This is an automated email" +
                "from PowerETL for batches run on "+ sdf.format(today) +":", "Best,", null);
        return "runBatch";
    }
}
