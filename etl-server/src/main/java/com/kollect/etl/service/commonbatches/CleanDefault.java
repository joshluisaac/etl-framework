package com.kollect.etl.service.commonbatches;

import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanDefault {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private boolean lock;
    private static final Logger LOG = LoggerFactory.getLogger(CleanDefault.class);

    @Autowired
    public CleanDefault(IReadWriteServiceProvider rwProvider,
                        BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
    }

    public int runDefaultClean(Integer batch_id, List<String> dataSource,
                               String selectQuery, String updateQuery){
        int numberOfRows = 0;
        long timeTaken = 0;
        String status;
        for (String src : dataSource) {
            try {
                if (!lock) {
                    long startTime = System.nanoTime();
                    List<Object> idList = rwProvider.executeQuery(selectQuery,
                            null);
                    for (Object id : idList) {
                        rwProvider.updateQuery(updateQuery, id);
                        numberOfRows +=1;
                    }
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
