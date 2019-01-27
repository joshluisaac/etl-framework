package com.joshluisaac.example.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPools implements Runnable {

  private static final Logger LOG = LoggerFactory.getLogger(ThreadPools.class);
  private int id;
  private static final int NUMBER_OF_THREADS = 10;

  public ThreadPools(final int id) {
    this.id = id;
  }

  public ThreadPools() {
  }
  
  
  public void streamFilter() {
    List<Integer> values = Arrays.asList(0,0,1,2,3,4,5,6,7,8,9,10,64);
    System.out.println(values.stream()
        .filter(e -> e > 3)
        .filter(e -> e % 2 == 0)
        .map(e -> e * 2).findFirst().orElse(0));
  }

  public void run() {
    String threadName = Thread.currentThread().getName();
    System.out.println("Starting Task: " + id + " by thread " + threadName);
    try {
      Thread.sleep(2000);
      System.out.println("Processing Task: " +id);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Completed Task: " + id + " by thread " + threadName);
  }
  
  
  Runnable someRunnableTask = () -> {
    String threadName = Thread.currentThread().getName();
    LOG.debug("Starting Task: {} by thread {}", id, threadName);
    try {
      Thread.sleep(2000);
      LOG.debug("Processing Task: {}", id);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    LOG.debug("Completed Task: " + id + " by thread " + threadName);
  };

  Callable<String> callableTask = () -> {
    String threadName = Thread.currentThread().getName();
    LOG.debug("Starting Task by thread {}", threadName);
    TimeUnit.MILLISECONDS.sleep(6000);
    LOG.debug("Executing by {}", threadName);
    return "done!";
  };

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ThreadPools tp = new ThreadPools();
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    List<Future<String>> ftureList = new ArrayList<>();
    for (int i = 0; i < 1; i++) {
      //executorService.submit(new ThreadPools(i));
      //Future<?> fr = executorService.submit(tp.someRunnableTask);
      Future<String> future = executorService.submit(tp.callableTask);
      ftureList.add(future);
    }

    executorService.shutdown();
    LOG.debug("All tasks submitted");

    // blocks program execution until all tasks are executed
    boolean isCompleted = false;
    isCompleted = executorService.awaitTermination(1, TimeUnit.DAYS);

    LOG.debug("{}",ftureList.get(0).get());
    LOG.debug("All tasks completed");

  }

}
