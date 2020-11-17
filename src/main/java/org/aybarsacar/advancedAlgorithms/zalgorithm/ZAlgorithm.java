package org.aybarsacar.advancedAlgorithms.zalgorithm;

/**
 * Z Algorithm
 * this algorithm uses a table to help improve the search performance -> Z Table
 * this implementation assumes the array and the pattern do not contain $
 */
public class ZAlgorithm
{
  private final char SEPARATOR = '$';

  public int search(char[] pattern, char[] array)
  {
    if ((pattern == null || pattern.length == 0) && array != null) return 0;
    if (array == null) return -1;

    int[] z = createZTable(pattern, array);

    int i = pattern.length + 1;
    while (i < z.length)
    {
      if (z[i] == pattern.length)
      {
        return i - pattern.length - 1;
      }
      i++;
    }

    return -1;
  }

  /**
   * result array contains the indices of the
   * we can use a list or a stack to preserve some memory
   * because we don't know how many results we will have
   *
   * @param pattern
   * @param array
   * @return result array
   */
  public int[] searchAll(char[] pattern, char[] array)
  {
    int[] result = new int[array.length];

    int[] z = createZTable(pattern, array);

    int i = pattern.length + 1;
    while (i < z.length)
    {
      if (z[i] == pattern.length)
      {
        result[i - pattern.length - 1] = z[i];
      }
      i++;
    }

    return result;
  }

  /**
   * we concat the array and the patterns and separate them with a special char
   *
   * @param array
   * @param pattern
   * @return
   */
  public int[] createZTable(char[] pattern, char[] array)
  {
    int[] z = new int[array.length + pattern.length + 1];
    char[] longString = createLongString(pattern, array);

    int left = 0, right = 0;
    for (int i = 1; i < longString.length; i++)
    {
      if (i > right)
      {
        left = right = i;

        while (right < longString.length && longString[right - left] == longString[right])
        {
          right++;
        }

        z[i] = right - left;

        right--;
      }
      else
      {
        int k = i - left;

        if (z[k] < right - i + 1)
        {
          z[i] = z[k];
        }
        else
        {
          left = i;
          while (right < longString.length && longString[right - left] == longString[right])
          {
            right++;
          }

          z[i] = right - left;

          right--;
        }
      }
    }

    return z;
  }

  public char[] createLongString(char[] pattern, char[] array)
  {
    char[] s = new char[pattern.length + array.length + 1];

    for (int i = 0; i < pattern.length; i++)
    {
      s[i] = pattern[i];
    }

    s[pattern.length] = SEPARATOR;

    for (int i = 0; i < array.length; i++)
    {
      s[pattern.length + 1 + i] = array[i];
    }

    return s;
  }
}
