package com.kollect.etl.service.yyc;

import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.service.IAsyncExecutorService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.app.BatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class YycInAgingService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private IAsyncExecutorService executorService;
    private BatchHistoryService batchHistoryService;
    private boolean lock;

    @Autowired
    public YycInAgingService(IReadWriteServiceProvider rwProvider, @Value("${app.datasource_pelita_test}") String dataSource, BatchHistoryService batchHistoryService, @Qualifier("simple") IAsyncExecutorService executorService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
        this.executorService =  executorService;
    }

    public int combinedYycAgeInvoiceService(Integer batch_id) {
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> inAgingList = this.rwProvider.executeQuery(dataSource, "getYycInAgingById", null);
            Map<String, CrudProcessHolder> map = new TreeMap<>();
            map.put("IN_AGING", new CrudProcessHolder(dataSource,"NONE", 10, 100, new ArrayList<>(Arrays.asList("yycUpdateInAging"))));
            executorService.processEntries(map, inAgingList);
            int numberOfRecords = inAgingList.size();
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime ) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, dataSource);
        }
        System.out.println("AgeInvoice - Number of rows updated: " + numberOfRows);
        return numberOfRows;
    }
}
