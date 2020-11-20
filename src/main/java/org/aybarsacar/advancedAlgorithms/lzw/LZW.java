package org.aybarsacar.advancedAlgorithms.lzw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZW
{
  public List<Integer> compress(String text)
  {
    if (text == null) return null;

    int dictionarySize = 256;
    List<Integer> result = new ArrayList<>();

//    populate the dictionary with the chars from 1 to 255 from ASCII table
    Map<String, Integer> dictionary = new HashMap<>();
    for (int i = 0; i < dictionarySize; i++)
    {
      dictionary.put("" + (char) i, i);
    }

    String previous = "";
    for (char c : text.toCharArray())
    {
      String combined = previous + c;

      if (dictionary.containsKey(combined))
      {
        previous = combined;
      }
      else
      {
        result.add(dictionary.get(previous));
        dictionary.put(combined, dictionarySize++);
        previous = "" + c;
      }
    }

//    the last item
    if (!previous.equals("")) result.add(dictionary.get(previous));

    return result;
  }

  public String decompress(List<Integer> compressed)
  {
    if (compressed == null || compressed.size() == 0) return null;

    int dictionarySize = 256;
    Map<Integer, String> dictionary = new HashMap<>();

    for (int i = 0; i < dictionarySize; i++)
    {
      dictionary.put(i, "" + (char) i);
    }

//    get the first value and set it to previous string
    String previous = "" + (char) (int) compressed.remove(0);
    StringBuilder result = new StringBuilder(previous);
    for (int j : compressed)
    {
      String combined;
      if (dictionary.containsKey(j))
      {
        combined = dictionary.get(j);
      }
      else if (j == dictionarySize)
      {
        combined = previous + previous.charAt(0);
      }
      else
      {
        return "-1"; // Throw an exception here
      }

      result.append(combined);
      dictionary.put(dictionarySize++, previous + combined);
      previous = combined;
    }

    return result.toString();
  }
}
