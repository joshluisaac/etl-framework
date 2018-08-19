package com.kollect.etl.util.dataconnector;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.kollect.etl.util.JsonUtils;
import com.kollect.etl.util.PropertiesUtils;
import com.kollect.etl.util.Utils;

public class DcStatistics {

  Regex r;
  JsonUtils j;
  Properties prop;
  Utils u;
  private final String FILEPATH = "../dclog.json";

//  public DcStatistics() throws IOException {
//
//    this.r = new Regex();
//    this.j = new JsonUtils();
//    this.prop = new PropertiesUtils().loadPropertiesFileResource("server.properties");
//    this.u = new Utils();
//  }

  public DcStatistics(Regex r, JsonUtils j, Properties prop, Utils u) {

    this.r = r;
    this.j = j;
    this.prop = prop;
    this.u = u;
  }

  public void dcStatstoJson(String[] args) throws NumberFormatException, IOException {

    String daysAgo = prop.getProperty("email.daysAgo");
    List<TotalLoaded> bodyList = r.getMatchedTokens(args[0], Integer.parseInt(daysAgo));

    // This should be serialized to a JSON formatted output and written to disk.
    // PowerETL notification engine will pick up that JSON fie, process it and the
    // send an email using thyme leaf templates
    u.writeTextFile(FILEPATH, j.toJson(bodyList), false);

  }
}
