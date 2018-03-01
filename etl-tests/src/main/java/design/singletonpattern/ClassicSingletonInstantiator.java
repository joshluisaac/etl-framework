package design.singletonpattern;

public class ClassicSingletonInstantiator {

  public static void main( String[] args ) {
    ClassicSingleton.getInstance();
    ClassicSingleton.getInstance();
    ClassicSingleton.getInstance();
  }

}
