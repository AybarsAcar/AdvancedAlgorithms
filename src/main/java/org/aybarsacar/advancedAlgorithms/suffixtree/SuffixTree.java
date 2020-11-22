package org.aybarsacar.advancedAlgorithms.suffixtree;

import java.util.ArrayList;
import java.util.List;

/**
 * This Suffix Tree is implemented using Ukkonen's Algorithm
 */
public class SuffixTree
{
  private final char UNIQUE_CHAR = '$';

  SuffixNode root;
  private ActivePoint activePoint;
  private char[] input;
  private int remaining;
  private End end;

  /**
   * banana -> banana$
   *
   * @param input the text
   */
  public SuffixTree(char[] input)
  {
    this.input = addUnique(input);
  }

  /**
   * Builds the Suffix Tree and also sets the indexes on the edges using Ukkonen's Algorithm
   * <p>
   * banana$
   * 0123456
   */
  public void buildSuffixTree()
  {
    root = new SuffixNode(1, new End(0));
    root.index = -1;
    this.end = new End(-1);
    activePoint = new ActivePoint(root);

    for (int i = 0; i < input.length; i++)
    {
      startPhase(i);
    }

    setIndex(root, 0, input.length);
  }

  /**
   * i
   * banana$
   * 0123456
   * E000111
   * L000123
   * <p>
   * internalNodes will have an index of -1, so never have an index
   * <p>
   * top ones are rule 3 extension and bottom ones are root 2 extension
   * Rule 2 Extension -> creates
   * Rule 3 Extension -> walks to the next phase
   *
   * @param index
   */
  public void startPhase(int index)
  {
    SuffixNode lastInternalNode = null;

    end.end++;
    remaining++;

    while (remaining > 0)
    {
      if (activePoint.activeLength == 0)
      {
//        Rule 3 Extension
        if (activePoint.activeNode.children[input[index]] != null)
        {
          activePoint.activeEdge = activePoint.activeNode.children[input[index]].start;
          activePoint.activeLength++;

          System.out.println("Phase(" + input[index] + ") Rule 3 Extension - applied("
              + activePoint.activeNode.start + ":" + input[index] + ")");

          break;
        }
        else
        {
//          Rule 2 Extension
          root.children[input[index]] = new SuffixNode(index, end);
          remaining--;

          System.out.println("Phase(" + input[index] + ") Rule 2 Extension - applied("
              + activePoint.activeNode.start + ":" + input[index] + ")");
        }
      }
      else
      {
//        walk through the activePoints
        char c = getNextCharacter(index);

        if (c != 0)
        {
          if (c == input[index])
          {
            SuffixNode edge = selectEdge();
            //          rule 3 extension
            if (lastInternalNode != null)
            {
              lastInternalNode.suffixLink = edge;
            }

            System.out.println("Phase(" + input[index] + ") Rule 3 Extension - start("
                + edge.start + ":" + input[edge.start]
                + ") Next Char match: " + index + ":" + input[index] + "-" + c);

            //          walk down the tree
            walkDown(index);
            break;
          }
          else
          {
            SuffixNode edge = selectEdge();
            //          Rule 2 Extension -> create an internal node
            //          anana$ [1, 6]
            int currentStart = edge.start;
            //          change 1 to 4 -> na$ [4, 6]
            edge.start += activePoint.activeLength;

            //          create the new node from 1 to 3 [1, 3]
            SuffixNode internalNode =
                new SuffixNode(currentStart, new End(currentStart + activePoint.activeLength - 1));

            //          leaf node open end [6, 6] which is the $
            SuffixNode leafNode = new SuffixNode(index, end);

            //          go to the edge and add those 2 children
            internalNode.children[input[edge.start]] = edge;
            internalNode.children[input[leafNode.start]] = leafNode;
            internalNode.index = -1;

            activePoint.activeNode.children[input[internalNode.start]] = internalNode;

            if (lastInternalNode != null)
            {
              lastInternalNode.suffixLink = internalNode;
            }

            lastInternalNode = internalNode;
            internalNode.suffixLink = root;

            System.out.println("Phase(" + input[index] + ") Rule 2 Extension - changed node ("
                + internalNode.start + "-" + internalNode.end.end + "):" + input[internalNode.start]);
            System.out.println("Phase(" + input[index] + ") Rule 2 Extension --- children[edge] "
                + edge.start + ":" + input[edge.start]);
            System.out.println("Phase(" + input[index] + ") Rule 2 Extension --- children[leafNode] "
                + leafNode.start + ":" + input[leafNode.start]);
          }
        }
        else
        {
          SuffixNode edge = selectEdge();
          edge.children[input[index]] = new SuffixNode(index, end);
          if (lastInternalNode != null)
          {
            lastInternalNode.suffixLink = edge;
          }
          lastInternalNode = edge;
        }

        if (activePoint.activeNode != root)
        {
          // if i am not on the root and next time if i am not on the root it means its internal node
          // so i will follow the suffix node
          activePoint.activeNode = activePoint.activeNode.suffixLink;
        }
        else
        {
          // if it is root
          activePoint.activeEdge++;
          activePoint.activeLength--;
        }

        remaining--;
      }
    }
  }

