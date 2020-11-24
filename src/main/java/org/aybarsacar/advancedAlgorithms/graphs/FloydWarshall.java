package org.aybarsacar.advancedAlgorithms.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * implementation of the Floyd-Warshall algorithm
 * to find all pairs of shortest paths between nodes in a graph
 * negative cycles are allowed
 * Graph is represented as an adjacency matrix double[][]
 * <p>
 * Time Complexity: O(V^3)
 */
public class FloydWarshall
{
  //  to identify when we reach negative cycles
  private static final int REACHES_NEGATIVE_CYCLE = -1;

  private int n; // number of nodes
  private boolean solved; // tracks whether it is solver or not
  private double[][] dp; // distances
  private Integer[][] next; // used to reconstruct the path

  /**
   * Constructor
   * <p>
   * As input, this class takes an adjacency matrix with edge weights between nodes,
   * where POSITIVE_INFINITY is used to indicate that two nodes are not connected.
   * cost to go from a node is itself is assumed to be 0
   *
   * @param matrix - graph represented as an adjacency matrix
   */
  public FloydWarshall(double[][] matrix)
  {
    n = matrix.length;
    dp = new double[n][n];
    next = new Integer[n][n];

//    Copy input matrix and setup 'next' matrix for path reconstruction
    for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
      {
        if (matrix[i][j] != Double.POSITIVE_INFINITY) next[i][j] = j;
        dp[i][j] = matrix[i][j];
      }
    }
  }

  /**
   * Runs Floyd-Warshall to compute the shortest distance between every pair of nodes
   *
   * @return The solved All Pairs Shortest Path (APSP) matrix
   */
  public double[][] getApspMatrix()
  {
    if (!solved) solve();
    return dp;
  }

  /**
   * Executes the Floyd-Warshall Algorithm
   */
  public void solve()
  {
    if (solved) return;

//    Compute all pairs of shortest paths
    for (int k = 0; k < n; k++)
    {
      for (int i = 0; i < n; i++)
      {
        for (int j = 0; j < n; j++)
        {
          if (dp[i][k] + dp[k][j] < dp[i][j])
          {
//            assign if detouring through k to reach from i to j is shorter
//            i.e i -> j is 10 but i -> k is 2 and k -> j is 3 so take the path i -> k -> j
            dp[i][j] = dp[i][k] + dp[k][j];
            next[i][j] = next[i][k];
          }
        }
      }
    }

//    Identify negative cycles by propagating the value Double.NEGATIVE_INFINITY
//    to every edge that is part of or reaches into a negative cycle
    for (int k = 0; k < n; k++)
    {
      for (int i = 0; i < n; i++)
      {
        for (int j = 0; j < n; j++)
        {
          if (dp[i][k] + dp[k][j] < dp[i][j])
          {
            dp[i][j] = Double.NEGATIVE_INFINITY;
            next[i][j] = REACHES_NEGATIVE_CYCLE;
          }
        }
      }
    }
    solved = true;
  }

  /**
   * @param start - start index
   * @param end   - end index
   * @return - a list of indices from start the end which construct the shortest path
   */
  public List<Integer> reconstructShortestPath(int start, int end)
  {
    if (!solved) solve();

    List<Integer> path = new ArrayList<>();

//    no path exists
    if (dp[start][end] == Double.POSITIVE_INFINITY) return path;

    int current = start;
    for (; current != end; current = next[current][end])
    {
//      return null since there are an infinite number of shortest paths
      if (current == REACHES_NEGATIVE_CYCLE) return null;

      path.add(current);
    }

//   return null since there are an infinite number of shortest paths
    if (next[current][end] == REACHES_NEGATIVE_CYCLE) return null;

    path.add(end);
    return path;
  }

  /**
   * Helper method that initialises a n*n matrix
   * diagonal is filled with 0's and the rest with infinity
   *
   * @param n - size
   * @return - the graph represented as an adjacency matrix
   */
  public static double[][] createGraph(int n)
  {
    double[][] matrix = new double[n][n];

    for (int i = 0; i < n; i++)
    {
      Arrays.fill(matrix[i], Double.POSITIVE_INFINITY);
      matrix[i][i] = 0;
    }

    return matrix;
  }
}
