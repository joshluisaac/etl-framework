package com.joshluisaac.example.patterns.withIOC;

public class MySqlDataAccessLayer implements IDataAccess {
  
  public void Add(User user) {
    System.out.println("User added to Mysql database...");
  }

}
