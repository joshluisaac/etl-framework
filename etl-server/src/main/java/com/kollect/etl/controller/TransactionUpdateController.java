package com.kollect.etl.controller;

import com.kollect.etl.config.BatchConfig;
import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TransactionUpdateController {

    //private static final Logger LOG = LoggerFactory.getLogger(TransactionUpdateController.class);
    private IAsyncExecutorService executorService;
    private BatchHistoryService batchHistoryService;
    private static final Map<String, CrudProcessHolder> MAP = new HashMap<>();

    @Autowired
    public TransactionUpdateController(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService,
                                       BatchHistoryService batchHistoryService, IAsyncExecutorService executorService) {
        this.executorService = executorService;
        this.batchHistoryService = batchHistoryService;
        new BatchConfig().crudHolderMap(MAP);
    }



    @PostMapping(value = "/updateTrxLoadInvoicesByDocType")
    @ResponseBody
    public Object updateInvoicesByDocType(@RequestParam(required = false) Integer batch_id) {
        long startTime = System.nanoTime();
        executorService.cycleAndProcessEntries(MAP);
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime) / 1000000;
        this.batchHistoryService.runBatchHistory(batch_id, 0, timeTaken);
        return 0;
    }

}
