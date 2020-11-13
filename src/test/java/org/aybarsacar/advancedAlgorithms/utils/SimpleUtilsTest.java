package org.aybarsacar.advancedAlgorithms.utils;

import org.aybarsacar.advancedAlgorithms.SimpleUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleUtilsTest
{
  SimpleUtils utils;

  @BeforeEach
  public void init()
  {
    utils = new SimpleUtils();
  }

  @Test
  public void stringToBooleanTrueTest()
  {
    Assertions.assertTrue(utils.stringToBoolean("y"));
    Assertions.assertTrue(utils.stringToBoolean("Yes"));
    Assertions.assertTrue(utils.stringToBoolean("YES"));
    Assertions.assertTrue(utils.stringToBoolean("true"));
    Assertions.assertTrue(utils.stringToBoolean("True"));
    Assertions.assertTrue(utils.stringToBoolean("TRUE"));
  }

  @Test
  public void stringToBooleanFalseTest()
  {
    Assertions.assertFalse(utils.stringToBoolean(null));
    Assertions.assertFalse(utils.stringToBoolean("n"));
  }
}
