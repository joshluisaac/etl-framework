package com.kollect.etl.dataaccess;

public class DaoProviderMahb extends AbstractDaoProvider {

  public DaoProviderMahb(IAbstractSqlSessionProvider sqlSessionProvider) {
    super(sqlSessionProvider);
  }
  
  
  public static void main() {
    new DaoProviderMahb(new AbstractSqlSessionProvider("")).insertQuery("", null);
  }

}
