package org.aybarsacar.advancedAlgorithms.huffman;

import java.util.Comparator;

public class FrequencyComparator implements Comparator<HuffmanNode>
{
  @Override
  public int compare(HuffmanNode first, HuffmanNode second)
  {
//    which position the object being compared will take into the priority queue
    return first.frequency - second.frequency;
  }
}
