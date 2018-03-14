package com.kollect.etl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddBatchController {

    /**
     * GET request mapping to show the page to add new batches
     * @return
     *          returns the addBatch form
     */
    @GetMapping(value = "/addbatch")
    public String addBatch() {

        return "addBatch";
    }
}
