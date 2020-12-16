package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Lazy implementation of the Prim's Algorithm to find the Minimum Spanning Tree in a graph
 * where the edges in the graph is represented as an Adjacency List
 * Priority Queue to query the next best edge, each poll operation is O(log(E))
 * <p>
 * Time Complexity: O(E * log(E)) where E is the number of edges in the graph
 */
public class LazyPrims
{
  /**
   * Represents the Edges in the graph
   */
  static class Edge implements Comparable<Edge>
  {
    int from, to, cost;

    public Edge(int from, int to, int cost)
    {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }

    @Override
    public int compareTo(Edge o)
    {
      return this.cost - o.cost;
    }
  }

  private final int n; // size of the graph
  private final List<List<Edge>> graph;

  private boolean solved;
  private boolean mstExists;
  private boolean[] visited;
  private PriorityQueue<Edge> pq;

  private long minCostSum; // minimum cost of the spanning tree
  private Edge[] mstEdges; // the mst path

  public LazyPrims(List<List<Edge>> graph)
  {
    if (graph == null || graph.isEmpty()) throw new IllegalArgumentException("Graph cannot be null or empty");
    this.n = graph.size();
    this.graph = graph;
  }

  /**
   * returns the edges used in finding the mst
   * or returns null if no mst exists
   *
   * @return
   */
  public Edge[] getMst()
  {
    if (!solved) solve();
    return mstExists ? mstEdges : null;
  }

  public Long getMstCost()
  {
    if (!solved) solve();
    return mstExists ? minCostSum : null;
  }

  /**
   * @param nodeIndex
   */
  private void addEdges(int nodeIndex)
  {
    visited[nodeIndex] = true;

//    get the edge List of the node
    List<Edge> edges = graph.get(nodeIndex);
    for (Edge edge : edges)
    {
      if (!visited[edge.to])
      {
        pq.offer(edge);
      }
    }
  }

  /**
   *
   */
  private void solve()
  {
    if (solved) return;
    solved = true;

    int m = n - 1, edgeCount = 0;
    pq = new PriorityQueue<>();
    visited = new boolean[n];
    mstEdges = new Edge[m];

//    add initial set of edges to the priority queue starting at node 0
    addEdges(0);

//    loop while the MST is not complete
    while (!pq.isEmpty() && edgeCount != m)
    {
//      get the next promising edge from the priority queue
      Edge edge = pq.poll();
      int nodeIndex = edge.to;

//      Skip any edge pointing to an already visited node
      if (visited[nodeIndex]) continue;

      mstEdges[edgeCount++] = edge;
      minCostSum += edge.cost;

//      at the node to the pq and move to the next node in the next iteration
      addEdges(nodeIndex);
    }

//    check if MST spans the entire graph
    mstExists = (edgeCount == m);
  }


  /* Graph construction helpers. */

