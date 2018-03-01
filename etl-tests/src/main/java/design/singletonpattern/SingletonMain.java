package design.singletonpattern;

public class SingletonMain {

  public static void main(String[] args) {

    Thread t1 = new Thread(new Runnable() {
      public void run() {
        SingletonLogger logger = SingletonLogger.getInstance_v1();
      }
    });

    Thread t2 = new Thread(new Runnable() {
      public void run() {
        SingletonLogger logger2 = SingletonLogger.getInstance_v1();
      }
    });

    t1.start();
    t2.start();

  }

}