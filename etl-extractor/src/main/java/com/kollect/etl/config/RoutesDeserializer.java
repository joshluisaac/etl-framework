package com.kollect.etl.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class RoutesDeserializer {
  
  private static final String JSON_DOM = "/home/joshua/IdeaProjects/projects/poweretl-resources/resources/routesConfig.json";
  private static final String JSON_DOM1 = "/home/joshua/IdeaProjects/projects/poweretl-resources/resources/dailyBatchRoutesConfig.json";
  
  
  
  
  public RouteItem[] readJsonDom(final File f) throws FileNotFoundException, IOException {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));){
      return new Gson().fromJson(br, RouteItem[].class);
    }
    
  }
  
  


}
