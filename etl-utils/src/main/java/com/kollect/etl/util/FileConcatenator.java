package com.kollect.etl.util;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

//The role of this is to append a group of CSV files starting with the same prefix and the same structure into one file
public class FileConcatenator {

  private static final Logger LOG = LoggerFactory.getLogger(FileConcatenator.class);
  private Configuration config;

  public FileConcatenator(Configuration config) {
    this.config = config;
  }

  /**
   * Retrieves the list of files in a directory starting with the specified prefix
   * 
   * @param dir
   *          directory from which to get the files
   * @param startsWith
   *          specified prefix
   * @return returns the list of files starting with the specified prefix
   */
  public List<String> retrieveFilesStartingWith(File dir, String startsWith) {
    List<String> list = new FileUtils().getFileList(dir);
    List<String> dest = new ArrayList<>();
    for (String l : list) {
      if (l.startsWith(startsWith))
        dest.add(l);
    }
    if (!dest.isEmpty()) {
      return dest;
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * Abstracted the dirPath property to the source files
   * 
   * @return returns the dirPath property
   */
  public String getDirPath() {
    return config.getDirPath();
  }

  /**
   * Splits the file prefix property into an array of strings
   * 
   * @param regex
   *          the delimiting regular expression
   * @return returns an array of prefixes derived by splitting the file prefix
   *         property
   */
  public String[] splitFilePrefix(String regex) {
    return config.getFilePrefix().split(regex);
  }

  /**
   * Replaces characters not within the boundaries of the regex expression to the
   * replacement text
   *
   * @param v
   *          list from which characters are to be replaced
   * @param regex
   *          regular expression
   * @param replacement
   *          replacement character
   * @return replaced list
   */
  public List<String> replaceNonAsciiCharacters(List<String> v, String regex, String replacement) {
    for (int i = 0; i < v.size(); i++) {
      v.set(i, v.get(i).replaceAll(regex, replacement));
    }
    return v;
  }

  // write target to disk
  public long[] deleteAndWriteToDisk(List<String> src, String rootPath, String targetFile) {
    File file = new File(rootPath, targetFile);
    new FileUtils().deleteFile(file);
    long rowsWritten = new FileUtils().writeListToFile(file, src, true);
    long bytesWritten = file.length();
    return new long[] { rowsWritten, bytesWritten };
  }

  /**
   * Appends a set of files starting with the same prefix
   * 
   * @param rootPath
   *          which is the absolute path to the source files
   * @return returns an array of prefixes derived by splitting the file prefix
   *         property
   * @throws IllegalArgumentException
   *         If <tt>fileSize</tt> is equal to zero
   */
  public List<String> append(String rootPath, String fileNamePrefix, ICsvAppenderReport stat) throws IOException {
    List<String> fileList = retrieveFilesStartingWith(new File(rootPath), fileNamePrefix);
    int fileSize = fileList.size();
    final String LOG_MESSAGE = MessageFormat.format(
        "The size of {0} is {1}. \n " + "This implies that, file grouping {0} "
            + "wasn't found in your args[0] directory. \n " + "Please check that first and try again",
        fileNamePrefix, fileSize);
    if (fileSize == 0) {
      LOG.error(LOG_MESSAGE);
      throw new IllegalArgumentException(LOG_MESSAGE);
    }
    List<String> target = new ArrayList<>();
    for (String file : fileList) {
      target.addAll(new FileUtils().readFile(new File(rootPath, file)));
    }
    stat.setFileCount(fileSize);
    stat.setFileName(fileNamePrefix);
    stat.setRecordCount(target.size());
    return target;
  }

  /**
   * Will remove duplicates from a list either based on a single unique key or
   * composite key leaving behind unique entries
   * 
   * @param appendedList
   *          input list which contains duplicates
   * @param keyArr
   *          the list of unique key or keys
   * 
   */
  public List<String> retainUniqueEntries(List<String> appendedList, String[] keyArr) {
    int uniqueKeyColumn = -1;
    int keyLength = keyArr.length;
    if (keyLength == 1) {
      uniqueKeyColumn = Integer.parseInt(keyArr[0]);
      if (uniqueKeyColumn >= 0) {
        int uniqueKeyIndex = uniqueKeyColumn - 1;
        appendedList = uniqueOnKey(appendedList, uniqueKeyIndex);
      } else {
        LOG.info("Skipping unique step for {}, since unique column is set to {}th column", uniqueKeyColumn);
      }
    } else if (keyLength > 1) {
      Composite comp = buildListWithCompositeKey(appendedList, keyArr);
      appendedList = uniqueOnKey(comp.getCompositeList(), comp.getLength());
    }
    return appendedList;
  }

  public void execute(String rootPath, String fileNamePrefix, ICsvAppendContext csvContext) throws IOException {
    final ICsvAppenderReport stat = csvContext.getReport();
    final long rowsWritten, bytesWritten;
    long[] dwStats = null;
    final String destFileName = fileNamePrefix.toLowerCase() + ".csv";
    final String regex = config.getRegex(fileNamePrefix).trim();
    final boolean isClonable = Boolean.parseBoolean(config.getCloneFlag(fileNamePrefix));
    final String cloneAs = config.getCloneAs(fileNamePrefix);
    final String replacement = config.getReplacement(fileNamePrefix);
    final String[] keyArr = config.getUniqueKeyIndex(fileNamePrefix).split("\\,");

    List<String> appendedList = append(rootPath, fileNamePrefix, stat);

    if (regex.length() > 0) {
      appendedList = replaceNonAsciiCharacters(appendedList, regex, replacement);
      if (isClonable)
        dwStats = deleteAndWriteToDisk(appendedList, rootPath, cloneAs);
    } else {
      LOG.debug("Skipping REGX replacment step for {}", fileNamePrefix);
    }
    appendedList = retainUniqueEntries(appendedList, keyArr);
    dwStats = deleteAndWriteToDisk(appendedList, rootPath, destFileName);
    rowsWritten = dwStats[0];
    bytesWritten = dwStats[1];
    stat.setRecordCount(rowsWritten);
    stat.setNumberOfBytes(bytesWritten);
    LOG.info("Concatenated files starting with {} to {}", new Object[] { fileNamePrefix, destFileName });
  }

  /**
   * Removes duplicates in a list based off of a specified keyIndex
   *
   * @param src
   *          list from which duplicates are to be removed
   * @param keyIndex
   *          an index which serves as the unique key
   * @return unique list
   */
  public List<String> uniqueOnKey(List<String> src, int keyIndex) {
    Map<String, String> tmp = new HashMap<>();
    List<String> result = new ArrayList<>();
    for (String s : src) {
      String[] columns = s.split("\\|");
      String key = columns[keyIndex];
      if (!tmp.containsKey(key)) {
        tmp.put(key, s);
        result.add(s);
      }
    }
    return result;
  }

  /**
   * Builds a list based off the src list and then adds a new column to the nth
   * position based on the composite key. Uniqueness is only guaranteed when the
   * columns are combined. The composite key would be used to uniquely identify
   * records within a list. This key would be used to detect duplicates.
   * 
   * @param src
   *          input source list to be worked on
   * @param position
   *          keys used to create composite key
   * 
   * @return returns a composite object
   */
  public Composite buildListWithCompositeKey(List<String> src, String[] position) {
    List<String> result = new ArrayList<>();
    for (String s : src) {
      StringBuffer sb = new StringBuffer();
      sb.append(s + "|");
      String[] columns = s.split("\\|");
      for (int i = 0; i < position.length; i++) {
        int index = Integer.parseInt(position[i]);
        String str = columns[index - 1];
        sb.append(str);
      }
      result.add(sb.toString());
    }
    return new Composite(result, src.get(0).split("\\|").length);
  }

  public static void main(String[] args) throws Exception {
    FileConcatenator con = new FileConcatenator(new Configuration());
    String rootPath = args[0];
    if (rootPath == null) {
      LOG.debug("Directory path is null, will check dirPath property in util.properties");
      rootPath = con.getDirPath();
      if (rootPath.equals("")) {
        LOG.error("Directory path is still null");
        throw new NullPointerException();
      }
    }
    if (!new File(rootPath).exists()) {
      LOG.error("{} does not exist. Please check util.properties/concatenator.dirPath", rootPath);
      throw new IllegalArgumentException(MessageFormat.format("{0} does not exist", rootPath));
    }
    String[] prefixes = con.splitFilePrefix("\\,");
    LOG.info("Concatenating files in {}", rootPath);

    List<ICsvAppenderReport> statList = new ArrayList<>();
    for (String p : prefixes) {
      ICsvAppenderReport stat = new CsvAppenderReport();
      //Argument Defined Anonymous inner class
      con.execute(rootPath, p, new ICsvAppendContext() {
        @Override
        public ICsvAppenderReport getReport() {
          return stat;
        }
      });
      statList.add(stat);
    }
    String json = new Gson().toJson(statList );
    LOG.info("{}", json);
  }

}
