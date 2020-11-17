package org.aybarsacar.advancedAlgorithms.kmp;

public class KnuthMorrisPratt
{
  /**
   * The KMP Searching algorithm method
   * Returns the index for the first match of the pattern in the array
   * it will return -1 if pattern does not match
   * <p>
   * 0 1 2 3 4 5 6 7 8 9 10 11 12 13
   * a b a z a c a b a b a  b  a  c
   * <p>
   * a b a b a c
   * 0 0 1 2 3 0
   * <p>
   * returns 8;
   *
   * @param array   input array
   * @param pattern we are searching for
   * @return the first index of the match in the array
   */
  public int search(char[] array, char[] pattern)
  {
    int[] lsp = computeLSPTable(pattern);

    int j = 0;
//    we are comparing the pattern against the array so we need to compare the first character
//    that's why start the loop from 0
    for (int i = 0; i < array.length; i++)
    {
      while (j > 0 && array[i] != pattern[j])
      {
//        decrement the j index as they don't match until they match or j hits 0
        j = lsp[j - 1];
      }

      if (array[i] == pattern[j])
      {
        j++;

        if (j == pattern.length)
        {
//          index of where patterns starts in the array
          return i - (j - 1);
        }
      }
    }

    return -1;
  }

  /**
   * This computes the Longest Suffix Prefix Table for the pattern we are searching for
   * used by the KMP Algorithm
   *
   * @param pattern
   * @return the table array that describes the pattern
   */
  public int[] computeLSPTable(char[] pattern)
  {
    int[] lsp = new int[pattern.length];

    int j = 0;
    for (int i = 1; i < pattern.length; i++)
    {
      while (j > 0 && pattern[i] != pattern[j])
      {
        j = lsp[j - 1];
      }

      if (pattern[i] == pattern[j])
      {
        lsp[i] = j + 1;
        j++;
      }
      else
      {
        lsp[i] = 0;
      }
    }

    return lsp;
  }
}
