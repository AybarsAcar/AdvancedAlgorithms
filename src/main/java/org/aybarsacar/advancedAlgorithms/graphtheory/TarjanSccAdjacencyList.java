package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.*;

/**
 * this class is a solver to find the Strongly Connected Components in a graph
 * the graph is represented as an Adjacency List
 * Tarjan's Algorithm is applied to find the SCCs
 * <p>
 * TODO: revisit the algorithm, optimise and clarify it
 */
public class TarjanSccAdjacencyList
{
  private final int UNVISITED = -1;

  private int n;
  private List<List<Integer>> graph;

  private boolean solved;

  private int sccCount, id;
  private boolean[] visited;
  private int[] ids, low, sccs;
  private Deque<Integer> stack; // part of Tarjan's algorithm

  public TarjanSccAdjacencyList(List<List<Integer>> graph)
  {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
    this.graph = graph;
    this.n = graph.size();
  }

  /**
   * @return the number of strongly connected components in a graph
   */
  public int sccCount()
  {
    if (!solved) solve();
    return this.sccCount;
  }

  /**
   * Get the connected components of this graph
   * if 2 indexes have the same value then they are in the same SCC
   *
   * @return
   */
  public int[] getSccs()
  {
    if (!solved) solve();
    return this.sccs;
  }

  public void solve()
  {
    if (solved) return;

    ids = new int[n];
    low = new int[n];
    sccs = new int[n];
    visited = new boolean[n];
    stack = new ArrayDeque<>();

    Arrays.fill(ids, UNVISITED);

    for (int i = 0; i < n; i++)
    {
      if (ids[i] == UNVISITED) dfs(i);
    }
    solved = true;
  }

  /**
   * DFS traversal
   *
   * @param current node we are currently exploring
   */
  private void dfs(int current)
  {
    ids[current] = low[current] = id++;
    stack.push(current);
    visited[current] = true;

//    iterate over the neighbours of the current node we are exploring
    for (int to : graph.get(current))
    {
      if (ids[to] == UNVISITED)
      {
//        explore that too
        dfs(to);
      }

      if (visited[to])
      {
//        update the low link while backtracking
        low[current] = Math.min(low[current], low[to]);
      }
    }

    if (ids[current] == low[current])
    {
      for (int node = stack.pop(); ; node = stack.pop())
      {
        visited[node] = false;
        sccs[node] = sccCount;
        low[node] = ids[current];
        if (node == current) break;

        sccCount++;
      }
    }
  }

  /* HELPERS */
  // Initializes adjacency list with n nodes.
  public static List<List<Integer>> createGraph(int n)
  {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Adds a directed edge from node 'from' to node 'to'
  public static void addEdge(List<List<Integer>> graph, int from, int to)
  {
    graph.get(from).add(to);
  }


  public static void main(String[] arg)
  {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 6, 0);
    addEdge(graph, 6, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 6, 4);
    addEdge(graph, 2, 0);
    addEdge(graph, 0, 1);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 5);
    addEdge(graph, 1, 2);
    addEdge(graph, 7, 3);
    addEdge(graph, 5, 0);

    TarjanSccAdjacencyList solver = new TarjanSccAdjacencyList(graph);

    int[] sccs = solver.getSccs();
    Map<Integer, List<Integer>> multimap = new HashMap<>();
    for (int i = 0; i < n; i++)
    {
      if (!multimap.containsKey(sccs[i])) multimap.put(sccs[i], new ArrayList<>());
      multimap.get(sccs[i]).add(i);
    }

    // Prints:
    // Number of Strongly Connected Components: 3
    // Nodes: [0, 1, 2] form a Strongly Connected Component.
    // Nodes: [3, 7] form a Strongly Connected Component.
    // Nodes: [4, 5, 6] form a Strongly Connected Component.
    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
    for (List<Integer> scc : multimap.values())
    {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
    }
  }
}
