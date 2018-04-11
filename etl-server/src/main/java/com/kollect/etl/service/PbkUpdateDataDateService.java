package com.kollect.etl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PbkUpdateDataDateService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    @Autowired
    public PbkUpdateDataDateService(IReadWriteServiceProvider rwProvider,
                                    @Value("${app.datasource_pbk1}") String dataSource, BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int runupdateDataDate(Integer batch_id){
        int numberOfRows = 1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            this.rwProvider.updateQuery(dataSource, "updateDataDate", null);
            lock = false;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime ) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);
        }
        return numberOfRows;
    }
}
