package org.aybarsacar.advancedAlgorithms.prefixtree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrefixTreeTest
{
  @Test
  public void insertTest()
  {
    PrefixTree tree = new PrefixTree();
    tree.insert("cat".toCharArray(), 1);
    tree.insert("car".toCharArray(), 2);
    tree.insert("door".toCharArray(), 3);
    tree.insert("cats".toCharArray(), 4);

//    System.out.println(tree.root);
    Assertions.assertTrue(tree.root.getChildren('c').getChildren('a').getChildren('t').isWord);
    Assertions.assertFalse(tree.root.getChildren('c').getChildren('a').isWord);
    Assertions.assertEquals(0, tree.root.getChildren('c').id);
    Assertions.assertEquals(1, tree.root.getChildren('c').getChildren('a').getChildren('t').id);
  }

  @Test
  public void findTest()
  {
    PrefixTree tree = new PrefixTree();
    tree.insert("cat".toCharArray(), 1);
    tree.insert("car".toCharArray(), 2);
    tree.insert("door".toCharArray(), 3);
    tree.insert("cats".toCharArray(), 4);

    Assertions.assertEquals(1, tree.find("cat".toCharArray()).id);
    Assertions.assertTrue(tree.find("cat".toCharArray()).isWord);

    Assertions.assertNull(tree.find("this does not exist in the tree".toCharArray()));
  }

  @Test
  public void deleteTest()
  {
    PrefixTree tree = new PrefixTree();
    tree.insert("cat".toCharArray(), 1);
    tree.insert("car".toCharArray(), 2);
    tree.insert("door".toCharArray(), 3);
    tree.insert("cats".toCharArray(), 4);
    tree.insert("done".toCharArray(), 5);
    tree.insert("horse".toCharArray(), 6);

    System.out.println("Before Delete: " + tree.root);

//    tree.delete("horse".toCharArray());
    tree.delete("cat".toCharArray());
    tree.delete("cats".toCharArray());

    System.out.println("After Delete: " + tree.root);
  }
}
