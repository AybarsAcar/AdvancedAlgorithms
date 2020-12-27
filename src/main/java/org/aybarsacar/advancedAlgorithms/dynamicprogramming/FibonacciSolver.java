package org.aybarsacar.advancedAlgorithms.dynamicprogramming;

public class FibonacciSolver
{
  /**
   * iterative implementation with memoization
   * bottom up approach
   *
   * @param n
   * @return
   */
  public int fib(int n)
  {
    int[] result = new int[n + 1];
    result[0] = 0;
    result[1] = 1;

    for (int i = 2; i < result.length; i++)
    {
      result[i] = result[i - 1] + result[i - 2];
    }

    return result[n];
  }

  /**
   * more memory efficient
   * bottom up approach
   *
   * @param n
   * @return
   */
  public int fib2(int n)
  {
    if (n == 0) return 0;
    if (n == 1) return 1;

    int a = 0;
    int b = 1;
    int result = 0;

    for (int i = 2; i <= n; i++)
    {
      result = a + b;
      a = b;
      b = result;
    }

    return result;
  }

  /**
   * exponential big O time complexity
   *
   * @param n
   * @return
   */
  public int fibBAD(int n)
  {
    if (n == 0) return 0;
    if (n == 1) return 1;

    return fibBAD(n - 2) + fibBAD(n - 1);
  }

  /**
   * Time Complexity: O(log(n))
   *
   * @param n
   * @return
   */
  public int fibM(int n)
  {
    int[][] F = new int[][]{{1, 1}, {1, 0}};

    if (n == 0) return 0;

    power(F, n - 1);

    return F[0][0];
  }

  private void power(int[][] F, int n)
  {
    if (n == 0 || n == 1) return;

    int[][] M = new int[][]{{1, 1}, {1, 0}};

    power(F, n / 2);
    multiply(F, F);

    if (n % 2 != 0) multiply(F, M);
  }

  private void multiply(int[][] F, int[][] M)
  {
    int x = F[0][0] * M[0][0] + F[0][1] * M[1][0];
    int y = F[0][0] * M[0][1] + F[0][1] * M[1][1];
    int z = F[1][0] * M[0][0] + F[1][1] * M[1][0];
    int w = F[1][0] * M[0][1] + F[1][1] * M[1][1];

    F[0][0] = x;
    F[0][1] = y;
    F[1][0] = z;
    F[1][1] = w;
  }

  public static void main(String[] args)
  {
    FibonacciSolver solver = new FibonacciSolver();

    System.out.println(solver.fib(75));
    System.out.println(solver.fib2(75));
  }
}
