package org.aybarsacar.advancedAlgorithms.huffman;

import java.util.PriorityQueue;

/**
 * this implementation does not accept - as a character
 * - is used to represent the root nodes for the characters
 * only characters are leaf
 */
public class Huffman
{
  final int CHARACTER_LIMIT = 256;
  StringBuilder header = new StringBuilder();

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

//    replace the string with the compressed bites
    return header.toString() + encodeString(text, root);
  }

  /**
   * Decodes the encodedText passed as a character array previously compressed by this algorithm
   *
   * @param encodedText as a char array
   * @return array of decoded characters
   */
  public char[] decompress(char[] encodedText)
  {
    if (encodedText[0] != (char) 1) return null;
    int[] frequencies = parseHeaderAsFrequency(encodedText);
    PriorityQueue<HuffmanNode> queue = createPriorityQueue(frequencies);
    HuffmanNode root = createHuffmanTree(queue);

    String decompressed = decodeString(encodedText, root);

    return decompressed.toCharArray();
  }

  /**
   * parses the header to frequency table
   * header starts with char 1 ands with char 2 from ASCII table
   * and frequencies are separated by : when encoding
   * \u0001:a2:b3:c1:d1\u0002 -> a.frequency = 2, b.frequenct = 3, etc
   *
   * @param text
   * @return
   */
  public int[] parseHeaderAsFrequency(char[] text)
  {
    int[] frequencies = new int[CHARACTER_LIMIT];
    int i = 0;

    for (; i < text.length && text[i] != (char) 2; i++)
    {
      header.append(text[i]);

      if (text[i] == ':')
      {
        i++;
        header.append(text[i]);

        int frequency = 0;
        int multiplier = 1;
        int j = i + 1;

        for (; j < text.length && text[j] != (char) 2 && text[j] != ':'; j++)
        {
          frequency = (frequency * multiplier) + (text[j] - '0'); // - '0' so it will cast is as int
          if (frequency != 0) multiplier = 10;
          header.append(text[i] - '0');
        }

        frequencies[text[i]] = frequency;
        i = j - 1; // because when we loop it will increase 1 more anyways
      }
    }

    return frequencies;
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

  /**
   * This method decodes the HuffmanCompression bits into characters
   *
   * @param text
   * @param root
   * @return
   */
  public String decodeString(char[] text, HuffmanNode root)
  {
    StringBuilder sb = new StringBuilder();

    HuffmanNode current = root;
    for (int i = header.length(); i < text.length; i++)
    {
      if (text[i] - '0' == 0) current = current.left;
      else if (text[i] - '0' == 1) current = current.right;

      if (isLeaf(current))
      {
//        it is a leaf so assign the character of that bnode
        sb.append(current.c);
//        reset the root
        current = root;
      }
    }
    return sb.toString();
  }

  public boolean isLeaf(HuffmanNode node)
  {
    if (node == null) return false;
    return node.left == null && node.right == null;
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
    header = new StringBuilder();

//    char to indicate the beginning the of the header
    header.append((char) 1);

    PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(1, new FrequencyComparator());

    for (int i = 0; i < frequencies.length; i++)
    {
      if (frequencies[i] > 0)
      {
        queue.add(new HuffmanNode((char) i, frequencies[i]));
        header.append(":").append((char) i).append(frequencies[i]);
      }
    }

//    char to indicate the end the of the header
    header.append((char) 2);

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
