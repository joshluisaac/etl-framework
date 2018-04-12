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
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> outstandingList = this.rwProvider.executeQuery(dataSource, "getOutstandingByTenantId", null);
            int numberOfRecords = outstandingList.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) outstandingList.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("invoice_plus_gst", map.get("invoice_plus_gst"));
                args.put("total_transactions", map.get("total_transactions"));
                args.put("invoice_id", map.get("invoice_id"));
                this.rwProvider.updateQuery(dataSource, "updateOutstanding", args);
            }
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);
        }
        return numberOfRows;
    }
}
