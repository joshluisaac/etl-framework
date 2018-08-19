package com.kollect.etl.component;



import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.dataconnector.Regex;
import com.kollect.etl.util.dataconnector.TotalLoaded;

@Component
public class DcStatistics {
  
  Regex r;
  JsonUtils j;
  
  @Autowired
  public DcStatistics(Regex r, JsonUtils j) {

    this.r = r;
    this.j = j;
  }
  
  
  public List<TotalLoaded> getStats(String serverLog, String daysAgo) {
    List<TotalLoaded> stats = null;
    try {
      stats = r.getMatchedTokens(serverLog, Integer.parseInt(daysAgo));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }
    return stats;
  }
  
  
  
  


}
