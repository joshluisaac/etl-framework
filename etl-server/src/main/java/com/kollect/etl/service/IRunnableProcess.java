package com.kollect.etl.service;

import java.util.List;


public interface IRunnableProcess<T> {
  void process (final List<T> rows);
}
