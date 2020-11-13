package org.aybarsacar.advancedAlgorithms.euclid;

/*
 * Euclid Algorithm
 * it calculates the Greatest common divisor between 2 int -> GCD(A, B)
 * */
public class Euclid
{
  /**
   * Implementation using recursion
   * 22/6 -> returns 2
   *
   * @param number  larger number
   * @param divisor smaller number
   * @return integer
   */
  public int gcd(int number, int divisor)
  {
    int remaining = number % divisor;

    if (remaining == 0) return divisor;

    return gcd(divisor, remaining);
  }

  /**
   * implementation iteratively
   * <p>
   * 2 stages
   * number / temp = result rest of divisor
   */
  public int gcdIterative(int number, int divisor)
  {
    while (divisor != 0)
    {
      int temp = divisor;

      divisor = number % divisor;
      number = temp;
    }
    return number;
  }
}
