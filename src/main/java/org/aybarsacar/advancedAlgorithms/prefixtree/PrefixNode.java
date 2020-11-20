package org.aybarsacar.advancedAlgorithms.prefixtree;

import java.util.HashMap;
import java.util.Map;

public class PrefixNode
{
  char c;
  int id;
  Map<Character, PrefixNode> children;
  boolean isWord = false;

  public PrefixNode()
  {
    this.c = 0;
    this.id = 0;
  }

  public PrefixNode(char c, int id)
  {
    this.c = c;
    this.id = id;
  }

  public boolean hasChildren(char c)
  {
    return children != null && children.containsKey(c);
  }

  public PrefixNode getChildren(char c)
  {
    if (!hasChildren(c)) return null;

    return children.get(c);
  }

  public void addChildren(PrefixNode node)
  {
    if (children == null) children = new HashMap<>();

    if (!hasChildren(node.c)) children.put(node.c, node);
  }

  public boolean canDelete()
  {
    return children == null || children.size() == 0;
  }

  @Override
  public String toString()
  {
    if (children != null) return c + (isWord ? "." + id : "") + "->[" + children.values() + "]";

    return c + "." + id;
  }
}
