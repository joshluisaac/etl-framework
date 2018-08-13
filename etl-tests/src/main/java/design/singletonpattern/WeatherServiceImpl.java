package design.singletonpattern;

public class WeatherServiceImpl implements Service {
  
  public WeatherServiceImpl(){
    System.out.println("Created weather com.kollect.etl.service");
  }

  @Override
  public String getName() {
    return "Weather com.kollect.etl.service";
  }

}
