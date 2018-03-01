package design.singletonpattern;

public class WeatherServiceImpl implements Service {
  
  public WeatherServiceImpl(){
    System.out.println("Created weather service");
  }

  @Override
  public String getName() {
    return "Weather service";
  }

}
