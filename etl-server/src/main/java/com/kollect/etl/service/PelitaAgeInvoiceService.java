package com.kollect.etl.service;

import com.kollect.etl.config.CrudProcessHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PelitaAgeInvoiceService {

    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private IAsyncExecutorService executorService;
    private String dataSource;
    private boolean lock;

    @Autowired
    public PelitaAgeInvoiceService(IReadWriteServiceProvider rwProvider, BatchHistoryService batchHistoryService,
                                @Value("${app.datasource_pelita_test}") String dataSource, @Qualifier("simple") IAsyncExecutorService executorService){
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
        this.dataSource = dataSource;
        this.executorService = executorService;
    }

    public int combinedAgeInvoiceService(Integer batch_id){
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> ageInvoiceList = this.rwProvider.executeQuery(dataSource, "getPelitaAgeInvoicesById", null);
            Map<String, CrudProcessHolder> map = new TreeMap<>();
            map.put("AGE_INV", new CrudProcessHolder("NONE", 10, 100, new ArrayList<>(Arrays.asList("updatePelitaAgeInvoices"))));
            executorService.processEntries(map, ageInvoiceList);
            int numberOfRecords = ageInvoiceList.size();
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime ) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken);
        }
        return numberOfRows;
    }
}
