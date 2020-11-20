package org.aybarsacar.advancedAlgorithms.suffixtrie;

import java.util.LinkedList;
import java.util.List;

public class TrieNode
{
  public final int MAX_ARRAY = 256; // ASCII Table Size

  TrieNode[] children = new TrieNode[MAX_ARRAY];
  List<Integer> indexes = new LinkedList<>();

  public TrieNode()
  {
    for (int i = 0; i < MAX_ARRAY; i++)
    {
      children[i] = null;
    }
  }

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

      if (children[current] == null)
      {
        children[current] = new TrieNode();
      }

      children[current].indexes.add(index);
      children[current].insertSuffix(text, ++index);
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
    if (children[pattern.charAt(startPosition)] != null)
    {
      return children[pattern.charAt(startPosition)].search(pattern, ++startPosition);
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
      return (children['$'] != null);
    }

    if (children[pattern.charAt(startPosition)] != null)
    {
      return children[pattern.charAt(startPosition)].isSuffix(pattern, ++startPosition);
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

    for (int c = 0; c < children.length; c++)
    {
      if (children[c] != null) s.append((char) c).append("->").append(children[c].toString());
    }

    return s.toString();
  }
}
