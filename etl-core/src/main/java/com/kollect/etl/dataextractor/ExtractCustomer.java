package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractCustomer implements IExtract {
  
  private static final Logger LOG = LoggerFactory.getLogger(ExtractCustomer.class);
  
  
  @Override
  public List<String> query( Connection dbConnection, String queryString ) {
    List<String> sb = new ArrayList<>();
    try (Statement stmt = dbConnection.createStatement(); ResultSet res = stmt.executeQuery(queryString);) {
      while (res.next()) {
        String line = 
        res.getString("customer_no") + "|" + 
        res.getString("customer_name") + "|"+ 
        res.getString("person_in_charge") + "|" + 
        res.getString("address_1") +"|"+ 
        res.getString("address_2") +"|"+ 
        res.getString("address_3") +"|"+ 
        res.getString("address_4") +"|"+ 
        res.getString("postcode") +"|"+ 
        res.getString("office_phone_1") +"|"+ 
        res.getString("office_phone_2") +"|"+ 
        res.getString("fax_1") +"|"+ 
        res.getString("fax_2") +"|"+ 
        res.getString("email") +"|"+ 
        res.getString("sales_agent") +"|"+ 
        res.getString("created_at") +"|"+ 
        res.getString("updated_at") +"|"+ 
        res.getString("register_no") +"|"+ 
        res.getString("line_of_business");
        sb.add(line);
      }
      return sb;
    } catch (SQLException sqle) {
      LOG.error("QUERY_ERROR: {}", sqle.getMessage());
    }
    return sb;
  }

}
