/*
 * All files in the distribution of BLOAT (Bytecode Level Optimization and
 * Analysis tool for Java(tm)) are Copyright 1997-2001 by the Purdue
 * Research Foundation of Purdue University.  All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms are permitted
 * provided that this entire copyright notice is duplicated in all
 * such copies, and that any documentation, announcements, and other
 * materials related to such distribution and use acknowledge that the
 * software was developed at Purdue University, West Lafayette, IN by
 * Antony Hosking, David Whitlock, and Nathaniel Nystrom.  No charge
 * may be made for copies, derivations, or distributions of this
 * material without the express written consent of the copyright
 * holder.  Neither the name of the University nor the name of the
 * author may be used to endorse or promote products derived from this
 * material without specific prior written permission.  THIS SOFTWARE
 * IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR PURPOSE.
 *
 * <p>
 * Java is a trademark of Sun Microsystems, Inc.
 */

package EDU.purdue.cs.bloat.util;

import java.util.*;

/** 
 * Represents the union-find data structure.
 *
 * <p>
 *
 * Sometimes we need to group elements into disjoint sets.  Two
 * important operations of these sets are finding the set that
 * contains a given element ("find") and uniting two sets ("union").
 * <tt>UnionFind</tt> provides an efficient implementation of a data
 * structure that support these operations on disjoint sets of
 * integers.
 *
 * <p>
 *
 * Each disjoint set is represented by a tree consisting of
 * <tt>Node</tt>s.  (This <tt>Node</tt> is a class local to
 * <tt>UnionFind</tt> and should not be confused with
 * <tt>tree.Node</tt>.)  Each <tt>Node</tt> knows its parent and child
 * and has a rank associated with it.  The parent node is always the
 * root node of the set tree.  A <tt>Node</tt>'s rank is essentially
 * the height of the (sub)tree rooted by that node.  When the union of
 * two trees is formed, the root with the smaller rank is made to
 * point to the root with the larger rank.  Naturally, each
 * <tt>Node</tt> has an integer "value" associated with it.  
 *
 * <p>
 *
 * A good description of union-find can be found in [Cormen, et. al. 
 * 1990].
 */
public class UnionFind {
  // The trees of Nodes that represent the disjoint sets.
  ResizeableArrayList nodes;  

  /**
   * Constructor.
   */
  public UnionFind() {
    nodes = new ResizeableArrayList();
  }

  /** 
   * Constructor.  Make a <tt>UnionFind</tt> with a given number of
   * disjoint sets.  
   */
  public UnionFind(int size) {
    nodes = new ResizeableArrayList(size);
  }

  /**
   * Searches the disjoint sets for a given integer.  Returns the set
   * containing the integer a.  Sets are represented by a local class
   * <tt>Node</tt>.
   */
  public Node findNode(int a) {
    nodes.ensureSize(a+1);

    Node na = (Node) nodes.get(a);

    if (na == null) {
      // Start a new set with a
      Node root = new Node(a);

      root.child = new Node(a);
      root.child.parent = root;

      nodes.set(a, root.child);

      return root;
    }

    return findNode(na);
  }

  /**
   * Returns the integer value associated with the first <tt>Node</tt>
   * in a set.
   */
  public int find(int a) {
    return findNode(a).value;
  }

  /**
   * Finds the set containing a given Node.
   */
  private Node findNode(Node node) {
    Stack stack = new Stack();

    // Find the child of the root element.
    while (node.parent.child == null) {
      stack.push(node);
      node = node.parent;
    }

    // Do path compression on the way back down.
    Node rootChild = node;

    while (! stack.empty()) {
      node = (Node) stack.pop();
      node.parent = rootChild;
    }

    Assert.isTrue(rootChild.parent.child != null);

    return rootChild.parent;
  }

  /**
   * Returns true if a and b are in the same set.
   */
  public boolean isEquiv(int a, int b) {
    return findNode(a) == findNode(b);
  }

  /**
   * Combines the set that contains a with the set that contains b.
   */
  public void union(int a, int b) {
    Node na = findNode(a);
    Node nb = findNode(b);

    if (na == nb) {
      return;
    }

    // Link the smaller tree under the larger.
    if (na.rank > nb.rank) {
      // Delete nb.
      nb.child.parent = na.child;
      na.value = b;

    } else {
      // Delete na.
      na.child.parent = nb.child;
      nb.value = b;

      if (na.rank == nb.rank) {
	nb.rank++;
      }
    }
  }

  class Node {
    Node parent;  // The root of the tree in which this Node resides
    Node child;
    int value;
    int rank;     // This Node's height in the tree

    public Node(int v) {
      value = v;
      rank = 0;
    }
  }
}
