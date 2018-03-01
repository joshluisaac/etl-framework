package design.singletonpattern;

import java.text.MessageFormat;

public class ClassicSingleton {

  // lazy instantiation to null
  private static ClassicSingleton singInstance = null;

  // Exists only to thwart instantiation
  private ClassicSingleton() {
    System.out.println(MessageFormat.format("{0}", "Instance created"));

  }
  
  /**
   * A synchronized method which creates an instance of ClassicSingleton by providing a global point of access to ClassicSingleton, 
   * while ensuring method is thread-safe, such that two or more threads cannot concurrently create multiple instances of ClassicSingleton.
   * Creating multiple instances of ClassicSingleton defeats the whole purpose of singleton design pattern. 
   *  <p/>
   * 
   * Synchronization ensures that a mutual-exclusive lock on an object is acquired on-behalf of the executing thread.
   * Lock is only released when that thread has completed it's task. For example, when lock is acquired by a 
   * thread-A no other thread-B or thread-C can have access to the object being locked until it is released by thread-A
   * <p/>
   *
   * @return A ClassicSingleton instance.
   */

  // Provides a global point of access to the object
  // used synchronized keyword to make it thread-safe, such that two or more threads cannot 
  // concurrently create multiple instances of ClassicSingleton
  public static synchronized ClassicSingleton getInstance() {
    if (singInstance == null) {
      
      singInstance = new ClassicSingleton();
      System.out.println(MessageFormat.format("Singleton created and cached in JVM memory. Hash code: {0}", singInstance.hashCode()));
      return singInstance;
    }
    System.out.println(String.format("Retrieving object from cache %s", singInstance.hashCode()));
    return singInstance;
  }

}