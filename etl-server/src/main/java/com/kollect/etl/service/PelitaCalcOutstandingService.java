package com.kollect.etl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PelitaCalcOutstandingService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;

    @Autowired
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    @Autowired
    public PelitaCalcOutstandingService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_pelita_test}") String dataSource){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
    }

    public List<Object> getOutstandingByTenantId(Object object) {
        return this.rwProvider.executeQuery(dataSource, "getInvoiceAmountAfterTaxById", object);
        // TODO Auto-generated method stub

    }


    public int combinedPelitaCalcOutstanding(@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id) {
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> outstandingList = this.getOutstandingByTenantId(tenant_id);
            int numberOfRecords = outstandingList.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) outstandingList.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("invoice_amount", map.get("invoice_amount"));
                args.put("gst", map.get("gst"));
                args.put("id", map.get("id"));

                this.rwProvider.updateQuery(dataSource, "updatePelitaOutstanding", args);
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

