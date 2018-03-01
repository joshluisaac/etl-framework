package com.kollect.etl.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by joshua on 10/3/17.
 */
public class BulkSearch {

  private static void log(Object obj) {
    System.out.println(obj);
  }

  private List<String> getListOfFiles(final String dir) {
    return new FileUtils().getFileList(new File(dir));
  }

  private List<String> readFile(String fileName) throws IOException {
    return new FileUtils().readFile(fileName);
  }

  private String getDirSearchPath(){
    return "/home/joshua/martian/ptrworkspace/ecollect/serverconfig";
  }
  
  private boolean DeleteIfAbsent(File f){
    return new FileUtils().deleteFile(f);
  }

  public static void main(String[] args) throws IOException {
    
    StringBuffer notMatchedKeyBuffer = new StringBuffer();
    BulkSearch bs = new BulkSearch();
    bs.DeleteIfAbsent(new File("/home/joshua/Desktop/last_used_tables/not_used_tables.txt"));
    String dirPath = bs.getDirSearchPath();
    List<String> dirList = bs.getListOfFiles(dirPath);
    List<String> keyFile = bs.readFile("/home/joshua/Desktop/last_used_tables/tables.txt");

    for (int k = 0; k < keyFile.size(); k++) {
      boolean isMatched = false;
      String keyPattern = keyFile.get(k);
      for (int h = 0; h < dirList.size(); h++) {
        String s = dirList.get(h);
        if (s.endsWith(".xml")) {
          List<String> content = bs.readFile(dirPath + "/" + s);
            Pattern p = Pattern.compile(keyPattern);
            for (int i = 0; i < content.size(); i++) {
              Matcher m = p.matcher(content.get(i).toUpperCase());
              if (m.find()) {
                //log("Key " + keyPattern + " found in " + s + " @ line number: " + i + " with " + rows + " rows");
                isMatched = true; // if pattern is matched, set a break point flag to stop searching for pattern. Proceed to next key
              }
              if (isMatched) break; // stop searching for pattern in current file
            }
          if (isMatched) break; // stop searching for pattern in other files
          }
        }
        if (!isMatched) {
        //log and append key pattern to not matched key buffer
          log(keyPattern + " not found!");
          notMatchedKeyBuffer.append(keyPattern + "\n");
        }
      }

new FileUtils().writeTextFile("/home/joshua/Desktop/last_used_tables/not_used_tables.txt", notMatchedKeyBuffer, true);

  }

}
