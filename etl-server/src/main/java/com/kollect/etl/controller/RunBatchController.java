package com.kollect.etl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RunBatchController {
    /**
     * HTTP GET request to retrieve all batches
     *
     * @return allbatches - used to return the HTML for first time visit.
     */

    @GetMapping("/runbatch")
    public Object allBatches() {
        return "runBatch";
    }
}
