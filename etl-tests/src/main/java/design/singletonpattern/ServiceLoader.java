package design.singletonpattern;


//maintains a static reference to the lone singleton instance
//returns that reference from the static getInstance() method

//explicitly declared as final because constructor is private and therefore it cannot be sub-classed
public final class ServiceLoader {
  
  //maintains a static reference to the lone singleton instance
  private static ServiceLoader servLoad;
  
  private ServiceLoader(){
    System.out.println("Instance created");
  }
  
  //returns a reference to ServiceLoader
  public static synchronized ServiceLoader getInstance(){
    if(servLoad == null){
      servLoad = new ServiceLoader();
    }
    
    return servLoad;
  }



}
