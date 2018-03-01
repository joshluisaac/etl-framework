package com.kollect.etl.services.business.batch;

import java.sql.SQLException;
import java.util.Iterator;

import com.kollect.etl.dataaccess.AbstractSqlSessionProvider;
import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public class AppImpRunnable {
  
  
  private IAbstractSqlSessionProvider getSqlSessionProvider(final String dbInstance) {
    IAbstractSqlSessionProvider provider = new AbstractSqlSessionProvider(dbInstance);
    return provider;
  }
  
  
  public void execute(final String dbInstance) {
    IAbstractSqlSessionProvider dataProvider = getSqlSessionProvider(dbInstance);
    try {
      dataProvider.query("getAllCustomer", null);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }
  
  
  public static void main(String[] args) {
    Thread[] threads = new Thread[3];
    for (int k=0; k < threads.length; k++) {
      threads[k] = new Thread(new Runnable() {
        @Override
        public void run() {
          new AppImpRunnable().execute("postgres_pbk");
        }
      });
      threads[k].start();
      
      
    }
    
    for (int k=0; k < threads.length; k++) {
      
      try {
        threads[k].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    
    
    
    
    
    for(int i=0; i < 4; i++) {
      new AppImpRunnable().execute("postgres_pbk");
    }
    
  }

}
