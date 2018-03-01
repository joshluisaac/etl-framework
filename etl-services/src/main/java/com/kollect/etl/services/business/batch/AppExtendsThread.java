package com.kollect.etl.services.business.batch;

class Runner extends Thread {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      String currThreadName = Thread.currentThread().getName();
      System.out.println(i + "from " + currThreadName);
      try {
        Thread.sleep(100);
        System.out.println("Thread " + currThreadName);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

public class AppExtendsThread {

  public static void main(String[] args) {
    Runner runner = new Runner();
    runner.setName("");
    runner.start();
    //this will look for the run method and then execute it in it's own thread
    //runner.start();
    

    //Runner runner2 = new Runner();
    //runner2.start();
  }

}
