package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.List;
import java.util.Map;

/**
 * This topological sort implementation takes an adjacency list of an acyclic graph and returns an
 * array with the indexes of the nodes in a (non unique) topological order which tells you how to
 * process the nodes in the graph. More precisely from wiki: A topological ordering is a linear
 * ordering of its vertices such that for every directed edge uv from vertex u to vertex v, u comes
 * before v in the ordering.
 * <p>
 * Time Complexity: O(V + E)
 * <p>
 * Graph is implemented as a HashMap
 * each node(vertex) is a unique integer value which is the key of our map
 * and the value is a list of Edges which represents that node's edges
 */
public class TopologicalSortAdjacencyList
{

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
   * Helper method Depth First Search
   *
   * @param i        is the index
   * @param at       the current value
   * @param visited  array of vertices true if visited
   * @param ordering our topolicatl ordering array
   * @param graph    the graph we operate in
   * @return
   */
  private static int dfs(int i, int at, boolean[] visited, int[] ordering, Map<Integer, List<Edge>> graph)
  {
    visited[at] = true;

    List<Edge> edges = graph.get(at);

    if (edges != null)
    {
      for (Edge edge : edges)
      {
        if (!visited[edge.to]) i = dfs(i, edge.to, visited, ordering, graph);
      }
    }
    ordering[i] = at;
    return i - 1;
  }

  /**
   * Finds a topological ordering of hte nodes in a Directed Acyclic Graph(DAG)
   * <p>
   * numNodes doesn't represent all the nodes, it excludes the nodes without any edges
   *
   * @param graph
   * @param numNodes number of nodes in hte graph
   * @return ordering
   */
  public static int[] topologicalSort(Map<Integer, List<Edge>> graph, int numNodes)
  {
    int[] ordering = new int[numNodes];
    boolean[] visited = new boolean[numNodes];

    int i = numNodes - 1;
    for (int at = 0; at < numNodes; at++)
    {
      if (!visited[at]) i = dfs(i, at, visited, ordering, graph);
    }

    return ordering;
  }

  /**
   * @param graph    we are working with as an adjacency list
   * @param start    is the starting node
   * @param numNodes number of nodes in our graph
   * @return distance to each node stored in an integer array
   */
  public static Integer[] dagShortestPath(Map<Integer, List<Edge>> graph, int start, int numNodes)
  {
    int[] topSort = topologicalSort(graph, numNodes);
    Integer[] distance = new Integer[numNodes];
    distance[start] = 0;

//    loop through each node
    for (int i = 0; i < numNodes; i++)
    {
//      get the node at the topSort
      int nodeIndex = topSort[i];
      if (distance[nodeIndex] != null)
      {
//        grab all the edges for that node at the nodeIndex
        List<Edge> adjacentEdges = graph.get(nodeIndex);

        if (adjacentEdges != null)
        {
          for (Edge edge : adjacentEdges)
          {
//            relaxation step
//            find the new distance and update it if its less then what is stored
            int newDistance = distance[nodeIndex] + edge.weight;

            if (distance[edge.to] == null) // this means infinity
            {
//              assign it if no entry is present
              distance[edge.to] = newDistance;
            }
            else
            {
//              or set it to the min of the present and new distance value
              distance[edge.to] = Math.min(distance[edge.to], newDistance);
            }
          }
        }
      }
    }
    return distance;
  }
}
