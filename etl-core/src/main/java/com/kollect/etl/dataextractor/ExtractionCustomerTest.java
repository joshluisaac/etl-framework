package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractionCustomerTest implements IExtract {
  
  private static final Logger LOG = LoggerFactory.getLogger(ExtractionCustomerTest.class);

  @Override
  public List<String> query( Connection dbConnection, String queryString ) {
    List<String> list = new ArrayList<String>();
    try (Statement stmt = dbConnection.createStatement(); ResultSet res = stmt.executeQuery(queryString);) {
      while (res.next()) {
        String line = 
        res.getString("customer_no") + "|" + 
        res.getString("customer_name") + "|"+
        res.getString("created_at") +"|"+ 
        res.getString("updated_at");
        list.add(line);
      }
      return list;
    } catch (SQLException sqle) {
      sqle.getMessage();
      LOG.error("QUERY_ERROR: {}", sqle.getMessage());
    }
    return list;
  }

}
