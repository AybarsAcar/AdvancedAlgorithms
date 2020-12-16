package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of finding an Eulerian Path on a Directed Graph
 * where the edges are represented as an Adjacency List
 * Also verifies that the input graph is fully connected and supports self loops and
 * repeated edges between nodes
 */
public class EulerianPathDirectedEdges
{
  private final int n; // size of the graph
  private int edgeCount;
  private int[] in, out; // track in and out degree of each node
  private LinkedList<Integer> path; // Eulerian Path Solution
  private final List<List<Integer>> graph;

  /**
   * pass in a directed graph
   *
   * @param graph
   */
  public EulerianPathDirectedEdges(List<List<Integer>> graph)
  {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");

    n = graph.size();
    this.graph = graph;
    path = new LinkedList<>();
  }

  /**
   * Returns a list of edgeCount + 1 node ids that give the Eulearian Path or
   * null if no path exists or the graph is disconnected
   *
   * @return
   */
  public int[] getEulerianPath()
  {
    setUp();

    if (!graphHasEulerianPath()) return null;

    dfs(findStartNode());

//    Make sure all the edges of the graph is traversed
//    it could be a case that the graph is disconnected
    if (path.size() != edgeCount + 1) return null;

    int[] solution = new int[edgeCount + 1];
    for (int i = 0; !path.isEmpty(); i++)
    {
      solution[i] = path.removeFirst();
    }
    return solution;
  }

  /**
   * loop through the edges and increment the in and out degrees
   * and calculate the number of edges in a graph
   */
  private void setUp()
  {
//    Arrays that track the in degree and out degree of each node
    in = new int[n];
    out = new int[n];

    edgeCount = 0;

//    compute the in and out degrees
    for (int from = 0; from < n; from++)
    {
      for (int to : graph.get(from))
      {
        in[to]++;
        out[from]++;
        edgeCount++;
      }
    }
  }

  /**
   * checks the conditions for a graph to have a Eulerian Path
   * returns true if there is a one end and one start node
   * returns true if there is no specific end and start node -> Eulerian Circuit
   *
   * @return
   */
  private boolean graphHasEulerianPath()
  {
    if (edgeCount == 0) return false;

//    counters
    int startNodes = 0, endNodes = 0;
    for (int i = 0; i < n; i++)
    {
      if (out[i] - in[i] > 1 || in[i] - out[i] > 1) return false;
      else if (out[i] - in[i] == 1) startNodes++;
      else if (in[i] - out[i] == 1) endNodes++;
    }

    return (endNodes == 0 && startNodes == 0) || (endNodes == 1 && startNodes == 1);
  }

  private int findStartNode()
  {
    int start = 0;
    for (int i = 0; i < n; i++)
    {
//      unique starting node
      if (out[i] - in[i] == 1) return i;
//      start at a node with an outgoing edge
      if (out[i] > 0) start = i;
    }

    return start;
  }

  /**
   * Perform modified DFS to find Eulerain path
   * Backtracks to visit each edge
   *
   * @param at - the current node
   */
  private void dfs(int at)
  {
//    while the current node has visited edges
//    we select the next node to explore and call df recursively
    while (out[at] != 0)
    {
      out[at]--;
      int next = graph.get(at).get(out[at]);
      dfs(next);
    }
    path.addFirst(at);
  }

  /* Graph creation helper methods */

  public static List<List<Integer>> initializeEmptyGraph(int n)
  {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addDirectedEdge(List<List<Integer>> g, int from, int to)
  {
    g.get(from).add(to);
  }

  /* Examples */

  public static void main(String[] args)
  {
    exampleFromSlides();
    smallExample();
  }

  private static void exampleFromSlides()
  {
    int n = 7;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 2, 2);
    addDirectedEdge(graph, 2, 4);
    addDirectedEdge(graph, 2, 4);
    addDirectedEdge(graph, 3, 1);
    addDirectedEdge(graph, 3, 2);
    addDirectedEdge(graph, 3, 5);
    addDirectedEdge(graph, 4, 3);
    addDirectedEdge(graph, 4, 6);
    addDirectedEdge(graph, 5, 6);
    addDirectedEdge(graph, 6, 3);

    EulerianPathDirectedEdges solver;
    solver = new EulerianPathDirectedEdges(graph);

    // Outputs path: [1, 3, 5, 6, 3, 2, 4, 3, 1, 2, 2, 4, 6]
    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

  private static void smallExample()
  {
    int n = 5;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    addDirectedEdge(graph, 0, 1);
    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 1, 4);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 2, 1);
    addDirectedEdge(graph, 4, 1);

    EulerianPathDirectedEdges solver;
    solver = new EulerianPathDirectedEdges(graph);

    // Outputs path: [0, 1, 4, 1, 2, 1, 3]
    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }
}
