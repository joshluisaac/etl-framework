package com.kollect.etl.incubation;

import java.io.File;
import com.kollect.etl.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService {

  private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
  
  public void find(File f, long size){
    if(f.isDirectory()) {
      String[] fileList = f.list();
      Preconditions.checkNotNull(fileList);
      for(String x : fileList) {
        File subFile = new File(f,x);
        if(subFile.isDirectory()) {
          find(subFile, size);
        }else {
          if (subFile.length() > size) {
            LOG.debug("{} - {}", new Object[]{subFile.getAbsolutePath(),subFile.length()});
          }
          //System.out.println( subFile.getAbsolutePath() + " - " + subFile.length());
        }
      }
    }
  }

  public static void main(String[] args) {
    String base_path = args[0];
    long fileSize = Long.parseLong(args[1]);
    new FileService().find(new File(base_path),fileSize);
  }
}
