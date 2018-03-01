package com.joshluisaac.priorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class LoadEmployee {

  private static PriorityQueue<Employee> queue = new PriorityQueue<Employee>(new Comparator<Employee>() {
    @Override
    public int compare(Employee o1, Employee o2) {
      return o1.getSerialNo().compareTo(o2.getSerialNo());
    }
  });

  public static void loadQueue() {
    Random rand = new Random();
    for (int i = 0; i < 5; i++) {
      queue.add(new Employee(rand.nextInt()));
    }
  }

  public static void main(String[] args) {
    loadQueue();
    while (!queue.isEmpty()) {
      System.out.printf("%s%n", queue.remove());
    }
  }
}
