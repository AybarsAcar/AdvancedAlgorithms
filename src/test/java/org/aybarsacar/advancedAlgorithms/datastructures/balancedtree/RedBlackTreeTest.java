package org.aybarsacar.advancedAlgorithms.datastructures.balancedtree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RedBlackTreeTest
{
  RedBlackTree<Integer> rb;

  @BeforeEach
  public void init()
  {
    rb = new RedBlackTree<>();

    rb.insert(100);
    rb.insert(340);
    rb.insert(103);
    rb.insert(39);
    rb.insert(20);
    rb.insert(9);
  }

  @Test
  public void visualiseTree()
  {
    System.out.println(rb.toString());
  }
}
