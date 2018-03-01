package com.kollect.etl.util.parser;

public class IntegerParser {
  
  public Integer parseInt( final String s ) {
    if ((s instanceof String) && (!s.equals("")) && s != null) {
      return Integer.parseInt(s);
    }
    return null;
  }

}
