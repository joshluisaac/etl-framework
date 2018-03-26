package com.kollect.etl.controller;

import com.kollect.etl.service.BatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BatchHistoryController {
    @Autowired
    private BatchHistoryService batchHistoryService;

    @GetMapping("/batch_history")
    public Object viewHistory (@RequestParam Integer batch_id, Model model){
        this.batchHistoryService.viewBatchHistory(batch_id, model);
        this.batchHistoryService.viewLatestBatchHistory(batch_id, model);
        return "runBatch";
    }
}
