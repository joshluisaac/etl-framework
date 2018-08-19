package com.kollect.etl.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kollect.etl.component.DcStatistics;
import com.kollect.etl.util.dataconnector.TotalLoaded;

@Service
public class DataConnectorNotification {

  @Value("${app.serverLogPath}")
  private String serverLogPath;

  @Value("${app.daysAgo}")
  private String daysAgo;
  private DcStatistics dcStats;

  @Autowired
  public DataConnectorNotification(DcStatistics dcStats) {
    this.dcStats = dcStats;
  }

  void jsonEncode() {
    List<TotalLoaded> stats = dcStats.getStats(serverLogPath, daysAgo);
  }
}
