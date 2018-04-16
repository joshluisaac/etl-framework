package com.kollect.etl.controller;

import com.kollect.etl.config.BatchConfig;
import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.service.*;

import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.TreeMap;

@Controller
public class TransactionUpdateController {

    //private static final Logger LOG = LoggerFactory.getLogger(TransactionUpdateController.class);
    private IAsyncExecutorService executorService;
    private BatchHistoryService batchHistoryService;
    private static final Map<String, CrudProcessHolder> MAP = new TreeMap<>();
    private String dataSource;

    @Autowired
    public TransactionUpdateController(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService,
                                       BatchHistoryService batchHistoryService, @Qualifier("simple") IAsyncExecutorService executorService,
                                       @Value("${app.datasource_mahb_prod2}") String dataSource) {
        this.executorService = executorService;
        this.batchHistoryService = batchHistoryService;
        this.dataSource = dataSource;
    }



    @PostMapping(value = "/updateTrxLoadInvoicesByDocType")
    @ResponseBody
    public Object updateInvoicesByDocType(@RequestParam(required = false) Integer batch_id) {
      
      new BatchConfig().crudHolderMap(MAP,dataSource);
      
        long startTime = System.nanoTime();
        executorService.processEntries(MAP, null);
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime) / 1000000;
        this.batchHistoryService.runBatchHistory(batch_id, 0, timeTaken, dataSource);
        return 0;
    }

}
