package com.joshluisaac.example.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPools implements Runnable {

  private int id;

  public ThreadPools(final int id) {
    this.id = id;
  }

  public void run() {
    System.out.println("Starting: " + id);

    try {
      Thread.sleep(1);
      System.out.println("Processing: " +id);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Completed: " + id);
  }

  public static void main(String[] args) {
    
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    for(int i = 0; i<50; i++) {
      executorService.submit(new ThreadPools(i));
    }
    
    executorService.shutdown();
    System.out.println("All tasks submitted");
    
    //blocks program execution until all tasks are executed
    boolean isCompleted = false;
    try {
      isCompleted = executorService.awaitTermination(1, TimeUnit.DAYS);
      System.out.println(isCompleted);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(isCompleted);
    System.out.println("All tasks completed");

  }

}
