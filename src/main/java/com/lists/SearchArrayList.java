package com.lists;

import com.lists.utility.IgnoreCaseStringList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SearchArrayList {
  static List<String> languages = Arrays.asList("Java", "Python", "Kotlin", "Ruby", "Javascript", "Go");

  public static void main(String[] args) {

    // Element cannot be found if provided in not matching case
    String invalidSearchString = "jAvA";
    System.out.println("Match: " + languages.contains("jAvA"));
    assertFalse(languages.contains("jAvA"));

    // Solutions:
    // 1. Using Stream API:
    // https://www.baeldung.com/java-8-streams
    invalidSearchString = "koTliN";
    System.out.println("Match with Stream API: " + languages.stream().anyMatch(invalidSearchString::equalsIgnoreCase));
    assertTrue(languages.stream().anyMatch(invalidSearchString::equalsIgnoreCase));

    // 2. Classic approach - utility method
    invalidSearchString = "ruBY";
    System.out.println("Match with utility method: " + ignoreCaseContains(invalidSearchString));
    assertTrue(ignoreCaseContains(invalidSearchString));

    // 3. Subclassing ArrayList<String>
    invalidSearchString = "pYtHoN";
    HashSet<String> ignoreCaseList = new IgnoreCaseStringList(languages);
    System.out.println("Match with subclassed ArrayList: " + ignoreCaseList.contains(invalidSearchString));
    assertTrue(ignoreCaseList.contains(invalidSearchString));
    // The benefit of the above approach is that it makes containsAll case-insensitive too,
    // as it is calling contains() internally
    List<String> invalidArray = Arrays.asList("pYtHon", "jAvA", "koTliN", "ruBY");
    System.out.println("Contains all result default: " + new HashSet<>(languages).containsAll(invalidArray));
    System.out.println("Contains all result custom: " + ignoreCaseList.containsAll(invalidArray));
    // Default containsAll will fail if the items are not matching case
    assertFalse(new HashSet<>(languages).containsAll(invalidArray));
    assertTrue(ignoreCaseList.containsAll(invalidArray));
  }

  private static boolean ignoreCaseContains(String searchString) {
    for (String item : languages) {
      if (item.equalsIgnoreCase(searchString)) {
        return true;
      }
    }
    return false;
  }
}
