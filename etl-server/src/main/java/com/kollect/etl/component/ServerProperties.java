package com.kollect.etl.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Maps server.properties file to this object
 */

@Component
@ConfigurationProperties("server")
public class ServerProperties {
  
  private String port;
  
  @Autowired
  public ServerProperties(String port) {
    this.port = port;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "ServerProperties [port=" + port + "]";
  }
  
  
  public void getx() {
    
  }
  
  

}
