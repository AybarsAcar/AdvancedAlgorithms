package org.aybarsacar.advancedAlgorithms;

public class SimpleUtils
{
  public boolean stringToBoolean(String value)
  {
    if (value == null) return false;

    if (value.equalsIgnoreCase("y")
        || value.equalsIgnoreCase("Yes")
        || value.equalsIgnoreCase("true")) return true;

    return false;
  }
}
