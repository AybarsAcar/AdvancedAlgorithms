package org.aybarsacar.advancedAlgorithms.boyermoore;

/**
 * Boyer Moore Algorithm
 * search for a pattern returns the index of the match or -1 if no match
 * uses the Good Suffix Table and the Bad Character Table
 */
public class BoyerMoore
{
  private final int ASCII_TABLE_SIZE = 256;

  /**
   * The main searching algorithm Boyer Moore
   *
   * @param array
   * @param pattern
   * @return
   */
  public int search(char[] array, char[] pattern)
  {
    if ((pattern == null || pattern.length == 0) && array != null) return 0;
    if (array == null) return -1;

    int[] badCharTable = preComputeBadCharTable(pattern);
    int[] suffixTable = preprocessSuffixTable(pattern);

    for (int i = pattern.length - 1, j; i < array.length; )
    {
      for (j = pattern.length - 1; pattern[j] == array[i]; --i, --j)
      {
//        j at the first position we found our match
        if (j == 0) return i;
      }

//      good suffix allows us to escape 1 extra character horspool didn't allow us
      i += Math.max(suffixTable[pattern.length - 1 - j], badCharTable[array[i]]);
    }

    return -1;
  }

  /**
   * abcdabaibai
   * baibai
   * 1st round -> table == [6,7,8,9,10,11]
   *
   * @param pattern
   * @return the Good Suffix Table
   */
  public int[] preprocessSuffixTable(char[] pattern)
  {
    int[] table = new int[pattern.length];

//    1st part -- first pointer is fixed, decrement the pointer at the end
    computePrefix(pattern, table);

//    2nd part -- last pointer is fixed, increment the pointer at index 0
//    this overrides the table
    computeSuffix(pattern, table);

    return table;
  }

  public void computePrefix(char[] pattern, int[] table)
  {
    int lastPrefixPosition = pattern.length;

    for (int i = pattern.length; i > 0; --i)
    {
      if (isPrefix(pattern, i)) lastPrefixPosition = i;

//      populate the table
      table[pattern.length - i] = lastPrefixPosition - i + pattern.length;
    }
  }

  public void computeSuffix(char[] pattern, int[] table)
  {
    for (int i = 0; i < pattern.length - 1; ++i)
    {
      int len = suffixLength(pattern, i);

//      update the table
      table[len] = pattern.length - 1 - i + len;
    }
  }

  /**
   * baibai
   * j    i then keeps matching them and move i to the left
   * if they match both increment if they reach to the end it returns true
   * if not returns false
   *
   * @param pattern our pattern
   * @param index   -
   * @return true if end is reached
   */
  public boolean isPrefix(char[] pattern, int index)
  {
    for (int i = index, j = 0; i < pattern.length; ++i, ++j)
    {
      if (pattern[i] != pattern[j]) return false;
    }
    return true;
  }

  /**
   * baidai
   * j
   *
   * @param pattern
   * @param index
   * @return the length of the match
   */
  public int suffixLength(char[] pattern, int index)
  {
    int len = 0;
    int j = pattern.length - 1;

    for (int i = index; i >= 0 && pattern[i] == pattern[j]; --i, --j)
    {
      len++;
    }

    return len;
  }

  /**
   * This method creates the Bad Character Table
   * It will add pattern.length to every character that is not in the table
   * it will also add pattern.length to the last character of the pattern in case it is unique
   * <p>
   * test(*) -- recalculate t because it repeats
   * 3214
   * so the updated table will be [1214]
   * max(1, p.length - index - 1)
   * <p>
   * abc -> c becomes star -- no repeating letter
   * [213]
   *
   * @param pattern we search for
   * @return Bad Character Table
   */
  public int[] preComputeBadCharTable(char[] pattern)
  {
    int[] table = new int[ASCII_TABLE_SIZE];

    for (int i = 0; i < ASCII_TABLE_SIZE; i++)
    {
      table[i] = pattern.length;
    }

//    because last letter is the length itself
    for (int t = 0; t < pattern.length - 1; t++)
    {
      table[pattern[t]] = Math.max(1, pattern.length - t - 1);
    }

//    check if we are at the last letter
    if (table[pattern[pattern.length - 1]] < pattern.length)
    {
//      recalculate because matching chars
      table[pattern[pattern.length - 1]] = 1; // why 1 ? because it will be always max(1, 0) at the pattern.length
    }

    return table;
  }
}
