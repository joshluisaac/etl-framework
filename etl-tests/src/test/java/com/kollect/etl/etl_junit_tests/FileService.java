package com.kollect.etl.etl_junit_tests;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import com.kollect.etl.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by joshua on 10/6/17.
 */
public class FileService {

  private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
  List<String> resultList = new ArrayList<>();

  public List<String> getFileList(final String rootDir, final String fileExt) {
    File file = new File(rootDir);
    if (file.isDirectory()) {
      String[] fileList = file.list();
      Preconditions.checkNotNull(fileList);
      Preconditions.checkNotNull(fileList.length);
      for (int x = 0; x < fileList.length; x++) {
        File subFile = new File(rootDir + fileList[x]);
        if (subFile.isFile() && fileList[x].endsWith(fileExt)) {
          String absolutePath = rootDir + fileList[x];
          LOG.debug(absolutePath);
          System.out.println(absolutePath);
          resultList.add(absolutePath);
        } else if (subFile.isDirectory()) {
          String childDir = rootDir + fileList[x] + "/";
          getFileList(childDir, fileExt);
        }
      }
    }
    return resultList;
  }


  public static void main(String[] args) {
    FileService fs = new FileService();
    fs.getFileList("/home/joshua/martian/ptrworkspace", ".xml");
  }

  //@Test
  public void test_get_file_list(){
    getFileList("/home/joshua/martian/ptrworkspace/ecollect", ".xml");
  }


}
