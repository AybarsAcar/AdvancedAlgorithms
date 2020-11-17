package org.aybarsacar.advancedAlgorithms.zalgorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ZAlgorithmTest
{
  ZAlgorithm z;

  @BeforeEach
  public void init()
  {
    z = new ZAlgorithm();
  }

  @Test
  public void searchTest()
  {
    Assertions.assertEquals(3, z.search("low".toCharArray(), "helloworld".toCharArray()));
    Assertions.assertEquals(0, z.search("".toCharArray(), "helloworld".toCharArray()));
    Assertions.assertEquals(0, z.search(null, "helloworld".toCharArray()));
    Assertions.assertEquals(-1, z.search("hello".toCharArray(), null));
    Assertions.assertEquals(-1, z.search(null, null));
    Assertions.assertEquals(0, z.search("helloworld".toCharArray(), "helloworld".toCharArray()));
    Assertions.assertEquals(-1, z.search("helloworld".toCharArray(), "hello".toCharArray()));
    Assertions.assertEquals(-1, z.search("haha".toCharArray(), "helloworld".toCharArray()));
  }

  @Test
  public void searchAllTest()
  {
    Assertions.assertArrayEquals(new int[]{0, 2, 0, 0, 2, 0, 0, 2, 0, 0,}, z.searchAll("ab".toCharArray(),
        "aabaabbaba".toCharArray()));

    Assertions.assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0}, z.searchAll("hello".toCharArray(),
        "ahnsosle".toCharArray()));
  }

  @Test
  public void createLongStringTest()
  {
    Assertions.assertArrayEquals("hello$world".toCharArray(), z.createLongString("hello".toCharArray(),
        "world".toCharArray()));
  }

  @Test
  public void createZTableTest()
  {
//    ab$aabb
//    0001200
    Assertions.assertArrayEquals(new int[]{0, 0, 0, 1, 2, 0, 0}, z.createZTable("ab".toCharArray(),
        "aabb".toCharArray()));
  }
}
