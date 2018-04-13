package com.kollect.etl.service.pbk;

import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.service.app.BatchHistoryService;
import com.kollect.etl.service.IAsyncExecutorService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PbkAgeInvoiceService {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private IAsyncExecutorService executorService;
    private String dataSource;
    private boolean lock;

    @Autowired
    public PbkAgeInvoiceService(IReadWriteServiceProvider rwProvider, BatchHistoryService batchHistoryService,
                                @Value("${app.datasource_pbk1}") String dataSource, @Qualifier("simple") IAsyncExecutorService executorService){
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
          List<Object> ageInvoiceList = this.rwProvider.executeQuery(dataSource, "getAgeInvoiceById", null);
          Map<String, CrudProcessHolder> map = new TreeMap<>();
          map.put("AGE_INV", new CrudProcessHolder(dataSource,"NONE", 10, 100, new ArrayList<>(Arrays.asList("updateAgeInvoice"))));
          executorService.processEntries(map, ageInvoiceList);
          int numberOfRecords = ageInvoiceList.size();
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
