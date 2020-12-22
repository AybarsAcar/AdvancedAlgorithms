package org.aybarsacar.advancedAlgorithms.graphtheory.networkflow.examples;

import org.aybarsacar.advancedAlgorithms.graphtheory.networkflow.FordFulkersonDfsSolver;
import org.aybarsacar.advancedAlgorithms.graphtheory.networkflow.NetworkSolverBase;

import java.awt.geom.Point2D;

/**
 * M mice and a hungry owl
 * each mouse can run a radius r before being caught by the owl
 * there are holes in the ground with capacities for mice to hide
 * save the most number of mice
 * <p>
 * Turn the problem into a Bipartite graph - Network
 * run max-flow to find the matches
 * Ford Fulkerson's Max Flow algorithm is used
 */
public class MiceAndOwls
{
  /**
   * wrapper around a point object
   */
  static class Mouse
  {
    Point2D point;

    public Mouse(int x, int y)
    {
      point = new Point2D.Double(x, y);
    }
  }

  /**
   * wrapper around a point object with a capacity
   */
  static class Hole
  {
    int capacity;
    Point2D point;

    public Hole(int x, int y, int capacity)
    {
      this.point = new Point2D.Double(x, y);
      this.capacity = capacity;
    }
  }

  /**
   * @param mice
   * @param holes
   * @param radius - how far a mouse can run before getting caught
   */
  static void solve(Mouse[] mice, Hole[] holes, int radius)
  {
    final int M = mice.length;
    final int H = holes.length;

    final int N = M + H + 2;  // number of nodes
    final int S = N - 1;      // source node
    final int T = N - 2;      // sink node

    NetworkSolverBase solver;
    solver = new FordFulkersonDfsSolver(N, S, T);

//    Setting up the Flow Graph
//    source to mice
    for (int i = 0; i < M; i++)
    {
      solver.addEdge(S, i, 1);
    }

//    hook up each mouse with the holes they are able to reach
    for (int i = 0; i < M; i++)
    {
      Point2D mouse = mice[i].point;
      for (int j = 0; j < H; j++)
      {
        Point2D hole = holes[j].point;
        if (mouse.distance(hole) <= radius)
        {
          solver.addEdge(i, M + j, 1);
        }
      }
    }

//    holes to sink
    for (int i = 0; i < H; i++)
    {
//      their capacity represents the number of mice that can fit in a hole
      solver.addEdge(M + i, T, holes[i].capacity);
    }

    System.out.println("Number of safe mice: " + solver.getMaxFlow());
  }

  public static void main(String[] args)
  {
    Mouse[] mice = {
        new Mouse(1, 0),
        new Mouse(0, 1),
        new Mouse(8, 1),
        new Mouse(12, 0),
        new Mouse(12, 4),
        new Mouse(15, 5)
    };
    Hole[] holes = {
        new Hole(1, 1, 1),
        new Hole(10, 2, 2),
        new Hole(14, 5, 1)
    };
    solve(mice, holes, 3);
    solve(mice, holes, 1);
  }
}
