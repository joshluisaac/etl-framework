package com.kollect.etl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.kollect.etl.config.CrudProcessHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AgeInvoiceService {
    private IReadWriteServiceProvider rwProvider;
    private BatchHistoryService batchHistoryService;
    private IAsyncExecutorService executorService;
    private String dataSource;
    private boolean lock;

    @Autowired
    public AgeInvoiceService(IReadWriteServiceProvider rwProvider, BatchHistoryService batchHistoryService,
                             @Value("${app.datasource_pbk1}") String dataSource, @Qualifier("simple") IAsyncExecutorService executorService){
        this.rwProvider = rwProvider;
        this.batchHistoryService = batchHistoryService;
        this.dataSource = dataSource;
        this.executorService = executorService;
    }
    
    public int combinedAgeInvoiceService(@RequestParam(required = false) Integer tenant_id, @RequestParam Integer batch_id){
      int numberOfRows = -1;
      if (!lock) {
          long startTime = System.nanoTime();
          lock = true;
          List<Object> ageInvoiceList = this.rwProvider.executeQuery(dataSource, "getAgeInvoiceById", tenant_id);
          Map<String, CrudProcessHolder> map = new TreeMap<>();
          map.put("AGE_INV", new CrudProcessHolder("NONE", 10, 100, new ArrayList<>(Arrays.asList("updateAgeInvoice"))));
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
