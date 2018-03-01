package com.joshluisaac.example.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcHmapExample {

  static Map<String, Long> orders = new ConcurrentHashMap<String, Long>();

  public static void processOrders() {
    Iterator<String> orderItr = orders.keySet().iterator();

    while (orderItr.hasNext()) {
      String city = orderItr.next();

      for (int i = 0; i < 50; i++) {
        Long oldOrder = orders.get(city);
        orders.put(city, oldOrder + 1);
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {

    orders.put("Lagos", 0l);
    orders.put("Accra", 0l);
    orders.put("Johannesburg", 0l);
    orders.put("Abuja", 0l);
    orders.put("Bamako", 0l);
    orders.put("Monrovia", 0l);
    orders.put("Harare", 0l);
    orders.put("Maputo", 0l);

    ExecutorService executor = Executors.newFixedThreadPool(2);
    executor.submit(ConcHmapExample::processOrders);
    executor.submit(ConcHmapExample::processOrders);
    executor.awaitTermination(1, TimeUnit.SECONDS);
    executor.shutdown();

    // log(orders);

    // processOrders();

    log(orders);

  }

  private static void log(Object aMsg) {
    System.out.println(String.valueOf(aMsg));
  }

}
