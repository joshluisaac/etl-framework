package com.kollect.etl.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

  @Test
  public void dateFormat() throws ParseException {
    Date date = DateFormat.getInstance().parse("9/11/18 12:34 PM");
    System.out.println(date.toInstant().toEpochMilli());
    System.out.println(date.getTime());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateText = sdf.format(date);
    
    System.out.println(date);
    System.out.println(dateText);
    
    Date date2 = new SimpleDateFormat("MM/dd/yy HH:mm").parse("9/11/18 12:34 PM");
    System.out.println(date2);
  }
  
  
  @Test
  public void parseDate() throws ParseException {
    String dateText = "9/11/18 12:34 PM";
    Date date = DateFormat.getInstance().parse(dateText);
    System.out.println("An example of a timestamp: " + new Timestamp(date.getTime()));
    System.out.println("An example of an EPOCH: " + date.getTime());
  }

}
