package com.kollect.etl.service.commonbatches;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateDataDateService {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private boolean lock;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateDataDateService.class);

    public UpdateDataDateService(IReadWriteServiceProvider rwProvider,
                                 BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
    }

    public int runUpdateDataDate(Integer batch_id, List<String> dataSource,
                                 String query){
        int numberOfRows = 1;
        long timeTaken = 0;
        String status;
        for (String src: dataSource) {
            try {
                if (!lock) {
                    long startTime = System.nanoTime();
                    lock = true;
                    this.rwProvider.updateQuery(src, query, null);
                    lock = false;
                    long endTime = System.nanoTime();
                    timeTaken = (endTime - startTime) / 1000000;
                }
            }catch (Exception e){
                LOG.error("An error occurred when running the batch." + e);
            }
            if (lock)
                status = "Failed";
            else
                status = "Success";
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows,
                    timeTaken, src, status);
        }
        /* Necessary in case a batch fails bec DB issues, release the lock so it can
         * run next time. */
        lock = false;
        return numberOfRows;
    }
}
