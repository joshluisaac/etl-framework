package com.joshluisaac.example.nested.classes;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * These are used to provide ad hoc implementations of interfaces
 */

public class AnonymousInnerClass {
  
  private static int x = 0x0755_FFFF;

  private List<String> list(File file) {
    String[] fileList = file.list();
    List<String> l = new ArrayList<String>(Arrays.asList(fileList));
    return l;
  }

  private void listFiles(File dir) {
    if (dir.isDirectory()) {
      File[] files = dir.listFiles(new FileFilter() {
        @Override
        public boolean accept(File f) {
          return f.isFile();
        }
      });
    }
  }



  public static void main(String[] args) {
    AnonymousInnerClass in = new AnonymousInnerClass();

    File myDir = new File("/home/joshua/desktop");
    
    System.out.println(Integer.toBinaryString(x));;

  }

  interface Marsupials {

  }

}
