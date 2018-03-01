package com.joshluisaac.example.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * HashMap
 * <p>
 * <strong>Synchronization: this implementation is not synchronized.</strong> If
 * two or more threads are accessing a hash map concurrently, and one of those
 * threads makes a structural change to the map, any other thread reading from
 * that hash map is not guaranteed to return the right value or the program is
 * at risk a non-deterministic behavior at an undetermined time in the future. A
 * structural change to a hash map is defined as a change that adds or removes a
 * specified key. Note that just changing the value of a key will not affect the
 * structure of a hash map. As such if two or more threads are making structural
 * changes to this map, it has to be synchronized externally to avoid risking a
 * non-deterministic behaviour.Most enterprise applications populate the map
 * once by a single thread then multiple reads from so many threads.
 * 
 * <p>
 * <strong>Iterator is fail-fast.</strong> Structural changes (put or remove
 * operations) to the hash map after iterator object has been created or during
 * iteration will cause the iterator to throw a ConcurrentModificationException.
 * Thus, in the face of concurrent modification, the iterator fails quickly and
 * cleanly, rather than risking arbitrary, non-deterministic behavior at an
 * undetermined time in the future. Allowing the program to fail is a much more
 * cleaner approach than risking a non-deterministic behavior at an undetermined
 * time in the future by allowing the program to run.
 * 
 * <pre>
 * Exception in thread "main" java.util.ConcurrentModificationException
    at java.util.HashMap$HashIterator.nextNode(HashMap.java:1429)
    at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
    at com.joshluisaac.maps.HashMapExample.main(HashMapExample.java:93)
 * </pre>
 * 
 * -Not thread safe
 * 
 * 
 * -Performance is better than HashTable because it's not synchronized.
 * Synchronization effort/overhead is eliminated
 * 
 * -Iteration is not guaranteed in insertion order (used LinkedHashMap if you
 * require iteration to be same as insertion order)
 * 
 * 
 * <p>
 * <strong>How does it handle null keys and values.</strong> It could contain
 * null keys and null values
 * 
 * @author Josh Uzo
 * 
 */

public class HashMapExample {

  // Map<Integer, Integer> scores = new HashMap<Integer, Integer>();
  Map<Integer, Integer> scores = new LinkedHashMap<Integer, Integer>();
  Random r = new Random();

  public HashMapExample() {
  }

  public long getTimeInMillisec() {
    return System.currentTimeMillis();
  }

  public void populateMap() {
    long start, end;
    start = getTimeInMillisec();
    for (int i = 0; i <= 1000000; i++)
      scores.put(i, r.nextInt());
    end = getTimeInMillisec();

    System.out.println("Time taken for put operation: " + (end - start));
  }

  public void deleteMapEntry() {

  }

  public void updateMapEntry() {
  }

  public void addMapEntry() {
  }

  public static void main(String[] args) {

    HashMapExample hmexp = new HashMapExample();

    Map<Integer, Integer> scoresMap = hmexp.scores;

    Iterator<Integer> userItr = scoresMap.keySet().iterator();
    while (userItr.hasNext()) {
      int key = userItr.next();
      int value = scoresMap.get(key);
      // System.out.println(key + " >> " + value);

      // fail-fast: a put or remove operation while iterating will throw a
      // ConcurrentModificationException
      // scoresMap.put("user9", 900); //fail-fast with
      // ConcurrentModificationException: put operation changes the structure of
      // the map
      // scores.remove(key); //fail-fast with ConcurrentModificationException:
      // remove operation changes the structure of the map
    }

  }

}
