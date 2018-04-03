package com.kollect.etl.controller;

import com.kollect.etl.config.BatchConfig;
import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionUpdateController {

    //private static final Logger LOG = LoggerFactory.getLogger(TransactionUpdateController.class);
    private IReadWriteServiceProvider rwProvider;
    private IAsyncExecutorService executorService;
    private BatchHistoryService batchHistoryService;
    private static final Map<String, CrudProcessHolder> MAP = new HashMap<>();

    @Autowired
    public TransactionUpdateController(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService,
                                       BatchHistoryService batchHistoryService, IAsyncExecutorService executorService) {
        this.rwProvider = rwProvider;
        this.executorService = executorService;
        this.batchHistoryService = batchHistoryService;
        BatchConfig.buildHolderMap(MAP);
    }

    public void cycleAndProcessEntries() {
        for (Map.Entry<String, CrudProcessHolder> entry : MAP.entrySet()) {
            CrudProcessHolder holder = entry.getValue();
            final int thread = holder.getThread();
            final int commitSize = holder.getCommitSize();
            final String queryName = holder.getQueryName();
            final String updateQuery = "updateTransactionLoad";
            List<TransactionLoad> list = rwProvider.executeQuery(queryName, null);
            int recordCount = list.size();
            executorService.invoke(list, updateQuery, thread, commitSize);
            holder.setRecordCount(recordCount);
        }
    }

    @PostMapping(value = "/updateTrxLoadInvoicesByDocType")
    @ResponseBody
    public Object updateInvoicesByDocType(@RequestParam(required = false) Integer batch_id) {
        long startTime = System.nanoTime();
        cycleAndProcessEntries();
        long endTime = System.nanoTime();
        long timeTaken = (endTime - startTime) / 1000000;
        this.batchHistoryService.runBatchHistory(batch_id, 0, timeTaken);
        return 0;
    }

}
