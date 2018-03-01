package com.joshluisaac.example.comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class Tests {

  //@Test
  public void testComparableInterface() {

    User user = new User("joshua", "joshua1234");
    User user2 = new User("candice", "password!");

    List<User> list = new ArrayList<User>();
    list.add(user2);
    list.add(user);

    Collections.sort(list);
    System.out.println(list);

    Assert.assertThat(list.get(0).getUsername(), CoreMatchers.is("joshua"));
    Assert.assertThat(list.get(1).getUsername(), CoreMatchers.is("candice"));

  }

  //@Test
  public void testComparatorInterface() {

    User user = new User("joshua", "joshua1234");
    User user2 = new User("candice", "password!");

    List<User> list = new ArrayList<User>();
    list.add(user2);
    list.add(user);

    Collections.sort(list, new Comparator<User>() {
      public int compare(User o1, User o2) {
        return o1.getUsername().compareToIgnoreCase(o2.getUsername());
      }
    });

    Assert.assertThat(list.get(0).getUsername(), CoreMatchers.is("joshua"));
    Assert.assertThat(list.get(1).getUsername(), CoreMatchers.is("candice"));

  }

}
