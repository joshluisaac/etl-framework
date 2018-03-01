package com.kollect.etl.services.business.batch;

public class Runner2 implements Runnable {
  
  protected Object mutex = new Object();

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      String currThreadName = Thread.currentThread().getName();
      System.out.println(i + " ....... " + currThreadName);
      try {
        synchronized (mutex) {
          Thread.sleep(10000);
        }
        
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
