package com.joshluisaac.priorityqueue;

public class User {
  
  private final String name;
  private long id;
  private static long userCounter;
  
  public User(String name){
    this.name = name;
    this.id = userCounter++;
  }
  
  public String getName(){return name;}
  public long getId(){return id;}
  
  public String toString(){
    return "User(id:" + id + " , name: " + name + ")";
    
  }
  

}
