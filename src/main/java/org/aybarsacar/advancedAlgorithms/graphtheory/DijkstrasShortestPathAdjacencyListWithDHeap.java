package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.*;

/**
 * Implementation of Dijkstra's Shortest Path Algorithm from a starting node to a specified node
 * Optimisation by using a D-ary Heap instead of a Priority Queue to store the node index, cost pairs
 * so the algorithm is more efficient we just update the value in the Heap
 * because it is an indexed Heap
 */
public class DijkstrasShortestPathAdjacencyListWithDHeap
{
  /**
   * Helper Class to represents the edges in a graph
   * it is a directed edge
   * the node which this edge comes from will be implicitly represented in our Adjacency List
   */
  static class Edge
  {
    int to, cost;

    public Edge(int to, int cost)
    {
      this.to = to;
      this.cost = cost;
    }
  }

  //  number of nodes
  private final int n;

  private int edgeCount;
  private double[] dist;
  private Integer[] prev;
  private List<List<Edge>> graph;

  /**
   * initialise the solver by providing the graph size and a starting node
   * this initialises the adjacency list with createEmptyGraph
   *
   * @param n - number of nodes in the graph
   */
  public DijkstrasShortestPathAdjacencyListWithDHeap(int n)
  {
    this.n = n;
    createEmptyGraph();
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void createEmptyGraph()
  {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
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
    if (cost < 0) throw new IllegalArgumentException("Cost cannot be a negative value");

    edgeCount++;
    graph.get(from).add(new Edge(to, cost));
  }

  public List<List<Edge>> getGraph()
  {
    return graph;
  }

  /**
   * Runs Dijkstra's Algorithm o a directed graph to find the shortest path
   *
   * @param start - index of starting node
   * @param end   - index of ending node
   * @return - the shortest path cost if exists or returns Double.POSITIVE_INFINITY
   */
  public double dijkstra(int start, int end)
  {
//    Keep an Indexed Priority Queue (ipq) of the next promising node to visit
    int degree = edgeCount / n;
    MinIndexedDHeap<Double> ipq = new MinIndexedDHeap<>(degree, n);
    ipq.insert(start, 0.0);

//    maintain an array of the min distance to each node
    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0.0;

//    store the visited nodes in an array
    boolean[] visited = new boolean[n];

//    store previous nodes in an array to reconstruct the path later on
    prev = new Integer[n];

    while (!ipq.isEmpty())
    {
//      get the id of the node with the shortest distance
      int nodeId = ipq.peekMinKeyIndex();

      visited[nodeId] = true;
      double minValue = ipq.pollMinValue();

//      the min value we get from the ipq is greater than the one in the distance array
//      we already found a better path before we got to processing this node so ignore it
      if (minValue > dist[nodeId]) continue;

//      get all the edges coming out of the current node
      for (Edge edge : graph.get(nodeId))
      {
//        we cannot get a shorter path by revisiting a node we already visited before
        if (visited[edge.to]) continue;

//        relax edge by updating the minimum cost if applicatble
        double newDistance = dist[nodeId] + edge.cost;
        if (newDistance < dist[edge.to])
        {
          prev[edge.to] = nodeId;
          dist[edge.to] = newDistance;

          if (ipq.contains(edge.to))
          {
//          if it exists in the indexed priority queue update the value
            ipq.decrease(edge.to, newDistance);
          }
          else
          {
//            insert it
            ipq.increase(edge.to, newDistance);
          }
        }
      }
//      shortest path found
      if (nodeId == end) return dist[end];
    }

//    path does not exist
    return Double.POSITIVE_INFINITY;
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
    if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid ending node index");
    if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid starting node index");

    List<Integer> path = new ArrayList<>();
    double dist = dijkstra(start, end);

    if (dist == Double.POSITIVE_INFINITY) return path;

    for (Integer at = end; at != null; at = prev[at])
    {
      path.add(at);
    }

    Collections.reverse(path);
    return path;
  }

  /**
   * indexed D-ary Heap implementation
   * where min value is at the root
   * <p>
   * this allows us to avoid duplicate values in our priority queue
   *
   * @param <T>
   */
  private static class MinIndexedDHeap<T extends Comparable<T>>
  {
    // number of elements in the heap
    private int sz;

    //    Maximum number of elements in the heap
    private final int N;

    //    the degree of evey node in the heap
    private final int D;

    //    Lookup array s to track teh child/parent indexes of each node
    private final int[] child, parent;

    //    THe Position Map (pm) maps Key Indexes (ki) to where the position of that
//    key is represented in the priority queue in the domain [0, sz)
    public final int[] pm;

    //    the Inverse Map (im) stores the indexes of the keys in the range
//    [0, sz) which make up the priority queue. it should be noted that
//    'im' and 'pm' are inverses of each other, so pm[im[i]] = im[pm[i]] = i
    public final int[] im;

    //    the values associated wiht the keys. it is very important to note
//    that this array is indexed by the key indexes (ki)
    public final Object[] values;

    /**
     * Constructor
     * Initialises a D-ary Heap with a maximum capacity of maxSize
     */
    public MinIndexedDHeap(int degree, int maxSize)
    {
      if (maxSize <= 0) throw new IllegalArgumentException("maxSize has to be greater than 0");

      D = Math.max(2, degree);
      N = Math.max(D + 1, maxSize);

      im = new int[N];
      pm = new int[N];
      child = new int[N];
      parent = new int[N];
      values = new Object[N];

      for (int i = 0; i < N; i++)
      {
        parent[i] = (i - 1) / D;
        child[i] = i * D + 1;
        pm[i] = im[i] = -1;
      }
    }

    public int size()
    {
      return sz;
    }

    public boolean isEmpty()
    {
      return sz == 0;
    }

    public boolean contains(int ki)
    {
      keyInBoundsOrThrow(ki);
      return pm[ki] != -1;
    }

    public int peekMinKeyIndex()
    {
      isNotEmptyOrThrow();
      return im[0];
    }

    public int pollMinKeyIndex()
    {
      int minki = peekMinKeyIndex();
      delete(minki);
      return minki;
    }

    @SuppressWarnings("unchecked")
    public T peekMinValue()
    {
      isNotEmptyOrThrow();
      return (T) values[im[0]];
    }

    public T pollMinValue()
    {
      T minValue = peekMinValue();
      delete(peekMinKeyIndex());
      return minValue;
    }

    public void insert(int ki, T value)
    {
      if (contains(ki)) throw new IllegalArgumentException("index already exists; received: " + ki);
      valueNotNullOrThrow(value);

      pm[ki] = sz;
      im[sz] = ki;
      values[ki] = value;
      swim(sz++);
    }

    @SuppressWarnings("unchecked")
    public T valueOf(int ki)
    {
      keyExistsOrThrow(ki);
      return (T) values[ki];
    }

    @SuppressWarnings("unchecked")
    public T delete(int ki)
    {
      keyExistsOrThrow(ki);
      final int i = pm[ki];
      swap(i, --sz);
      sink(i);
      swim(i);
      T value = (T) values[ki];
      values[ki] = null;
      pm[ki] = -1;
      im[sz] = -1;
      return value;
    }

    @SuppressWarnings("unchecked")
    public T update(int ki, T value)
    {
      keyExistsAndValueNotNullOrThrow(ki, value);
      final int i = pm[ki];
      T oldValue = (T) values[ki];
      values[ki] = value;
      sink(i);
      swim(i);
      return oldValue;
    }

    // Strictly decreases the value associated with 'ki' to 'value'
    public void decrease(int ki, T value)
    {
      keyExistsAndValueNotNullOrThrow(ki, value);
      if (less(value, values[ki]))
      {
        values[ki] = value;
        swim(pm[ki]);
      }
    }

    // Strictly increases the value associated with 'ki' to 'value'
    public void increase(int ki, T value)
    {
      keyExistsAndValueNotNullOrThrow(ki, value);
      if (less(values[ki], value))
      {
        values[ki] = value;
        sink(pm[ki]);
      }
    }

    /* Helper functions */

    private void sink(int i)
    {
      for (int j = minChild(i); j != -1; )
      {
        swap(i, j);
        i = j;
        j = minChild(i);
      }
    }

    private void swim(int i)
    {
      while (less(i, parent[i]))
      {
        swap(i, parent[i]);
        i = parent[i];
      }
    }

    // From the parent node at index i find the minimum child below it
    private int minChild(int i)
    {
      int index = -1, from = child[i], to = Math.min(sz, from + D);
      for (int j = from; j < to; j++) if (less(j, i)) index = i = j;
      return index;
    }

    private void swap(int i, int j)
    {
      pm[im[j]] = i;
      pm[im[i]] = j;
      int tmp = im[i];
      im[i] = im[j];
      im[j] = tmp;
    }

    // Tests if the value of node i < node j
    @SuppressWarnings("unchecked")
    private boolean less(int i, int j)
    {
      return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
    }

    @SuppressWarnings("unchecked")
    private boolean less(Object obj1, Object obj2)
    {
      return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
    }

    @Override
    public String toString()
    {
      List<Integer> lst = new ArrayList<>(sz);
      for (int i = 0; i < sz; i++) lst.add(im[i]);
      return lst.toString();
    }

    /* Helper functions to make the code more readable. */

    private void isNotEmptyOrThrow()
    {
      if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
    }

    private void keyExistsAndValueNotNullOrThrow(int ki, Object value)
    {
      keyExistsOrThrow(ki);
      valueNotNullOrThrow(value);
    }

    private void keyExistsOrThrow(int ki)
    {
      if (!contains(ki)) throw new NoSuchElementException("Index does not exist; received: " + ki);
    }

    private void valueNotNullOrThrow(Object value)
    {
      if (value == null) throw new IllegalArgumentException("value cannot be null");
    }

    private void keyInBoundsOrThrow(int ki)
    {
      if (ki < 0 || ki >= N)
        throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
    }
  }
}
