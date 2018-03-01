package com.kollect.etl.pbk;

import java.util.Iterator;
import java.util.List;

public interface Formattable {
  
  public List<String> format (Iterator<Object> itr);

}
