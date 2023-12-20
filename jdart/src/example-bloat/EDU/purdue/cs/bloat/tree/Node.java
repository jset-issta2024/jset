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

package EDU.purdue.cs.bloat.tree;

import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.util.*;
import java.io.*;
import java.util.*;

/**
 * Node represents a node in an expression tree.  Each Node has a value 
 * number and a key associated with it.  The value number is used to eliminate
 * statements and expressions that have the same value (PRE).  Statements 
 * and expressions of the same value will be mapped to the same value number.  
 *
 * @see Expr
 * @see Stmt
 * @see Tree
 */
public abstract class Node {
  protected Node parent; // This Node's parent in a Tree.
  int key;               // integer used in some analyses.  For instance, when
                         // dead code elimination is performed, key is set to
                         // DEAD or LIVE.
  int valueNumber;       // Used in eliminating redundent statements
  

  /**
   * Constructor.
   */
  public Node()
  {
//      if (Tree.DEBUG) {
//        // We can't print the Node because things aren't initialized yet
//        System.out.println("      new node " +
//  			 System.identityHashCode(this));
//      }
    
    parent = null;
    valueNumber = -1;
    key = 0;
  }

  /**
   * Returns this Node's value number.
   */  
  public int valueNumber()
  {
    return valueNumber;
  }
  
  /**
   * Sets this Node's value number.
   */
  public void setValueNumber(int valueNumber)
  {
//      if (Tree.DEBUG) {
//        System.out.println("      setVN[" + this + "] = " + valueNumber);
//      }
    this.valueNumber = valueNumber;
  }

  /**
   * A Node's key represents an integer value that can be used by an algorithm
   * to mark this node.  For instance, when dead code elimination is performed
   * a Node is marked as DEAD or ALIVE.
   */  
  public int key()
  {
    return key;
  }
  
  public void setKey(int key)
  {
    this.key = key;
  }
  
  /**
   * Visit the children of this node.  Not all Nodes will have children to
   * visit.
   */
  public abstract void visitForceChildren(TreeVisitor visitor);
  public abstract void visit(TreeVisitor visitor);
  
  public void visitChildren(TreeVisitor visitor)
  {
    if (! visitor.prune()) {
      visitForceChildren(visitor);
    }
  }
  
  public void visitOnly(TreeVisitor visitor)
  {
    visitor.setPrune(true);
    visit(visitor);
    visitor.setPrune(false);
  }

  /**
   * Returns the basic block in which this Node resides.
   */
  public Block block()
  {
    Node p = this;
    
    while (p != null) {
      if (p instanceof Tree) {
	return ((Tree) p).block();
      }
      
      p = p.parent;
    }
    
    throw new RuntimeException(this + " is not in a block");
  }
  
  /**
   * Sets the parent Node of this Node.
   */
  public void setParent(Node parent) {
//      if (Tree.DEBUG) {
//        System.out.println("      setting parent of " + this + " (" +
//  			 System.identityHashCode(this) + ") to " + parent);
//      }
    
    this.parent = parent;
  }
  
  public boolean hasParent() {
    return parent != null;
  }
  
  public Node parent() {
    // Note that we can't print this Node because of recursion.  Sigh.
    Assert.isTrue(parent != null, "Null parent for " +
		  this.getClass().toString() + " node " +
		  System.identityHashCode(this));
    return parent;
  }
  
  /**
   * Copies the contents of one Node into another.
   *
   * @param node
   *        A Node from which to copy.
   *
   * @return
   *        node containing the contents of this Node.
   */
  protected Node copyInto(Node node) {
    node.setValueNumber(valueNumber);
    return node;
  }
  
  /**
   * Clean up this Node only.  Does not effect its children nodes.
   */
  public abstract void cleanupOnly();
  
  /**
   * Cleans up this node so that it is independent of the expression 
   * tree in which it resides.  This is usually performed before a Node
   * is moved from one part of an expression tree to another.
   * <p>
   * Traverse the Tree starting at this Node.  Remove the parent of each
   * node and perform any Node-specific cleanup (see cleanupOnly).
   * Sets various pointers to null so that they eventually may be garbage
   * collected.
   */
  public void cleanup()
  {
//      if (Tree.DEBUG) {
//        System.out.println("      CLEANING UP " + this + " " +
//  			 System.identityHashCode(this));
//      }
    
    visit(new TreeVisitor()
	  {
            public void visitNode(Node node)
	      {
                node.setParent(null);
                node.cleanupOnly();
                node.visitChildren(this);
	      }
	  });
  }
  
  /**
   * Replaces this node with another and perform cleanup.
   */
  public void replaceWith(Node node)
  {
    replaceWith(node, true);
  }
  
  /**
   * Replaces this Node with node in its (this's) tree.
   *
   * @param node
   *        The Node with which to replace.
   * @param cleanup
   *        Do we perform cleanup on the tree?
   */
  public void replaceWith(Node node, boolean cleanup)
  {
    // Check a couple of things:
    //   1. The node with which we are replace this does not have a parent.
    //   2. This Node does have a parent. 
    Assert.isTrue(node.parent == null, node + " already has a parent");
    Assert.isTrue(parent != null, this + " has no parent");
    
    Node oldParent = parent;
    
    if (this instanceof Stmt) {
      Assert.isTrue(node instanceof Stmt,
		    "Attempt to replace " + this + " with " + node);
    }
    
    if (this instanceof Expr) {
      Assert.isTrue(node instanceof Expr,
		    "Attempt to replace " + this + " with " + node);
      
      Expr expr1 = (Expr) this;
      Expr expr2 = (Expr) node;
      
      // Make sure the expressions can be interchanged (i.e. their descriptors
      // are compatible).
      Assert.isTrue(expr1.type().simple().equals(expr2.type().simple()),
		    "Type mismatch when replacing " + expr1 + " with " + expr2 +
		    ": " + expr1.type() + " != " + expr2.type());
    }
    
    // Iterate over this parent's tree and replace this with node.
    parent.visit(new ReplaceVisitor(this, node));
    
    Assert.isTrue(node.parent == oldParent,
		  node + " parent == " + node.parent + " != " + oldParent);
    
    if (cleanup) {
      cleanup();
    }
  }
  
  /**
   * @return A textual representation of this Node.
   */
  public String toString() {
    StringWriter w = new StringWriter();
    
    visit(new PrintVisitor(w) {
      protected void println(Object s) { print(s); }
      protected void println() {}
    });
    
    w.flush();
    
    return w.toString();
  }
}
