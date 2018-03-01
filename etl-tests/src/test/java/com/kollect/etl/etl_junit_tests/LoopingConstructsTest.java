package com.kollect.etl.etl_junit_tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class LoopingConstructsTest {
  
  private List<Integer> customerIds;
  
  @Before
  public void loadCustomerId(){
    customerIds = new ArrayList<Integer>();
    for(int i=0; i<200; i++) {
      customerIds.add(new Random().nextInt(1000));
      //customerIds.add(e)
    }
  }
  
  @Test
  public void test_iteration(){
    
  }

}
