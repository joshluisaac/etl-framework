package com.kollect.etl.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;

public class DateUtils {
  
  public DateTime getCurrentDate(){
    return new DateTime();
   }
  
  public String getDateToString(String format, Date juDate){
    return new DateTime(juDate).toString(format);
   }
  
  public String getDaysAgoToString(String format, int days, Date juDate){
    return new DateTime(juDate).minusDays(days).toString(format);
   }
  
  public String getCurrentDateToString(String format){
    return new DateTime().toString(format);
   }
  
  public DateTime getBeginOfMonth(){
    return new DateTime();
   }
  
  public DateTime getEndOfMonth(){
    return new DateTime();
   }
  
  public static long getEpoch(){
    return System.currentTimeMillis();
  }
  
  
  public static long parseDate(String dateText){
    Date date;
    try {
      date = DateFormat.getInstance().parse(dateText);
      return date.getTime();
    } catch (ParseException e) {
      System.err.println("Unparsable date " + dateText);
    }
    return -1;
  }
  
  public static Timestamp getTimestamp(long epoch){
    return new Timestamp(epoch);
  }
  
  public static String dateFormatter(Date date) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
  }
  
  
  
  
  

}
