package org.aybarsacar.advancedAlgorithms.graphs;

import org.junit.jupiter.api.Test;

public class BellmanFordEdgeListTest
{
  @Test
  public void bellmanFordTest()
  {
    int E = 10, V = 9, start = 0;

    BellmanFordEdgeList.Edge[] edges = new BellmanFordEdgeList.Edge[E];
    edges[0] = new BellmanFordEdgeList.Edge(0, 1, 1);
    edges[1] = new BellmanFordEdgeList.Edge(1, 2, 1);
    edges[2] = new BellmanFordEdgeList.Edge(2, 4, 1);
    edges[3] = new BellmanFordEdgeList.Edge(4, 3, -3);
    edges[4] = new BellmanFordEdgeList.Edge(3, 2, 1);
    edges[5] = new BellmanFordEdgeList.Edge(1, 5, 4);
    edges[6] = new BellmanFordEdgeList.Edge(1, 6, 4);
    edges[7] = new BellmanFordEdgeList.Edge(5, 6, 5);
    edges[8] = new BellmanFordEdgeList.Edge(6, 7, 4);
    edges[9] = new BellmanFordEdgeList.Edge(5, 7, 3);

    double[] d = BellmanFordEdgeList.bellmanFord(edges, V, start);

    for (int i = 0; i < V; i++)
    {
      System.out.printf("The Cost to get from node %d to %d is %.2f\n", start, i, d[i]);
    }
  }
}
