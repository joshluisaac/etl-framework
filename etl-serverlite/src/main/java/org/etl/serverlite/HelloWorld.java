package org.etl.serverlite;

import static spark.Spark.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
  
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  
  void invoke() {
    scheduler.scheduleAtFixedRate(new ScheduledTask(), 10, 10, TimeUnit.SECONDS);
    //scheduler.
  }
  
  
  public static void main(String[] args) {
    //get("/hello", (request, response) -> "Hello World!");
    
    new HelloWorld().invoke();
    
  }
  
  
  
  
}
