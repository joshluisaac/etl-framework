package com.kollect.etl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
 
  public String[] splitString( final String s, final String regex ) {
    return s.split(regex);
  }
  
  public static boolean hasMatch(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    return m.find();
  }
}
