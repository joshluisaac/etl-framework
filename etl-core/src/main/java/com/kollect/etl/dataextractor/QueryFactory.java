package com.kollect.etl.dataextractor;

public class QueryFactory {

  public IQuery createQuery(String query) {
    if (query.equalsIgnoreCase("payment")) {
      return new PaymentQuery();
    }
    if (query.equalsIgnoreCase("invoice")) {
      return new PaymentQuery();
    }
    if (query.equalsIgnoreCase("customer")) {
      return new PaymentQuery();
    }
    if (query.equalsIgnoreCase("creditNote")) {
      return new PaymentQuery();
    } else {
      return null;
    }
  }

}
