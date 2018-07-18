package com.joshluisaac.example.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPools implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(ThreadPools.class);

  private int id;

  public ThreadPools(final int id) {
    this.id = id;
  }

  public ThreadPools() {
  }

  public void run() {
    String threadName = Thread.currentThread().getName();
    System.out.println("Starting Task: " + id + " by thread " + threadName);
    try {
      Thread.sleep(2000);
      // System.out.println("Processing Task: " +id);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Completed Task: " + id + " by thread " + threadName);
  }

  Callable<String> callableTask = () -> {
    TimeUnit.MILLISECONDS.sleep(300);
    log.debug("Executing task");
    return "Task's execution";
  };

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    List<Integer> values = Arrays.asList(0,0,1,2,3,4,5,6,7,8,9,10,64);

    System.out.println(values.stream()
        .filter(e -> e > 3)
        .filter(e -> e % 2 == 0)
        .map(e -> e * 2).findFirst().orElse(0));

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    // ExecutorService executorService = Executors.newSingleThreadExecutor();

    List<Future<String>> ftureList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      // executorService.submit(new ThreadPools(i));
      Future<String> future = executorService.submit(new ThreadPools().callableTask);
      ftureList.add(future);

    }

    executorService.shutdown();
    log.debug("All tasks submitted");

    // blocks program execution until all tasks are executed
    boolean isCompleted = false;
    isCompleted = executorService.awaitTermination(1, TimeUnit.DAYS);

    log.debug("{}",ftureList.get(0).get());
    log.debug("All tasks completed");

  }

}
