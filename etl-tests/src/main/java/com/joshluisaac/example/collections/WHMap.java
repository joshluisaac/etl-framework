package com.joshluisaac.example.collections;

import java.util.Map;
import java.util.WeakHashMap;

public class WHMap {

  public static void main(String[] args) {
Map<Order, Integer> orders = new WeakHashMap<Order, Integer>();

    orders.put(new Order(1,"Minions"), 100);
    orders.put(new Order(2,"Fipper"), 2100);
    orders.put(new Order(3,"Earphones"), 300);
    orders.put(new Order(4,"Stylus"), 400);
    orders.put(new Order(5,"Notepad"), 500);
   
    System.out.println(orders);
  }

}
