package com.kollect.etl.dataextractor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
  
  private static final Properties appProp = EtlExtractor.propertyMap.get("SERVER_PROPERTY");
  
  //private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  //private static final String DB_CONNECTION = "jdbc:sqlserver://192.168.1.27:1433;databaseName=aed_elegant";
  //this works WIN-G4RAMGRHE08 is same as 192.168.1.27, therefore 192.168.1.27:1433 is same as WIN-G4RAMGRHE08:1433
  //private static final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.27:1433/aed_elegant;useNTLMv2=true;domain=no";
  
//  private static final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
//  private static final String URL = "jdbc:jtds:sqlserver://192.168.1.27";
//  private static final String PORT = "1433";
//  //private static final String DB_CONNECTION = "jdbc:jtds:sqlserver://192.168.1.27:1433/aed_elegant;useNTLMv2=true;domain=no";
//  private static final String ADDITIONAL_ARGS = "useNTLMv2=true;domain=no";
//  private static final String DB_USER = "Administrator";
//  private static final String DB_PASSWORD = "(12kollect34)";
  
  
  private static final String DB_DRIVER = appProp.getProperty("db.driver");
  private static final String URL = appProp.getProperty("db.url");
  private static final String PORT = appProp.getProperty("db.port");
  private static final String ADDITIONAL_ARGS = appProp.getProperty("db.additional_args");
  private static final String DB_USER = appProp.getProperty("db.username");
  private static final String DB_PASSWORD = appProp.getProperty("db.password");
  
  
  public Connection getDbConnection(final String DB_NAME) {
    Connection dbConnection = null;
    
    try {
      Class.forName(DB_DRIVER);
    } catch (ClassNotFoundException e1) {
      throw new ClassCastException(e1.getMessage() + ": class cannot be found");
    }
    
    try {
      dbConnection = DriverManager.getConnection(buildDbConnection(DB_NAME), DB_USER, DB_PASSWORD);
      return dbConnection;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return dbConnection;
  }
  
  
  public static boolean isValidDriver(){
    return true;
  }
  
  
  private String buildDbConnection(final String DB_NAME){
    if (ADDITIONAL_ARGS != null) {
      return URL +":"+ PORT +"/"+ DB_NAME +";"+ ADDITIONAL_ARGS;
    }
    return URL +":"+ PORT +"/"+ DB_NAME;
  }
  

}
