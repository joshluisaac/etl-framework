package org.etl.serverlite;

public class ScheduledTask implements Runnable {

  @Override
  public void run() {
    String threadName = Thread.currentThread().getName();
    System.out.println("Logging from runnable..." + threadName);
    
    
    
  }

}
