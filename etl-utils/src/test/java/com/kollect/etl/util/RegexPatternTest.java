package com.kollect.etl.util;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.kollect.etl.util.dataconnector.Regex;
import com.kollect.etl.util.dataconnector.TotalLoaded;

public class RegexPatternTest {
  
  Regex regex;
  private static final String SERVER_LOG_PATH = "/media/joshua/martian/kvworkspace/PowerETL/poweretl-notifier/sample/yycloading/Server.log";  
  private static final String DAYS_AGO = "4";
  
  @Before
  public void run_once_per_test() {
    regex = new Regex();
  }
  
  @Test
  public void getStats() {
    List<TotalLoaded> stats = null;
    try {
      stats = regex.getMatchedTokens(SERVER_LOG_PATH, Integer.parseInt(DAYS_AGO));
    } catch (NumberFormatException | IOException e) {
      e.printStackTrace();
    }
    System.out.println(stats.toString());
  }

}
