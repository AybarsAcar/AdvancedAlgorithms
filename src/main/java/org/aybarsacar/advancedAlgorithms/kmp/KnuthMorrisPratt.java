package org.aybarsacar.advancedAlgorithms.kmp;

public class KnuthMorrisPratt
{
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
