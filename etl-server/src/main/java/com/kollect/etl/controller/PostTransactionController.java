package com.kollect.etl.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.config.CrudProcessHolder;
import com.kollect.etl.config.PostTransactionBatchConfig;
import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.service.AsyncBatchService;
import com.kollect.etl.service.BatchHistoryService;
import com.kollect.etl.service.IAsyncExecutorService;
import com.kollect.etl.service.IReadWriteServiceProvider;
import com.kollect.etl.service.RouteService;
import com.kollect.etl.util.FileUtils;

@Controller
public class PostTransactionController {

  private ComponentProvider compProvider;
  private RouteService routeService;
  private IReadWriteServiceProvider rwProvider;
  private IAsyncExecutorService executorService;
  private BatchHistoryService batchHistoryService;
  private static final SortedMap<String, CrudProcessHolder> MAP = new TreeMap<>();

  @Autowired
  public PostTransactionController(AsyncBatchService asyncService, ComponentProvider compProvider,
      RouteService routeService, IReadWriteServiceProvider rwProvider, @Qualifier("simple") IAsyncExecutorService executorService,
      BatchHistoryService batchHistoryService) {
    this.compProvider = compProvider;
    this.routeService = routeService;
    this.rwProvider = rwProvider;
    this.executorService = executorService;
    this.batchHistoryService = batchHistoryService;
    new PostTransactionBatchConfig().crudHolderMap(MAP);
  }

  private static final String REGEX_PATTERN = "^3[0-9]{8}";
  
  @PostMapping(value = "/isCommercial")
  @ResponseBody
  public Object isCommercialEvaluation() {
    Map<String, CrudProcessHolder> map = new TreeMap<>();
    map.put("IS_COMMERCIAL", new CrudProcessHolder(null, 10, 100, new ArrayList<>(Arrays.asList("updateCommercialTransaction"))));
    List<TransactionLoad> list = rwProvider.executeQuery("getTransactionLoad", null);
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
      TransactionLoad transactionLoad = list.get(i);
      boolean isCommercial = compProvider.isCommericalResolver(transactionLoad.getAccountNo(), REGEX_PATTERN);
      transactionLoad.setCommercial(isCommercial);
    }
    executorService.processEntries(map, list);
    return rowCount;
  }
  
  @PostMapping(value = "/updateAllPostTransaction")
  @ResponseBody
  public Object updateAll() {
    String fileName = new FileUtils().getClassPathResource("routesConfig.json").getFile();
    System.out.println(routeService.readJsonBlob(new File(fileName))[2].getDescription());
    executorService.processEntries(MAP);
    return -1;
  }

  @PostMapping(value = "/updateParentInvoiceNo")
  @ResponseBody
  public Object updateParentInvoiceNo() {
    String[] postJobs = { "updateParentInvoiceNo" };
    for (String string : postJobs) {
      rwProvider.updateQuery(string, null);
    }
    return 0;
  }

}
