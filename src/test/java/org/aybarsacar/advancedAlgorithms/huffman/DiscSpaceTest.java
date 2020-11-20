package org.aybarsacar.advancedAlgorithms.huffman;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.BitSet;

public class DiscSpaceTest
{
  @Test
  public void saveAsBinary()
  {
    try
    {
      // compress string
      Huffman h = new Huffman();
      String compressed = h.compress(createLongString().toCharArray());
      // Convert string into zeros and ones
      BitSet biteSet = new BitSet(compressed.length());
      //start from after the header
      for (int i = 0; i < compressed.length() - h.header.length(); i++)
      {
        if (compressed.charAt(i + h.header.length()) == '1') biteSet.set(i);
      }

      // Save Binary File
      ObjectOutputStream outputStream = null;
      outputStream = new ObjectOutputStream(new FileOutputStream("saved_as_bytes.txt"));
      outputStream.writeBytes(h.header.toString());
      outputStream.writeObject(biteSet);
      outputStream.flush();
      outputStream.close();

      // Using the same bitSet which contains false for 0 and true for 1
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < biteSet.length(); i++)
      {
        s.append(biteSet.get(i) ? 1 : 0);
      }
      // I am printing the compressed string - header
      System.out.println(compressed.substring(h.header.length()));
      //this is the bitSet reverted back from trues and falses to the compressed String
      System.out.println(s.toString());
    } catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  @Test
  public void saveAsText()
  {
    try
    {
      PrintWriter out = new PrintWriter("saved_as_string.txt");
      out.println(createLongString());
      out.flush();
      out.close();
    } catch (Exception e)
    {
      System.out.println("Error saving file");
    }
  }

  @Test
  public void saveHuffmanAsText()
  {
    Huffman h = new Huffman();
    String s = h.compress(createLongString().toCharArray());
    try
    {

      FileOutputStream out = new FileOutputStream("saved_huffman_as_text.txt");
      byte[] bytes = s.getBytes();
      for (byte b : bytes)
      {
        out.write(b);
      }

      out.flush();
      out.close();
    } catch (Exception e)
    {
      System.out.println("Error saving file");
    }
  }

  /**
   * This method only creates the long string
   *
   * @return
   */
  private String createLongString()
  {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < 100; i++)
    {
      s.append("This is a test of disk space used saving as String");
    }
    return s.toString();
  }
}
