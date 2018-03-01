package com.kollect.etl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility providing list helper methods
 *
 * @author Josh Uzo Nwankwo
 * @version 1.3
 */

public class ListUtils {

  /**
   * Get contents of list2 which aren't in list1, by removing list1 items from
   * list2
   *
   * @param list1 list of first items
   * @param list2 list of second items
   * @return the difference between list2 and list1 category, which is the
   * contents of list2 which aren't in list1
   */
  public <T> List<T> subtract(final List<T> list1, final List<T> list2) {
    List<T> interimList = new ArrayList<>(list2);
    interimList.removeAll(list1);
    return interimList;
  }

  /**
   * Takes a list of strings and removes duplicate elements in that list
   *
   * @param list list of items
   * @return unique list of the specified type
   */
  public <T> List<T> unique(List<T> list) {
    List<T> tmp = new ArrayList<>();
    for (T x : list) {
      if (!tmp.contains(x)) {
        tmp.add(x);
      }
    }
    return tmp;
  }

}
