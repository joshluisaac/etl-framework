package com.kollect.etl.util;

import java.io.Reader;

import com.google.gson.Gson;

public class JsonUtils {
  
  private Gson gson;
  
  public JsonUtils() {
    this.gson = new Gson();
  }


  public <T> String toJson(T prop) {
    return gson.toJson(prop);
}
  
  
  public <T> T fromJson(String jsonText, Class<T> t){
    return  gson.fromJson(jsonText, t);
 }
  
  
  public <T> T fromJson(Reader reader, Class<T> t){
    return  gson.fromJson(reader, t);
 }

  
  

}
