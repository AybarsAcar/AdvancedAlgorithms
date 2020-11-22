package org.aybarsacar.advancedAlgorithms.suffixtree;

public class SuffixNode
{
  private final int MAX_CHARACTERS = 256;

  SuffixNode[] children = new SuffixNode[MAX_CHARACTERS];
  SuffixNode suffixLink;

  int start;
  End end;
  int index;

  public SuffixNode(int start, End end)
  {
    this.start = start;
    this.end = end;
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("SuffixNode [start=").append(start).append(", end=").append(end.end).append("] ");

    for (int i = 0; i < children.length; i++)
    {
      if (children[i] != null)
      {
        sb.append((char) i);
        sb.append("->").append(children[i]);
      }
    }


    return sb.toString();
  }
}
