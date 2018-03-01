package app.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileService {
  private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
  
  public static StringBuilder getFileList(final String basePath, final String fileExtension, final StringBuilder s) {
    LOG.debug(Integer.toString(s.hashCode()));
    File file = new File(basePath);
    if (file.isDirectory()) {
      String[] fileList = file.list();
      for (int x = 0; x < fileList.length; x++) {
        File subFile = new File(basePath + fileList[x]);
        if (subFile.isFile() && fileList[x].endsWith(fileExtension)) {
          String completeFileName = basePath + fileList[x];
          LOG.debug(completeFileName);
          s.append(completeFileName + "<br>");
        } else if (subFile.isDirectory()) {
          String childPath = basePath + fileList[x] + "/";
          getFileList(childPath, fileExtension, s);
        }
      }
    }
    return s;
  }

}
