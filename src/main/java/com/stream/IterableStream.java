package com.stream;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IterableStream {
  public static void main(String[] args) {

    String[] listOfStrings = new String[]{"one", "two", "three"};

    Stream<String> stringIterableStream = Arrays.stream(listOfStrings);

    System.out.println("");
    // Iterate over stream by casting to Iterable
    for (String stringElement : (Iterable<String>) stringIterableStream::iterator) {
      System.out.println("Iterable string element: " + stringElement);
    }

    // Stream is a single use data structure, it closes itself after
    // being iterated upon, needs to be initialized again
    Stream<String> stringCollectionStream = Arrays.stream(listOfStrings);

    System.out.println("");
    // Iterate by converting stream to a Collection
    for (String collectionStringElement : stringCollectionStream.toList()) {
      System.out.println("Collection string element: " + collectionStringElement);
    }
  }
}
