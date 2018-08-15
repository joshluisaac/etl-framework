package com.kollect.etl.util;

/**
 * Created by joshua on 10/1/17.
 */
public class Preconditions {

  /**
   * Checks if the given value is {@code null}.
   *
   * @throws NullPointerException if {@code value} is {@code null}
   * @return the {@code value} argument unchanged
   */
  public static <T> T checkNotNull(T value) {
    if (value == null) {
      throw new NullPointerException("The value of argument is null");
    }
    return value;
  }
  
  public static <T> void throwIllegalArgException(T value) {
    if (value == null) throw new IllegalArgumentException();
  }


}
