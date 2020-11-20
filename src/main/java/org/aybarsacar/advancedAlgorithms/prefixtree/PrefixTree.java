package org.aybarsacar.advancedAlgorithms.prefixtree;

public class PrefixTree
{
  PrefixNode root;

  public PrefixTree()
  {
    root = new PrefixNode();
  }

  /**
   * cat
   * we want to assign the id at the last node where isWord flag is true
   *
   * @param word
   * @param id
   */
  public void insert(char[] word, int id)
  {
    PrefixNode current = root;

    for (int i = 0; i < word.length; i++)
    {
      if (current.hasChildren(word[i]))
      {
        current = current.getChildren(word[i]);
      }
      else
      {
        PrefixNode node = new PrefixNode(word[i], 0);
        current.addChildren(node);
        current = node;
      }
    }

    current.isWord = true;
    current.id = id;
  }

  public PrefixNode find(char[] word)
  {
    PrefixNode current = root;

    for (char c : word)
    {
      if (current.hasChildren(c))
      {
        current = current.getChildren(c);
      }
      else
      {
        return null;
      }
    }
    return current;
  }

  public boolean delete(char[] word)
  {
    return delete(word, root, 0);
  }

  public boolean delete(char[] word, PrefixNode node, int wordIndex)
  {
    if (wordIndex == word.length - 1)
    {
      PrefixNode w = node.getChildren(word[wordIndex]);
//      set the is word to false and get rid of the id
      w.isWord = false;
      w.id = 0;

      if (w.canDelete())
      {
        node.children.remove(word[wordIndex]);
        return true;
      }
    }
    else
    {
      if (!node.hasChildren(word[wordIndex])) return false;
      boolean canDelete = delete(word, node.getChildren(word[wordIndex]), ++wordIndex);

      if (canDelete)
      {
        if (node.getChildren(word[--wordIndex]).isWord) return false;

        node.children.remove(word[wordIndex]);
        return true;
      }
    }

    return false;
  }
}
