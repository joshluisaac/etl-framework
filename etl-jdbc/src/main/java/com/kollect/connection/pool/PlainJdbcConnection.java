package com.kollect.connection.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlainJdbcConnection {
  
  IDataSource dataSource;
  
  public PlainJdbcConnection(IDataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(PlainJdbcConnection.class);
  
  public PreparedStatement prepare(Connection con, String sql) throws SQLException {
    PreparedStatement prepStmt = con.prepareStatement(sql);
    return prepStmt;
  }

  public void executeQuery(Connection con, String sql) throws SQLException {
    PreparedStatement prepStmt = con.prepareStatement(sql);
    //prepStmt.setInt(1, 65);
    Connection conp = prepStmt.getConnection();
    ResultSet result = prepStmt.executeQuery();
    //LOG.info("{}", new Object[] {conp.hashCode()});
  }
  
  
  public void invokeSynchronous(String sql) {
    for (int i=0; i <= 10; i++) {
      long queryStart =  System.currentTimeMillis();
      try {
        //executeQuery(new DataSource().initiateConnection("kollectvalley"), sql);
        executeQuery(new DataSource().initiateAndCacheConnection("kollectvalley"), sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      long queryEnd =  System.currentTimeMillis();
      LOG.info("Query timing {}ms using ({})", (queryEnd - queryStart), Thread.currentThread().getName());
    }
  }
  
  private  long getEpoch() {
    return System.currentTimeMillis();
  }

  volatile long queryStart, queryEnd;
  
  //this will execute queries in parallel, that is, simultaneously 
  public void invokeAsynchronous(String sql, int thread) {
    final Thread[] threads = new Thread[thread];
    for(int p=0; p<threads.length; p++) {
      threads[p] = new Thread(new Runnable() {
        @Override
        public void run() {
          queryStart =  getEpoch();
          String currThreadName = Thread.currentThread().getName();
          System.out.println("Start: " + queryStart + ":" + currThreadName);
          try {
            executeQuery(dataSource.initiateAndCacheConnection("kollectvalley"), sql);
            //dataSource.testSync(currThreadName);
            queryEnd =  getEpoch();
            System.out.println("End: " + queryEnd + ":" + currThreadName);
            LOG.info("Query timing {}ms using ({})", (queryEnd - queryStart), currThreadName);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
      threads[p].start();
    }
    
    for(int p=0; p<threads.length; p++) {
      try {
        threads[p].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws SQLException {
    //long start =  System.currentTimeMillis();
    String sql = "select customer_name, customer_no, created_at,updated_at from customers limit 20";
      String sql2 = "select customer_name, customer_no, created_at,updated_at from customers limit 20";
    PlainJdbcConnection app = new PlainJdbcConnection(new DataSource());
    //app.invokeSynchronous(sql);
    long start = app.getEpoch();
    app.invokeAsynchronous(sql,3);
    System.out.println(app.getEpoch() - start);
      start = app.getEpoch();
    app.invokeAsynchronous(sql2, 7);
      System.out.println(app.getEpoch() - start);
//    app.invokeAsynchronous(sql);
//    app.invokeAsynchronous(sql);
    //LOG.info("{}ms", (System.currentTimeMillis() - start));
  }
}
