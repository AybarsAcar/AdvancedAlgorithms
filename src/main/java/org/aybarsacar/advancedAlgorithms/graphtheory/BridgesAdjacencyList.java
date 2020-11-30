package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all the bridges in a graph
 * Graph is represented as an Adjacency List
 * <p>
 * Briges are returned as a pair of node ids
 * so brides = [0,1] means there is one bridge which is between node 0 and 1
 */
public class BridgesAdjacencyList
{
  //  n -> size of the graph, number of nodes
  private int n, id;
  private int[] low, ids;
  private boolean solved; // so we don't need to run the algorithm again if the bridges are already found
  private boolean[] visited;
  private List<List<Integer>> graph;
  public List<Integer> bridges;

  public BridgesAdjacencyList(int n, List<List<Integer>> graph)
  {
    if (graph == null || n <= 0 || graph.size() != n) throw new IllegalArgumentException("Graph is not valid");
    this.n = n;
    this.graph = graph;
  }

  /**
   * Returns a list of pairs of nodes indicating which nodes form bridges
   * The returned list is always of even length and indexes (2i, 2i+1) format
   * i.e (0,1) and (2,3)
   *
   * @return bridges
   */
  public List<Integer> findBridges()
  {
    if (solved) return bridges;

    id = 0;
    low = new int[n]; // low link values
    ids = new int[n]; // node ids
    visited = new boolean[n];

    bridges = new ArrayList<>();

//    Finds all bridges in the graph across various connected components
    for (int i = 0; i < n; i++)
    {
      if (!visited[i]) dfs(i, -1, bridges);
    }

    solved = true;
    return bridges;
  }

  /**
   * Depth First Search Traversal
   *
   * @param current
   * @param parent
   * @param bridges
   */
  public void dfs(int current, int parent, List<Integer> bridges)
  {
    visited[current] = true;
    low[current] = ids[current] = ++id;

//    next is the all the nodes we can reach from the current node
    for (Integer next : graph.get(current))
    {
      if (next == parent) continue; // skip the node we were just at which is current

      if (!visited[next])
      {
        dfs(next, current, bridges);
        low[current] = Math.min(low[current], low[next]);

        if (ids[current] < low[next])
        {
//          we have found our bridge so add it
          bridges.add(current);
          bridges.add(next);
        }
      }
      else
      {
        low[current] = Math.min(low[current], ids[next]);
      }
    }
  }

  /* Example usage: */

  public static void main(String[] args)
  {

    int n = 9;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 4);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 6, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 8, 5);

    BridgesAdjacencyList solver = new BridgesAdjacencyList(n, graph);
    List<Integer> bridges = solver.findBridges();

    // Prints:
    // Bridge between nodes: 3 and 4
    // Bridge between nodes: 2 and 3
    // Bridge between nodes: 2 and 5
    for (int i = 0; i < bridges.size() / 2; i++)
    {
      int node1 = bridges.get(2 * i);
      int node2 = bridges.get(2 * i + 1);
      System.out.printf("Bridge between nodes: %d and %d\n", node1, node2);
    }
  }

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
