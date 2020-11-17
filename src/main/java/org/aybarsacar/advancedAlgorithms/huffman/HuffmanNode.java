package org.aybarsacar.advancedAlgorithms.huffman;

/**
 * This Node is used to compose the Huffman Tree
 */
public class HuffmanNode
{
  char c;
  int frequency;

  HuffmanNode left = null;
  HuffmanNode right = null;

  public HuffmanNode(char c, int frequency)
  {
    this.c = c;
    this.frequency = frequency;
  }
}
