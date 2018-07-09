package com.kollect.etl.service.commonbatches;

import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.service.IAsyncExecutorService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AsyncBatchExecutorService {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private IAsyncExecutorService executorService;
    private boolean lock;

    public AsyncBatchExecutorService(IReadWriteServiceProvider rwProvider, BatchHistoryService batchHistoryService,
                                     @Qualifier("simple") IAsyncExecutorService executorService) {
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
        this.executorService = executorService;
    }

    public int execute(Integer batch_id, List<String> dataSource,
                       String getQuery, String updateQuery, String getName) {
        int numberOfRows = -1;
        long timeTaken = 0;
        String status;
        for (String src : dataSource) {
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                List<Object> ageInvoiceList = this.rwProvider.executeQuery(src, getQuery, null);
                Map<String, CrudProcessHolder> map = new TreeMap<>();
                map.put(getName, new CrudProcessHolder(src, "NONE", 10, 100, new ArrayList<>(Arrays.asList(updateQuery))));
                executorService.processEntries(map, ageInvoiceList);
                int numberOfRecords = ageInvoiceList.size();
                lock = false;
                numberOfRows = numberOfRecords;
                long endTime = System.nanoTime();
                timeTaken = (endTime - startTime) / 1000000;
            }
            if (lock)
                    status = "Failed";
            else
                status = "Success";
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, src, status);
        }
        /* Necessary in case a batch fails bec DB issues, release the lock so it can
         * run next time. */
        lock = false;
        return numberOfRows;
    }
}
