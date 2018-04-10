package com.kollect.etl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgeInvoiceService {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private String dataSource;
    private boolean lock;

    @Autowired
    public AgeInvoiceService(IReadWriteServiceProvider rwProvider, BatchHistoryService batchHistoryService,
                             @Value("${app.datasource_pbk1}") String dataSource){
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
        this.dataSource = dataSource;
    }

    public int combinedAgeInvoiceService(@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id){
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> ageInvoiceList = this.rwProvider.executeQuery(dataSource, "getAgeInvoiceById", tenant_id);
            int numberOfRecords = ageInvoiceList.size();
            for (int i = 0; i < numberOfRecords; i++) {
                Map<Object, Object> map = (Map<Object, Object>) ageInvoiceList.get(i);
                Map<Object, Object> args = new HashMap<>();
                args.put("invoice_due_date", map.get("invoice_due_date"));
                args.put("id", map.get("id"));
                args.put("mpd", map.get("mpd"));
                args.put("tenant_id", tenant_id);
                this.rwProvider.updateQuery(dataSource, "updateAgeInvoice", args);
            }
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime ) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);

        }
        System.out.println("AgeInvoice - Number of rows updated: " + numberOfRows);
        return numberOfRows;
    }

}
