package com.kollect.etl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PbkLumpSumPaymentService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private boolean lock;
    private BatchHistoryService batchHistoryService;


    @Autowired
    public PbkLumpSumPaymentService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_pbk1}") String dataSource, BatchHistoryService batchHistoryService) {
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
    }

    public List<Object> getSumAmount(Object object) {
        return this.rwProvider.executeQuery(dataSource, "getSumAmount", object);
    }

    public int updateGetSumAmount(Object object) {
        return this.rwProvider.updateQuery(dataSource, "updateGetSumAmount",object);
    }

    public int insertGetSumAmount(Object object) {
        return this.rwProvider.insertQuery(dataSource, "insertGetSumAmount",object);
    }

    public int deleteNetLumpSum(Object object) {

            return this.rwProvider.updateQuery(dataSource, "truncateNetLumpSum", object);

    }

    public int combinedLumpSumPaymentService(@RequestParam Integer batch_id){
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> selectLumSumPaymentList = this.getSumAmount(null);
            this.deleteNetLumpSum(null);
            int numberOfRecords = selectLumSumPaymentList.size();
            for (int x = 0; x < numberOfRecords; x++) {

                Map<Object, Object> map = (Map<Object, Object>) selectLumSumPaymentList.get(x);
                Map<Object, Object> args = new HashMap<>();
                args.put("account_id", map.get("account_id"));
                args.put("net_lump_sum_amount", map.get("net_lump_sum_amount"));
                int updateCount = this.updateGetSumAmount(args);
                if (updateCount == 0) {
                    this.insertGetSumAmount(args);
                }
            }
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);
        }
        System.out.println("LumpSumPayment - Number of rows updated: " + numberOfRows);
        return numberOfRows;
    }
}
