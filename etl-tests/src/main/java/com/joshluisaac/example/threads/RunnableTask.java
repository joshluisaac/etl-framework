package com.joshluisaac.example.threads;

public class RunnableTask implements Runnable {
  
  
  private int id;

  public RunnableTask(final int id) {
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

}
