package org.aybarsacar.advancedAlgorithms.graphtheory.networkflow;

import java.util.ArrayList;
import java.util.List;

/**
 * initialises the solver for any network problem
 */
public abstract class NetworkSolverBase
{
  //  to avoid overflow, set infinity to a value less than Long.MAX_VALUE
  protected static final long INF = Long.MAX_VALUE / 2;

  public static class Edge
  {
    public int from, to;
    public Edge residual; // the edge that goes in the opposite direction
    public long flow, cost;
    public final long capacity, originalCost;

    public Edge(int from, int to, long capacity)
    {
      this(from, to, 0, capacity);
    }

    public Edge(int from, int to, long cost, long capacity)
    {
      this.from = from;
      this.to = to;
      this.originalCost = this.cost = cost;
      this.capacity = capacity;
    }

    public boolean isResidual()
    {
      return capacity == 0;
    }

    /**
     * maximum amount of flow we can push through this edge
     *
     * @return
     */
    public long remainingCapacity()
    {
      return capacity - flow;
    }

    /**
     * augments the method for this edge alone
     *
     * @param bottleNeck
     */
    public void augment(long bottleNeck)
    {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
    }

    public String toString(int s, int t)
    {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));

      return String.format(
          "Edge %s -> %s | flow = %d | capacity = %d | is residual; %s",
          u, v, flow, capacity, isResidual()
      );
    }
  }

  //  inputs: n = number of nodes, s = source, t = sink
  protected final int n, s, t;

  protected long maxFlow;
  protected long minCost;

  protected boolean[] minCut;
  protected List<Edge>[] graph;

  /**
   * 'visited' and 'visitedToken' are variables used for graph sub-routines to
   * track whether a node has been visited or not. In particular, node 'i' was
   * recently visited if visited[i] == visitedToken is true. This is handy because
   * to mark all nodes as unvisited simply increment the visitedToken
   * <p>
   * Otherwise, it may result in a cycle
   */
  private int visitedToken = 1;
  private int[] visited;

  //  indicates whether the algorithm ran before
  private boolean solved;

  //  method to implement which solves the network flow problem
  public abstract void solve();

  /**
   * Creates an instance of a flow network solver
   *
   * @param n - The number of nodes in the graph including source and sink nodes
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public NetworkSolverBase(int n, int s, int t)
  {
    this.n = n;
    this.s = s;
    this.t = t;

    initializeGraph();
    minCut = new boolean[n];
    visited = new int[n];
  }

  @SuppressWarnings("unchecked")
  private void initializeGraph()
  {
    graph = new List[n];
    for (int i = 0; i < n; i++)
      graph[i] = new ArrayList<>();
  }

  /**
   * Adds a directed edge and a residual edge to the flow graph
   *
   * @param from     - The index of the node the directed edge stars at
   * @param to       - The index of hte node directed edge ends at.
   * @param capacity - The capacity of the edge
   */
  public void addEdge(int from, int to, long capacity)
  {
    if (capacity <= 0) throw new IllegalArgumentException("Capacity must be greater than 0");

    Edge e1 = new Edge(from, to, capacity);
    Edge e2 = new Edge(to, from, 0); // create the corresponding residual edge

    e1.residual = e2;
    e2.residual = e1;

    graph[from].add(e1);
    graph[to].add(e2);
  }

  /**
   * Cost variant of {@link #addEdge(int, int, long)} for min-cost max-flow
   */
  public void addEdge(int from, int to, long capacity, long cost)
  {
    if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be less than 0");

    Edge e1 = new Edge(from, to, capacity, cost);
    Edge e2 = new Edge(to, from, 0, -cost);

    e1.residual = e2;
    e2.residual = e1;

    graph[from].add(e1);
    graph[to].add(e2);
  }

  /**
   * marks node 'i' as visited
   *
   * @param i
   */
  public void visit(int i)
  {
    visited[i] = visitedToken;
  }

  /**
   * returns whether or not node 'i' has been visited
   *
   * @param i
   * @return
   */
  public boolean visited(int i)
  {
    return visited[i] == visitedToken;
  }

  /**
   * Resets all nodes as unvisited.
   * This is especially useful to do between iterations of finding augmenting paths, O(1)
   */
  public void markAllNodesAsUnvisited()
  {
    visitedToken++;
  }

  /**
   * @return the maximum flow from the source to the sink
   */
  public long getMaxFlow()
  {
    execute();
    return maxFlow;
  }

  /**
   * @return the min cost from source to the sink
   * NOTE: This method only applies to min-cost max-flow algorithms
   */
  public long getMinCost()
  {
    execute();
    return minCost;
  }

  /**
   * Returns the graph after the solver has been executed. This allow you to inspect the {@link
   * Edge#flow} compared to the {@link Edge#capacity} in each edge. This is useful if you want to
   * figure out which edges were used during the max flow.
   */
  public List<Edge>[] getGraph()
  {
    execute();
    return graph;
  }

  private void execute()
  {
    if (solved) return;
    solved = true;
    solve();
  }
}
