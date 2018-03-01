package com.kollect.etl.etl_junit_tests;

import org.hamcrest.CoreMatchers;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class TestingLists {
  
  private List<String> actual;
  private List<String> expected;
  
  
  @Before
  public void run_once_for_every_method_tested(){
   actual = Arrays.asList("1","2","3","4","5","6");
   expected = Arrays.asList("1","2","3","4","5","6");
  }
  
  @Test
  public void test_list_of_strings_equality_using_assert_equals(){
    Assert.assertEquals(expected, actual);
    Assert.assertNotSame(expected, actual);
  }
  
  @Test
  public void test_list_of_strings_equality_using_assert_that(){
    Assert.assertThat(actual, CoreMatchers.is(expected));
  }

}
