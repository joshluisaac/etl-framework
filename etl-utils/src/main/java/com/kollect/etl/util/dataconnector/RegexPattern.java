package com.kollect.etl.util.dataconnector;

public enum RegexPattern {
  
  REJECTED_HEADER_FOOTER_ERROR("headerFooter","rejected as headers/footers"),
  PARSE_ERROR("parseError","failed due to parsing errors"),
  FILTER_COND_ERROR("filterCond","rejected due to not meeting filter conditions"),
  DATA_INTEGRITY_ERROR("dataIntegrity","failed due to data integrity errors"),
  UNKNOWN_ERROR("unknownErr","failed due to unknown errors"),
  DB_LATENCY_ERROR("dbLatency","failed due to database taking too long to process request"),
  DB_COMMITS_ERROR("dbCommit","failed due to failed database commits"),
  TOTAL_FAIL_ERROR("totalFailure","total failures and rejects"),
  TOTAL_LOADED("totalLoaded","([A-Za-z0-9_\\-]+)\\u0020+loaded\\u0020+([\\d]+)\\u0020+of\\u0020+([\\d]+)\\u0020+eligible\\u0020+lines\\u0020+in\\u0020+(\\d+)\\u0020+seconds\\u0020+([0-9,]+)");

  private final String key;
  private final String suffixMsg;
  private static final String PREFIX = "\\d{4}-\\d{2}-\\d{2}\\s+\\d+:\\d+:\\d+,\\d+\\s+INFO";
  private static final String NO_OF_RECS = "\\s+(\\d+)\\s+of\\s+(\\d+)\\s+";

  
  RegexPattern(String key, String suffixMsg){
    this.key = key;
    this.suffixMsg = suffixMsg;
  }
  
  public String getPattern() {
    return getKey().equals("totalLoaded") ? suffixMsg :  PREFIX + NO_OF_RECS + suffixMsg;
  }
  
  public String getKey() {
    return key;
  }

}
