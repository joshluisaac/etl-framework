package com.kollect.etl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LogStats {
  
  private static final Logger LOG = LoggerFactory.getLogger(LogStats.class);
  
  public static void logQueryStatistics(String strategy, String queryName, long queryStart, long queryEnd) {
    Logger log = getLog();
    log.info("Query {} {} ms using {} ({})", queryName, (queryEnd - queryStart), strategy, Thread.currentThread().getName());
  }
  
  
  private static Logger getLog() {
    return LOG;
  }

}
