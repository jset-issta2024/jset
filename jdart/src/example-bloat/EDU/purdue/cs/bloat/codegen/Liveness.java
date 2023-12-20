/**
 * All files in the distribution of BLOAT (Bytecode Level Optimization and
 * Analysis tool for Java(tm)) are Copyright 1997-2001 by the Purdue
 * Research Foundation of Purdue University.  All rights reserved.
 *
 * <p>
 *
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

package EDU.purdue.cs.bloat.codegen;

import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.tree.*;
import EDU.purdue.cs.bloat.util.*;
import java.io.*;
import java.util.*;

/** 
 * Liveness represents the interference graph of the local variables
 * contained in a control flow graph.
 *
 * When the liveness of two variables overlap each other, the two variables are
 * said to <i>interfere</i> with each other.  The interference graph represents
 * this relationship between variables.  There is an (un-directed) edge between
 * variables <tt>a</tt> and <tt>b</tt> in the interference graph if variable 
 * <tt>a</tt> interferes with variable <tt>b</tt>.  
 */
public class Liveness {
  public static boolean DEBUG = false;
  public static boolean UNIQUE = false;

  public static final boolean BEFORE = false;
  public static final boolean AFTER  = true;

  FlowGraph cfg;
  Graph ig;

  /**
   * Constructor.
   *
   * @param cfg
   *        Control flow graph on which to perform liveness analysis.
   */
  public Liveness(FlowGraph cfg) {
    this.cfg = cfg;
    computeIntersections();
  }

  /**
   * Removes a local expression from the interference graph.
   */
  public void removeVar(LocalExpr expr) {
    ig.removeNode(expr);
  }

  /**
   * Should not be called.
   */
  public boolean liveAtUse(final VarExpr isLive,
			   final VarExpr at, final boolean after) {
    throw new RuntimeException();
  }

  /**
   * Should not be called.
   */
  public boolean liveAtStartOfBlock(VarExpr isLive, Block block) {
    throw new RuntimeException();
  }

  /**
   * Should not be called.
   */
  public boolean liveAtEndOfBlock(VarExpr isLive, Block block) {
    throw new RuntimeException();
  }

  /** 
   * Returns the <tt>LocalExpr</tt>s (variables) that occur in the
   * CFG.  They correspond to nodes in the interference graph.  
   */
  public Collection defs()
  {
    return ig.keySet();
  }

  /**
   * Returns an <tt>Iterator</tt> of <tt>LocalExpr</tt>s that interfere
   * with a given <tt>VarExpr</tt>.
   */
  public Iterator intersections(VarExpr a)
  {
    Assert.isTrue(a != null, "Cannot get intersections for null def");
    Assert.isTrue(a.isDef(), "Cannot get intersections for variable uses");

    final GraphNode node = ig.getNode(a);

    Assert.isTrue(node != null, "Cannot find IG node for " + a);

    return new Iterator() {
      Iterator succs = ig.succs(node).iterator();

      public boolean hasNext() {
	return succs.hasNext();
      }

      public Object next() {
	IGNode next = (IGNode) succs.next();
	return next.def;
      }

      public void remove() {
	throw new RuntimeException();
      }
    };
  }

  /**
   * Determines whether or not two variables interfere with one another.
   */
  public boolean liveRangesIntersect(VarExpr a, VarExpr b) {
    Assert.isTrue(a != null && b != null,
		  "Cannot get intersections for null def");
    Assert.isTrue(a.isDef() && b.isDef(),
		  "Cannot get intersections for variable uses");

    if (a == b) {
      return false;
    }

    // If all locals should have unique colors, return true.
    if (UNIQUE) {
      return true;
    }

    IGNode na = (IGNode) ig.getNode(a);
    IGNode nb = (IGNode) ig.getNode(b);

    Assert.isTrue(na != null && nb != null);

    return ig.hasEdge(na, nb);
  }

