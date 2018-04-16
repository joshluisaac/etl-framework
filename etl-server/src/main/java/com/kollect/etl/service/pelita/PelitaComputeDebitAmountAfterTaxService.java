package com.kollect.etl.service.pelita;

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
public class PelitaComputeDebitAmountAfterTaxService {
    private IReadWriteServiceProvider rwProvider;
    private String dataSource;
    private BatchHistoryService batchHistoryService;
    private IAsyncExecutorService executorService;
    private boolean lock;

    @Autowired
    public PelitaComputeDebitAmountAfterTaxService(IReadWriteServiceProvider rwProvider,
                                                   @Value("${app.datasource_pelita_test}") String dataSource,
                                                   BatchHistoryService batchHistoryService,
                                                   @Qualifier("simple") IAsyncExecutorService executorService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
        this.executorService =  executorService;
    }

    public int combinedPelitaComputeDebitAmountAfterTax(Integer batch_id) {
        int numberOfRows = -1;
        if (!lock) {
            long startTime = System.nanoTime();
            lock = true;
            List<Object> debitList = this.rwProvider.executeQuery(dataSource, "getDebitAmountAfterTaxById", null);
            Map<String, CrudProcessHolder> map = new TreeMap<>();
            map.put("COMPUTE_DEBIT", new CrudProcessHolder(dataSource, "NONE", 10, 100, new ArrayList<>(Arrays.asList("updateDebitAmountAfterTax"))));
            rwProvider.getBatchSqlSession(dataSource);
            executorService.processEntries(map, debitList);
            int numberOfRecords = debitList.size();
            lock = false;
            numberOfRows = numberOfRecords;
            long endTime = System.nanoTime();
            long timeTaken = (endTime - startTime) / 1000000;
            this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, dataSource);
        }
        return numberOfRows;
    }
}
