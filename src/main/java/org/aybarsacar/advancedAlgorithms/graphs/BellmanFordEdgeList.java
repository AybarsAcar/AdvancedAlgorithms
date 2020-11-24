package org.aybarsacar.advancedAlgorithms.graphs;

import java.util.Arrays;

/**
 * Bellman Ford Algorithm with a time complexity of O(V * E)
 * implemented for graphs that are represented as an Edge List
 * [[1, 2], [2, 1], [3, 4], ...]
 * Edges are stored in an Array - Edges[]
 */
public class BellmanFordEdgeList
{
  /**
   * Helper Class to represents the directed edges in a graph
   * goes from a node to a node with a certain cost
   */
  static class Edge
  {
    int from, to;
    double cost;

    public Edge(int from, int to, double cost)
    {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  /**
   * Implementation of the Bellman-Ford Algorithm
   * this algorithm finds the shortest path between the start node and all other nodes
   * Detects the negative cycles
   * if a node is in the negative cycle then the cost to get to that node is set to Double.NEGATIVE_INFINITY
   *
   * @param edges - Edge list representation of the graph
   * @param V     - the number of vertices on the graph
   * @param start - the id of the starting node
   * @return - the shortest path between the start node and all other nodes, distance array
   */
  public static double[] bellmanFord(Edge[] edges, int V, int start)
  {
    double[] dist = new double[V];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

//    Only in the worst case does it take V-1 iterations for the Bellman-Ford to complete
//    Another stopping condition is when we're unable to relax an edge
//    which means we have reached the optimal solution
    boolean relaxedAnEdge = true;

//    for each vertex, apply relaxation for all edges
    for (int v = 0; v < V - 1 && relaxedAnEdge; v++)
    {
      relaxedAnEdge = false;
      for (Edge edge : edges)
      {
        if (dist[edge.from] + edge.cost < dist[edge.to])
        {
          dist[edge.to] = dist[edge.from] + edge.cost;
          relaxedAnEdge = true;
        }
      }
    }


    // Run algorithm a second time to detect which nodes are part of a negative cycle.
    // A negative cycle has occurred if we can find a better path beyond the optimal solution.
    relaxedAnEdge = true;
    for (int v = 0; v < V - 1 && relaxedAnEdge; v++)
    {
      relaxedAnEdge = false;
      for (Edge edge : edges)
      {
        if (dist[edge.from] + edge.cost < dist[edge.to])
        {
          dist[edge.to] = Double.NEGATIVE_INFINITY;
          relaxedAnEdge = true;
        }
      }
    }

    return dist;
  }
}
