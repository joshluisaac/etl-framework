package com.joshluisaac.priorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;

// does not depend on natural ordering rather it depends on user comparator logic which is then used to compare objects
public class ProcessUsersByName {

  public static void main(String[] args) {
    
    //using anonymous inner class
    PriorityQueue<User> queue1 = new PriorityQueue<User>(new Comparator<User>() {
      public int compare(User u1, User u2) {
        return u1.getName().compareTo(u2.getName());
      }
    });
    
    //using a separate class
    PriorityQueue<User> queue = new PriorityQueue<User>(new NameComparator());
    
    queue.add(new User("Zinab"));
    queue.add(new User("Kelly"));
    queue.add(new User("Josh"));
    queue.add(new User("Tamara"));
    queue.add(new User("Amanda"));
    queue.add(new User("Boogie"));
    queue.add(new User("Buki"));

    while (!queue.isEmpty()) {
      System.out.printf("%s%n", queue.remove());
    }

  }

}