  /**
   * @param index -> we pass the edge
   * @return
   */
  public char getNextCharacter(int index)
  {
    SuffixNode edge = selectEdge();

    if ((edgeSize(edge)) >= activePoint.activeLength)
    {
//      we have enough characters
      return input[edge.start + activePoint.activeLength];
    }
    else if (edgeSize(edge) + 1 == activePoint.activeLength)
    {
      if (edge.children[input[index]] != null)
      {
        return input[index];
      }
    }
    else
    {
      activePoint.activeNode = edge;
      activePoint.activeEdge = activePoint.activeEdge + edgeSize(edge) + 1;
      activePoint.activeLength = activePoint.activeLength - edgeSize(edge) - 1;

      return getNextCharacter(index);
    }

    return 0; // 0 means null in ASCII Table
  }

  public SuffixNode selectEdge()
  {
    return activePoint.activeNode.children[input[activePoint.activeEdge]];
  }

  /**
   * just changing our activePoints
   * moving the pointer from the active points
   */
  public void walkDown(int index)
  {
    SuffixNode edge = selectEdge();

//    activePoint.activeLength is the char i want to skip
    if (edgeSize(edge) < activePoint.activeLength)
    {
//      i need to jump a node
      activePoint.activeNode = edge;
      activePoint.activeLength = activePoint.activeLength - edgeSize(edge);
      activePoint.activeEdge = edge.children[input[index]].start;
    }
    else
    {
//      enough to walk
      activePoint.activeLength++;
    }
  }

  public void dfsTraversal()
  {
    List<Character> result = new ArrayList<>();
    for (SuffixNode node : root.children)
    {
      dfsTraversal(node, result);
    }
  }


  /**
   * this method will be overloaded because the user doesn't need to know the internals
   * and about the recursion
   *
   * @param node
   * @param result
   */
  private void dfsTraversal(SuffixNode node, List<Character> result)
  {
    if (node == null) return;

    int end = node.end.end;
    if (node.index != -1)
    {
//      so it is not an internal node
      for (int i = node.start; i <= end; i++)
      {
        result.add(input[i]);
      }

      for (Character character : result)
      {
        System.out.println(character);
      }
      System.out.println("index = " + node.index);

      for (int i = node.start; i <= end; i++)
      {
        result.remove(result.size() - 1);
      }
    }
    else
    {
//      it is an internal node
      for (int i = node.start; i <= end; i++)
      {
        result.add(input[i]);
      }

      for (SuffixNode s : node.children)
      {
        dfsTraversal(s, result);
      }

      for (int i = node.start; i <= end; i++)
      {
        result.remove(result.size() - 1);
      }

    }
  }

  public int edgeSize(SuffixNode edge)
  {
    return edge.end.end - edge.start;
  }

  private char[] addUnique(char[] input)
  {
    char[] c = new char[input.length + 1];
    System.arraycopy(input, 0, c, 0, input.length);
    c[input.length] = UNIQUE_CHAR;
    return c;
  }

  public char[] getInput()
  {
    return input;
  }

  private void setIndex(SuffixNode root, int val, int size)
  {
    if (root == null) return;

    val = val + root.end.end - root.start + 1;

    if (root.index != -1)
    {
      root.index = size - val;
    }
    else
    {
      for (SuffixNode node : root.children)
      {
        setIndex(node, val, size);
      }
    }
  }
}
