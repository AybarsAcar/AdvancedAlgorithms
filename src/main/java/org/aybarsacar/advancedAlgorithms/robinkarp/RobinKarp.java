package org.aybarsacar.advancedAlgorithms.robinkarp;

/**
 * Robin-Karp Algorithm
 * it uses rolling hashing to find a match between the pattern and the array it is searching for
 * returns the index of the first char in the array
 * returns -1 if no match
 */
public class RobinKarp
{
  private final int PRIME = 3;

  /**
   * learning = 8
   * nin = 3
   * ing is our last 3 which is the (8 - 3)th element
   * otherwise we get out of bounds exception
   *
   * @param array
   * @param pattern
   * @return
   */
  public int search(char[] array, char[] pattern)
  {
    if (array == null || pattern == null) return -1;

    int n = array.length;
    int m = pattern.length;
    int lastChar = n - m;

    long patternHash = calculateHash(pattern, m);
    long arrayHash = calculateHash(array, m);

    for (int i = 0; i <= lastChar; i++)
    {
      if (patternHash == arrayHash && match(array, pattern, i))
      {
//        Brute force
        return i;
      }

      if (i < lastChar)
      {
//        recalculate the hash
        arrayHash = recalculateHash(arrayHash, array[i], array[i + m], m);
      }
    }

    return -1;
  }

  public boolean match(char[] array, char[] pattern, int index)
  {
    for (int i = 0; i < pattern.length; i ++)
    {
      if (array[index + i] != pattern[i]) return false;
    }
    return true;
  }

  public long recalculateHash(long oldHash, char oldChar, char newChar, int hashSize)
  {
    long hash = oldHash - charValue(oldChar);
    hash = hash / PRIME;
    hash += charValue(newChar) * Math.pow(PRIME, hashSize - 1);
    return hash;
  }

  /**
   * @param text
   * @param hashSize is for how many characters i will calculate the hash for
   * @return
   */
  public long calculateHash(char[] text, int hashSize)
  {
    long hash = 0;

    for (int i = 0; i < hashSize; i++)
    {
      hash += charValue(text[i]) * Math.pow(PRIME, i);
    }

    return hash;
  }

  //  we don really need this function, its more like a convenience
  public int charValue(char c)
  {
    return c - 96; // so lets say 'a' -> 1
  }
}
