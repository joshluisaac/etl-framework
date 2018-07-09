package com.kollect.etl.dataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DaoFactory implements IDaoFactory {

  private static final Logger LOG = LoggerFactory.getLogger(DaoFactory.class);

  
  @Override
  public IDaoProvider getDaoSource(String dataSource) {
    if (dataSource == null) {
      LOG.error("Datasource points to null, please check dataSource");
      throw new IllegalArgumentException("Datasource points to null, please check dataSource");
    } else if (dataSource.equalsIgnoreCase(dataSource)) {
      return new DaoProvider(new AbstractSqlSessionProvider(dataSource));
    }
    return null;
  }

}
