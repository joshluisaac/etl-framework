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
public class YycInvoiceStatusEvaluationService {
    private IReadWriteServiceProvider rwProvider;
    private List<String> dataSource;
    private BatchHistoryService batchHistoryService;
    private IAsyncExecutorService executorService;
    private boolean lock;

    @Autowired
    public YycInvoiceStatusEvaluationService(IReadWriteServiceProvider rwProvider,
                                             @Value("#{'${app.datasource_all2}'.split(',')}") List<String> dataSource, BatchHistoryService batchHistoryService, @Qualifier("simple") IAsyncExecutorService executorService){
        this.rwProvider = rwProvider;
        this.dataSource = dataSource;
        this.batchHistoryService = batchHistoryService;
        this.executorService = executorService;
    }

    public int combinedYycInvoiceStatusEvaluation(Integer batch_id){
        int numberOfRows = -1;
        for (String src: dataSource) {
            if (!lock) {
                long startTime = System.nanoTime();
                lock = true;
                List<Object> statusIdList = this.rwProvider.executeQuery(src, "getYycInvoiceStatusById", null);
                Map<String, CrudProcessHolder> map = new TreeMap<>();
                map.put("INV_STAT", new CrudProcessHolder(src, "NONE", 10, 100, new ArrayList<>(Arrays.asList("updateYycInvoiceStatusEvaluation"))));
                executorService.processEntries(map, statusIdList);
                int numberOfRecords = statusIdList.size();
                lock = false;
                numberOfRows = numberOfRecords;
                long endTime = System.nanoTime();
                long timeTaken = (endTime - startTime) / 1000000;
                this.batchHistoryService.runBatchHistory(batch_id, numberOfRows, timeTaken, src);
            }
        }
        return numberOfRows;
    }
}