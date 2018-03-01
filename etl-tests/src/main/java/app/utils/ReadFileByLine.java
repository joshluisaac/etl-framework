package app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadFileByLine {

  private static final Logger LOG = LoggerFactory.getLogger(ReadFileByLine.class);

  public static final String BASE_PATH = "/home/joshua/martian/workspace/jupiter_callisto/resources";
  public static final String FILE_PATH = "/media/allihave/workspace_sts/_oca_review_questions/resources/file.txt";
  public static final String SQL_DIRECTORY_PATH = "/media/allihave/ptrworkspace/_workspace_jboss/hlbb_ecollect/database/incremental/";

  public static boolean isRegularFile(String filePath) throws FileNotFoundException {
    File file = new File(filePath);
    if (file.exists() && !file.isDirectory() && file.isFile()) {
      return true;
    } else {
      throw new FileNotFoundException(filePath + " is not a regular file");
    }
  }

  /**
   * Reads the lines in a file.
   * 
   * @param fileName
   * @return lines in a String array; null if the file does not exist or if the
   *         file name is null
   * @throws IOException
   */
  public static String[] readLines(String fileName) throws IOException {
    try {
      String filePath = BASE_PATH + "/" + fileName;
      if (filePath != null && ReadFileByLine.isRegularFile(filePath)) {
        File file = new File(filePath);
        if (file.exists()) {
          try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), "utf-8");
              BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
              lines.add(line);
            }
            LOG.debug("Loaded list of size " + lines.size() + " from file = " + filePath);
            return (lines.toArray(new String[lines.size()]));
          }
        } else {
          System.out.println("Missing file : " + filePath);
        }
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public static String[] getDirectoryFileList(String directoryPath) {
    File file = new File(directoryPath);
    if (file.isDirectory()) {
      String[] fileList = file.list();
      return fileList;
    }
    return null;
  }

  static StringBuilder sb = new StringBuilder();
  public static StringBuilder getRecursiveDirectoryList(final String basePath, final String fileExtension) {

    File file = new File(basePath);
    if (file.isDirectory()) {
      String[] fileList = file.list();
      for (int x = 0; x < fileList.length; x++) {
        File subFile = new File(basePath + fileList[x]);
        if (subFile.isFile() && fileList[x].endsWith(fileExtension)) {
          String completeFileName = basePath + fileList[x];
          LOG.debug(completeFileName);
          System.out.println(completeFileName);
          sb.append(completeFileName + "<br>");
        } else if (subFile.isDirectory()) {
          ReadFileByLine.getRecursiveDirectoryList(basePath + fileList[x] + "/", fileExtension);
        }
      }
    }
    return sb;
  }

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

  public static void getFileListRecurse(final String basePath, final String fileExtension, final StringBuilder s) {
    LOG.debug(Integer.toString(s.hashCode()));
    getFileList(basePath, fileExtension, s);

  }

  public static void main(String[] args) throws IOException {
    ReadFileByLine.readLines("file.txt");
    final String bPath = "/media/allihave/ptrworkspace/_workspace_jboss/hlbb_ecollect/database";
    final String fExt = ".sql";
    // System.out.println(ReadFileByLine.getRecursiveDirectoryList( bPath + "/",
    // fExt));
    System.out.println(getFileList(bPath + "/", fExt, new StringBuilder()));
  }

}
