package com.kollect.etl.dataaccess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.util.IRecordDispenser;
import com.kollect.etl.util.IteratorRecordDispenser;

public class AsyncBulkUpdate {
  
  private static final Logger LOG = LoggerFactory.getLogger(AsyncBulkUpdate.class);
  
  private IAbstractSqlSessionProvider dao;
  Iterator mQueryResults = null;
  private String identifier;
  private Date effectiveDate;
  
  public AsyncBulkUpdate() {
    dao = new AbstractSqlSessionProvider("postgres");
  }
  
  
  protected String getIdentifier(){
    if (identifier == null)
      throw new IllegalStateException("Identifier not yet set for instance of " + this.getClass().getName());
    return identifier;
  }
  
  
  
  
  public <T> void execute2(IAbstractSqlSessionProvider daoProvider, Iterator<T> itr, final int thread, final int commitSize) {
    this.identifier = "ASYNC-BATCH";
    mQueryResults = dao.queryMultipleObjects("getBulkInvoices", null);
    final IRecordDispenser dispenser = new IteratorRecordDispenser(mQueryResults, 200, getIdentifier());
    Thread[] threads = new Thread[10];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Runnable() {
        public void run() {
          while (true) {
            List records = new ArrayList();
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
              daoProvider.batchInsert(records, "insertInvoices");
              
              
            } catch (Exception e) {
              //logException(e);
              e.printStackTrace();
            }
          }
        }
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
  
  
  public void execute() {
    this.identifier = "ASYNC-BATCH";
    mQueryResults = dao.queryMultipleObjects("getBulkInvoices", null);
    final IRecordDispenser dispenser = new IteratorRecordDispenser(mQueryResults, 200, getIdentifier());
    Thread[] threads = new Thread[10];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Runnable() {
        public void run() {
          while (true) {
            List records = new ArrayList();
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
              //System.out.println(records.size());
              //dao.batchInsert(records, "insertCustomerTest");
              dao.batchInsert(records, "insertInvoices");
              
              
            } catch (Exception e) {
              //logException(e);
              e.printStackTrace();
            }
          }
        }
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
  
  public static void main(String[] args) {
    AsyncBulkUpdate asy = new AsyncBulkUpdate();
    asy.execute();
  }

}
