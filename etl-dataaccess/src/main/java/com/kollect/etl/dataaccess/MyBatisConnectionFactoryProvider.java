package com.kollect.etl.dataaccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MyBatisConnectionFactoryProvider {
  
  //Added a private constructor to hide the implicit public one.
  private MyBatisConnectionFactoryProvider() {}

  private static final Logger LOG = LoggerFactory.getLogger(MyBatisConnectionFactoryProvider.class);
  private static final Map<String, SqlSessionFactory> SQL_SESSION_FACTORIES = new HashMap<>();

  public static synchronized SqlSessionFactory getSqlSessionFactory(String envName) {
    SqlSessionFactory sqlSessionFactory = SQL_SESSION_FACTORIES.get(envName);
    if (sqlSessionFactory != null) {
      LOG.debug("SQL_SESSION_FACTORY: Obtained {} from cache", envName);
      return sqlSessionFactory;
    } else {
      String resource = "server.xml";
      try (Reader reader = Resources.getResourceAsReader(resource);) {
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, envName);
        LOG.info("SQL_SESSION_FACTORY: Created and cached {} SqlSessionFactory instance successfully",envName);
      } catch (FileNotFoundException fnf) {
        LOG.error(fnf.getMessage(), fnf);
      } catch (IOException ioe) {
        LOG.error(ioe.getMessage(), ioe);
      }
    }
    SQL_SESSION_FACTORIES.put(envName, sqlSessionFactory);
    return sqlSessionFactory;
  }
}
