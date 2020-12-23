package org.aybarsacar.advancedAlgorithms.datastructures.balancedtree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AVLTreeRecursiveTest
{
  AVLTreeRecursive<Integer> avlTree;

  @BeforeEach
  public void init()
  {
    avlTree = new AVLTreeRecursive<>();

    avlTree.insert(6);
    avlTree.insert(8);
    avlTree.insert(10);
    avlTree.insert(12);
    avlTree.insert(90);
    avlTree.insert(109);
    avlTree.insert(300);
  }

  @Test
  public void visualiseTree()
  {
//    The tree is balanced even though the values are added in a sequential manner
    System.out.println(avlTree.toString());
  }

  @Test
  public void cannotAddNullOrDuplicateValues()
  {
    Assertions.assertFalse(avlTree.insert(null));
    Assertions.assertFalse(avlTree.insert(10));
  }

  @Test
  public void cannotRemoveNonExistentOrNullValues()
  {
    Assertions.assertFalse(avlTree.remove(124));
    Assertions.assertFalse(avlTree.remove(null));
  }

  @Test
  public void maintainsBSTInvariant()
  {
    Assertions.assertTrue(avlTree.validateBSTInvarient(avlTree.root));
  }

  @Test
  public void isEmptyTest()
  {
    Assertions.assertFalse(avlTree.isEmpty());
  }

  @Test
  void heightTest()
  {
    Assertions.assertEquals(2, avlTree.height());
  }

  @Test
  void sizeTest()
  {
    Assertions.assertEquals(7, avlTree.size());
  }

  @Test
  public void testMisc()
  {
    System.out.println(avlTree.height());
    System.out.println(avlTree.size());
    System.out.println(avlTree.root.value);
    System.out.println(avlTree.root.bf);
  }
}
