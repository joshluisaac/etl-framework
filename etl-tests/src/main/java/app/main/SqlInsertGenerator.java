package app.main;

import java.io.IOException;
import java.util.Random;

import app.utils.ReadFileByLine;

public class SqlInsertGenerator {
  
  public static void main (String[] args) throws IOException {
    
    String[] lines = ReadFileByLine.readLines("sample.txt");
    
    Random rand = new Random();
    
    long startTime = System.currentTimeMillis();
    
    for (int i = 0; i < lines.length; i++){
      System.out.println("insert into ptrcall_log (id,log_id,call_log_time,duration,load_execution_id,extension_id,call_type_id,no) values (" 
      + i + "," + rand.nextInt(1700) + "," + "'2017-04-27 00:00:00.0'" + "," + rand.nextInt(2500) + "," + 3666 + "," + 4 + "," + 3 + "," + "'" + lines[i]  + "'" + ");");
    }
    
    long endTime = System.currentTimeMillis();
    
    System.out.println(endTime - startTime);
    
  }
  
  

}
