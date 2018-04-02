package com.kollect.etl.controller;

import com.kollect.etl.entity.Invoice;
import com.kollect.etl.service.AsyncBatchService;
import com.kollect.etl.service.IRunnableProcess;
import com.kollect.etl.service.InvoiceTransactionService;
import com.kollect.etl.service.ReadWriteServiceProvider;
import com.kollect.etl.util.LogStats;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InvoiceTransactionController {
    @Autowired
    private InvoiceTransactionService Service;
    @Autowired
    private ReadWriteServiceProvider rwProvider;
    @Autowired
    private AsyncBatchService asyncService;
    
    
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceTransactionController.class);

    @PostMapping(value ="/updateInvoiceTransaction")
    @ResponseBody
    public Object selectLumSumPayment () {
        return Service.executeInvoiceTransactionService();
    }
    
    
    @PostMapping(value ="/loadInv")
    @ResponseBody
    public Object loadInv() {
      int thread = 10;
      int commitSize = 100;
      String insertQuery = "insertInvoiceTransaction";
      String updateQuery = "updateInvoiceTransaction";
      Iterator<Invoice> itr = rwProvider.executeQueryItr("getInvoiceTransaction", null);
      asyncService.execute(itr, new IRunnableProcess<Invoice>() {

        @Override
        public void process(List<Invoice> threadData) {
          long queryStart = System.currentTimeMillis();
          try (final SqlSession sqlSession = rwProvider.getBatchSqlSession();) {
            for (int i = 0; i < threadData.size(); i++) {
              if(!threadData.get(i).isUpdateRow()) {
                sqlSession.insert(insertQuery, threadData.get(i));
              } else {
                sqlSession.update(updateQuery, threadData.get(i));
              }
            }
            sqlSession.commit();
            long queryEnd = System.currentTimeMillis();
            LogStats.logQueryStatistics("parallelStream", insertQuery, queryStart, queryEnd);
          } catch (PersistenceException persEx) {
            LOG.error("Failed to execute update statement: {}",insertQuery, persEx.getCause());
            throw persEx;
          }
          
        }
      }, thread, commitSize);
        return 0;
    }
}


