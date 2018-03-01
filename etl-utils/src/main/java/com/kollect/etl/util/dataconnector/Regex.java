package com.kollect.etl.util.dataconnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kollect.etl.util.DateUtils;

public class Regex {
  
  private static final Logger LOG = LoggerFactory.getLogger(Regex.class);

  List<TotalLoaded> loadMatchedTokens (List<String> logList, RegexHelper helper) {
    List<TotalLoaded> l = new ArrayList<>();
    for (RegexPattern r : RegexPattern.values()) {
      if (r.getKey().equals("totalLoaded")) {
        for (int i = 0; i < logList.size(); i++) {
          TotalLoaded result = helper.matchTotalLoaded(r.getPattern(), logList.get(i), r.getKey());
          if (result != null)
            l.add(result);
        }
      }
    }
    return l;
  }
  
  // pass the regex helper as an interface such as IRegexHelper
  // let the client code pass the file name
  // construct the starts with parameter from current/system date
  
  public  List<TotalLoaded> getMatchedTokens(final String logFile, int daysAgo) throws IOException {
    String lineStartsWith = new DateUtils().getDaysAgoToString("yyyy-MM-dd", daysAgo, new Date());
    LOG.info("Extracting lines starting with {}", lineStartsWith);
    RegexHelper helper = new RegexHelper();
    List<String> rowsPerSecLines = helper.readLinesEndingWith(logFile, " rows/sec");
    List<String> logList = new ArrayList<>();
    for (String s : rowsPerSecLines) {
      if(s.startsWith(lineStartsWith))  logList.add(s);
    }
    return loadMatchedTokens(logList, helper);
  }

}
