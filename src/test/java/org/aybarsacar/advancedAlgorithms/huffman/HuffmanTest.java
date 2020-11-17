package org.aybarsacar.advancedAlgorithms.huffman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

public class HuffmanTest
{
  Huffman h;

  @BeforeEach
  public void init()
  {
    h = new Huffman();
  }

  @Test
  public void createFrequencyTableTest()
  {
    // a -> 2, b -> 3, c -> 1, d -> 1
    int[] frequencies = h.createFrequencyTable("aabbbcd".toCharArray());

    Assertions.assertEquals(h.CHARACTER_LIMIT, frequencies.length);
    Assertions.assertEquals(2, frequencies['a']);
    Assertions.assertEquals(3, frequencies['b']);
  }

  @Test
  public void createPriorityQueueTest()
  {
    int[] frequencies = h.createFrequencyTable("aabbbcd".toCharArray());
    PriorityQueue<HuffmanNode> queue = h.createPriorityQueue(frequencies);

//    c=1, d=1, a=2, b=3
    Assertions.assertEquals('c', queue.peek().c);
    Assertions.assertEquals(1, queue.poll().frequency);

//    d=1, a=2, b=3
    Assertions.assertEquals('d', queue.peek().c);
    Assertions.assertEquals(1, queue.poll().frequency);

//    a=2, b=3
    Assertions.assertEquals('a', queue.peek().c);
    Assertions.assertEquals(2, queue.poll().frequency);

//    b=3 left in the queue
    Assertions.assertEquals('b', queue.peek().c);
    Assertions.assertEquals(3, queue.poll().frequency);

//    queue is empty
    Assertions.assertEquals(0, queue.size());
  }

  @Test
  public void pullLeastUsedAsNodeTest()
  {
    int[] frequencies = h.createFrequencyTable("aabbbcd".toCharArray());
    PriorityQueue<HuffmanNode> queue = h.createPriorityQueue(frequencies);

//    children -> c=1, d=1
    HuffmanNode root = h.pullLeastUsedAsNode(queue);

    Assertions.assertEquals('-', root.c);
    Assertions.assertEquals(2, root.frequency);
    Assertions.assertEquals('c', root.left.c);
    Assertions.assertEquals(1, root.left.frequency);
    Assertions.assertEquals('d', root.right.c);
    Assertions.assertEquals(1, root.right.frequency);

    Assertions.assertNull(root.right.right);

    root = h.pullLeastUsedAsNode(queue);

    Assertions.assertEquals('-', root.c);
    Assertions.assertEquals(5, root.frequency);
  }

  /**
   * aabbbcd
   * <p>
   * -7
   * b3       -4
   * a2       -2
   * c1    d1
   */
  @Test
  public void createHuffmanTreeTest()
  {
    int[] frequencies = h.createFrequencyTable("aabbbcd".toCharArray());
    PriorityQueue<HuffmanNode> queue = h.createPriorityQueue(frequencies);

    HuffmanNode root = h.createHuffmanTree(queue);

    Assertions.assertEquals('-', root.c);
    Assertions.assertEquals(7, root.frequency);

    Assertions.assertEquals('b', root.left.c);
    Assertions.assertEquals(3, root.left.frequency);

    Assertions.assertEquals('-', root.right.c);
    Assertions.assertEquals(4, root.right.frequency);

    Assertions.assertEquals('-', root.right.right.c);
    Assertions.assertEquals(2, root.right.right.frequency);

    Assertions.assertEquals('a', root.right.left.c);
    Assertions.assertEquals(2, root.right.left.frequency);

    Assertions.assertEquals('d', root.right.right.right.c);
    Assertions.assertEquals(1, root.right.right.right.frequency);

    Assertions.assertEquals('c', root.right.right.left.c);
    Assertions.assertEquals(1, root.right.right.left.frequency);
  }

  @Test
  public void generateBytesTest()
  {
    String[] st = new String[h.CHARACTER_LIMIT];
    int[] frequencies = h.createFrequencyTable("aabbbcd".toCharArray());
    PriorityQueue<HuffmanNode> queue = h.createPriorityQueue(frequencies);
    HuffmanNode root = h.createHuffmanTree(queue);

    h.generateBytes(st, root, new StringBuilder());

    Assertions.assertEquals("10", st['a']);
    Assertions.assertEquals("0", st['b']);
    Assertions.assertEquals("110", st['c']);
    Assertions.assertEquals("111", st['d']);
  }

  @Test
  public void encodeStringTest()
  {
    String text = "aabbbcd";
    int[] frequencies = h.createFrequencyTable(text.toCharArray());
    PriorityQueue<HuffmanNode> queue = h.createPriorityQueue(frequencies);
    HuffmanNode root = h.createHuffmanTree(queue);

    String s = h.encodeString(text.toCharArray(), root);

    Assertions.assertEquals("1010000110111", s);
  }

  @Test
  public void compressTest()
  {
    System.out.println(h.compress("aabbbcd".toCharArray()));
  }
}
