package org.aybarsacar.advancedAlgorithms.euclid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EuclidTest
{
  Euclid e;

  @BeforeEach
  public void init()
  {
    e = new Euclid();
  }

  @Test
  public void gcdTest()
  {
    Assertions.assertEquals(2, e.gcd(10,2));
    Assertions.assertEquals(2, e.gcd(22,6));

    System.out.println(e.gcd(78, 35));
  }

  @Test
  public void gcdIterativeTest()
  {
    Assertions.assertEquals(2, e.gcdIterative(10,2));
    Assertions.assertEquals(2, e.gcdIterative(22,6));

    System.out.println(e.gcdIterative(78, 35));
  }
}
