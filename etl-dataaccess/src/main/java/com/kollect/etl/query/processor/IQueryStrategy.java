package com.kollect.etl.query.processor;

import java.io.IOException;
import java.util.Properties;

import com.kollect.etl.dataaccess.IAbstractSqlSessionProvider;

public interface IQueryStrategy {
  
  public Properties loadDbProperties( String fileName ) throws IOException;
  public Properties loadQueryProperties( String fileName ) throws IOException;
  public void execute( IAbstractSqlSessionProvider iSqlSessionProvider, String queryName, Object object );
  public Object getObjectParams();
  public String getDbSources();
  public String[] getDbList( String dbSources );
  public String getQueryNames(String batchName );
  public String[] getQueryNameList( String queryList );

}
