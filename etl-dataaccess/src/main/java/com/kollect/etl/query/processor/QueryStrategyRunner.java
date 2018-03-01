package com.kollect.etl.query.processor;

import java.io.IOException;

public class QueryStrategyRunner {

  public static void main( String[] args ) throws IOException {

    String propertyFileName = args[0];
    String batchName = args[1];
    String queryType = args[2];

    QueryContext ctx = null;

    if (queryType.equals("select")) {
      ctx = new QueryContext(new SelectQueryStrategy(propertyFileName), batchName);
    }

    if (queryType.equals("delete")) {
      ctx = new QueryContext(new DeleteQueryStrategy(propertyFileName), batchName);
    }
    
    if (queryType.equals("update")) {
      ctx = new QueryContext(new UpdateQueryStrategy(propertyFileName), batchName);
    }
    
    if (queryType.equals("query")) {
      ctx = new QueryContext(new ListQueryStrategy(propertyFileName), batchName);
    }

    ctx.executeStrategyQuery();

  }

}
