package org.aybarsacar.advancedAlgorithms.graphtheory.networkflow;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Edmonds Karps algorithm to find the augmenting paths
 * uses Breadth First Search
 * Time complexity is independent of the edge capacities -> O(V * E ^ 2)
 * where V is the number of vertices and E is the number of edges
 */
public class EdmondsKarpSolver extends NetworkSolverBase
{
  /**
   * Creates an instance of a flow network solver
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public EdmondsKarpSolver(int n, int s, int t)
  {
    super(n, s, t);
  }

  /**
   * Runs Edmond-Karp and computes the max flow from source to the sink
   * until the flow == 0 which means the graph is fully saturated
   * and no more augmenting paths can be found
   */
  @Override
  public void solve()
  {
    long flow;
    do
    {
      markAllNodesAsUnvisited();
      flow = bfs();
      maxFlow += flow; // sum over all the bottleneck values to get the maxFlow
    } while (flow != 0);

    for (int i = 0; i < n; i++)
      if (visited(i)) minCut[i] = true;
  }

  /**
   * Perfoms a breath first search from source to sink
   *
   * @return
   */
  private long bfs()
  {
    Edge[] prev = new Edge[n];

//    The queue can be optimised to use faster queue
//    initialise a queue
    Queue<Integer> q = new ArrayDeque<>(n);
    visit(s);     // visit the source node
    q.offer(s);   // add it to the queue

//    Perform BFS from source to sink
    while (!q.isEmpty())
    {
      int node = q.poll();
      if (node == t) break;

      for (Edge edge : graph[node])
      {
        long cap = edge.remainingCapacity();
        if (cap > 0 && !visited(edge.to))
        {
          visit(edge.to);         // visit the node
          prev[edge.to] = edge;   // track where it came from
          q.offer(edge.to);
        }
      }
    }

//    Sink not reachable
    if (prev[t] == null) return 0;

//    Find the augmented path and bottle neck
    long bottleNeck = Long.MAX_VALUE;

//    Find augmented path and bottleneck
    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
      bottleNeck = Math.min(bottleNeck, edge.remainingCapacity());

//      Retrace augmented path and update the flow values
    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
      edge.augment(bottleNeck);

    return bottleNeck;
  }


  /* Example */

  public static void main(String[] args)
  {
    testSmallFlowGraph();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph()
  {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    EdmondsKarpSolver solver;
    solver = new EdmondsKarpSolver(n, s, t);

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
}
