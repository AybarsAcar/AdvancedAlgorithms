package org.aybarsacar.advancedAlgorithms.dynamicprogramming.magicalcows;

/**
 * Turn the question into a more general solver
 */
public class MegicalCowsSolver
{
  private final int maxDays;    //
  private final int C;          // The max number of days
  private final int N;          // The initial number of farms
  private final int M;          // The number of queries

  private long[][] dp;

  public MegicalCowsSolver(int maxDays, int c, int n, int m)
  {
    this.maxDays = maxDays;
    C = c;
    N = n;
    M = m;

    long[][] dp = new long[maxDays + 1][c + 1];
  }

  public int solve()
  {
    return -1;
  }
}
