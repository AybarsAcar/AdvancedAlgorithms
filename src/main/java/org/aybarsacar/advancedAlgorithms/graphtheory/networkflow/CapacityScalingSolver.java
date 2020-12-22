package org.aybarsacar.advancedAlgorithms.graphtheory.networkflow;

import java.util.List;

/**
 * this implementation of the Capacity Scaling Solver uses DFS to find augmenting paths
 * the graph is represented as an Adjacency List
 * <p>
 * Time Complexity: O(E^2log(U)), where E = num edges, U = max capacity
 */
public class CapacityScalingSolver extends NetworkSolverBase
{
  //  threshold parameter whether an edge should be accepted or not based on the edge's remaining capacity
  private long delta;

  /**
   * Creates an instance of a flow network solver
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public CapacityScalingSolver(int n, int s, int t)
  {
    super(n, s, t);
  }

  /**
   * Adds a directed edge
   * overridden because we update the delta as we add edges
   *
   * @param from     - The index of the node the directed edge stars at
   * @param to       - The index of hte node directed edge ends at.
   * @param capacity - The capacity of the edge
   */
  @Override
  public void addEdge(int from, int to, long capacity)
  {
    super.addEdge(from, to, capacity);
    delta = Math.max(delta, capacity);
  }

  /**
   * Performs the Ford-Fulkerson method applying a dfs as a means of
   * finding an augmenting path
   */
  @Override
  public void solve()
  {
//    Start delta at the largest power of 2 <= the largest capacity
//    Equivalent of: delta = (long) pow(2, (int)floor(log(delta)/log(2)))
    delta = Long.highestOneBit(delta);

//    Repeatedly find augmenting paths from source to sink using only edges
//    with a remaining capacity >= delta. Half delta every time we become unable
//    to find an augmenting path from source to sink until the graph is saturated
    for (long f = 0; delta > 0; delta /= 2)
    {
      do
      {
        markAllNodesAsUnvisited();
        f = dfs(s, INF);
        maxFlow += f; // sum over the bottleneck values
      } while (f != 0);
    }

//    find min cut
    for (int i = 0; i < n; i++)
      if (visited(i)) minCut[i] = true;
  }

  /**
   * @param node - The current node
   * @param flow - Minimum flow found along the path so far
   * @return - The Bottleneck value
   */
  private long dfs(int node, long flow)
  {
//    At sink node, return augmenting path flow
    if (node == t) return flow;

    List<Edge> edges = graph[node];
    visit(node);

    for (Edge edge : edges)
    {
      long cap = edge.remainingCapacity();

//      extra check for delta for Capacity Scaling heuristic
//      test on the delta against the remaining capacity for the edge
      if (cap >= delta && !visited(edge.to))
      {
        long bottleNeck = dfs(edge.to, Math.min(flow, cap));

//        Augment the flow with bottleNeck value
        if (bottleNeck > 0)
        {
//          we have a valid augmenting path so augment the flow
          edge.augment(bottleNeck);
          return bottleNeck;
        }
      }
    }
    return 0;
  }

  /* Example */

  public static void main(String[] args)
  {
    testSmallFlowGraph();
    testExampleFromMySlides();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph()
  {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    CapacityScalingSolver solver;
    solver = new CapacityScalingSolver(n, s, t);

    // Source edges
    solver.addEdge(s, 0, 10);
    solver.addEdge(s, 1, 10);

    // Sink edges
    solver.addEdge(2, t, 10);
    solver.addEdge(3, t, 10);

    // Middle edges
    solver.addEdge(0, 1, 2);
    solver.addEdge(0, 2, 4);
    solver.addEdge(0, 3, 8);
    solver.addEdge(1, 3, 9);
    solver.addEdge(3, 2, 6);

    System.out.println(solver.getMaxFlow()); // 19
  }

  private static void testExampleFromMySlides()
  {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    CapacityScalingSolver solver;
    solver = new CapacityScalingSolver(n, s, t);

    // Source edges
    solver.addEdge(s, 0, 6);
    solver.addEdge(s, 1, 14);

    // Sink edges
    solver.addEdge(2, t, 11);
    solver.addEdge(3, t, 12);

    // Middle edges
    solver.addEdge(0, 1, 1);
    solver.addEdge(2, 3, 1);
    solver.addEdge(0, 2, 5);
    solver.addEdge(1, 2, 7);
    solver.addEdge(1, 3, 10);

    System.out.println(solver.getMaxFlow()); // 20
  }
}
