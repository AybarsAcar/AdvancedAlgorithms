package org.aybarsacar.advancedAlgorithms.datastructures.balancedtree;

import org.aybarsacar.advancedAlgorithms.datastructures.utils.TreePrinter;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * This is a recursive implementation of an AVL Tree, a self-balancing tree
 * AVL tree is a special type of binary tree which self balances itself to keep operations logarithmic
 */
public class AVLTreeRecursive<T extends Comparable<T>> implements Iterable<T>
{
  public class Node implements TreePrinter.PrintableNode
  {
    public int bf;              // Balance Factor
    public T value;             // the value, data contained within node
    public int height;          // the height of this node in the tree - to be able to query in constant time
    public Node left, right;    // the left and right children of this node

    public Node(T value)
    {
      this.value = value;
    }

    @Override
    public TreePrinter.PrintableNode getLeft()
    {
      return left;
    }

    @Override
    public TreePrinter.PrintableNode getRight()
    {
      return right;
    }

    @Override
    public String getText()
    {
      return value.toString();
    }
  }

  public Node root;             // the root node of the AVL Tree
  private int nodeCount = 0;    // tracks the number of nodes inside the tree

  /**
   * the height of a rooted tree
   * it is the number of edges between the tree's root and its furthest leaf
   * tree with a single node has a height of 0
   *
   * @return
   */
  public int height()
  {
    if (root == null) return 0;
    return root.height;
  }

  /**
   * @return the number of nodes in the tree
   */
  public int size()
  {
    return nodeCount;
  }

  /**
   * @return whether or not the tree is empty
   */
  public boolean isEmpty()
  {
    return nodeCount == 0;
  }

  /**
   * @param value
   * @return
   */
  public boolean contains(T value)
  {
//    root as the initial node
    return contains(root, value);
  }

  /**
   * Recursive contains helper method
   *
   * @param node
   * @param value
   * @return
   */
  private boolean contains(Node node, T value)
  {
    if (node == null) return false;

//    Compare the current value to the value in the node
    int cmp = value.compareTo(node.value);

//    dig into hte left subtree
    if (cmp < 0) return contains(node.left, value);

//    dig into the right subtree
    if (cmp > 0) return contains(node.right, value);

    return true;
  }

  /**
   * insert / add a value to the AVL Tree
   * the value must not me null and duplicate values are not allowed
   * Time Complexity: O(log(n))
   *
   * @param value
   * @return whether the insertions is successful or not
   */
  public boolean insert(T value)
  {
    if (value == null) return false;

    if (!contains(root, value))
    {
      root = insert(root, value);
      nodeCount++;
      return true;
    }
    return false;
  }

  /**
   * insert a value in the AVL tree
   *
   * @param node
   * @param value
   * @return
   */
  private Node insert(Node node, T value)
  {
//    Base case
    if (node == null) return new Node(value);

//    Compare current value to the value in the node
    int cmp = value.compareTo(node.value);

//    insert in the left subtree
    if (cmp < 0)
    {
      node.left = insert(node.left, value);
    }
    else
    {
      node.right = insert(node.right, value);
    }

//    Extra lines for the AVL Tree to keep it balanced
//    update the balance factor
    update(node);

//    Re-balance tree
    return balance(node);
  }

