package com.kollect.etl.service.email;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.kollect.etl.util.FileUtils;
import com.kollect.etl.util.StringUtils;

public class EtlTemplateBuilder {
  
  private List<String> getExtractionStat(){
    try {
      return new FileUtils().readFile("../logs/extractionStats.log");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public String buildHtmlTemplate(String content) {
    String message = MessageFormat.format(content, new Object[]{new Date(),new Date()});
    StringBuilder template = new StringBuilder();
    template.append("<!DOCTYPE html>");
    template.append("<html lang=\"en\">");
    template.append("<head>");
    template.append("</head>");
    template.append("<body>");
    template.append("<h3>" + message + "</h3>");
    template.append("<body>");
    template.append("<table align=\"left\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"1000\">");
    template.append("<tr>"
        + "<th>DB Name</th>"
        + "<th>File Name</th><th>Query & formatting (ms)</th>"
        + "<th> Wrting to disk (ms)</th>"
        + "<th>Data Size (Bytes)</th><th>Number of Records</th>"
        + "</tr>");

    List<String> l = getExtractionStat();
    StringUtils su = new StringUtils();
    for (int i = 0; i < l.size(); i++) {
      Object[] row = su.splitString(l.get(i), "\\|");
      String rowTag = MessageFormat.format("<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><tr>", row);
      template.append(rowTag);
    }
    template.append("</body></html>");
    return template.toString();
  }
  

}
