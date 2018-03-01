package com.joshluisaac.example.patterns.withIOC;

public class OracleDataAccessLayer  implements IDataAccess {

  @Override
  public void Add(User user) {
    System.out.println("User added to Oracle database...");
    
  }

}
