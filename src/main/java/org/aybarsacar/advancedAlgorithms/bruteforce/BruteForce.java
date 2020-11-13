package org.aybarsacar.advancedAlgorithms.bruteforce;

import java.util.Arrays;

/**
 * Brute Force Algorithm
 * It will search for a pattern into an array
 * and if the pattern is found it will return teh index of the array
 * where the first letter of the pattern is found
 * <p>
 * if no match is found, it will return -1;
 */
public class BruteForce
{
  /**
   * This method searches for the pattern into an array and return the index of the first char
   * if no match is found -1 is returned
   * (abcdef, def) -> returns 3
   *
   * @param arr
   * @param pattern
   * @return int // the index
   */
  public int firstMatch(char[] arr, char[] pattern)
  {
    if (arr.length < pattern.length) return -1;

    for (int i = 0; i <= arr.length - pattern.length; i++)
    {
      for (int j = 0; j < pattern.length; j++)
      {
        if (arr[i + j] != pattern[j]) break;

        if (j == pattern.length - 1) return i;
      }
    }

    return -1;
  }

  /**
   * This method searches for a pattern into an array
   * returns an array of int with the indexes of elements found
   */
  public int[] everyMatch(char[] arr, char[] pattern)
  {
    int[] found = new int[arr.length];
    Arrays.fill(found, -1);
    int index = 0;

    for (int i = 0; i <= arr.length - pattern.length; i++)
    {
      for (int j = 0; j < pattern.length; j++)
      {
        if (arr[i + j] != pattern[j]) break;

        if (j == pattern.length - 1)
        {
          found[index++] = i;
        }
      }
    }

    return found;
  }
}
