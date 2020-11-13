package org.aybarsacar.advancedAlgorithms.bruteforce;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BruteForceTest
{
  BruteForce bf;
  char[] array;

  @BeforeEach
  public void init()
  {
    bf = new BruteForce();
    String s = "aybarsacarisamazing";
    array = s.toCharArray();
  }

  @Test
  public void firstMatchTest()
  {
    Assertions.assertEquals(0, bf.firstMatch(array, new char[]{'a'}));
    Assertions.assertEquals(0, bf.firstMatch(array, new char[]{'a', 'y', 'b', 'a', 'r', 's'}));
    Assertions.assertEquals(4, bf.firstMatch(array, new char[]{'r'}));
    Assertions.assertEquals(-1, bf.firstMatch(array, new char[]{'q'}));
    Assertions.assertEquals(-1, bf.firstMatch(array, new char[]{'h', 'e', 'l', 'l', 'o'}));
    Assertions.assertEquals(-1, bf.firstMatch(array, new char[]{'a', 'c', 'a', 'r', 's'}));
  }

  @Test
  public void everyMatchTest()
  {
    int[] expectedArray = new int[array.length];
    Arrays.fill(expectedArray, -1);

    expectedArray[0] = 0;
    expectedArray[1] = 3;
    expectedArray[2] = 6;
    expectedArray[3] = 8;
    expectedArray[4] = 12;
    expectedArray[5] = 14;

    System.out.println(Arrays.toString(expectedArray));
    Assertions.assertArrayEquals(expectedArray, bf.everyMatch(array, new char[]{'a'}));

    Arrays.fill(expectedArray, -1);
    Assertions.assertArrayEquals(expectedArray, bf.everyMatch(array, new char[]{'q'}));

    Arrays.fill(expectedArray, -1);
    expectedArray[0] = 6;
    Assertions.assertArrayEquals(expectedArray, bf.everyMatch(array, new char[]{'a', 'c', 'a', 'r'}));
  }
}
