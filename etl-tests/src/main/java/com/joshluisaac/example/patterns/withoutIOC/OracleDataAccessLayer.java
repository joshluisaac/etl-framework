package com.joshluisaac.example.patterns.withoutIOC;

public class OracleDataAccessLayer  implements IDataAccess {

  @Override
  public void Add(UserWithOutIOC user) {
    System.out.println("User added to Oracle database...");
    
  }

}
