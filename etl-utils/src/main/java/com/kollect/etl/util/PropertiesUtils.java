package com.kollect.etl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {

  private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtils.class);

  /**
   * Reads properties file from an absolute path
   * @param file the file name to read 
   * @return returns the read file as a properties object
   */
  public final Properties loadPropertiesFile(final String fileName) throws IOException {
    Properties p = new Properties();
    try (FileInputStream in = new FileInputStream(new File(fileName))) {
      p.load(in);
      LOG.info("Loaded {}", fileName);
      return p;
    } catch (FileNotFoundException e) {
      LOG.error("{} cannot be found", fileName);
      throw new FileNotFoundException();
    }
  }

  private File getFile(final String name) {
    File f = null;
    try {
      f = new File(name);
    } catch (NullPointerException e) {
      LOG.error("Pathname argument is null \n {}", e);
      throw e;
    }
    return f;
  }

  private String getClassPathResource(String fileName) {
    URL url = getClass().getClassLoader().getResource(fileName);
    return url.getFile();
  }

  /**
   * Reads properties file from a classpath
   * @param file the file name to read 
   * @return returns the read file as a properties object
   */
  public final Properties loadPropertiesFileResource(final String fileName) throws IOException {
    Properties p = new Properties();
    try (FileInputStream in = new FileInputStream(getFile(getClassPathResource(fileName)))) {
      p.load(in);
      LOG.info("Loaded {}", fileName);
      return p;
    } catch (FileNotFoundException e) {
      LOG.error("{} cannot be found", fileName);
      throw new FileNotFoundException();
    }
  }
}
