package com.kollect.etl.service.yyc;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class YycUpdateDataDateService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    public YycUpdateDataDateService(IReadWriteServiceProvider rwProvider,
                                    @Value("${app.datasource_pelita_test}") String dataSource,
                                    BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int runupdateDataDate(Integer batch_id){
        int numberOfRows = 1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            this.rwProvider.updateQuery(dataSource, "yycUpdateDataDate", null);
            lock = false;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime ) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, dataSource);
        }
        return numberOfRows;
    }
}
