package org.aybarsacar.advancedAlgorithms.suffixtrie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SuffixTrieMapTest
{
  SuffixTrieMap t;

  @BeforeEach
  public void init()
  {
    /*
    banana$
    a$na$na$
    na$na$
     */
//    this calls the insert method on the node
    t = new SuffixTrieMap("banana");
//    System.out.println(t.root);
  }

  @Test
  public void insertSuffixTest()
  {
    Assertions.assertNotNull(t.root.children.get('b'));
    Assertions.assertNotNull(t.root.children.get('a'));
    Assertions.assertNotNull(t.root.children.get('n'));
    Assertions.assertNull(t.root.children.get('c'));

    Assertions.assertNotNull(t.root.children.get('b').children.get('a'));
    Assertions.assertNull(t.root.children.get('b').children.get('n'));

    Assertions.assertNotNull(t.root.children.get('a').children.get('$'));
    Assertions.assertNotNull(t.root.children.get('a').children.get('n'));

    Assertions.assertNotNull(t.root
        .children.get('b')
        .children.get('a')
        .children.get('n')
        .children.get('a')
        .children.get('n')
        .children.get('a')
        .children.get('$'));
  }

  @Test
  public void searchTest()
  {
    List<Integer> r1 = t.search("ana");
    List<Integer> r2 = t.search("a");

    System.out.println(t.search("ana"));
    System.out.println(t.search("a"));
    System.out.println(t.search("anc"));

    Assertions.assertEquals(2, r1.size());
    Assertions.assertEquals(3, r1.get(0));
    Assertions.assertEquals(5, r1.get(1));

    Assertions.assertEquals(3, r2.size());

    Assertions.assertNull(t.search("anc"));
  }

  @Test
  public void isSuffixTest()
  {
    Assertions.assertTrue(t.isSuffix("ana"));
    Assertions.assertTrue(t.isSuffix("a"));
    Assertions.assertTrue(t.isSuffix("banana"));
    Assertions.assertFalse(t.isSuffix("an"));
    Assertions.assertFalse(t.isSuffix("ba"));
  }

  @Test
  public void isSubstring()
  {
    Assertions.assertTrue(t.isSubstring(""));
    Assertions.assertTrue(t.isSubstring("banana"));
    Assertions.assertTrue(t.isSubstring("an"));
    Assertions.assertFalse(t.isSubstring("ac"));
  }
}
