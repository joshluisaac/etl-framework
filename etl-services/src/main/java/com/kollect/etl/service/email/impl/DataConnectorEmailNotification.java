package com.kollect.etl.service.email.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.kollect.etl.util.dataconnector.TotalLoaded;

public class DataConnectorEmailNotification extends AbstractEmailService {
  
  private static final Map<String, ConfigDto> CONFIG_MAP = new HashMap<>();
  private static final String ALL_DBS = "ALL DATABASES";
  private static final String CUSTOMER_CSV = "CUSTOMER.csv";
  
  public DataConnectorEmailNotification(final String str) {
    String[] config = str.split("\\,");
    for(int i = 0; i < config.length; i++) {
      loadConfigMap(config[i]);
    }
  }
  
  public DataConnectorEmailNotification() {
    loadConfigMap();
  }
  
  
  
  void loadConfigMap() {
    CONFIG_MAP.put("CUSTOMER", new ConfigDto(ALL_DBS, CUSTOMER_CSV));
    CONFIG_MAP.put("ACCOUNTS", new ConfigDto(ALL_DBS, "CUSTOMER_ACCOUNTS.csv"));
    CONFIG_MAP.put("CUSTOMER_ADDRESSES", new ConfigDto(ALL_DBS, CUSTOMER_CSV));
    CONFIG_MAP.put("CUSTOMER_EMAIL", new ConfigDto(ALL_DBS, CUSTOMER_CSV));
    CONFIG_MAP.put("CUSTOMER_PHONE_NOS", new ConfigDto(ALL_DBS, CUSTOMER_CSV));
    CONFIG_MAP.put("CUSTOMER_PERSON_INCHARGE", new ConfigDto(ALL_DBS, CUSTOMER_CSV));
    CONFIG_MAP.put("CREDIT", new ConfigDto(ALL_DBS, "CREDIT_NOTE.csv"));
    CONFIG_MAP.put("INVOICE", new ConfigDto(ALL_DBS, "INVOICE.csv"));
    CONFIG_MAP.put("INVOICE_TRANS1", new ConfigDto(ALL_DBS, "INVOICE.csv"));
    CONFIG_MAP.put("TRANSACTIONS", new ConfigDto(ALL_DBS, "PAYMENT.csv"));
  }
  
  
  void loadConfigMap(final String s) {
    CONFIG_MAP.put(s, new ConfigDto(ALL_DBS, s+".csv"));
  }
  
  
  class ConfigDto {
    String dbName;
    String fileName;
    
    public ConfigDto(String dbName, String fileName) {
     this.dbName = dbName;
     this.fileName = fileName;
    }
    
    private String getDbName() {
      return this.dbName;
    }
    
    private String getFileName() {
      return this.fileName;
    }
  }
  
  public String generateHtmlTemplate(String message, List<TotalLoaded> bodyList) {
    String messageHeader = MessageFormat.format(message, new Object[]{new Date(),new Date()});
    StringBuilder template = new StringBuilder();
    template.append("<!DOCTYPE html>");
    template.append("<html lang=\"en\">");
    template.append("<head>");
    template.append("</head>");
    template.append("<body>");
    template.append("<h3>" + messageHeader + "</h3>");
    template.append("<body>");
    template.append("<table align=\"left\" border=\"1\" cellpadding=\"5\" cellspacing=\"0\" width=\"1500\">");
    template.append("<tr>"
        + "<th>No.</th>"
        + "<th>Load Type</th>"
        + "<th>DB Name</th>"
        + "<th>File Name</th>"
        + "<th>Total Number of Records Extracted (New/Updated)</th>"
        + "<th>Total Number of Records Loaded</th>"
        + "<th>Total Number of Records Rejected</th>"
        + "<th>Duration (sec)</th>"
        + "<th>Number of Row Processed per/sec (sec)</th>"
        + "</tr>");

    for (int i = 0; i < bodyList.size(); i++) {
      TotalLoaded loaded = bodyList.get(i);
      int rowNumber = i;
      String dbName = CONFIG_MAP.get(loaded.getKey()).getDbName();
      String fileName = CONFIG_MAP.get(loaded.getKey()).getFileName();
      Object[] row = {(rowNumber + 1),loaded.getKey(), dbName, fileName, loaded.getEligible(), loaded.getLoaded(), (loaded.getEligible() - loaded.getLoaded()), loaded.getDuration(), loaded.getRowsPerSec()};
      String rowTag = MessageFormat.format("<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}</td></tr>", row);
      template.append(rowTag);
    }
    template.append("</table>");
    template.append("</body></html>");
    return template.toString();
  }
  
  
  
  
  

}
