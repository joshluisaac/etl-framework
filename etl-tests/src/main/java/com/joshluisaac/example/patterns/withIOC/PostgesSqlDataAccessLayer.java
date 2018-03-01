package com.joshluisaac.example.patterns.withIOC;

public class PostgesSqlDataAccessLayer  implements IDataAccess {
  
  public void Add(User user){
    System.out.println("User added to Postgres database...");
  }

}
