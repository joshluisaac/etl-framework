package com.joshluisaac.example.comparable;

public class User implements Comparable<User> {
  
  String username,password;
  
  User(String username,String password){
    this.username = username;
    this.password = password;
  }

  public String getUsername(){
    return this.username;
  }
  
  public String getPassword(){
    return this.password;
  }

  public int compareTo(User user) {
    // TODO Auto-generated method stub
    
    return this.username.compareTo(user.getUsername());
  }
  
}