  /**
   * Constructs the interference graph.
   */
  private void computeIntersections() {
    ig = new Graph();      // The interference graph

    if (Liveness.DEBUG) {
      System.out.println("-----------Computing live ranges-----------");
    }

    // All of the nodes (IGNodes) in the IG
    final List defNodes = new ArrayList();

    // The IGNodes whose local variable is defined by a PhiCatchStmt
    final List phiCatchNodes = new ArrayList();

    // An array of NodeInfo for each node in the CFG (indexed by the
    // node's pre-order index).  Gives information about the local
    // variables (nodes in the IG) that are defined in each block.
    // The NodeInfos are stored in reverse order.  That is, the
    // NodeInfo for the final variable occurrence in the block is the
    // first element in the list.
    final List[] nodes = new ArrayList[cfg.size()];    

    // We need to keep track of the order of the statements in which
    // variables occur.  There is an entry in nodeIndices for each
    // block in the CFG.  Each entry consists of a mapping between a
    // statement in which a variable occurs and the number of the
    // statement (with respect to the other statements in which
    // variables occur) of interest.  This is hard to explain in
    // words.  This numbering comes into play in the liveOut method.
    final Map[] nodeIndices = new HashMap[cfg.size()];

    Iterator iter = cfg.nodes().iterator();

    // Initialize nodes and nodeIndices
    while (iter.hasNext()) {
      Block block = (Block) iter.next();
      int blockIndex = cfg.preOrderIndex(block);
      nodes[blockIndex] = new ArrayList();
      nodeIndices[blockIndex] = new HashMap();
    }

    // Go in trace order.  Code generation for phis in the presence of
    // critical edges depends on it!

    iter = cfg.trace().iterator();

    // When performing liveness analysis, we traverse the tree from
    // the bottom up.  That is, we do a REVERSE traversal.
    while (iter.hasNext()) {
      final Block block = (Block) iter.next();

      block.visit(new TreeVisitor(TreeVisitor.REVERSE) {
	public void visitPhiJoinStmt(PhiJoinStmt stmt) {
	  if (! (stmt.target() instanceof LocalExpr)) {
	    return;
	  }

	  LocalExpr target = (LocalExpr) stmt.target();

	  // Examine each predacessor and maintain some information
	  // about the definitions.  Remember that we're dealing with
	  // a PhiJoinStmt.  The predacessors of PhiJoinStmts are
	  // statements that define or use the local (SSA) variable.
	  Iterator preds = cfg.preds(block).iterator();

	  while (preds.hasNext()) {
	    Block pred = (Block) preds.next();
	    int predIndex = cfg.preOrderIndex(pred);

	    List n = nodes[predIndex];
	    Map indices = nodeIndices[predIndex];

	    indices.put(stmt, new Integer(n.size()));
	    NodeInfo info = new NodeInfo(stmt);
	    n.add(info);

	    // Make a new node in the interference graph for target,
	    // if one does not already exists
	    IGNode node = (IGNode) ig.getNode(target);

	    if (node == null) {
	      node = new IGNode(target);
	      ig.addNode(target, node);
	      defNodes.add(node);
	    }

	    info.defNodes.add(node);
	  }
	}

	public void visitPhiCatchStmt(PhiCatchStmt stmt)
	  {
	  }

	public void visitStmt(Stmt stmt)
	  {
	  }
      });
    }

    iter = cfg.trace().iterator();

    while (iter.hasNext()) {
      final Block block = (Block) iter.next();
      final int blockIndex = cfg.preOrderIndex(block);

      block.visit(new TreeVisitor(TreeVisitor.REVERSE) {
	Node parent = null;

	public void visitNode(Node node) {
	  Node p = parent;
	  parent = node;
	  node.visitChildren(this);
	  parent = p;
	}

	public void visitLocalExpr(LocalExpr expr) {
	  Assert.isTrue(parent != null);

	  // Recall that a LocalExpr represents a use or a definition
	  // of a local variable.  If the LocalExpr is defined by a
	  // PhiJoinStmt, the block in which it resides should already
	  // have some information about it.

	  NodeInfo info;

	  List n = nodes[blockIndex];
	  Map indices = nodeIndices[blockIndex];

	  Integer i = (Integer) indices.get(parent);

	  if (i == null) {
	    if (DEBUG) {
	      System.out.println("adding " + parent +
				 " at " + n.size());
	    }

	    indices.put(parent, new Integer(n.size()));
	    info = new NodeInfo(parent);
	    n.add(info);

	  } else {
	    if (DEBUG) {
	      System.out.println("found " + parent + " at " + i);
	    }

	    info = (NodeInfo) n.get(i.intValue());
	    Assert.isTrue(info != null);
	  }

	  if (expr.isDef()) {
	    IGNode node = (IGNode) ig.getNode(expr);

	    if (node == null) {
	      node = new IGNode(expr);
	      ig.addNode(expr, node);
	      defNodes.add(node);
	    }

	    info.defNodes.add(node);
	  }
	}

	public void visitPhiCatchStmt(PhiCatchStmt stmt) {
	  NodeInfo info;

	  List n = nodes[blockIndex];
	  Map indices = nodeIndices[blockIndex];

	  Integer i = (Integer) indices.get(stmt);

	  if (i == null) {
	    if (DEBUG) {
	      System.out.println("adding " + stmt +
				 " at " + n.size());
	    }

	    indices.put(stmt, new Integer(n.size()));
	    info = new NodeInfo(stmt);
	    n.add(info);

	  } else {
	    if (DEBUG) {
	      System.out.println("found " + parent + " at " + i);
	    }

	    info = (NodeInfo) n.get(i.intValue());
	    Assert.isTrue(info != null);
	  }

	  LocalExpr target = (LocalExpr) stmt.target();

	  IGNode node = (IGNode) ig.getNode(target);

	  if (node == null) {
	    node = new IGNode(target);
	    ig.addNode(target, node);
	    defNodes.add(node);
	    phiCatchNodes.add(node);
	  }

	  info.defNodes.add(node);
	}

	public void visitPhiJoinStmt(PhiJoinStmt stmt)
	  {
	  }
      });
    }

    // Iterate over all of the nodes in the IG
    int numDefs = defNodes.size();

    for (int i = 0; i < numDefs; i++) {
      IGNode node = (IGNode) defNodes.get(i);
      LocalExpr def = node.def;

      // Set of blocks where this variable is live out (i.e. live on
      // any of the block's outgoing edges).
      BitSet m = new BitSet(cfg.size());

      Iterator uses = def.uses().iterator();

      // Look at each use of the local variable  
      while (uses.hasNext()) {
	LocalExpr use = (LocalExpr) uses.next();
	Node parent = use.parent();

	if (parent instanceof MemRefExpr &&
	    ((MemRefExpr) parent).isDef()) {
	  parent = parent.parent();
	}

	// Skip catch-phis.  We handle this later.
	if (parent instanceof PhiCatchStmt) {
	  // If we want to be less conservative:
	  // Need to search back from the operand from all
	  // points in the protected region where it is live
	  // back to the def of the operand.  For each block
	  // in protected region, if the operand def is closest
	  // dominator of the block
	  continue;
	}

	if (DEBUG) {
	  System.out.println("searching for " + def +
			     " from " + parent);
	}

	Block block = parent.block();

	if (parent instanceof PhiJoinStmt) {
	  PhiJoinStmt phi = (PhiJoinStmt) parent;

	  // The local variable (LocalExpr) occurs within a
	  // PhiJoinStmt.  Look at the predacessors of the
	  // PhiJoinStmt.  Recall that each predacessor defines one of
	  // the operands to the PhiJoinStmt.  Locate the block that
	  // defines the LocalExpr in question.  Call liveOut to
	  // determine for which nodes the LocalExpr is live out.

	  // Examine the predacessors of the block containing the
	  // LocalExpr
	  Iterator preds = cfg.preds(block).iterator();

	  while (preds.hasNext()) {
	    Block pred = (Block) preds.next();

	    if (phi.operandAt(pred) == use) {
	      Map indices = nodeIndices[cfg.preOrderIndex(pred)];
	      Integer index = (Integer) indices.get(parent);
	      Assert.isTrue(index != null,
			    "No index for " + parent);

	      liveOut(m, nodes, pred, index.intValue(), node,
		      phiCatchNodes);
	      break;
	    }
	  }

	} else {
	  // The LocalExpr is define in a non-Phi statement.  Figure
	  // out which number definition define the LocalExpr in quest
	  // and call liveOut to compute the set of block in which the
	  // LocalExpr is live out.

	  Map indices = nodeIndices[cfg.preOrderIndex(block)];
	  Integer index = (Integer) indices.get(parent);
	  Assert.isTrue(index != null, "No index for " + parent);

	  liveOut(m, nodes, block, index.intValue(), node,
		  phiCatchNodes);
	}
      }
    }

    // Go through all of the variables that are defined by
    // PhiCatchStmts and make them (the variables) conflict with
    // everything that the operands of the PhiCatchStmt conflict
    // with.  See liveOut for a discussion.

    int numPhiCatches = phiCatchNodes.size();

    for (int i = 0; i < numPhiCatches; i++) {
      IGNode node = (IGNode) phiCatchNodes.get(i);

      PhiCatchStmt phi = (PhiCatchStmt) node.def.parent();

      Iterator operands = phi.operands().iterator();

      while (operands.hasNext()) {
	LocalExpr operand = (LocalExpr) operands.next();
	LocalExpr def = (LocalExpr) operand.def();

	if (def != null) {
	  IGNode opNode = (IGNode) ig.getNode(def);

	  // Conflict with everything the operand conflicts with.
	  Iterator edges = new ImmutableIterator(ig.succs(opNode));

	  while (edges.hasNext()) {
	    IGNode otherNode = (IGNode) edges.next();

	    if (otherNode != node) {
	      if (DEBUG) {
		System.out.println(otherNode.def +
				   " conflicts with " + opNode.def +
				   " and thus with " + node.def);
	      }

	      ig.addEdge(otherNode, node);
	      ig.addEdge(node, otherNode);
	    }
	  }
	}
      }
    }

    if (DEBUG) {
      System.out.println("Interference graph =");
      System.out.println(ig);
    }
  }

