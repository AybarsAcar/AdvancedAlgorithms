package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

/**
 * this class implementation is a solver
 * finds the Articulation Points in a Graph represented as an Adjacency List
 */
public class ArticulationPointsAdjacencyList
{
  private int n, id, rootNodeOutcomingEdgeCount;
  private boolean solved;
  private int[] low, ids;
  private boolean[] visited, isArticulationPoint;
  private List<List<Integer>> graph;

  public ArticulationPointsAdjacencyList(int n, List<List<Integer>> graph)
  {
    if (graph == null || graph.size() != n) throw new IllegalArgumentException("Graph is not valid");
    this.n = n;
    this.graph = graph;
  }

  public boolean[] findArticulationPoints()
  {
    if (solved) return isArticulationPoint;

    id = 0;
    low = new int[n]; // Low link values
    ids = new int[n]; // Node ids
    visited = new boolean[n];
    isArticulationPoint = new boolean[n];

    for (int i = 0; i < n; i++)
    {
      if (!visited[i])
      {
        rootNodeOutcomingEdgeCount = 0;
        dfs(i, i, -1);
        isArticulationPoint[i] = rootNodeOutcomingEdgeCount > 1;
      }
    }

    solved = true;
    return isArticulationPoint;
  }

  /**
   * @param root
   * @param at
   * @param parent
   */
  private void dfs(int root, int at, int parent)
  {
    if (parent == root) rootNodeOutcomingEdgeCount++;

    visited[at] = true;
    low[at] = ids[at] = id++;

    List<Integer> edges = graph.get(at);

    for (Integer to : edges)
    {
      if (to == parent) continue;

      if (!visited[to])
      {
        dfs(root, to, at);
        low[at] = Math.min(low[at], low[to]);

        if (ids[at] <= low[to])
        {
          isArticulationPoint[at] = true;
        }
      }
      else
      {
        low[at] = Math.min(low[at], ids[to]);
      }
    }
  }

  /* Helper Methods*/
  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n)
  {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to)
  {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }
}
