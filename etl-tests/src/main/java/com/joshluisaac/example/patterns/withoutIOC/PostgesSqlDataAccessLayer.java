package com.joshluisaac.example.patterns.withoutIOC;

public class PostgesSqlDataAccessLayer  implements IDataAccess {
  
  public void Add(UserWithOutIOC user){
    System.out.println("User added to Postgres database...");
  }

}
