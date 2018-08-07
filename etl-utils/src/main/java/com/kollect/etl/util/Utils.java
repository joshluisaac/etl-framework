package com.kollect.etl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The purpose of this class is to perform adhoc tasks such as reading and writing to files, formatting tasks etc
 *
 * @author joshua
 */

public class Utils {

  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);


  public static Matcher matcher(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    return m;
  }


  public void writeTextFile(final String filePath, final Object data, boolean append) {
    new FileUtils().writeTextFile(filePath, data, append);
  }

  public StringBuffer listToBuffer(List<String> list) {
    return new ListUtils().ListToBuffer(list);
  }


  public List<String> readFile(File file) {
    List<String> list = null;
    try {
      list = new FileUtils().readFile(file);
    } catch (IOException io) {
      LOG.error("{}", io.getMessage());
    }
    return list;
  }


}
