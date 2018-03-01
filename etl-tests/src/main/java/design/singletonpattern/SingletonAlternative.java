package design.singletonpattern;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class SingletonAlternative {
  
  private static final Logger LOG = LoggerFactory.getLogger(SingletonAlternative.class);
  private static Map<String, Service> cache = new HashMap<String, Service>();
  
  private SingletonAlternative(){}
  
  public static Service getService(final String service){
    Service wServ = cache.get(service);
    if (wServ != null && wServ instanceof Service) {
      LOG.debug("SERVICE_OBJECT: {} obtained from cache", new Object[]{service});
      return wServ;
    }
    if(wServ == null) {
      cache.put(service, new WeatherServiceImpl());
    }
    
    return wServ;

  }

  public static void main( String[] args ) {
    SingletonAlternative.getService("weather");
    SingletonAlternative.getService("weather");
    SingletonAlternative.getService("weather");
    SingletonAlternative.getService("weather");
    SingletonAlternative.getService("weather");

  }

}
