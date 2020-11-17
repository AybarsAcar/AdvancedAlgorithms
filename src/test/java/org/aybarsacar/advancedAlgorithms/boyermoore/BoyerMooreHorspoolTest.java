package org.aybarsacar.advancedAlgorithms.boyermoore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoyerMooreHorspoolTest
{
  BoyerMooreHorspool b;

  @BeforeEach
  public void init()
  {
    b = new BoyerMooreHorspool();
  }

  @Test
  public void searchTest()
  {
    Assertions.assertEquals(4, b.search("learning".toCharArray(), "nin".toCharArray()));
    Assertions.assertEquals(0, b.search("learning".toCharArray(), "lea".toCharArray()));
    Assertions.assertEquals(0, b.search("learning".toCharArray(), "learning".toCharArray()));
    Assertions.assertEquals(-1, b.search("learning".toCharArray(), "hello".toCharArray()));
    Assertions.assertEquals(0, b.search("learning".toCharArray(), null));
    Assertions.assertEquals(-1, b.search(null, "nin".toCharArray()));
  }

  @Test
  public void preprocessTableTest()
  {
    int[] table = b.preprocessTable("test".toCharArray());
    Assertions.assertEquals(1, table['t']);
    Assertions.assertEquals(2, table['e']);
    Assertions.assertEquals(1, table['s']);
    Assertions.assertEquals(4, table['a']);

    table = b.preprocessTable("abc".toCharArray());
    Assertions.assertEquals(2, table['a']);
    Assertions.assertEquals(1, table['b']);
    Assertions.assertEquals(3, table['c']);
    Assertions.assertEquals(3, table['e']);

    table = b.preprocessTable("abcdb".toCharArray());
    Assertions.assertEquals(4, table['a']);
    Assertions.assertEquals(1, table['b']);
    Assertions.assertEquals(2, table['c']);
    Assertions.assertEquals(1, table['d']);
    Assertions.assertEquals(5, table['j']);
  }
}
