package com.kollect.etl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceUtils {
  
  private static final Logger LOG = LoggerFactory.getLogger(ResourceUtils.class);
  private static final String DIR_PATH = "../config/";
  public static final String SERVER_PROP = "server.properties";
  private static String[] propertyFilePaths = new String[] {SERVER_PROP};
  
  
  
  public final Properties getServerProps() throws IOException {
    Properties p = new Properties();
    String fileName = DIR_PATH + propertyFilePaths[0];
    File file = new File(fileName);
    if (file.exists()) {
        p.load(new FileInputStream(file));
        LOG.info("Loaded " + propertyFilePaths[0]);
        return p;
    } else {
        LOG.error("Server properties file " + fileName + " not found");
        throw new FileNotFoundException();
    }
}
  

  
  /**
   * Returns default port if isn't set
   * <p/>
   * 
   * @return The default port
   */
  public final int getAssignedPort(Map<String, Properties> propertyMap) {
    ProcessBuilder procBuilder = new ProcessBuilder();
    final String portNumber = procBuilder.environment().get("PORT");
    if (portNumber != null) {
      return Integer.parseInt(portNumber);
    }
    return Integer.parseInt(propertyMap.get("SERVER_PROPERTY").getProperty("server.port"));
  }

}
