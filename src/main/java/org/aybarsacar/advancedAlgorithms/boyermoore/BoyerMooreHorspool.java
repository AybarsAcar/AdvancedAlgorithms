package org.aybarsacar.advancedAlgorithms.boyermoore;

/**
 * Boyer Moore Horspool algorithm
 * this implementation is the simplified version of the Boyer Moore Algorithm
 * it only implements the bad character table
 */
public class BoyerMooreHorspool
{
  //  we will assume our search will only happen in this range of characters
  private final int ASCII_TABLE_SIZE = 256;

  public int search(char[] array, char[] pattern)
  {
    if (array != null && (pattern == null || pattern.length == 0)) return 0;
    if (array == null) return -1;

    int[] table = preprocessTable(pattern);

    for (int i = pattern.length - 1; i < array.length;)
    {
//      where we will start matching against the pattern
      for (int j = pattern.length - 1; pattern[j] == array[i]; i--, j--)
      {
        if (j == 0) return i; // 0 is we hit the first char in the pattern success!
      }

//      if it doesnt match we increase the i by the corresponding value in the table
//      so we can skip characters
      i+= table[array[i]];
    }

    return -1;
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
  public int[] preprocessTable(char[] pattern)
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