  static List<List<Edge>> createEmptyGraph(int n)
  {
    List<List<Edge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++) g.add(new ArrayList<>());
    return g;
  }

  static void addDirectedEdge(List<List<Edge>> g, int from, int to, int cost)
  {
    g.get(from).add(new Edge(from, to, cost));
  }

  static void addUndirectedEdge(List<List<Edge>> g, int from, int to, int cost)
  {
    addDirectedEdge(g, from, to, cost);
    addDirectedEdge(g, to, from, cost);
  }

  /* Example usage. */

  public static void main(String[] args)
  {
    example1();
    firstGraphFromSlides();
    squareGraphFromSlides();
    lazyPrimsDemoFromSlides();
  }

  private static void example1()
  {
    int n = 10;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 5);
    addUndirectedEdge(g, 1, 2, 4);
    addUndirectedEdge(g, 2, 9, 2);
    addUndirectedEdge(g, 0, 4, 1);
    addUndirectedEdge(g, 0, 3, 4);
    addUndirectedEdge(g, 1, 3, 2);
    addUndirectedEdge(g, 2, 7, 4);
    addUndirectedEdge(g, 2, 8, 1);
    addUndirectedEdge(g, 9, 8, 0);
    addUndirectedEdge(g, 4, 5, 1);
    addUndirectedEdge(g, 5, 6, 7);
    addUndirectedEdge(g, 6, 8, 4);
    addUndirectedEdge(g, 4, 3, 2);
    addUndirectedEdge(g, 5, 3, 5);
    addUndirectedEdge(g, 3, 6, 11);
    addUndirectedEdge(g, 6, 7, 1);
    addUndirectedEdge(g, 3, 7, 2);
    addUndirectedEdge(g, 7, 8, 6);

    LazyPrims solver = new LazyPrims(g);
    Long cost = solver.getMstCost();

    if (cost == null)
    {
      System.out.println("No MST does not exists");
    }
    else
    {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst())
      {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }

    // Output:
    // MST cost: 14
    // from: 0, to: 4, cost: 1
    // from: 4, to: 5, cost: 1
    // from: 4, to: 3, cost: 2
    // from: 3, to: 1, cost: 2
    // from: 3, to: 7, cost: 2
    // from: 7, to: 6, cost: 1
    // from: 6, to: 8, cost: 4
    // from: 8, to: 9, cost: 0
    // from: 8, to: 2, cost: 1
  }

  private static void firstGraphFromSlides()
  {
    int n = 7;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 9);
    addUndirectedEdge(g, 0, 2, 0);
    addUndirectedEdge(g, 0, 3, 5);
    addUndirectedEdge(g, 0, 5, 7);
    addUndirectedEdge(g, 1, 3, -2);
    addUndirectedEdge(g, 1, 4, 3);
    addUndirectedEdge(g, 1, 6, 4);
    addUndirectedEdge(g, 2, 5, 6);
    addUndirectedEdge(g, 3, 5, 2);
    addUndirectedEdge(g, 3, 6, 3);
    addUndirectedEdge(g, 4, 6, 6);
    addUndirectedEdge(g, 5, 6, 1);

    LazyPrims solver = new LazyPrims(g);
    Long cost = solver.getMstCost();

    if (cost == null)
    {
      System.out.println("No MST does not exists");
    }
    else
    {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst())
      {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }
  }

  private static void squareGraphFromSlides()
  {
    int n = 9;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 6);
    addUndirectedEdge(g, 0, 3, 3);
    addUndirectedEdge(g, 1, 2, 4);
    addUndirectedEdge(g, 1, 4, 2);
    addUndirectedEdge(g, 2, 5, 12);
    addUndirectedEdge(g, 3, 4, 1);
    addUndirectedEdge(g, 3, 6, 8);
    addUndirectedEdge(g, 4, 5, 7);
    addUndirectedEdge(g, 4, 7, 9);
    addUndirectedEdge(g, 5, 8, 10);
    addUndirectedEdge(g, 6, 7, 11);
    addUndirectedEdge(g, 7, 8, 5);

    LazyPrims solver = new LazyPrims(g);
    Long cost = solver.getMstCost();

    if (cost == null)
    {
      System.out.println("No MST does not exists");
    }
    else
    {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst())
      {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }
  }

  private static void lazyPrimsDemoFromSlides()
  {
    int n = 8;
    List<List<Edge>> g = createEmptyGraph(n);

    addDirectedEdge(g, 0, 1, 10);
    addDirectedEdge(g, 0, 2, 1);
    addDirectedEdge(g, 0, 3, 4);

    addDirectedEdge(g, 2, 1, 3);
    addDirectedEdge(g, 2, 5, 8);
    addDirectedEdge(g, 2, 3, 2);
    addDirectedEdge(g, 2, 0, 1);

    addDirectedEdge(g, 3, 2, 2);
    addDirectedEdge(g, 3, 5, 2);
    addDirectedEdge(g, 3, 6, 7);
    addDirectedEdge(g, 3, 0, 4);

    addDirectedEdge(g, 5, 2, 8);
    addDirectedEdge(g, 5, 4, 1);
    addDirectedEdge(g, 5, 7, 9);
    addDirectedEdge(g, 5, 6, 6);
    addDirectedEdge(g, 5, 3, 2);

    addDirectedEdge(g, 4, 1, 0);
    addDirectedEdge(g, 4, 5, 1);
    addDirectedEdge(g, 4, 7, 8);

    addDirectedEdge(g, 1, 0, 10);
    addDirectedEdge(g, 1, 2, 3);
    addDirectedEdge(g, 1, 4, 0);

    addDirectedEdge(g, 6, 3, 7);
    addDirectedEdge(g, 6, 5, 6);
    addDirectedEdge(g, 6, 7, 12);

    addDirectedEdge(g, 7, 4, 8);
    addDirectedEdge(g, 7, 5, 9);
    addDirectedEdge(g, 7, 6, 12);

    LazyPrims solver = new LazyPrims(g);
    Long cost = solver.getMstCost();

    if (cost == null)
    {
      System.out.println("No MST does not exists");
    }
    else
    {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst())
      {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }
  }
}
