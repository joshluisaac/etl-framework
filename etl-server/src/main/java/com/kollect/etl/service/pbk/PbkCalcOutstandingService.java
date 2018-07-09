package com.kollect.etl.service.pbk;

import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PbkCalcOutstandingService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    @Autowired
    public PbkCalcOutstandingService(IReadWriteServiceProvider rwProvider,
                                     @Value("${app.datasource_pbk1}") String dataSource, BatchHistoryService batchHistoryService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public int combinedCalcOutstanding(Integer batch_id) {
        int numberOfRows = -1;
        long timeTaken = 0;
        String status;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> outstandingList = this.rwProvider.executeQuery(dataSource, "getOutstandingByTenantId", null);
            int numberOfRecords = outstandingList.size();
            for (Object anOutstandingList : outstandingList) {
                Map<Object, Object> map = (Map<Object, Object>) anOutstandingList;
                Map<Object, Object> args = new HashMap<>();
                args.put("invoice_plus_gst", map.get("invoice_plus_gst"));
                args.put("total_transactions", map.get("total_transactions"));
                args.put("invoice_id", map.get("invoice_id"));
                this.rwProvider.updateQuery(dataSource, "updateOutstanding", args);
            }
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            timeTaken = (endTime - startTime) / 1000000;
        }
        if (lock)
            status = "Failed";
        else
            status = "Success";
        this.batchHistoryService.runBatchHistory(batch_id, numberOfRows,
                timeTaken, dataSource, status);
        /* Necessary in case a batch fails bec DB issues, release the lock so it can
         * run next time. */
        lock = false;
        return numberOfRows;
    }
}
