package org.aybarsacar.advancedAlgorithms.graphtheory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BridgesAdjacencyListTest
{
  int n;
  List<List<Integer>> graph;
  BridgesAdjacencyList solver;

  @BeforeEach
  public void init()
  {
    n = 9;
    graph = BridgesAdjacencyList.createGraph(n);

    BridgesAdjacencyList.addEdge(graph, 0, 1);

    BridgesAdjacencyList.addEdge(graph, 0, 2);

    BridgesAdjacencyList.addEdge(graph, 1, 2);

    BridgesAdjacencyList.addEdge(graph, 2, 3);

    BridgesAdjacencyList.addEdge(graph, 3, 4);

    BridgesAdjacencyList.addEdge(graph, 2, 5);

    BridgesAdjacencyList.addEdge(graph, 5, 6);

    BridgesAdjacencyList.addEdge(graph, 6, 7);

    BridgesAdjacencyList.addEdge(graph, 7, 8);

    BridgesAdjacencyList.addEdge(graph, 8, 5);

    solver = new BridgesAdjacencyList(n, graph);
  }

  @Test
  public void findBridgesTest()
  {
    List<Integer> bridges = solver.findBridges();

    Assertions.assertArrayEquals(new Integer[]{3, 4, 2, 3, 2, 5}, bridges.toArray());
  }
}
