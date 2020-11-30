package org.aybarsacar.advancedAlgorithms.graphtheory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this TSP implementation class uses Dynamic Programming and has an iterative approach
 * to obtain a Time Complexity of O(n^2 * 2^n) and a Space Complexity of O(n * 2^n)
 * <p>
 * the Graph is represented as an Adjacency Matrix which is the distance matrix
 */
public class TravelingSalesmanProblem
{
  private final int N, start;
  private final double[][] distance;
  private List<Integer> tour = new ArrayList<>();
  private double minTourCost = Double.POSITIVE_INFINITY;
  private boolean solved = false;

  /**
   * overloaded constructor if called with one argument
   * it calls the other constructor with start initialised to 0
   *
   * @param distance graph
   */
  public TravelingSalesmanProblem(double[][] distance)
  {
    this(0, distance);
  }

  public TravelingSalesmanProblem(int start, double[][] distance)
  {
    this.N = distance.length;

    if (N <= 2) throw new IllegalStateException("N <= not yet supported");
    if (N != distance[0].length) throw new IllegalStateException("Matrix must be square (n x n)");
    if (start < 0 || start >= N) throw new IllegalArgumentException("Invalid start node.");
    if (N > 32) throw new IllegalArgumentException("Matrix size is too large, not enough computing power");

    this.start = start;
    this.distance = distance;
  }

  public List<Integer> getTour()
  {
    if (!solved) solve();
    return tour;
  }

  /**
   * runs the solve method if not run
   *
   * @return
   */
  public double getMinTourCost()
  {
    if (!solved) solve();
    return minTourCost;
  }

  /**
   * Solves the traveling salesman problem and caches the solution
   */
  public void solve()
  {
    if (solved) return;

//    all bits are set to 1
    final int END_STATE = (1 << N) - 1;

//    memo table of size n to n consists of null values
    Double[][] memo = new Double[N][1 << N];

//    initialisation step
//    add all outgoing edges from the starting node to the memo table
    for (int end = 0; end < N; end++)
    {
      if (end == start) continue;
      memo[end][(1 << start) | (1 << end)] = distance[start][end];
    }

//    we are creating Tours of path that are 1 longer
//    r -> number of nodes in the partially completed tour
    for (int r = 3; r <= N; r++)
    {
//      loop through subsets with r bits of set
      for (int subset : combinations(r, N))
      {
//        make sure the starting node is inside the subset
        if (notIn(start, subset)) continue;

//        next node is our target node
//        the one we are trying to expand to
        for (int next = 0; next < N; next++)
        {
          if (next == start || notIn(next, subset)) continue;

//          flip off the next node and set it to 0
          int subsetWithoutNext = subset ^ (1 << next);
          double minDist = Double.POSITIVE_INFINITY;

//          every possible end node
          for (int end = 0; end < N; end++)
          {
            if (end == start || end == next || notIn(end, subset)) continue;

            double newDistance = memo[end][subsetWithoutNext] + distance[end][next];

            if (newDistance < minDist)
            {
              minDist = newDistance;
            }
          }
//          cache on the memo table
          memo[next][subset] = minDist;
        }
      }
    }

//    Calculating the minimum cost
//    Connect tour back to the starting node and minimise the cost
    for (int i = 0; i < N; i++)
    {
      if (i == start) continue;

      double tourCost = memo[i][END_STATE] + distance[i][start];

      if (tourCost < minTourCost)
      {
        minTourCost = tourCost;
      }
    }

//    Find the actual tour
//    by looking up the values we stored in the memo table
    int lastIndex = start;
    int state = END_STATE;
    tour.add(start);

//    Reconstruct TSP path from memo table
//    i is a counter variable
    for (int i = 1; i < N; i++)
    {
//      index is the index of the best next node
      int index = -1;

//      loop over all possible next nodes
      for (int j = 0; j < N; j++)
      {
        if (j == start || notIn(j, state)) continue;
        if (index == -1) index = j;

        double prevDistance = memo[index][state] + distance[index][lastIndex];
        double newDistance = memo[j][state] + distance[j][lastIndex];

        if (newDistance < prevDistance) index = j;
      }

      tour.add(index);
      state = state ^ (1 << index); // toggle that bit off
      lastIndex = index;
    }

    tour.add(start);
    Collections.reverse(tour);

    solved = true;
  }

  /**
   * if a bit is not set in the subset
   *
   * @param elem
   * @param subset
   * @return
   */
  private static boolean notIn(int elem, int subset)
  {
    return ((1 << elem) & subset) == 0;
  }

  /**
   * This method generates all bit sets of size n where r bits
   * are set to one. The result is returned as a list of integer masks.
   *
   * @param r
   * @param n
   * @return
   */
  public static List<Integer> combinations(int r, int n)
  {
    List<Integer> subsets = new ArrayList<>();
    combinations(0, 0, r, n, subsets);
    return subsets;
  }


  /**
   * To find all the combinations of size r we need to recurse until we have
   * selected r elements (aka r = 0), otherwise if r != 0 then we still need to select
   * an element which is found after the position of our last selected element
   *
   * @param set
   * @param at
   * @param r
   * @param n
   * @param subsets
   */
  private static void combinations(int set, int at, int r, int n, List<Integer> subsets)
  {

    // Return early if there are more elements left to select than what is available.
    int elementsLeftToPick = n - at;
    if (elementsLeftToPick < r) return;

    // We selected 'r' elements so we found a valid subset!
    if (r == 0)
    {
      subsets.add(set);
    }
    else
    {
      for (int i = at; i < n; i++)
      {
        // Try including this element, flipping on the ith bit
        set ^= (1 << i);

        combinations(set, i + 1, r - 1, n, subsets);

        // Backtrack and try the instance where we did not include this element, flip off the ith bit
        set ^= (1 << i);
      }
    }
  }

  public static void main(String[] args)
  {
    // Create adjacency matrix
    int n = 6;
    double[][] distanceMatrix = new double[n][n];
    for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
    distanceMatrix[5][0] = 10;
    distanceMatrix[1][5] = 12;
    distanceMatrix[4][1] = 2;
    distanceMatrix[2][4] = 4;
    distanceMatrix[3][2] = 6;
    distanceMatrix[0][3] = 8;

    int startNode = 0;
    TravelingSalesmanProblem solver =
        new TravelingSalesmanProblem(startNode, distanceMatrix);

    // Prints: [0, 3, 2, 4, 1, 5, 0]
    System.out.println("Tour: " + solver.getTour());

    // Print: 42.0
    System.out.println("Tour cost: " + solver.getMinTourCost());
  }
}
