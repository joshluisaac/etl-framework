package com.joshluisaac.example.patterns.withoutIOC;

public class UserWithOutIOC {
  
  IDataAccess dal;
  
  public UserWithOutIOC(int dataAccessTypeId){
    
    if(dataAccessTypeId == 1){
      dal = new MySqlDataAccessLayer();
    } else if (dataAccessTypeId == 2) {
      dal = new PostgesSqlDataAccessLayer();
    }else if (dataAccessTypeId == 3) {
      dal = new OracleDataAccessLayer();
    } else {
      dal = new Db2DataAccessLayer();
    }
  }
  
  private boolean isValid() {
    return true;
  }

  public  void Add() {
    if (isValid()) {
      dal.Add(this);
    }
  }
}

/**
 * 1. The User constructor decides which data access layer to use based on the arguments passed to it at runtime.
 * 2. The decision of which data access implementation to use is controlled by the User constructor. 
 * 3. This is a bad design because the User class now has more than one reason to change. 
 * 4. Every class should have just one reason/requirement to change (SRP). This brings us to IoC
 * 
 */

