package com.kollect.etl.util;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class PropertiesUtilsTest {
  
  private String fileName;
  private PropertiesUtils prop;
  
  @Before
  public void run_once_per_method_call() {
    fileName = "/util.properties";
    prop = new PropertiesUtils();
  }
  
  @Test
  public void load_properties_test() throws IOException {
    prop.loadPropertiesFileResource(fileName);
  }

}
