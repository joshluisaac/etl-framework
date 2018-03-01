package design.singletonpattern;

public class SingletonLogger {

  private SingletonLogger() {
    System.out.println("Instance created");
  }

  private static SingletonLogger log;

//Lazy Initialization (If required then only)
  
  /**
   * Lazy Initialization and Thread
   * @param message The message
   * @param cause The cause of the problem.
   */
  
  
  public static synchronized SingletonLogger getInstance_v1() {

    if (log == null) {
      log = new SingletonLogger();
    }
    return log;
  }
  
  
  

}
