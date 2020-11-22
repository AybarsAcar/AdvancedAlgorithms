package org.aybarsacar.advancedAlgorithms.suffixtree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuffixTreeTest
{
  SuffixTree t;

  @BeforeEach
  public void init()
  {
    t = new SuffixTree("banana".toCharArray());
  }

  @Test
  public void initializeWithAUniqueCharacterTest()
  {
    Assertions.assertArrayEquals("banana$".toCharArray(), t.getInput());
  }

  @Test
  public void buildSuffixTreeTest()
  {
    t.buildSuffixTree();
    t.dfsTraversal();
    System.out.println(t.root);
  }
}