  /** 
   * Computes (a portion of) the "live out" set for a given local
   * variable.  If a variable is live on a block's outgoing edge in
   * the CFG, then it is "live out" at that block.
   *
   * @param m
   *        Bit vector that indicates the block for which block the 
   *        defNode is live out
   * @param nodes
   *        The NodeInfo for the local variables used or defined in
   *        each block
   * @param block
   *        The block in which the LocalExpr of interest is defined
   * @param nodeIndex
   *        Which number definition in the defining block
   * @param defNode
   *        The node in the IG whose live out set we are interested in
   * @param phiCatchNode
   *        The nodes in the interference graph that represent local
   *        variables defined by PhiCatchStmts 
   */
  // Nate sez:
  //
  // In a PhiJoin pred, add
  //	...
  //	phi-target := phi-operand
  //	jump with throw succs
  //
  // Don't kill Phi targets in protected blocks
  // The phi target and operand don't conflict

  void liveOut(BitSet m, List[] nodes, Block block,
	       int nodeIndex, IGNode defNode, 
	       Collection phiCatchNodes) {
    boolean firstNode = true;

    int blockIndex = cfg.preOrderIndex(block);

    ArrayList stack = new ArrayList();

    Pos pos = new Pos();
    pos.block = block;
    pos.blockIndex = blockIndex;
    pos.nodeIndex = nodeIndex;

    stack.add(pos);

    while (! stack.isEmpty()) {
      pos = (Pos) stack.remove(stack.size()-1);

      block = pos.block;
      blockIndex = pos.blockIndex;
      nodeIndex = pos.nodeIndex;

      if (DEBUG) {
	System.out.println(defNode.def + " is live at position " +
			   nodeIndex + " of " + block);
      }

      boolean stop = false;

      // The nodes are sorted in reverse.  So, the below gets all of
      // the nodes defined at this block after nodeIndex.  I believe
      // this is an optimization so we don't calculate things twice.
      // Or maybe its how we get things to terminate.
      ListIterator iter = nodes[blockIndex].listIterator(nodeIndex);

      while (! stop && iter.hasNext()) {
	NodeInfo info = (NodeInfo) iter.next();

	if (DEBUG) {
	  System.out.println(defNode.def + " is live at " +
			     info.node);
	}

	if (firstNode) {
	  // We don't care about the definition in the block that
	  // defines the LocalExpr of interest.
	  firstNode = false;
	  continue;
	}

	// Look at all (?) of the definitions of the LocalExpr
	Iterator e = info.defNodes.iterator();

	while (e.hasNext()) {
	  IGNode node = (IGNode) e.next();

	  Iterator catchPhis = phiCatchNodes.iterator();

	  // Calculating the live region of the target of a phi-catch
	  // node is a little tricky.  The target (variable) must be
	  // live throughout the protected region as well as after the
	  // PhiCatchStmt (its definition).  However, we do not want
	  // the phi-catch target to conflict (interfere) with any of
	  // its operands.  So, we make the target conflict with all
	  // of the variables that its operand conflict with.  See
	  // page 37 of Nate's Thesis.

	PHIS:
	  while (catchPhis.hasNext()) {
	    IGNode catchNode = (IGNode) catchPhis.next();

	    PhiCatchStmt phi = (PhiCatchStmt)
	      catchNode.def.parent();

	    Handler handler = (Handler)
	      cfg.handlersMap().get(phi.block());

	    Assert.isTrue(handler != null,
			  "Null handler for " + phi.block());

	    if (handler.protectedBlocks().contains(block)) {
	      Iterator operands = phi.operands().iterator();

	      // If the block containing the LocalExpr in question
	      // resides inside a protected region.  Make sure that
	      // the LocalExpr is not one of the operands to the
	      // PhiCatchStmt associated with the protected region.

	      while (operands.hasNext()) {
		LocalExpr expr = (LocalExpr) operands.next();

		if (expr.def() == node.def) {
		  continue PHIS;
		}
	      }

	      if (DEBUG) {
		System.out.println(defNode.def +
				   " conflicts with " + node.def);
	      }
	
	      // Hey, wow.  The variable defined in the phi-catch
	      // interferes with the variable from the worklist.
	      ig.addEdge(node, catchNode);
	      ig.addEdge(catchNode, node);
	    }
	  }

	  if (node != defNode) {
	    if (DEBUG) {
	      System.out.println(defNode.def +
				 " conflicts with " + node.def);
	    }

	    // If the node in the worklist is not the node we started
	    // with, then they conflict.
	    ig.addEdge(node, defNode);
	    ig.addEdge(defNode, node);

	  } else {
	    if (DEBUG) {
	      System.out.println("def found stopping search");
	    }

	    // We've come across a definition of the LocalExpr in
	    // question, so we don't need to do any more.
	    stop = true;
	  }
	}
      }

      if (! stop) {
	// Propagate the liveness to each of the predacessors of the
	// block in which the variable of interest is defined.  This
	// is accomplished by setting the appropriate bit in m.  We
	// also add another Pos to the worklist to work on the
	// predacessor block.
	Iterator preds = cfg.preds(block).iterator();

	while (preds.hasNext()) {
	  Block pred = (Block) preds.next();
	  int predIndex = cfg.preOrderIndex(pred);

	  if (DEBUG) {
	    System.out.println(defNode.def +
			       " is live at end of " + pred);
	  }

	  if (! m.get(predIndex)) {
	    pos = new Pos();
	    pos.block = pred;
	    pos.blockIndex = predIndex;

	    // Look at all of the statements in which a variable occur
	    pos.nodeIndex = 0; 

	    m.set(predIndex);
	    stack.add(pos);
	  }
	}
      }
    }
  }

