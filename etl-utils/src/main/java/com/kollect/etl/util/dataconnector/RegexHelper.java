package com.kollect.etl.util.dataconnector;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kollect.etl.util.FileUtils;

 class RegexHelper {

  int matchAllKeys(String pattern, String candidate, String patternKey) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    if (m.find()) {
      return !patternKey.equals("totalLoaded") ? Integer.parseInt(m.group(1)) : 999;
    }
    return -1;
  }

  List<String> readLogFile(final String fileName) throws IOException {
    return new FileUtils().readFile(fileName);
  }
  
  List<String> readLinesEndingWith(final String fileName, final String suffix) throws IOException {
    return new FileUtils().readFileLinesEndsWith(fileName, suffix);
  }

  TotalLoaded matchTotalLoaded(String pattern, String candidate, String patternKey) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    if (m.find()) {
      if (patternKey.equals("totalLoaded")) {
        return new TotalLoaded(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)),
            Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5).replaceAll("\\,", "")));
      }
    }
    return null;
  }

}
