package com.kollect.etl.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class FileConcatenatorTest {
  
  private FileConcatenator concat;
  private Configuration config;
  private List<String> tmp, tmp2;
 
  @Before
  public void run_once_per_test() throws IOException {
    config = new Configuration();
    concat = new FileConcatenator(config);
    
    String dummy1 = "300-H012|||||||||CHEE|2017-09-20 17:19:57.847||186736-M|AED_YYC_8";
    String dummy2 = "300-H012|||||||||CHEE|2017-09-20 17:19:57.847||186736-M|AED_YYC_8";
    String dummy3 = "300-H012|||||||||CHEE|2017-09-20 17:19:57.847||186736-M|AED_YYC_8";
    String dummy4 = "300-H012|||||||||CHEE|2017-09-20 17:19:57.847||186736-M|AED_YYC_8";
    String dummy5 = "300-H012|||||||||CHEE|2017-09-20 17:19:57.847||186736-Mx|AED_YYC_8";
    String dummy6 ="300-Y002|YONG MECHANTRONICS SDN. BHD.|Mr Teo|No. 99A, Jalan NIP 2/5,|Taman Perindustrian Nusajaya 2,|79200 Nusajaya, Johor.|||017-799 0905||||teomc\"\"''\\/#&@yongmecha.com||2017-12-26 20:58:04.353|2018-01-12 01:10:12.39|1015343-U|AED_GCJB";
    tmp = new ArrayList<String>(Arrays.asList(dummy1, dummy2, dummy3, dummy4, dummy5));
    tmp2 = new ArrayList<String>(Arrays.asList(dummy1, dummy2, dummy3, dummy4, dummy5, dummy6));
  }
  
  
  @Test
  public void unique_test() {
    List<String> l = concat.uniqueOnKey(tmp,12);
    System.out.println(l.size());
    Assert.assertThat(l.size(), is(not(tmp.size())));
  }
  
  @Test
  public void test_replace_non_ascii_chars() {
    List<String> out  = concat.replaceNonAsciiCharacters(tmp2, config.getRegex("customer"), config.getReplacement("customer"));
    System.out.println(out.toString());
  }

}
