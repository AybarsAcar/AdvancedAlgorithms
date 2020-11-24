package org.aybarsacar.advancedAlgorithms.graphs;

import java.util.*;

/**
 * Implementation of Dijkstra's Shortest Path Algorithm from a starting node to a specified node
 * so the algorithm breaks early when the target node is reached for optimisation
 */
public class DijkstrasShortestPathAdjacencyList
{
  //  Small epsilon value to compare double values
  private static final double EPS = 1e-6;

  /**
   * Helper Class to represents the edges in a graph
   * it is a directed edge
   */
  static class Edge
  {
    int from, to, cost;

    public Edge(int from, int to, int cost)
    {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  /**
   * Helper Class to represents the Nodes in a graph
   * to track the nodes to visit while running Dijkstra's
   */
  static class Node
  {
    int id;
    double value;

    public Node(int id, double value)
    {
      this.id = id;
      this.value = value;
    }
  }

  private int n;
  private double[] dist; // distance array
  private Integer[] prev;
  private List<List<Edge>> graph;

  /**
   * Update the comparator on the node class
   * used in Dijkstra's where we pick the smallest value
   */
  private Comparator<Node> comparator = new Comparator<Node>()
  {
    @Override
    public int compare(Node node1, Node node2)
    {
      if (Math.abs(node1.value - node2.value) < EPS) return 0;
      return (node1.value - node2.value) > 0 ? +1 : -1;
    }
  };

  /**
   * initialise the solver by providing the graph size and a starting node
   *
   * @param n
   */
  public DijkstrasShortestPathAdjacencyList(int n)
  {
    this.n = n;
    createEmptyGraph();
  }

  public DijkstrasShortestPathAdjacencyList(int n, Comparator<Node> comparator)
  {
    this(n);
    if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null");
    this.comparator = comparator;
  }

  /**
   * Adds a directed edge to the graph
   *
   * @param from - the index of the node the directed edge starts at
   * @param to   - the index of the node the directed edge ends at
   * @param cost - the cost (weight) of the edge
   */
  public void addEdge(int from, int to, int cost)
  {
    graph.get(from).add(new Edge(from, to, cost));
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void createEmptyGraph()
  {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }

  public List<List<Edge>> getGraph()
  {
    return graph;
  }

  /**
   * Reconstruct the shortest path (of nodes) from start to end inclusive
   *
   * @param start - index of the starting node
   * @param end   - index of the target node
   * @return - a List of integer values which are the node indexes in the path
   */
  public List<Integer> reconstructPath(int start, int end)
  {
    if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
    if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");

    double dist = dijkstra(start, end);

    List<Integer> path = new ArrayList<>();

    if (dist == Double.POSITIVE_INFINITY) return path;

    for (Integer at = end; at != null; at = prev[at])
    {
      path.add(at);
    }

    Collections.reverse(path);
    return path;
  }

  /**
   * Runs the Dijkstra's algorithm on a directed graph to fin the shortest path
   * from a starting to the end node
   *
   * @param start - start index
   * @param end   - target index
   * @return - the shortest path or if no path exists returns Double.POSITIVE_INFINITY
   */
  public double dijkstra(int start, int end)
  {
//    Maintain an array of the minimum distance to each node
    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

//    Keep a PriorityQueue of the next most promising node to visit
    PriorityQueue<Node> pq = new PriorityQueue<>(2 * n, comparator);
    pq.offer(new Node(start, 0));

//    Array used to track which nodes have already been visited
    boolean[] visited = new boolean[n];
//    previous node to reconstruct the shortest path
    prev = new Integer[n];

    while (!pq.isEmpty())
    {
      Node node = pq.poll();
      visited[node.id] = true;

//      we already found a better path before we got to processing the node
//      ie Start -> A takes longer than Start -> B -> A
      if (dist[node.id] < node.value) continue;

      List<Edge> edges = graph.get(node.id);

      for (Edge edge : edges)
      {
        if (visited[edge.to]) continue;

        double newDistance = dist[edge.from] + edge.cost;
        if (newDistance < dist[edge.to])
        {
          prev[edge.to] = edge.from;
          dist[edge.to] = newDistance;
          pq.offer(new Node(edge.to, dist[edge.to]));
        }
      }

//      we can return early since Dijkstra's assumes only non-negative path weights
//      and its a greedy algorithm
      if (node.id == end) return dist[end];
    }

//    no path exists;
    return Double.POSITIVE_INFINITY;
  }
}
