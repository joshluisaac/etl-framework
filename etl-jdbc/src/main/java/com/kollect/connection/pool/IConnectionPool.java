package com.kollect.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {
  
  public void executeQuery(Connection con, String sql) throws SQLException;

}