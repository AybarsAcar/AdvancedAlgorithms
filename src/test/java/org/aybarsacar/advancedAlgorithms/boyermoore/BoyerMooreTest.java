package org.aybarsacar.advancedAlgorithms.boyermoore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoyerMooreTest
{
  BoyerMoore bm;

  @BeforeEach
  public void init()
  {
    bm = new BoyerMoore();
  }

  @Test
  public void searchTest()
  {
    Assertions.assertEquals(0, bm.search("".toCharArray(), "".toCharArray()));
    Assertions.assertEquals(0, bm.search("aldglsw".toCharArray(), null));
    Assertions.assertEquals(-1, bm.search(null, null));
    Assertions.assertEquals(-1, bm.search("aldglsw".toCharArray(), "nala".toCharArray()));
    Assertions.assertEquals(14, bm.search("thisisthefirsttest".toCharArray(), "test".toCharArray()));
    Assertions.assertEquals(0, bm.search("aldglsw".toCharArray(), "a".toCharArray()));
    Assertions.assertEquals(1, bm.search("abdglsw".toCharArray(), "b".toCharArray()));
    Assertions.assertEquals(6, bm.search("aldglsw".toCharArray(), "w".toCharArray()));
    Assertions.assertEquals(0, bm.search("hello world".toCharArray(), "hello world".toCharArray()));
    Assertions.assertEquals(-1, bm.search("hello".toCharArray(), "hello world".toCharArray()));
  }

  @Test
  public void computePrefixTest()
  {
    int[] table = new int[6];

    bm.computePrefix("baidai".toCharArray(), table);
    Assertions.assertArrayEquals(new int[]{6, 7, 8, 9, 10, 11}, table);

    bm.computePrefix("baibai".toCharArray(), table);
    Assertions.assertArrayEquals(new int[]{6, 7, 8, 6, 7, 8}, table);
  }

  @Test
  public void computeSuffixTest()
  {
    int[] table = new int[6];

    bm.computeSuffix("baidai".toCharArray(), table);
    Assertions.assertArrayEquals(new int[]{1, 0, 5, 0, 0, 0}, table);
  }

  @Test
  public void preprocessSuffixTableTest()
  {
    int[] table = bm.preprocessSuffixTable("baidai".toCharArray());
    Assertions.assertArrayEquals(new int[]{1, 7, 5, 9, 10, 11}, table);
  }

  @Test
  public void isPrefixTest()
  {
    Assertions.assertTrue(bm.isPrefix("baibai".toCharArray(), 6));
    Assertions.assertTrue(bm.isPrefix("baibai".toCharArray(), 3));
    Assertions.assertTrue(bm.isPrefix("baibai".toCharArray(), 0));
    Assertions.assertFalse(bm.isPrefix("baibai".toCharArray(), 2));
    Assertions.assertFalse(bm.isPrefix("baibai".toCharArray(), 1));

    Assertions.assertTrue(bm.isPrefix("baidai".toCharArray(), 6));
    Assertions.assertFalse(bm.isPrefix("baidai".toCharArray(), 3));
  }

  @Test
  public void suffixLengthTest()
  {
    Assertions.assertEquals(2, bm.suffixLength("baidai".toCharArray(), 2));
    Assertions.assertEquals(3, bm.suffixLength("baibai".toCharArray(), 2));
    Assertions.assertEquals(6, bm.suffixLength("mytestmytest".toCharArray(), 5));
    Assertions.assertEquals(4, bm.suffixLength("mytestyourtest".toCharArray(), 5));
  }
}
