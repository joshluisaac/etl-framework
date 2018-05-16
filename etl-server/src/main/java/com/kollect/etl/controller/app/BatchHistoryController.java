package com.kollect.etl.controller.app;

import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BatchHistoryController {
    private BatchHistoryService batchHistoryService;

    @Autowired
    public BatchHistoryController(BatchHistoryService batchHistoryService){
        this.batchHistoryService = batchHistoryService;
    }

    /**
     * HTTP GET request to render the batch history with associated batch id
     *
     * @param batch_id
     *          id of the batch
     * @param model
     *          a data structure of objects which needs to be rendered to view
     * @return
     *          returns to batchHistory form with rendered data
     */
    @GetMapping("/batch_history")
    public Object viewHistory (@RequestParam Integer batch_id, Model model){
        this.batchHistoryService.viewLastTenBatchHistory(batch_id, model);
        this.batchHistoryService.viewLatestBatchHistory(batch_id, model);
        return "batchHistory";
    }

    /**
     * HTTP GET request to render the entire batch history
     *
     * @param model
     *          a data structure of objects which needs to be rendered to view
     * @return
     *          returns to allBatchHistory form with rendered data
     */
    @GetMapping("/allbatchhistory")
    public Object viewAllBatchHistory(Model model){
        this.batchHistoryService.viewAllBatchHistory(model);
        return "allBatchHistory";
    }
}
