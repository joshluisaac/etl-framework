package com.kollect.etl.service.commonbatches;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateDataDateService {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    public UpdateDataDateService(IReadWriteServiceProvider rwProvider,
                                 BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
    }

    public int runUpdateDataDate(Integer batch_id, List<String> dataSource,
                                 String query){
        int numberOfRows = 1;
        for (String src: dataSource) {
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                this.rwProvider.updateQuery(src, query, null);
                lock = false;
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime) / 1000000;
                this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, src);
            }
        }
        return numberOfRows;
    }
}
