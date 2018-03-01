package com.kollect.etl.util;


import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListUtilsTest {

  private List<String> expectedUniqueListOfStrings;
  private List<Integer> expectedUniqueListInts, listOfInts1, listOfInts2, expected;
  private ListUtils listUtils;

  @Before
  public void run_once_per_test() {
    listUtils = new ListUtils();
    expectedUniqueListOfStrings = Arrays.asList("joshua", "zoe", "james");
    expectedUniqueListInts = Arrays.asList(0, 1, 7, 9, 10);
    listOfInts1 = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 10, 11, 900, 8, 7, 6);
    listOfInts2 = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 1, 10, 11, 900, 8, 7, 45);
    expected = Arrays.asList(45);
  }
  


  @Test
  public void test_unique_list_of_strings() {
    List<String> duplicate = Arrays.asList("joshua", "zoe", "james", "joshua");
    List<String> actual = listUtils.unique(duplicate);
    System.out.println(actual);
    Assert.assertThat(actual, CoreMatchers.is(expectedUniqueListOfStrings));
  }

  @Test
  public void test_unique_list_of_ints() {
    List<Integer> duplicate = Arrays.asList(0, 1, 1, 7, 9, 10, 1, 0, 9, 7, 9, 0);
    List<Integer> actual = listUtils.unique(duplicate);
    System.out.println(actual);
    Assert.assertThat(actual, CoreMatchers.is(expectedUniqueListInts));
  }

  @Test
  public void test_subtract_list_of_ints() {
    List<Integer> diff = listUtils.subtract(listOfInts1, listOfInts2);
    System.out.println(diff);
    Assert.assertThat(diff, CoreMatchers.is(expected));
  }
  
  @Test
  public void test_subtract_list_of_strings() {
    FakeInvoiceData invData = new FakeInvoiceData();
    List<String> prevInvList = invData.loadData().get("prevInvListKey");
    List<String> currInvList = invData.loadData().get("currInvListKey");
    List<String> diff = listUtils.subtract(prevInvList, currInvList);
    System.out.println(diff);
    Assert.assertThat(diff, CoreMatchers.is(invData.expectatedList()));
  }

}
