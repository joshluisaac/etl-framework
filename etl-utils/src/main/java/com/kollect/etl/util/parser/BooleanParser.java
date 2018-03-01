package com.kollect.etl.util.parser;

public class BooleanParser {
  
  public Boolean parseBoolean( final String s ) {
    if ((s instanceof String) && (!s.equals("")) && s != null) {
      return Boolean.parseBoolean(s);
    }
    return false;
  }

}
