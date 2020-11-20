package org.aybarsacar.advancedAlgorithms.suffixtrie;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * HashTable is used to store the nodes instead of an array in this implementation
 */
public class TrieNodeMap
{
  Map<Character, TrieNodeMap> children = new HashMap<>();
  List<Integer> indexes = new LinkedList<>();

  /**
   * banana
   *
   * @param text
   * @param index we are starting from
   */
  public void insertSuffix(String text, int index)
  {
    if (text.length() > index)
    {
      char current = text.charAt(index);

      if (children.get(current) == null)
      {
        children.put(current, new TrieNodeMap());
      }

      children.get(current).indexes.add(index);
      children.get(current).insertSuffix(text, ++index);
    }
  }

  public void insertSuffix(String text)
  {
    text = text + "$";

    for (int i = 0; i < text.length(); i++)
    {
      insertSuffix(text, i);
    }
  }

  //  expose this one
  public List<Integer> search(String pattern)
  {
    return search(pattern, 0);
  }

  /**
   * "banana"
   * search "ana" -> [3, 5]
   *
   * @param pattern
   * @param startPosition
   * @return the index position of the end patterns
   */
  private List<Integer> search(String pattern, int startPosition)
  {
    if (pattern.length() == startPosition) return indexes;

//    we are currently at the root
    if (children.get(pattern.charAt(startPosition)) != null)
    {
      return children.get(pattern.charAt(startPosition)).search(pattern, ++startPosition);
    }

    return null;
  }

  public boolean isSuffix(String pattern)
  {
    return isSuffix(pattern, 0);
  }

  private boolean isSuffix(String pattern, int startPosition)
  {
    if (pattern.length() == startPosition)
    {
//      return true if the last node points to a $ which indicates that it is a suffix
      return (children.get('$') != null);
    }

    if (children.get(pattern.charAt(startPosition)) != null)
    {
      return children.get(pattern.charAt(startPosition)).isSuffix(pattern, ++startPosition);
    }

    return false;
  }

  public boolean isSubstring(String pattern)
  {
    List<Integer> indexes = search(pattern);
    return indexes != null;
  }

  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();

    for (char c : children.keySet())
    {
      s.append(c).append("->").append(children.get(c).toString());
    }

    return s.toString();
  }
}
