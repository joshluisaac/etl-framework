package com.kollect.etl.dataaccess;

import org.springframework.stereotype.Repository;

@Repository
public class DaoFactory {
  
  public IDaoProvider getDaoSource(String dataSource) {
    
    if(dataSource == null){
      return null;
   }
    
    if(dataSource.equalsIgnoreCase(dataSource)) {
      return new DaoProviderMahb(new AbstractSqlSessionProvider(dataSource));
    }
    

    return null;
  }

}
