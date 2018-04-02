package com.kollect.etl.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kollect.etl.component.ComponentProvider;
import com.kollect.etl.entity.TransactionLoad;
import com.kollect.etl.service.AsyncBatchService;
import com.kollect.etl.service.IRunnableProcess;
import com.kollect.etl.service.ReadWriteServiceProvider;
import com.kollect.etl.util.LogStats;

@Controller
public class PostTransactionController {

  @Autowired
  private ReadWriteServiceProvider rwProvider;
  @Autowired
  private AsyncBatchService asyncService;
  @Autowired
  private ComponentProvider compProvider;

  private static final String REGEX_PATTERN = "^3[0-9]{8}";

  @PostMapping(value = "/updatePostTrx")
  @ResponseBody
  public Object updatePostTransaction() {
    String[] postJobs = { "updateIsDepositTransaction", "updateInvoiceTransactionType", "updatePaymentTransactionType",
        "updateInvoiceStatusId", "updateInvoiceInAging" };
    for (String string : postJobs) {
      rwProvider.updateQuery(string, null);
    }

    return 0;
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

  @PostMapping(value = "/isCommercial")
  @ResponseBody
  public Object updateIsCommercialAccount() {
    final int thread = 10;
    final int commitSize = 100;
    final String updateQuery = "updateCommercialTransaction";

    List<TransactionLoad> list = rwProvider.executeQuery("getTransactionLoad", null);
    int rowCount = list.size();
    for (int i = 0; i < rowCount; i++) {
      TransactionLoad transactionLoad = list.get(i);
      boolean isCommercial = compProvider.isCommericalResolver(transactionLoad.getAccountNo(), REGEX_PATTERN);
      transactionLoad.setCommercial(isCommercial);
    }
    Iterator<TransactionLoad> mQueryResults = list.iterator();
    asyncService.execute(mQueryResults, new IRunnableProcess<TransactionLoad>() {
      @Override
      public void process(List<TransactionLoad> rows) {
        long queryStart = System.currentTimeMillis();
        try (final SqlSession sqlSession = rwProvider.getBatchSqlSession();) {
          for (int i = 0; i < rows.size(); i++) {
            sqlSession.update(updateQuery, rows.get(i));
          }
          sqlSession.commit();
          long queryEnd = System.currentTimeMillis();
          LogStats.logQueryStatistics("parallelStream", updateQuery, queryStart, queryEnd);
        }
      }
    }, thread, commitSize);
    return list.size();
  }

  @PostMapping(value = "/updateIsInvoiceAndIsCredit")
  @ResponseBody
  public Object updateIsInvoiceAndIsCredit() {
    final int thread = 10;
    final int commitSize = 100;
    final String updateQueryx = "updateIsInvoiceAndIsCredit";
    
    
    List<TransactionLoad> list = rwProvider.executeQuery("getTrxPostKeyFlagsForDocTypeAB", null);
    Iterator<TransactionLoad> itr = list.iterator();
    
    //Iterator<TransactionLoad> itr = rwProvider.executeQueryItr("getTrxPostKeyFlagsForDocTypeAB", null);
    
    asyncService.execute(itr, new IRunnableProcess<TransactionLoad>() {
      @Override
      public void process(List<TransactionLoad> rows) {
        long queryStart = System.currentTimeMillis();
        try (final SqlSession sqlSession = rwProvider.getBatchSqlSession();) {
          for (int i = 0; i < rows.size(); i++) {
            sqlSession.update(updateQueryx, rows.get(i));
            
          }
          sqlSession.commit();
          long queryEnd = System.currentTimeMillis();
          LogStats.logQueryStatistics("parallelStream", updateQueryx, queryStart, queryEnd);
        }
      }
    }, thread, commitSize);
    return list.size();
  }

}
