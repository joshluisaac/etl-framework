package com.kollect.connection.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlainJdbcConnection {

    IDataSource dataSource;
    volatile long queryStart, queryEnd;
    private static final Logger LOG = LoggerFactory.getLogger(PlainJdbcConnection.class);

    public PlainJdbcConnection(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeQuery(Connection con, String sql) throws SQLException {
        PreparedStatement prepStmt = con.prepareStatement(sql);
        Connection conp = prepStmt.getConnection();
        ResultSet result = prepStmt.executeQuery();
    }
    
    public void metaData() throws SQLException {
      Connection conn = dataSource.initiateConnection("kollectvalley");
    }

    private long getEpoch() {
        return System.currentTimeMillis();
    }

    public void invokeAsynchronous(String sql, int thread) {
        final Thread[] threads = new Thread[thread];
        for (int p = 0; p < threads.length; p++) {
            threads[p] = new Thread(new Runnable() {
                @Override
                public void run() {
                    queryStart = getEpoch();
                    String currThread = Thread.currentThread().getName();
                    System.out.println("Start: " + queryStart + ":" + currThread);
                    try {
                        executeQuery(dataSource.initiateAndCacheConnection("kollectvalley", currThread), sql);
                        queryEnd = getEpoch();
                        System.out.println("End: " + queryEnd + ":" + currThread);
                        LOG.info("Query timing {}ms using ({})", (queryEnd - queryStart), currThread);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[p].start();
        }

        for (int p = 0; p < threads.length; p++) {
            try {
                threads[p].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        String sql = "select customer_name, customer_no, created_at,updated_at from customers limit 20";
        PlainJdbcConnection app = new PlainJdbcConnection(new DataSource());
        //app.invokeAsynchronous(sql, 10);
        app.metaData();
        
    }
}
