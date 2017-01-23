package yyl.example.basic.jdk7;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdk7Features {



  public static void main(String[] args) throws Exception {
    testSwitch();
    testNumber();
    testGeneric();
    testClose();
  }

  public static void testSwitch() {
    String s = "1";

    switch (s) {
      case "0":
        System.out.println("+0");
        break;
      case "1":
        System.out.println("+1");
        break;
      case "2":
        System.out.println("+2");
        break;
      default:
        System.out.println("default");
    }
  }

  public static void testNumber() {
    int one_million = 1_000____000;

    System.out.println(one_million);

    int binary = 0b101;
    System.out.println(binary);
  }

  public static void testGeneric() {
    Map<String, List<String>> anagrams = new HashMap<>();
    System.out.println(anagrams);
  }

  public static void testClose() throws Exception {
    try (AutoCloseable m = new AutoCloseable() {
      @Override
      public void close() throws Exception {
        System.out.println("close");
      }

    }) {

    }

    try (AutoCloseable m = null) {

    }
  }
}
