package com.kollect.etl.etl_junit_tests;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by joshua on 10/2/17.
 */
public class Container {

  private Set<Integer> s1;
  private Set<Integer> s2;

  @Before
  public void run_once_per_method() {
    s1 = new HashSet<Integer>(Arrays.asList(1, 2, 3));
    s2 = new HashSet<Integer>(Arrays.asList(1, 2, 3, 10, 22, 88));
  }

  public <E> Set<E> union(Set<E> set1, Set<E> set2) {
    Set<E> result = new HashSet<E>(set1);
    result.addAll(set2);
    System.out.println(result.toString());
    return result;
  }


  @Test
  public void test_set_union() {
    union(s1, s2);
  }

}
