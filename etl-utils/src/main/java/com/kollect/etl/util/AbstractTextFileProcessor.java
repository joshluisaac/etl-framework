package com.kollect.etl.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTextFileProcessor {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractTextFileProcessor.class);

  public Configuration config;
  public static List<String> BAD_ROWS;
  public static List<String> DUPLICATE_ROWS;

  public AbstractTextFileProcessor(Configuration config) {
    this.config = config;
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
   * Will remove duplicates from a list either based on a single unique key or
   * composite key leaving behind unique entries
   * 
   * @param appendedList
   *          input list which contains duplicates
   * @param keyArr
   *          the list of unique key or keys
   * 
   */
  public List<String> retainUniqueEntries(List<String> appendedList, String[] keyArr, boolean isHashAble, int columnSize) {
    int uniqueKeyColumn = -1;
    int keyLength = keyArr.length;
    if (keyLength == 1) {
      uniqueKeyColumn = Integer.parseInt(keyArr[0]);
      if (uniqueKeyColumn >= 0) {
        int uniqueKeyIndex = uniqueKeyColumn - 1;
        if (isHashAble) {
          Composite comp = incorporateHashedKey(appendedList, keyArr, columnSize);
          List<String> compList = comp.getCompositeList();
          appendedList = uniqueOnKey(compList, uniqueKeyIndex);
        } else {
          appendedList = uniqueOnKey(appendedList, uniqueKeyIndex);
        }
      } else {
        LOG.info("Skipping unique step for {}, since unique column is set to {}th column", uniqueKeyColumn);
      }
    } else if (keyLength > 1) {
      Composite comp = incorporateHashedKey(appendedList, keyArr, columnSize);
      int uniqueKeyIndex = comp.getLength();
      List<String> compList = comp.getCompositeList();
      appendedList = uniqueOnKey(compList, uniqueKeyIndex);
    }
    return appendedList;
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
  public Composite incorporateHashedKey(List<String> src, String[] position, int columnSize) {
    BAD_ROWS = new ArrayList<String>();
    List<String> result = new ArrayList<>();
    for (String s : src) {
      //System.out.println(s);
      StringBuffer token = new StringBuffer();
      String[] columns = s.split("\\|",-1);
      
      if (columns.length == columnSize) {
        for (int i = 0; i < position.length; i++) {
          int index = Integer.parseInt(position[i]);
          String str = columns[index - 1];
          token.append(str);
        }
        // System.out.println(s + "|" + hash(token.toString()));
        result.add(s + "|" + hash(token.toString()));
      }
      else {
        BAD_ROWS.add(s);
      }
      
    }
    return new Composite(result, columnSize);
  }
  
  // will build a list of records that do not satisfy the expectedLength criteria
  void isolateBadRecord() {
    
  }
  
  // will build a list of duplicate rows
  void isolateDuplicateRecord() {
    
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
    DUPLICATE_ROWS = new ArrayList<String>();
    Map<String, String> tmp = new HashMap<>();
    List<String> result = new ArrayList<>();
    for (String s : src) {
      String[] columns = s.split("\\|");
      // System.out.println(columns.length);
      // System.out.println(keyIndex);
      // System.out.println(s);

      if ((columns.length) > keyIndex) {
        String key = columns[keyIndex];
        if (!tmp.containsKey(key)) {
          tmp.put(key, s);
          result.add(s);
        } else {
          DUPLICATE_ROWS.add(s);
        }
      }

    }
    return result;
  }



  public String hash(final String rawString) {
    MessageDigest digest = null;
    ;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));
    String sha256hex = new String(Hex.encode(hash));

    return sha256hex;
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
   *           If <tt>fileSize</tt> is equal to zero
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
    LOG.info("Initial size of appended list: {}", target.size());
    return target;
  }

}
