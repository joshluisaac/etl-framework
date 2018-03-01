package com.kollect.etl.util;


import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFileDIffResolver {
  
  private static final Logger LOG = LoggerFactory.getLogger(TextFileDIffResolver.class);

  //removes file1 items from file2
  public void resolveDiff(final String file1, final String file2, final String dest) throws IOException{
    FileUtils futils = new FileUtils();
    List<String> list1 = futils.readFile(file1);
    List<String> list2 = futils.readFile(file2);
    futils.writeListToFile(new File(dest), new ListUtils().subtract(list1, list2), true);
    LOG.info("Resolved differences between {} & {}", new Object[]{file1,file2});
  }

} 
