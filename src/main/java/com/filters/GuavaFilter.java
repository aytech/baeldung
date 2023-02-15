package com.filters;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
  Filtering and transforming collections using Guava
  https://www.baeldung.com/guava-filter-and-transform-a-collection
 */
public class GuavaFilter {

  public static void main(String[] args) {
    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
    // Simple filtering a collection by a letter "a"
    filterCollection(names);
    // Do the same with Collections2.filter() API
    // Note: manipulating array using Collections2
    // will modify the original array, not the copy
    filterCollectionWithFilter(names);
    // If a new array element that doesn't satisfy
    // the Predicate is added, IllegalArgumentException
    // will be thrown
    filterCollectionWithIllegalValue(names);
    // Filter with custom predicate
    filterWithCustomPredicate(names);
    // Combine multiple predicates
    filterWithMultiplePredicates(names);
    // Remove null values when filtering a collection
    filterWithNullValues(names);
  }

  private static void filterCollection(List<String> listToFilter) {
    //noinspection StaticPseudoFunctionalStyleMethod
    Iterable<String> result = Iterables.filter(listToFilter, Predicates.containsPattern("a"));
    assertThat(result, containsInAnyOrder("Jane", "Adam"));
  }

  private static void filterCollectionWithFilter(List<String> listToFilter) {
    Collection<String> result = Collections2.filter(listToFilter, Predicates.containsPattern("a"));
    assertEquals(2, result.size());
    assertThat(result, containsInAnyOrder("Jane", "Adam"));
    result.add("anna");
    assertEquals(5, listToFilter.size());
    result.remove("anna");
  }

  private static void filterCollectionWithIllegalValue(List<String> listToFilter) {
    Collection<String> result = Collections2.filter(listToFilter, Predicates.containsPattern("a"));
    try {
      result.add("elvis");
    } catch (IllegalArgumentException ex) {
      System.out.print("Guava Error: Illegal argument exception thrown");
    }
  }

  private static void filterWithCustomPredicate(List<String> listToFilter) {
    Predicate<String> predicate = input -> input.startsWith("A") || input.startsWith("J");
    Collection<String> result = Collections2.filter(listToFilter, predicate::test);
    assertEquals(3, result.size());
    assertThat(result, containsInAnyOrder("John", "Jane", "Adam"));
  }

  private static void filterWithMultiplePredicates(List<String> listToFilter) {
    Collection<String> result = Collections2.filter(listToFilter,
        Predicates.or(Predicates.containsPattern("J"), Predicates.not(Predicates.containsPattern("a"))));
    assertEquals(3, result.size());
    assertThat(result, containsInAnyOrder("John", "Jane", "Tom"));
    // Doing the same with Java API
    Predicate<String> containsJ = input -> input.contains("J");
    Predicate<String> containsA = input -> input.contains("a");
    List<String> resultNative = listToFilter.stream().filter(containsJ.or(containsA.negate())).toList();
    assertEquals(3, resultNative.size());
    assertThat(resultNative, containsInAnyOrder("John", "Jane", "Tom"));
  }

  private static void filterWithNullValues(List<String> listToFilter) {
    listToFilter.add(1, null);
    listToFilter.add(3, null);
    Collection<String> result = Collections2.filter(listToFilter, Objects::nonNull);
    assertEquals(4, result.size());
    assertThat(result, containsInAnyOrder("John", "Jane", "Adam", "Tom"));
  }
}
