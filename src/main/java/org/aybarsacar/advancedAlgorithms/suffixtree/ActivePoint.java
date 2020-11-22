package org.aybarsacar.advancedAlgorithms.suffixtree;

/**
 *
 */
public class ActivePoint
{
  SuffixNode activeNode;
  int activeEdge;
  int activeLength;

  public ActivePoint(SuffixNode activeNode)
  {
    this.activeNode = activeNode;
    this.activeEdge = -1;
    this.activeLength = 0;
  }

  public ActivePoint(SuffixNode activeNode, int activeEdge, int activeLength)
  {
    this.activeNode = activeNode;
    this.activeEdge = activeEdge;
    this.activeLength = activeLength;
  }

  @Override
  public String toString()
  {
    return "ActivePoint[node= " + activeNode + ", " +
        "edge= " + activeEdge + ", " +
        "length= " + activeLength + "]";
  }
}
