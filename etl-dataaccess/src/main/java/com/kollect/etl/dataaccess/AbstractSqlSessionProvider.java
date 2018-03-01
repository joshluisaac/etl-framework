package com.kollect.etl.dataaccess;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSqlSessionProvider implements IAbstractSqlSessionProvider {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractSqlSessionProvider.class);

  private SqlSessionFactory sqlSessionFactory;

  public AbstractSqlSessionProvider(String envContext) {
    sqlSessionFactory = MyBatisConnectionFactoryProvider.getSqlSessionFactory(envContext);
  }

  /**
   * Returns a list of map object
   * <p/>
   * 
   * The parameter object is generally used to supply the input data for the WHERE
   * clause parameter(s) of the SELECT statement.
   * 
   * @param queryName
   *          The name of the query want to execute.
   * @param object
   *          The SQL map client from iBatis ORM
   * @return List<Object>
   * @throws java.sql.SQLException
   *           If an error occurs.
   */

  public <T> List<T> queryObject(final String queryName, final Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      List<T> list = session.selectList(queryName, object);
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return list;
    } finally {
      session.close();
    }
  }

  @Override
  public Iterator<Object> queryMultipleObjects(String queryName, Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      Iterator<Object> iterator = null;
      iterator = session.selectList(queryName, object).iterator();
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return iterator;

    } finally {
      session.close();
    }
  }
  
  
  @Override
  public Iterator<Object> query (String queryName, Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      Iterator<Object> iterator = null;
      iterator = session.selectList(queryName, object).iterator();
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return iterator;

    } finally {
      session.close();
    }
  }

  /**
   * Insert an instance of the object into the database.
   *
   * @param queryName
   *          The name of the query want to execute.
   * @param object
   *          The parameter which is passed to the insert statement
   * @return int
   *          The number of records inserted
   */
  @Override
  public int insert(final String queryName, final Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      int rowsAffected = session.insert(queryName, object);
      session.commit();
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return rowsAffected;
    } finally {
      session.close();
    }
  }

  /**
   * Update an instance of the object into the database.
   *
   * @param queryName
   *          The name of the query want to execute.
   * @param object
   *          The parameter which is passed to the update statement
   * @return int
   *          The number of records affected by the update
   * 
   */
  @Override
  public int update(final String queryName, final Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      int rowsAffected = session.update(queryName, object);
      session.commit();
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return rowsAffected;
    } finally {
      session.close();
    }
  }
  
  /**
   * Deletes an instance of the object into the database.
   *
   * @param queryName
   *          The name of the query want to execute.
   * @param object
   *          The parameter which is passed to the delete statement
   * @return int
   *         The number of records affected by the delete
   */

  @Override
  public int delete(final String queryName, final Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      int rowsAffected = session.delete(queryName, object);
      session.commit();
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return rowsAffected;
    } finally {
      session.close();
    }
  }

  @Override
  public Object querySingleObject(String queryName, Object object) {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      long queryStart = System.currentTimeMillis();
      Object obj = session.selectOne(queryName, object);
      long queryEnd = System.currentTimeMillis();
      logQueryStatistics("NONE", queryName, queryStart, queryEnd);
      return obj;
    } finally {
      session.close();
    }
  }

  private void logQueryStatistics(String strategy, String queryName, long queryStart, long queryEnd) {
    Logger log = getLog();
    log.info("Query {} {} ms using {} ({})",
        new Object[] { queryName, (queryEnd - queryStart), strategy, Thread.currentThread().getName() });
  }

  private Logger getLog() {
    return LOG;
  }

}
