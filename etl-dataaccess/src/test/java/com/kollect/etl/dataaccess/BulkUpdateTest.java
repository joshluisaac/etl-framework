package com.kollect.etl.dataaccess;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;



public class BulkUpdateTest {

  private static final Logger LOG = LoggerFactory.getLogger(BulkUpdateTest.class);

  private IAbstractSqlSessionProvider daoProvider;
  private Service service;

  @Before
  public void run_one_per_method() {
    daoProvider = new AbstractSqlSessionProvider("mahb_prod");
    service = new Service(daoProvider);
  }

  //@Test(expected = PersistenceException.class)
  public void testUpdateThrowsPersistenceException() {
    service.update();
  }

  //@Test(expected = PersistenceException.class)
  public void testExecuteQuery() {
    service.queryMultipleObjects();
  }


  @Test
  public void testInvoiceCountMustBeEqualToExpectedResult() {
    Long expectedCount = 300000L;
    Map<String, Long> m= (Map) service.getInvoiceCount().get(0);

    Long actualResult = m.get("count");
    Assert.assertEquals(expectedCount, actualResult /*CoreMatchers.is(actualResult)*/);

  }

  @Test
  public void shouldNotHaveDuplicateInvoices() {
    Long expectedCount = 0L;
    Long actualResult = 0L;
    if (service.getInvoiceDuplicateCount().size() > 0) {
      Map<String, Long> m = (Map) service.getInvoiceDuplicateCount();
      actualResult = m.get("count");
    }


   Assert.assertEquals(expectedCount, actualResult);


  }
}
