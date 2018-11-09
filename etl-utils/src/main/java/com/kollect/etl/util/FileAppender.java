package com.kollect.etl.util;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

//The role of this is to append a group of CSV files starting with the same prefix and the same structure into one file
public class FileAppender extends AbstractTextFileProcessor {

  private static final Logger LOG = LoggerFactory.getLogger(FileAppender.class);

  public FileAppender(Configuration config) {
    super(config);
  }

  public void execute(String rootPath, String fileNamePrefix, ICsvAppendContext csvContext) throws IOException {
    final ICsvAppenderReport stat = csvContext.getReport();
    final long rowsWritten, bytesWritten;
    long[] dwStats = null;
    final String destFileName = fileNamePrefix.toLowerCase() + ".csv";
    final String badFileName = fileNamePrefix.toLowerCase() + "_bad.csv";
    final String duplicateFileName = fileNamePrefix.toLowerCase() + "_duplicate.csv";
    final String regex = config.getRegex(fileNamePrefix).trim();
    final String destDir = config.getFileDestination();
    final boolean isClonable = Boolean.parseBoolean(config.getCloneFlag(fileNamePrefix));
    final boolean isHashAble = Boolean.parseBoolean(config.getHashIndicator(fileNamePrefix));
    final int columnSize = Integer.parseInt(config.getExpectedLength(fileNamePrefix));
    final String cloneAs = config.getCloneAs(fileNamePrefix);
    final String replacement = config.getReplacement(fileNamePrefix);
    final String[] keyArr = config.getUniqueKeyIndex(fileNamePrefix).split("\\,");
    List<String> appendedList = append(rootPath, fileNamePrefix, stat);
    if (regex.length() > 0) {
      appendedList = replaceNonAsciiCharacters(appendedList, regex, replacement);
      if (isClonable)
        dwStats = deleteAndWriteToDisk(appendedList, destDir, cloneAs);
    } else {
      LOG.debug("Skipping REGX replacment step for {}", fileNamePrefix);
    }
    appendedList = retainUniqueEntries(appendedList, keyArr, isHashAble, columnSize);
    dwStats = deleteAndWriteToDisk(appendedList, destDir, destFileName);
    deleteAndWriteToDisk(BAD_ROWS, destDir, badFileName);
    deleteAndWriteToDisk(DUPLICATE_ROWS, destDir, duplicateFileName);

    rowsWritten = dwStats[0];
    bytesWritten = dwStats[1];
    stat.setRecordCount(rowsWritten);
    stat.setNumberOfBytes(bytesWritten);
    LOG.info("Final size of appended list: {}", appendedList.size());
    LOG.info("Appended files starting with {} to {}", new Object[] { fileNamePrefix, destFileName });
  }

  private static void displayUsage() {
    System.out.println("Usage: java com.kollect.etl.util.FileAppender outputDirectory");
    System.out.println("Flags are:");
    System.out.println("-v or --verbose: log statistics");
    System.out.println("-d or --distinct: prune duplicate lines");
    System.out.println("-h or --help: display this message");
  }

  public static void main(String[] args) throws Exception {
    String rootPath = null;
    String context = null;
    boolean verbose = false;

    for (int param = 0; param < args.length; param++) {
      if (args[param].equals("-v") || args[param].equals("--verbose")) {
        verbose = true;
      } else if ((args[param].equals("-h") || args[param].equals("--help"))) {
        displayUsage();
        return;
      } else {
        if (rootPath == null) rootPath = args[param];
        else if (context == null) context = args[param];
        else
          System.out.println("Unparsed: " + args[param]);
      }
    }
    if (context == null) {
      System.out.println("please provide input directory and context names");
      displayUsage();
      return;
    }

    Configuration configr = new Configuration();
    FileAppender appender = new FileAppender(configr);

    if (rootPath == null) {
      LOG.debug("Directory path is null, will check dirPath property in util.properties");
      rootPath = appender.getDirPath();
      if (rootPath.equals("")) {
        LOG.error("Directory path is still null");
        throw new NullPointerException();
      }
    }
    if (!new File(rootPath).exists()) {
      LOG.error("{} does not exist. Please check util.properties/concatenator.dirPath", rootPath);
      throw new IllegalArgumentException(MessageFormat.format("{0} does not exist", rootPath));
    }
    String[] prefixes = appender.splitFilePrefix("\\,");
    LOG.info("Appending files in {}", rootPath);

    List<ICsvAppenderReport> statList = new ArrayList<>();
    for (String p : prefixes) {
      ICsvAppenderReport stat = new CsvAppenderReport();
      // Argument Defined Anonymous inner class
      appender.execute(rootPath, p, new ICsvAppendContext() {
        @Override
        public ICsvAppenderReport getReport() {
          return stat;
        }
      });
      statList.add(stat);
    }
    String json = new Gson().toJson(statList);
    if (verbose)
      LOG.debug("{}", json);
    new FileUtils().writeTextFile(configr.getFileDestination() + "/" + context + "_transformation.control", null);
  }

}
