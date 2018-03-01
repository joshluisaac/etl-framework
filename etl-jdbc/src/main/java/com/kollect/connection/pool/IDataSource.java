package com.kollect.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDataSource {
  
  public void testSync(final String threadName) throws InterruptedException;
  public Connection initiateAndCacheConnection(final String dbName) throws SQLException;
  public Connection initiateConnection(final String dbName) throws SQLException;

}
