package org.aybarsacar.advancedAlgorithms.kmp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class KnuthMorrisPrattTest
{
  KnuthMorrisPratt kmp;

  @BeforeEach
  public void init()
  {
    kmp = new KnuthMorrisPratt();
  }

  @Test
  public void searchTest()
  {
    String s = "abazacabababac";
    String p = "ababac";

    char[] array = s.toCharArray();
    System.out.println(Arrays.toString(array));
    char[] pattern = p.toCharArray();
    System.out.println(Arrays.toString(pattern));
    int index = kmp.search(array, pattern);

    Assertions.assertEquals(8, index);
  }

  @Test
  public void computeLSPTableTest()
  {
    int[] lspTable = kmp.computeLSPTable(new char[]{'a'});
    System.out.println(Arrays.toString(lspTable));
    int[] expected = new int[]{0};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'a', 'a'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 1};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'a', 'b', 'a', 'b', 'a', 'c'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 0, 1, 2, 3, 0};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'a', 'a', 'b', 'a', 'c'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 1, 0, 1, 0};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'a', 'a', 'a', 'b', 'b'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 1, 2, 0, 0};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'a', 'b', 'c', 'd', 'a', 'b', 'c', 'a'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 0, 0, 0, 1, 2, 3, 1};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'c', 'f', 'g', 'c', 'f', 'a'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 0, 0, 1, 2, 0};
    Assertions.assertArrayEquals(expected, lspTable);

    lspTable = kmp.computeLSPTable(new char[]{'c', 'f', 'g', 'c', 'f', 'c'});
    System.out.println(Arrays.toString(lspTable));
    expected = new int[]{0, 0, 0, 1, 2, 1};
    Assertions.assertArrayEquals(expected, lspTable);
  }
}