  /** 
   * Represents a node in the interference graph.  Connected nodes in
   * the interference graph interfere with each other.  That is, their
   * live regions 
   */
  class IGNode extends GraphNode {
    LocalExpr def;

    /**
     * Constructor.
     *
     * @param def
     *        The local variable represented by this node.
     */
    public IGNode(LocalExpr def)
    {
      this.def = def;
    }

    public String toString()
    {
      return def.toString();
    }
  }

  /** 
   * Stores information about each Node in an expression tree (!)
   * that defines a local variable (i.e. PhiJoinStmt, PhiCatchStmt,
   * and the parent of a LocalExpr).
   */
  class NodeInfo {
    Node node;      // Node in an expression tree in which a variable occurs
    List defNodes;  // node(s) in IG that define above Node

    public NodeInfo(Node node) {
      this.node = node;
      defNodes = new ArrayList();
    }
  }

  class Key {
    int blockIndex;
    Node node;

    public Key(Node node, int blockIndex) {
      this.blockIndex = blockIndex;
      this.node = node;
    }

    public int hashCode() {
      return node.hashCode() ^ blockIndex;
    }

    public boolean equals(Object obj) {
      if (obj instanceof Key) {
	Key key = (Key) obj;
	return key.node == node && key.blockIndex == blockIndex;
      }

      return false;
    }
  }

  /**
   * A Pos is an element in the worklist used to determine the live
   * out set of a given LocalExpr.  It consists of the block in which
   * a local variable definition occurs, the block's index
   * (i.e. pre-order traversal number) in the CFG, and the number of
   * the definition in the block that defines the LocalExpr of
   * interest.  
   */
  class Pos {
    Block block;
    int blockIndex;
    int nodeIndex;
  }
}
