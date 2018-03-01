package com.joshluisaac.example.patterns.withIOC;

public class Db2DataAccessLayer  implements IDataAccess {

  @Override
  public void Add(User user) {
    System.out.println("User added to DB2 database...");
    
  }

}
