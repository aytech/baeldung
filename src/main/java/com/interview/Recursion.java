package com.interview;

public class Recursion {
  public static void main(String[] args) {
    divideNumber(274);
  }

  static void divideNumber(int n) {
    if (n >= 10) {
      divideNumber(n / 10);
    }
    System.out.print(n / 10);
  }
}
