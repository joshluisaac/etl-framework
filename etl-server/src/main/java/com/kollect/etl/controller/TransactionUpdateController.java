package com.kollect.etl.controller;

import com.kollect.etl.config.BatchConfig;
import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionUpdateController {

  //private static final Logger LOG = LoggerFactory.getLogger(TransactionUpdateController.class);
  private IReadWriteServiceProvider rwProvider;
  private IAsyncExecutorService executorService;
  private BatchHistoryService batchHistoryService;
  private static final Map<String, CrudProcessHolder> MAP = new HashMap<>();

  @Autowired
  public TransactionUpdateController(IReadWriteServiceProvider rwProvider, IAsyncBatchService asyncService,
      BatchHistoryService batchHistoryService, IAsyncExecutorService executorService) {
    this.rwProvider = rwProvider;
    this.executorService = executorService;
    this.batchHistoryService = batchHistoryService;
    BatchConfig.buildHolderMap(MAP);
  }
  
  private CrudProcessHolder lookupCrudProcessHolder(final String docType) {
    return MAP.get(docType);
  }

  public void process(final String docType) {
    CrudProcessHolder holder = lookupCrudProcessHolder(docType);
    final int thread = holder.getThread();
    final int commitSize = holder.getCommitSize();
    final String queryName = holder.getQueryName();
    final String updateQuery = "updateTransactionLoad";
    List<TransactionLoad> list = rwProvider.executeQuery(queryName, null);
    int recordCount = list.size();
    executorService.invoke(list, updateQuery, thread, commitSize);
    holder.setRecordCount(recordCount);
  }
  
  void x(@Value("${trx.doctypes}") String docTypes) {
    System.out.println(docTypes);
  }

  @PostMapping(value = "/updateTrxLoadInvoicesByDocType")
  @ResponseBody
  public Object updateInvoicesByDocType(@RequestParam(required = false) Integer batch_id) {
    long startTime = System.nanoTime();
    List<String> docTypes = new ArrayList<>(Arrays.asList("AB", "RG", "YY", "CLEARING_DOC_BASED_TYPES", "GI", "RI",
        "RM", "RV", "RY", "YC", "YD", "YH", "YI", "YJ", "YL", "YN", "YO", "YP", "YQ", "YR", "YS", "YT", "YU", "YV",
        "YW", "YX", "YK", "Y1", "YE", "YM", "YF", "ZZ", "OTHERS"));
    for (String docType : docTypes) {
      process(docType);
    }
    long endTime = System.nanoTime();
    long timeTaken = (endTime - startTime) / 1000000;
    this.batchHistoryService.runBatchHistory(batch_id, 0, timeTaken);
    return 0;
  }

}
