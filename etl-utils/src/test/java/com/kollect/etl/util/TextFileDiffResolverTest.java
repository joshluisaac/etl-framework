package com.kollect.etl.util;


import java.io.IOException;
import org.joda.time.DateTime;


public class TextFileDiffResolverTest {

  public static void main(String[] args) throws IOException {
    TextFileDIffResolver diff = new TextFileDIffResolver();
    
    
    //new DateUtils().getCurrentDate() - 1;
    
    String date = new DateTime().minusDays(300).toString("yyyyMMdd");
    
    
    System.out.println(date);
    
    //new DateUtils().getCurrentDate("yyyy-MM-dd", date);
    String file1 = "/home/joshua/Desktop/out/2017-08-16/INVOICE_AED_ELEGANT_0.csv";
    String file2 = "/home/joshua/Desktop/out/2017-08-17/INVOICE_AED_ELEGANT_0.csv";
    String dest = "out.txt";
    diff.resolveDiff(file1,file2,dest);
  }

}
