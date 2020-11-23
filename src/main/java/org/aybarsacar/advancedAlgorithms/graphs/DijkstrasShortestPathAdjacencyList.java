package org.aybarsacar.advancedAlgorithms.graphs;

import java.util.Comparator;
import java.util.List;

/**
 * Implementation of Dijkstra's Shortest Path Algorithm from a starting node to a specified node
 * so the algorithm breaks early when the target node is reached for optimisation
 */
public class DijkstrasShortestPathAdjacencyList
{
  //  Small epsilon value to compare double values
  private static final double EPS = 1e-6;

  /**
   * Helper Class to represents the edges in a graph
   */
  static class Edge
  {
    int from, to, weight;

    public Edge(int from, int to, int weight)
    {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  /**
   * Helper Class to represents the Nodes in a graph
   * to track the nodes to visit while running Dijkstra's
   */
  static class Node
  {
    int id;
    double value;

    public Node(int id, double value)
    {
      this.id = id;
      this.value = value;
    }
  }

  private int n;
  private double[] dist; // distance array
  private Integer[] prev;
  private List<List<Edge>> graph;

  /**
   * Update the comparator on the node class
   * used in Dijkstra's where we pick the smallest value
   */
  private Comparator<Node> comparator = new Comparator<Node>()
  {
    @Override
    public int compare(Node node1, Node node2)
    {
      if (Math.abs(node1.value - node2.value) < EPS) return 0;
      return (node1.value - node2.value) > 0 ? +1 : -1;
    }
  };

  public 
}
