package com.kollect.connection.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSource implements IDataSource {

  static final Logger LOG = LoggerFactory.getLogger(DataSource.class);

  // private static final String DB_DRIVER = "org.postgresql.Driver";
  private static final String URL = "jdbc:postgresql://localhost";
  private static final String PORT = "5432";
  private static final String DB_USER = "kollectvalley";
  private static final String DB_PASSWORD = "kollect1234";

  private static final Map<String, Connection> connCache = new HashMap<>();
  
  private static final Map<String, Object> registry = new HashMap<>();

  private Object mutex = new Object();
  
  
  /**
   * Initiates connection to database URL but does not cache the connection for reuse.
   * 
   * @param dbName
   *          database name to connect to
   * @return returns <code>connection</code> object
   */

  
  public Connection initiateConnection(final String dbName) throws SQLException {
    Connection conn=null;
    try {
      //Class.forName("org.postgresql.Driver");
      //conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.25:5432/mahb_prod2x", "kollectvalley", "kollect1234");
      
      Class.forName ("com.informix.jdbc.IfxDriver");
      conn = DriverManager.getConnection("jdbc:informix-sqli://172.20.146.10:9088/uatdata:INFORMIXSERVER=pronto", "informix", "ProC0gN0s");
    }
    
    catch (Exception e) {
      LOG.error("Error occured while registering JDBC driver", e.getCause(), e);
    }
    
    //Connection conn = DriverManager.getConnection("jdbc:informix-sqli://172.20.146.10:9088/uatdata:INFORMIXSERVER=pronto", "informix", "ProC0gN0s");
    LOG.info("Connection created and hashcode: {}", conn.hashCode());
    return conn;
  }

  /**
   * Initiates and caches a connection to database URL. Making calls to a
   * database server located on a different network could be very expensive.This
   * costly and expensive process which should be cached and reused for future or
   * subsequent query requests.
   * 
   * @param dbName
   *          database name to connect to
   * @return returns <code>connection</code> object
   */

  public synchronized Connection initiateAndCacheConnection(final String dbName, final  String currThread) throws SQLException {
    Connection conn = connCache.get("connect");
    if (conn != null) {
      return conn;
    } else {
      conn = DriverManager.getConnection(buildDbConnection(dbName), DB_USER, DB_PASSWORD);

      LOG.info("Connection created and hashcode: {} by {}", conn.hashCode(), currThread);
      connCache.put("connect", conn);
      return conn;
    }
  }
  
  
  public void testSync(final String threadName) throws InterruptedException {
    synchronized (mutex) {
      String key = (String) registry.get("key");
      
      if(key != null) {
        LOG.info("{} retrieved key from map: {}", threadName,key);
      } else {
        registry.put("key", "some value");
        LOG.info("Added key to map by {} {}", threadName, registry.hashCode());
      }
    }
    

  }

  public static boolean isValidDriver() {
    return true;
  }

  private static String buildDbConnection(final String dbName) {
    // System.get
    return URL + ":" + PORT + "/" + dbName;
  }

}
