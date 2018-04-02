package com.kollect.etl.service;

import java.util.List;


public interface IRunnableProcess<T> {
  public void process (final List<T> rows);
}
