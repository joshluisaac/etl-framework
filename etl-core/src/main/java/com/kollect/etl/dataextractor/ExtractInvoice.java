package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractInvoice implements IExtract {
  
  private static final Logger LOG = LoggerFactory.getLogger(ExtractInvoice.class);

  public List<String> query( Connection dbConnection, String queryString ) {
    List<String> sb = new ArrayList<>();
    try (Statement stmt = dbConnection.createStatement(); ResultSet res = stmt.executeQuery(queryString);) {
      while (res.next()) {
        String line = res.getString("customer_no") + "|" + res.getString("invoice_no") + "|"
            + res.getString("invoice_date") + "|" + res.getString("invoice_due_date") + "|"
            + res.getString("invoice_amount") + "|" + res.getString("invoice_gst") + "|"
            + res.getString("invoice_outstanding") + "|" + res.getString("created_at") + "|"
            + res.getString("updated_at") + "|" + res.getString("Description") + "|"
            + res.getString("line_of_business");
        sb.add(line);
      }
      return sb;
    } catch (SQLException sqle) {
      LOG.error("QUERY_ERROR: {}", sqle.getMessage());
    }
    return sb;
  }

}
