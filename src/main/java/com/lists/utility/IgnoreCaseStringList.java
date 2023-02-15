package com.lists.utility;

import java.util.Collection;
import java.util.HashSet;

public class IgnoreCaseStringList extends HashSet<String> {
  public IgnoreCaseStringList(Collection<? extends String> c) {
    super(c);
  }

  @Override
  public boolean contains(Object o) {
    String searchString = (String) o;
    for (String s : this) {
      if (searchString.equalsIgnoreCase(s)) {
        return true;
      }
    }
    return false;
  }
}
