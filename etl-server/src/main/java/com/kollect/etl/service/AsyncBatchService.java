package com.kollect.etl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.dataaccess.DaoProvider;
import com.kollect.etl.util.IRecordDispenser;
import com.kollect.etl.util.IteratorRecordDispenser;


@Service
public class AsyncBatchService {
  
  private static final Logger LOG = LoggerFactory.getLogger(AsyncBatchService.class);
  
  @Autowired
  private DaoProvider dao;
  
  Iterator<Object> mQueryResults = null;
  private String identifier;
  @SuppressWarnings("unused")
  private Date effectiveDate;
  

  
  protected String getIdentifier(){
    if (identifier == null)
      throw new IllegalStateException("Identifier not yet set for instance of " + this.getClass().getName());
    return identifier;
  }

  
  public <T> void execute(Iterator<T> itr, final String queryName,  final int thread, final int commitSize) {
    this.identifier = "asynchronous-batch";
    @SuppressWarnings("unchecked")
    final IRecordDispenser<Object> dispenser = new IteratorRecordDispenser(itr, commitSize, getIdentifier());
    Thread[] threads = new Thread[thread];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Runnable() {
        public void run() {
          while (true) {
            List<Object> records = new ArrayList<Object>();
            try {
              dispenser.dispenseRecords(records);
            } catch (Exception e) {
              LOG.error(getIdentifier() + " Thread " + Thread.currentThread().getName()
                  + " encountered exception while dispensing records. Proceeding to the next set of records. Message: "
                  + e.getMessage(), e);
            }
            if (records.size() == 0)
              break;
            try {
              
<<<<<<< HEAD
              dao.batchUpdate(records, queryName);
=======
              servProvider.batchInvoice(records, queryName);
>>>>>>> d2d6f10b045d20674cac171feb236982c2b1f027
              
            } catch (Exception e) {
              //logException(e);
              e.printStackTrace();
            }
          }
        }
        @SuppressWarnings("unused")
        private void logException(SQLException e) {
          LOG.error(
              getIdentifier() + " " + Thread.currentThread().getName()
                  + " encountered a database error while trying to commit, " + "proceeding to the next set of records",
              e);
        }
      });
      threads[i].setName(getIdentifier() + "-" + (i + 1));
      threads[i].start();
    }
    
    for (int i = 0; i < threads.length; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        // Should never happen
      }
    }
    
  }

}
