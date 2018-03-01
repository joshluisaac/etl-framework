package com.joshluisaac.example.threads;

import com.joshluisaac.samples.FakeService;

/**
 * Ways of creating a thread Extend the thread class or implement the Runnable
 * interface and pass it to the constructor of the thread class. You can also
 * use executor services to create and manage thread pools.
 * 
 * This example shows how to create a thread by extending a thread class
 */

public class Thread2 extends Thread {
  
  public void run(){
    new FakeService().loop();
  }
  
  public static void main(String[] args) {
   new Thread2().start();
  }

}
