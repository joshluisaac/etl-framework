package com.kollect.etl.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kollect.etl.config.RouteItem;
import com.kollect.etl.config.RoutesDeserializer;

@Service
public class RouteService {
  
  RoutesDeserializer routes;
  
  @Autowired
  RouteService(RoutesDeserializer routes){
    this.routes = routes;
  }
  
  public RouteItem[] readJsonBlob(final File file) {
    RouteItem[] routeItem = null;
    try {
      return routes.readJsonDom(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return routeItem;
  }

}
