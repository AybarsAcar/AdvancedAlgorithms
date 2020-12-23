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
  public int fibM(int n)
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

//  TODO: implement O(logn) solution with matrix multiplication

  public static void main(String[] args)
  {
    FibonacciSolver solver = new FibonacciSolver();

    System.out.println(solver.fib(75));
    System.out.println(solver.fibM(75));
  }
}
