package com.kollect.etl.extractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.dataaccess.AbstractSqlSessionProvider;
import com.kollect.etl.util.DateUtils;
import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.PropertiesUtils;

public class DataExtractor {

  private static final Logger LOG = LoggerFactory.getLogger(DataExtractor.class);
  protected static final Map<String, Properties> propertyMap = new HashMap<>();
  private static final String EXTRACTION_STAT_LOG = "../logs/extractionStats.log";
  private static final String SERV_PROP = "SERVER_PROPERTY";

  private static String getOutputDir(final String parent, final String dirName) {
    return parent + "/" + dirName;
  }

  private String getDirectoryName() {
    return new DateUtils().getDateToString("yyyyMMdd", new Date());
  }

  public final Properties getAppConfigs() throws IOException {
    return new PropertiesUtils().loadPropertiesFileResource("/server.properties");
  }

  private Query<Map<String, Object>> executeQuery(IDataExtractorService service, String queryName, Object object) {
    final long queryStart = System.currentTimeMillis();
    List<Map<String, Object>> data = service.executeQueryObject(queryName, object);
    final long queryDiff = DataExtractorUtils.getDifference(queryStart, System.currentTimeMillis());
    Query<Map<String, Object>> query = new Query<Map<String, Object>>();
    query.setQueryDiff(queryDiff);
    query.setData(data);
    return query;
  }

  public static void main(String[] args) throws Exception {
    final FileUtils fUtils = new FileUtils();
    final DataExtractor extractor = new DataExtractor();
    final DataWriter writer = new DataWriter();
    propertyMap.put(SERV_PROP, extractor.getAppConfigs());
    final Properties servProp = propertyMap.get(SERV_PROP);
    final String extractionPath = servProp.getProperty("extraction.path");
    final String[] dbList = servProp.getProperty("db.sources").split(",");
    final String dirName = extractor.getDirectoryName();
    final String destPath = getOutputDir(extractionPath, dirName);
    final File dstDir = new File(destPath);
    fUtils.deleteFile(dstDir);
    fUtils.createDirIfNotExists(dstDir);
    new FileUtils().deleteFile(new File(EXTRACTION_STAT_LOG));

    for (int i = 0; i < dbList.length; i++) {
      LOG.info("Extracting from {}", dbList[i]);

      IDataAccess dao = new DataAccess(new AbstractSqlSessionProvider(dbList[i]));
      IDataExtractorService service = new DataExtractorService(dao);
      Map<String, String> queryMap = new HashMap<>();
      queryMap.put("CFMAST_DATA", "getCfmastData");
      queryMap.put("CFACCT_DATA", "getCfAcctData");

      for (Map.Entry<String, String> entry : queryMap.entrySet()) {
        LOG.info("Quering {} from {}", entry.getKey(), entry.getValue());
        List<Map<String, Object>> result = extractor.executeQuery(service, entry.getValue(), null).getData();
        List<String> data = new ArrayList<>();
        for (Map<String, Object> map : result) {
          data.add((String) map.get(entry.getKey()));
        }
        writer.write(destPath, entry.getKey(), data);
      }
    }
  }

}
