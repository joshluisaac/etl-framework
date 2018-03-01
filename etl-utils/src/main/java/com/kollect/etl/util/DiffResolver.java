package com.kollect.etl.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiffResolver {
  private FileUtils fUtils;
  private Properties utilProp;
  private static final String UTIL_PROPERTIES = "../config/util.properties";
  private static final Logger LOG = LoggerFactory.getLogger(DiffResolver.class);

  private DiffResolver(final String utilPropFile, final FileUtils fUtils) throws IOException {
    this.fUtils = fUtils;
    loadUtilConfigs(utilPropFile);
    controlFileExistChecker(new File(getControlFile()));
  }

  private Properties loadUtilConfigs( final String fileName ) throws IOException {
    utilProp = new PropertiesUtils().loadPropertiesFile(fileName);
    return utilProp;
  }

  private String getControlFile() {
    return utilProp.getProperty("util.controlFile");
  }

  private String getSrcFolderHomeDir() {
    return utilProp.getProperty("util.srcFolderHomeDir");
  }

  private String getDiffOutHomeDir() {
    return utilProp.getProperty("util.diffOutHomeDir");
  }

  private void controlFileExistChecker( final File f ) throws FileNotFoundException {
    if (!f.exists()) {
      throw new FileNotFoundException("Control file can't be found");
    }
  }

  private String[] getCurrPrevDir( List<String> list, String srcFolderHomeDir ) {
    return new String[] { srcFolderHomeDir + "/" + list.get(0), srcFolderHomeDir + "/" + list.get(1) };
  }

  private List<String> getCsvFileList( final String srcFolderHomeDir, final String currDirPath ) {
    return fUtils.getFileList(new File(srcFolderHomeDir + "/" + currDirPath));
  }

  private void generateDeltaFiles( final List<String> dirSrcFiles, final String[] csvFileBaseDir,
      final String diffFilesDstDir ) throws IOException {
    if (dirSrcFiles.size() > 0) {
      fUtils.deleteFile(new File(diffFilesDstDir));
      fUtils.createDirIfNotExists(new File(diffFilesDstDir));
      Iterator<String> itr = dirSrcFiles.iterator();
      while (itr.hasNext()) {
        String next = itr.next();
        String diffFile = diffFilesDstDir + "/" + next.substring(0, next.indexOf(".")) + "_diff.csv";
        String pathFile1 = csvFileBaseDir[1] + "/" + next;
        String pathFile2 = csvFileBaseDir[0] + "/" + next;
        new TextFileDIffResolver().resolveDiff(pathFile1, pathFile2, diffFile);
      }
    } else {
      LOG.info("Source directory is empty");
    }
  }


  public static void main( String[] args ) throws Exception {
    final FileUtils fUtils = new FileUtils();
    final DiffResolver diff = new DiffResolver(UTIL_PROPERTIES, new FileUtils());
    final List<String> ctrlFileContentList = fUtils.readFile(diff.getControlFile());
    final List<String> uniqueList = new ListUtils().unique(ctrlFileContentList);
    Collections.reverse(uniqueList);
    final String srcFolderHomeDir = diff.getSrcFolderHomeDir();
    final String diffFilesDstDir = diff.getDiffOutHomeDir();
    final String[] currPrevDir = diff.getCurrPrevDir(uniqueList, srcFolderHomeDir);
    final List<String> csvFileList = diff.getCsvFileList(srcFolderHomeDir, currPrevDir[0]);
    diff.generateDeltaFiles(csvFileList, currPrevDir, diffFilesDstDir);

  }

}
