package org.aybarsacar.advancedAlgorithms.huffman;

import java.util.PriorityQueue;

/**
 * this implementation does not accept - as a character
 * - is used to represent the root nodes for the characters
 * only characters are leaf
 */
public class Huffman
{
  public final int CHARACTER_LIMIT = 256;

  /**
   * the method to encode a string
   * the method that will be exposed to the user
   *
   * @param text
   * @return a String of the encoded text
   */
  public String compress(char[] text)
  {
    int[] frequencies = createFrequencyTable(text);
    PriorityQueue<HuffmanNode> queue = createPriorityQueue(frequencies);
    HuffmanNode root = createHuffmanTree(queue);

    String compressed = encodeString(text, root);
//    replace the string with the compressed bites
    return compressed;
  }

  public String encodeString(char[] text, HuffmanNode root)
  {
    StringBuilder sb = new StringBuilder();

    String[] array = new String[CHARACTER_LIMIT];

//    we need to generate the binaries (bytes)
    generateBytes(array, root, new StringBuilder());

    for (int i = 0; i < text.length; i++)
    {
      sb.append(array[text[i]]);
    }

    return sb.toString();
  }

  public void generateBytes(String[] array, HuffmanNode root, StringBuilder sb)
  {
    if (root.c == '-')
    {
//      so it is not a leaf
//      traverse to the left
      sb.append("0");
      generateBytes(array, root.left, sb);

//      traverse to the right
      sb.append("1");
      generateBytes(array, root.right, sb);
    }
    else
    {
//      so it is a leaf now
//      breaking condition
      array[root.c] = sb.toString();
      sb.deleteCharAt(sb.length() - 1);
    }
  }

  //  Create Frequency Table
  public int[] createFrequencyTable(char[] text)
  {
    int[] frequencies = new int[CHARACTER_LIMIT];

    for (int i = 0; i < text.length; i++)
    {
      frequencies[text[i]]++;
    }

    return frequencies;
  }


  /**
   * Create Priority Queue
   * we will use the PriorityQueue from Java utils which implements compare
   *
   * @param frequencies
   * @return
   */
  public PriorityQueue<HuffmanNode> createPriorityQueue(int[] frequencies)
  {
    PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(1, new FrequencyComparator());

    for (int i = 0; i < frequencies.length; i++)
    {
      if (frequencies[i] > 0)
      {
        queue.add(new HuffmanNode((char) i, frequencies[i]));
      }
    }

    return queue;
  }

  /**
   * Construct Huffman Tree
   *
   * @param queue
   * @return the root of the tree
   */
  public HuffmanNode createHuffmanTree(PriorityQueue<HuffmanNode> queue)
  {
    HuffmanNode root = null;
    while (queue.size() > 0)
    {
      root = pullLeastUsedAsNode(queue);

      if (queue.size() > 0)
      {
//        add the root we create back to the queue
        queue.add(root);

      }
    }

    return root;
  }

  /**
   * Pull the two least used from the queue
   *
   * @param queue
   * @return
   */
  public HuffmanNode pullLeastUsedAsNode(PriorityQueue<HuffmanNode> queue)
  {
//    removes the next element from the queue
//    grab 2 nodes from the queue
    HuffmanNode node1 = queue.poll();
    HuffmanNode node2 = queue.poll();

//    create the root for the 2 nodes we pulled and assign them as left and right
    HuffmanNode root = new HuffmanNode('-', node1.frequency + node2.frequency);
    root.left = node1;
    root.right = node2;

    return root;
  }
}
