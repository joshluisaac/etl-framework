package com.joshluisaac.priorityqueue;

import java.util.PriorityQueue;
import java.util.Random;

//uses natural ordering to establish priority
public class PriorityQueueUsingNaturalOrdering {

  public static void main(String[] args) {

    PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
    Random rand = new Random();

    for (int i = 0; i < 20; i++) {
      queue.offer(rand.nextInt(100));
    }
    
    while (!queue.isEmpty()) {
      System.out.printf("%d,", queue.remove());
    }
    System.out.println();
  }
}
