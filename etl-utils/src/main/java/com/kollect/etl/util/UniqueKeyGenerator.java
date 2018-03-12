package com.kollect.etl.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UniqueKeyGenerator {
  
  public List<String> readFile(){
    List<String> l = null;
    try {
      return new FileUtils().readFile(new File(""));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return l;
  }
  

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
