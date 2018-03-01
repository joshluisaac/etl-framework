package com.joshluisaac.example.patterns.withoutIOC;

public class Db2DataAccessLayer  implements IDataAccess {

  @Override
  public void Add(UserWithOutIOC user) {
    System.out.println("User added to DB2 database...");
    
  }

}
