package org.aybarsacar.advancedAlgorithms.datastructures.balancedtree;

import org.aybarsacar.advancedAlgorithms.datastructures.utils.TreePrinter;

/**
 * Implementation of an RB Tree
 * RB Trees are a special type of self-balancing trees
 * Operations have a logarithmic time complexity: O(log(n))
 *
 * @param <T> and object that is comparable
 */
public class RedBlackTree<T extends Comparable<T>> implements Iterable<T>
{
  public static final boolean RED = true;
  public static final boolean BLACK = false;

  public class Node implements TreePrinter.PrintableNode
  {
    public boolean color = RED;             // The color of the node default color is red
    public T value;                         // The value/data contained within the node
    public Node left, right, parent;        // The left, right, and parent references of this node

    public Node(T value, Node parent)
    {
      this.value = value;
      this.parent = parent;
    }

    public Node(boolean color, T value)
    {
      this.color = color;
      this.value = value;
    }

    Node(T key, boolean color, Node left, Node right, Node parent)
    {
      this.value = key;
      this.color = color;

      if (parent == null && left == null && right == null)
      {
        parent = this;
        left = this;
        right = this;
      }

      this.parent = parent;
      this.left = left;
      this.right = right;
    }

    public boolean getColor()
    {
      return color;
    }

    public void setColor(boolean color)
    {
      this.color = color;
    }

    public T getValue()
    {
      return value;
    }

    public void setValue(T value)
    {
      this.value = value;
    }

    public Node getLeft()
    {
      return left;
    }

    public void setLeft(Node left)
    {
      this.left = left;
    }

    public Node getRight()
    {
      return right;
    }

    public void setRight(Node right)
    {
      this.right = right;
    }

    public Node getParent()
    {
      return parent;
    }

    public void setParent(Node parent)
    {
      this.parent = parent;
    }

    @Override
    public String getText()
    {
      return value.toString();
    }
  }

  public Node root;                   // root of the RB Tree
  private int nodeCount = 0;          // tracks the number of nodes inside the tree
  private final Node NIL;             // NIL nodes are always black and has a value of null

  public RedBlackTree()
  {
    NIL = new Node(BLACK, null);
    NIL.left = NIL;
    NIL.right = NIL;
    NIL.parent = NIL;

    root = NIL;
  }

  /**
   * @return the number of nodes in the tree
   */
  public int size()
  {
    return nodeCount;
  }

  public boolean isEmpty()
  {
    return nodeCount == 0;
  }

  public boolean contains(T value)
  {
    Node node = root;

    if (node == null || value == null) return false;

    while (node != NIL)
    {
//      Compare current value to  the value in the node
      int cmp = value.compareTo(node.value);

//      dig into the left subtree
      if (cmp < 0) node = node.left;

//      dig into the right subtree
      else if (cmp > 0) node = node.right;

//      found the node
      else return true;
    }
    return false;
  }

  /**
   * @param val
   * @return
   */
  public boolean insert(T val)
  {
    if (val == null) throw new IllegalArgumentException("Red-Black Tree does not allow null values.");

    Node x = root, y = NIL;

    while (x != NIL)
    {
      y = x;

      if (x.value.compareTo(val) > 0)
      {
        x = x.left;
      }
      else if (x.value.compareTo(val) < 0)
      {
        x = x.right;
      }
      else
      {
        return false;
      }
    }

    Node z = new Node(val, RED, y, NIL, NIL);

    if (y == NIL)
    {
      root = z;
    }
    else if (z.getValue().compareTo(y.getValue()) < 0)
    {
      y.left = z;
    }
    else
    {
      y.right = z;
    }

    insertFix(z);

    nodeCount++;
    return true;
  }

  /**
   * fixes the tree in insertion if there are any violations
   *
   * @param z
   */
  private void insertFix(Node z)
  {
    Node y;
    while (z.parent.color == RED)
    {
      if (z.parent == z.parent.parent.left)
      {
        y = z.parent.parent.right;
        if (y.color == RED)
        {
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        }
        else
        {
          if (z == z.parent.right)
          {
            z = z.parent;
            leftRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = RED;
          rightRotate(z.parent.parent);
        }
      }
      else
      {
        y = z.parent.parent.left;
        if (y.color == RED)
        {
          z.parent.color = BLACK;
          y.color = BLACK;
          z.parent.parent.color = RED;
          z = z.parent.parent;
        }
        else
        {
          if (z == z.parent.left)
          {
            z = z.parent;
            rightRotate(z);
          }
          z.parent.color = BLACK;
          z.parent.parent.color = BLACK;
          leftRotate(z.parent.parent);
        }
      }
    }
    root.setColor(BLACK);
    NIL.setParent(null);
  }

  private void leftRotate(Node x)
  {
    Node y = x.right;
    x.setRight(y.getLeft());

    if (y.getLeft() != NIL) y.getLeft().setParent(x);

    y.setParent(x.getParent());

    if (x.getParent() == NIL) root = y;

    if (x == x.getParent().getLeft()) x.getParent().setLeft(y);
    else x.getParent().setRight(y);

    y.setLeft(x);
    x.setParent(y);
  }

  private void rightRotate(Node y)
  {
    Node x = y.left;
    y.left = x.right;

    if (x.right != NIL) x.right.parent = y;

    x.parent = y.parent;

    if (y.parent == NIL) root = x;

    if (y == y.parent.left) y.parent.left = x;
    else y.parent.right = x;

    x.right = y;
    y.parent = x;
  }

//  TODO: Delete operations

  /**
   * Returns as iterator to traverse the tree in order
   *
   * @return
   */
  @Override
  public java.util.Iterator<T> iterator()
  {

    final int expectedNodeCount = nodeCount;
    final java.util.Stack<Node> stack = new java.util.Stack<>();
    stack.push(root);

    return new java.util.Iterator<T>()
    {
      Node trav = root;

      @Override
      public boolean hasNext()
      {
        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
        return root != NIL && !stack.isEmpty();
      }

      @Override
      public T next()
      {

        if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

        while (trav != NIL && trav.left != NIL)
        {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != NIL)
        {
          stack.push(node.right);
          trav = node.right;
        }

        return node.value;
      }

      @Override
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString()
  {
    return TreePrinter.getTreeDisplay(root);
  }
}
