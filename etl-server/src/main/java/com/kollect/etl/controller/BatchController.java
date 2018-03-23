package com.kollect.etl.controller;

import com.kollect.etl.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BatchController {
    @Autowired
    private BatchService batchService;

    /**
     * GET request mapping to show the page to add new batches, and get all batches
     * @return
     *          returns the addBatch form
     */
    @GetMapping(value = "/batch")
    public Object getBatchById(@RequestParam(required = false) Integer id, Model model) {
        return this.batchService.getBatch(id, model);
    }

    /**
     * POST method to either add a new batch or update an existing one.
     * @param id
     *          the id of the batch
     * @param code
     *          short code of the batch
     * @param name
     *          full name of the batch
     * @param description
     *                  the description of what the batch does
     * @param disable
     *              a toggle to enable/disable batch
     * @return
     *         returns the addUpdateBatch method from the service
     */
    @PostMapping("/batch")
    public Object addUpdateBatch(@RequestParam(required = false) Integer id,@RequestParam String code, @RequestParam String name, @RequestParam String description, @RequestParam (required = false)
                              boolean disable){
        return this.batchService.addUpdateBatch(id, code,name, description, disable);
    }
}
