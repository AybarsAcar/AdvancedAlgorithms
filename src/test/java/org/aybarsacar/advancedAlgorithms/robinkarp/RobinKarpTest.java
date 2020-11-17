package org.aybarsacar.advancedAlgorithms.robinkarp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RobinKarpTest
{
  RobinKarp rk;

  @BeforeEach
  public void init()
  {
    rk = new RobinKarp();
  }

  @Test
  public void searchTest()
  {
    Assertions.assertEquals(4, rk.search("learning".toCharArray(), "nin".toCharArray()));
    Assertions.assertEquals(4, rk.search("learning".toCharArray(), "ning".toCharArray()));
    Assertions.assertEquals(1, rk.search("learning".toCharArray(), "earni".toCharArray()));
    Assertions.assertEquals(-1, rk.search("learning".toCharArray(), "dog".toCharArray()));
    Assertions.assertEquals(0, rk.search("learning".toCharArray(), "l".toCharArray()));
    Assertions.assertEquals(0, rk.search("learning".toCharArray(), "learning".toCharArray()));
    Assertions.assertEquals(-1, rk.search("learning".toCharArray(), null));
    Assertions.assertEquals(-1, rk.search(null, "nin".toCharArray()));
    Assertions.assertEquals(-1, rk.search(null, null));
  }

  @Test
  public void matchTest()
  {
    Assertions.assertTrue(rk.match("learning".toCharArray(), "nin".toCharArray(), 4));
    Assertions.assertFalse(rk.match("learning".toCharArray(), "nin".toCharArray(), 2));
  }

  @Test
  public void calculateHashTest()
  {
//    calculate the hash for the first 3 values "acb"
    Assertions.assertEquals(28L, rk.calculateHash("acbacc".toCharArray(), 3));
  }

  @Test
  public void recalculateHashTest()
  {
    Assertions.assertEquals(18, rk.recalculateHash(28L, 'a', 'a', 3));
  }

  @Test
  public void charValueTest()
  {
    Assertions.assertEquals(97, 'a');
    Assertions.assertEquals(1, rk.charValue('a'));
  }
}
