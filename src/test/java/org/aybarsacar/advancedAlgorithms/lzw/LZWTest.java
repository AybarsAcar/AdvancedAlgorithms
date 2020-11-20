package org.aybarsacar.advancedAlgorithms.lzw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LZWTest
{
  LZW lzw;

  @BeforeEach
  public void init()
  {
    lzw = new LZW();
  }

  @Test
  public void compressTest()
  {
//    ababcbc -> [97, 98, 256, 99, 98, 99]
    List<Integer> result = lzw.compress("ababcbc");

    Assertions.assertEquals(6, result.size());
    Assertions.assertEquals(97, result.get(0).intValue());
    Assertions.assertEquals(98, result.get(1).intValue());
    Assertions.assertEquals(256, result.get(2).intValue());
    Assertions.assertEquals(99, result.get(3).intValue());
    Assertions.assertEquals(98, result.get(4).intValue());
    Assertions.assertEquals(99, result.get(5).intValue());
    Assertions.assertNull(lzw.compress(null));
  }

  @Test
  public void decompressTest()
  {
//    [97, 98, 256, 99, 98, 99] -> ababcbc
    List<Integer> compressed = lzw.compress("ababcbc");

//    create a new instance in case we return anything from cache
//    to simulate different moment, different machine etc
    LZW lzw2 = new LZW();
    String decompressed = lzw2.decompress(compressed);

    Assertions.assertNotNull(decompressed);
    Assertions.assertEquals("ababcbc", decompressed);

    List<Integer> compressed2 = lzw.compress("This is a test of compression");
    String decompressed2 = lzw2.decompress(compressed2);

    Assertions.assertNotNull(decompressed2);
    Assertions.assertEquals("This is a test of compression", decompressed2);
  }

  @Test
  public void decompressSpecialCaseTest()
  {
    List<Integer> compressed = lzw.compress("abababa");
//    when we use the same instance we are using the same dictionary size we have changed during
//    compression
//    that's why remove it as a class field and declare in the methods
//    System.out.println(lzw.decompress(compressed));

//    fixed it by declaring the dictionarySize inside the methods
    Assertions.assertEquals("abababa", lzw.decompress(compressed));
  }

  @Test
  public void decompressInvalidTest()
  {
    List<Integer> compressed = lzw.compress("abababa");
    compressed.add(456);

    System.out.println(lzw.decompress(compressed));
    Assertions.assertEquals("-1", lzw.decompress(compressed));
  }
}
