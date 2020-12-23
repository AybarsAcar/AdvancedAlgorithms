package org.aybarsacar.advancedAlgorithms.dynamicprogramming.magicalcows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * store the frequency of farms in a frequency table for that number of cows
 * instead of keeping track of the farms for faster processing
 */
public class MagicalCows
{
  static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  //  The maximum number of days
  static final int MAX_DAYS = 50;

  public static void main(String[] args) throws IOException
  {
    String[] line = reader.readLine().split(" ");

//    The max number of days
    final int C = Integer.parseInt(line[0]);

//    the initial number of farms
    final int N = Integer.parseInt(line[1]);

//    The number of queries
    final int M = Integer.parseInt(line[2]);

//    The dp table
    long[][] dp = new long[MAX_DAYS + 1][C + 1];

//    Count the initial frequency of farms of different sizes
    for (int i = 0; i < N; i++)
    {
      int cows = Integer.parseInt(reader.readLine());
      dp[0][cows]++; // increment the number of farms with that number of cows on day 0
    }

    for (int day = 0; day < MAX_DAYS; day++)
    {
//      For all farm sizes between 1 and C, double the number of cows
      for (int i = 1; i <= C; i++)
      {
        if (i <= C / 2)
        {
//          just double the number of the cows in that farm
          dp[day + 1][i * 2] += dp[day][i];
        }
        else
        {
//          the number of cows exceeds the farm capacity so double the number of farms
          dp[day + 1][i] += 2 * dp[day][i];
        }
      }
    }

//    Answer each query
    for (int i = 0; i < M; i++)
    {
      int day = Integer.parseInt(reader.readLine());
      System.out.println(query(dp, day));
    }
  }

  /**
   * returns the number of farms that needs to be inspected on a given day
   * by summing up all the farms on a given day from the table
   *
   * @param dp
   * @param day
   * @return
   */
  private static long query(long[][] dp, int day)
  {
    long farms = 0;
    long[] frequencies = dp[day];

    for (int i = 0; i < frequencies.length; i++)
    {
      farms += frequencies[i];
    }

    return farms;
  }
}
