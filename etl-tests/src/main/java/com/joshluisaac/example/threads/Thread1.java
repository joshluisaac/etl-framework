package com.joshluisaac.example.threads;

import com.joshluisaac.samples.FakeService;

/**
 * Ways of creating a thread 
 * Extend the thread class or implement the Runnable
 * interface and pass it to the constructor of the thread class. You can also
 * use executor services to create and manage thread pools
 */

public class Thread1 {

  public static void main(String[] args) {

    Thread t1 = new Thread(new Runnable() {
      public void run() {
        new FakeService().loop();
      }
    });
    
    t1.start();

  }

}
