package com.joshluisaac.example.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallableTask implements Callable<Integer> {

  @Override
  public Integer call() throws Exception {

    return null;
  }

  Callable<String> callableTask = () -> {
    TimeUnit.MILLISECONDS.sleep(300);
    return "Task's execution";
  };

  Runnable runnableTask = () -> {
    try {
      TimeUnit.MILLISECONDS.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  };

}
