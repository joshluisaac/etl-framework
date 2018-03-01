package com.kollect.etl.util;


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

}
