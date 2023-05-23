package com.guava;

import com.google.common.collect.*;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;


// Leverage Guava to work with Java Maps
// https://www.baeldung.com/guava-maps
public class Maps {

  java.sql.PreparedStatement statement;

  public static void main(String[] args) {
    // Create ImmutableMap using Guava
    guavaImmutableMap();
    // Using sorted map
    guavaSortedMap();
    // BiMap makes sure the values are unique, so you can map keys back to values.
    guavaBiMap();
    // MultiMap can be used to associate keys with multiple values
    guavaMultiMap();
    // Guava Table can be used to have more than one key to index a value
    guavaStoreDistances();
    // Guava row and column keys can be flipped
    guavaTransposeKeys();
  }
  private static void guavaImmutableMap() {
    Map<String, Integer> salary = ImmutableMap.<String, Integer>builder()
        .put("John", 1000)
        .put("Jane", 1500)
        .put("Adam", 2000)
        .put("Tom", 2000)
        .build();

    assertEquals(1000, Objects.requireNonNull(salary.get("John")).intValue());
    assertEquals(2000, Objects.requireNonNull(salary.get("Tom")).intValue());
  }

  private static void guavaSortedMap() {
    ImmutableSortedMap<String, Integer> salary = new ImmutableSortedMap
        .Builder<String, Integer>(Ordering.natural())
        .put("John", 1000)
        .put("Jane", 1500)
        .put("Adam", 2000)
        .put("Tom", 2000)
        .build();

    assertEquals("Adam", salary.firstKey());
    assertEquals(2000, Objects.requireNonNull(salary.lastEntry()).getValue().intValue());
  }

  public static void guavaBiMap() {
    BiMap<String, Integer> words = HashBiMap.create();
    words.put("First", 1);
    words.put("Second", 2);
    words.put("Third", 3);

    assertEquals(2, Objects.requireNonNull(words.get("Second")).intValue());
    assertEquals("Third", words.inverse().get(3));
  }

  private static void guavaMultiMap() {
    Multimap<String, String> multimap = ArrayListMultimap.create();
    multimap.put("fruit", "apple");
    multimap.put("fruit", "banana");
    multimap.put("pet", "cat");
    multimap.put("pet", "dog");

    Collection<String> fruit = multimap.get("fruit");
    Collection<String> pet = multimap.get("pet");
    assertThat(fruit, containsInAnyOrder("apple", "banana"));
    assertThat(pet, containsInAnyOrder("cat", "dog"));
  }

  private static void guavaStoreDistances() {
    Table<String, String, Integer> distance = HashBasedTable.create();
    distance.put("London", "Paris", 340);
    distance.put("New York", "Los Angeles", 3940);
    distance.put("London", "New York", 5576);

    assertEquals(3940, Objects.requireNonNull(distance.get("New York", "Los Angeles")).intValue());
    assertThat(distance.columnKeySet(),
        containsInAnyOrder("Paris", "New York", "Los Angeles"));
    assertThat(distance.rowKeySet(), containsInAnyOrder("London", "New York"));
  }

  private static void guavaTransposeKeys() {
    Table<String, String, Integer> distance = HashBasedTable.create();
    distance.put("London", "Paris", 340);
    distance.put("New York", "Los Angeles", 3940);
    distance.put("London", "New York", 5576);

    Table<String, String, Integer> transposed = Tables.transpose(distance);

    assertThat(transposed.rowKeySet(),
        containsInAnyOrder("Paris", "New York", "Los Angeles"));
    assertThat(transposed.columnKeySet(), containsInAnyOrder("London", "New York"));
  }
}
