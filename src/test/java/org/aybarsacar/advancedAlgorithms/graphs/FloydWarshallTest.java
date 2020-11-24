package org.aybarsacar.advancedAlgorithms.graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FloydWarshallTest
{
  //  construct the graph
  int n = 7;
  double[][] m = FloydWarshall.createGraph(n);

  //  init the solver
  FloydWarshall solver;

  @BeforeEach
  public void init()
  {
    m[0][1] = 2;
    m[0][2] = 5;
    m[0][6] = 10;
    m[1][2] = 2;
    m[1][4] = 11;
    m[2][6] = 2;
    m[6][5] = 11;
    m[4][5] = 1;
    m[5][4] = -2;

    solver = new FloydWarshall(m);
  }

  @Test
  public void solveTest()
  {
//    distances matrix
//    outcomes
//    there is a shortest path -> returns a double
//    no path exists -> returns Double.POSITIVE_INFINITY
//    effected by the negative cycle -> returns Double.NEGATIVE_INFINITY
    double[][] dist = solver.getApspMatrix();

    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        System.out.printf("This shortest path from node %d to node %d is %.3f\n", i, j, dist[i][j]);
  }

  @Test
  public void reconstructShortestPathTest()
  {
    for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
      {
        List<Integer> path = solver.reconstructShortestPath(i, j);
        String str;
        if (path == null)
        {
          str = "HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)";
        }
        else if (path.size() == 0)
        {
          str = String.format("DOES NOT EXIST (node %d doesn't reach node %d)", i, j);
        }
        else
        {
          str =
              String.join(
                  " -> ",
                  path.stream()
                      .map(Object::toString)
                      .collect(java.util.stream.Collectors.toList()));
          str = "is: [" + str + "]";
        }

        System.out.printf("The shortest path from node %d to node %d %s\n", i, j, str);
      }
    }
  }
}