  /**
   * Update a node's height and balance factor
   *
   * @param node
   */
  private void update(Node node)
  {
    int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
    int rightNodeHeight = (node.right == null) ? -1 : node.right.height;

//    Update this node's height
    node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);

//    Update balance factor
    node.bf = rightNodeHeight - leftNodeHeight;
  }

  /**
   * Re-balance a node it if its balance factor is -2 or +2
   *
   * @param node
   * @return
   */
  private Node balance(Node node)
  {
//    Left heavy subtree
    if (node.bf == -2)
    {
      if (node.left.bf <= 0)
      {
        return leftLeftCase(node);
      }
      else
      {
        return leftRightCase(node);
      }
    }
//    Right heavy node
    else if (node.bf == 2)
    {
      if (node.right.bf >= 0)
      {
        return rightRightCase(node);
      }
      else
      {
        return rightLeftCase(node);
      }
    }

//    Node either has a balance factor of -1, 0, or +1 which satisfies the AVL Tree condition
//    so no operations are done
    return node;
  }

  /**
   * Tree is unbalanced to the left
   *
   * @param node
   * @return
   */
  private Node leftLeftCase(Node node)
  {
    return rightRotation(node);
  }

  /**
   * goes left then right child heavy
   *
   * @param node
   * @return
   */
  private Node leftRightCase(Node node)
  {
    node.left = leftRotation(node.left);
    return leftLeftCase(node);
  }

  /**
   * Tree is unbalanced to the right
   *
   * @param node
   * @return
   */
  private Node rightRightCase(Node node)
  {
    return leftRotation(node);
  }

  /**
   * goes right then left child heavy
   *
   * @param node
   * @return
   */
  private Node rightLeftCase(Node node)
  {
    node.right = rightRotation(node.right);
    return rightRightCase(node);
  }

  private Node leftRotation(Node node)
  {
    Node newParent = node.right;
    node.right = newParent.left;
    newParent.left = node;

//    update the height and balance factors after rotation
//    because those values will undoubtedly change
//    make sure to update the child before the parent
    update(node);
    update(newParent);

    return newParent;
  }

  private Node rightRotation(Node node)
  {
    Node newParent = node.left;
    node.left = newParent.right;
    newParent.right = node;

//    update the height and balance factors after rotation
//    because those values will undoubtedly change
//    make sure to update the child before the parent
    update(node);
    update(newParent);

    return newParent;
  }

  /**
   * Remove a value from a binary tree if it exists
   * Time Complexity: O(log(n))
   *
   * @param value
   * @return
   */
  public boolean remove(T value)
  {
    if (value == null) return false;

    if (contains(root, value))
    {
      root = remove(root, value);
      nodeCount--;
      return true;
    }

    return false;
  }

  /**
   * Removes a value from the AVL Tree
   *
   * @param node
   * @param value
   * @return
   */
  private Node remove(Node node, T value)
  {
    if (node == null) return null;

    int cmp = value.compareTo(node.value);

//    Dig into left subtree, the value we are looking for is smaller than the current value
    if (cmp < 0)
    {
      node.left = remove(node.left, value);
    }
//    Dig into right subtree, the value we are looking for is greater than the current value
    else if (cmp > 0)
    {
      node.right = remove(node.right, value);
    }
//    Found the node we want to remove when cmp == 0
    else
    {
//      This is the case with only a right subtree or no subtree at all
//      in this situation just swap the node we wish to remove with its right child
      if (node.left == null)
      {
        return node.right;
      }
//      This is the case with only a left subtree or no subtree at all
//      in this situation just swap the node we wish to remove with its left child
      else if (node.right == null)
      {
        return node.left;
      }
//      when removing a node from a binary tree with two links the successor of the node being
//      removed can either be the largest value in the left subtree or the smallest value in the
//      right subtree. As a heuristic, I will remove from the subtree with the greatest height
//      in hopes that this may help with balancing
      else
      {
//        Choose to remove from left subtree
        if (node.left.height > node.right.height)
        {
//          Swap the value of the successor into the node
          T successorValue = findMax(node.left);
          node.value = successorValue;

//          Find the largest node in the left subtree
          node.left = remove(node.left, successorValue);
        }
        else
        {
//          Swap the value of the successor into the node
          T successorValue = findMin(node.right);
          node.value = successorValue;

//          Go into the right subtree and remove the leftmost node we found and swapped data with
//          This prevents us from having duplicate values in the tree
          node.right = remove(node.right, successorValue);
        }
      }
    }

//    Update the balance factor and height values
    update(node);

//    Re-balance tree
    return balance(node);
  }

  /**
   * Helper method to find the node with the min value
   * finds the leftmost node
   *
   * @param node
   * @return
   */
  private T findMin(Node node)
  {
    while (node.left != null)
      node = node.left;

    return node.value;
  }

  /**
   * Helper method to find the node with the max value
   * finds the rightmost node
   *
   * @param node
   * @return
   */
  private T findMax(Node node)
  {
    while (node.right != null)
      node = node.right;

    return node.value;
  }

  /**
   * Make sure all left child nodes are smaller in value than their parent and
   * make sure all right child nodes are greater in value than their parent
   *
   * @param node
   * @return
   */
  public boolean validateBSTInvarient(Node node)
  {
    if (node == null) return true;
    T val = node.value;

    boolean isValid = true;

    if (node.left != null) isValid = isValid && node.left.value.compareTo(val) < 0;
    if (node.right != null) isValid = isValid && node.right.value.compareTo(val) > 0;

    return isValid && validateBSTInvarient(node.left) && validateBSTInvarient(node.right);
  }

  /**
   * Returns as iterator to traverse the tree in order
   *
   * @return
   */
  @Override
  public Iterator<T> iterator()
  {
    final int expectedNodeCount = nodeCount;
    final Stack<Node> stack = new Stack<>();
    stack.push(root);

    return new Iterator<T>()
    {
      Node trav = root;

      @Override
      public boolean hasNext()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();

        while (trav != null && trav.left != null)
        {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != null)
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
