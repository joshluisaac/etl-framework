package com.joshluisaac.example.patterns.withoutIOC;

public class MySqlDataAccessLayer implements IDataAccess {
  
  public void Add(UserWithOutIOC user) {
    System.out.println("User added to Mysql database...");
  }

}
