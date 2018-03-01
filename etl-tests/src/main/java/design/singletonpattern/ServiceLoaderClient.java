package design.singletonpattern;

public class ServiceLoaderClient {

  public static void main( String[] args ) {
    Thread t1 = new Thread(new Runnable() {

      @Override
      public void run() {
        ServiceLoader.getInstance();
      }
    }, "thread-1");
    t1.start();
    
  }

}
