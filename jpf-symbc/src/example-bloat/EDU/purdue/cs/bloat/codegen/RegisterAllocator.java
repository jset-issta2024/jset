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
import java.util.*;

/** 
 * RegisterAllocator performs analysis on a control flow graph and
 * determines the minimum amount of local variables needed in a
 * method.
 *
 * @see LocalVariable 
 */
// Note that RegisterAllocator uses a different IGNode from Liveness!
public class RegisterAllocator {
  FlowGraph cfg;
  Liveness liveness;
  Map colors;
  int colorsUsed;
  final static float MAX_WEIGHT = Float.MAX_VALUE;
  final static float LOOP_FACTOR = 10.0F;
  final static int MAX_DEPTH = 
    (int) (Math.log(MAX_WEIGHT) / Math.log(LOOP_FACTOR));

  /** 
   * Constructor.  Builds an interference graph based on the
   * expression nodes found in liveness.  Traverses the graph and
   * determines which nodes needs to be precolored and which nodes can
   * be coalesced (move statements).  Nodes are coalesced and local
   * variables are assigned to expressions.
   *
   * @see FlowGraph
   * @see LocalVariable 
   */
  public RegisterAllocator(FlowGraph cfg, Liveness liveness) {
    this.cfg = cfg;
    this.liveness = liveness;
    colorsUsed = 0;
    colors = new HashMap();

    // Construct the interference graph.
    final Graph ig = new Graph();

    Iterator iter = liveness.defs().iterator();

    while (iter.hasNext()) {
      VarExpr def = (VarExpr) iter.next();

      if (! (def instanceof LocalExpr)) {
	// Ignore node in the Liveness IG that are not LocalExprs
	continue;
      }

      // Create a new node in the IG, if one does not already exist
      IGNode defNode = (IGNode) ig.getNode(def);

      if (defNode == null) {
	defNode = new IGNode((LocalExpr) def);
	ig.addNode(def, defNode);
      }

      // Examine each variable that interferes with def
      Iterator intersections = liveness.intersections(def);

      while (intersections.hasNext()) {
	VarExpr expr = (VarExpr) intersections.next();

	if (expr == def) {
	  // If for some reason, def interferes with itself, ignore it
	  continue;
	}

	// Add an edge in RegisterAllocator's IG between the variables
	// that interfere
	if (expr instanceof LocalExpr) {
	  IGNode node = (IGNode) ig.getNode(expr);

	  if (node == null) {
	    node = new IGNode((LocalExpr) expr);
	    ig.addNode(expr, node);
	  }

	  ig.addEdge(defNode, node);
	  ig.addEdge(node, defNode);
	}
      }
    }

    // Arrays of expressions that invovle a copy of one local variable
    // to another.  Expressions invovled in copies (i.e. "moves") can
    // be coalesced into one expression.
    final ArrayList copies = new ArrayList();

    // Nodes that are the targets of InitStmt are considered to be precolored.
    final ArrayList precolor = new ArrayList();

    cfg.visit(new TreeVisitor() {
      public void visitBlock(Block block) {
	// Don't visit the sink block.  There's nothing interesting there.
	if (block != RegisterAllocator.this.cfg.sink()) {
	  block.visitChildren(this);
	}
      }

      public void visitPhiStmt(PhiStmt stmt) {
	stmt.visitChildren(this);

	if (! (stmt.target() instanceof LocalExpr)) {
	  return;
	}

	// A PhiStmt invovles an assignment (copy).  So note the copy
	// between the target and all of the PhiStmt's operands in the
	// copies list.

	IGNode lnode = (IGNode) ig.getNode(stmt.target());

	HashSet set = new HashSet();

	Iterator e = stmt.operands().iterator();

	while (e.hasNext()) {
	  Expr op = (Expr) e.next();

	  if (op instanceof LocalExpr && op.def() != null) {
	    if (! set.contains(op.def())) {
	      set.add(op.def());

	      if (op.def() != stmt.target()) {
		IGNode rnode = (IGNode) ig.getNode(op.def());
		copies.add(new IGNode[] {lnode, rnode});
	      }
	    }
	  }
	}
      }

      public void visitStoreExpr(StoreExpr expr) {
	expr.visitChildren(this);

	if (! (expr.target() instanceof LocalExpr)) {
	  return;
	}

	IGNode lnode = (IGNode) ig.getNode(expr.target());

	if (expr.expr() instanceof LocalExpr &&
	    expr.expr().def() != null) {

	  // A store of a variable into another variable is a copy
	  IGNode rnode = (IGNode) ig.getNode(expr.expr().def());
	  copies.add(new IGNode[] {lnode, rnode});
	  return;
	}

	// Treat L := L + k as a copy so that they get converted
	// back to iincs.
	if (expr.target().type().equals(Type.INTEGER)) {
	  if (! (expr.expr() instanceof ArithExpr)) {
	    return;
	  }

	  // We're dealing with integer arithmetic.  Remember that an
	  // ArithExpr has a left and a right operand.  If one of the
	  // operands is a variable and if the other is a constant and
	  // the operation is addition or substraction, we have an
	  // increment. 

	  LocalExpr lhs = (LocalExpr) expr.target();
	  ArithExpr rhs = (ArithExpr) expr.expr();
	  LocalExpr var = null;

	  Integer value = null;

	  if (rhs.left() instanceof LocalExpr &&
	      rhs.right() instanceof ConstantExpr) {

	    var = (LocalExpr) rhs.left();

	    ConstantExpr c = (ConstantExpr) rhs.right();

	    if (c.value() instanceof Integer) {
	      value = (Integer) c.value();
	    }

	  } else if (rhs.right() instanceof LocalExpr &&
		     rhs.left() instanceof ConstantExpr) {

	    var = (LocalExpr) rhs.right();

	    ConstantExpr c = (ConstantExpr) rhs.left();

	    if (c.value() instanceof Integer) {
	      value = (Integer) c.value();
	    }
	  }

	  if (rhs.operation() == ArithExpr.SUB) {
	    if (value != null) {
	      value = new Integer(-value.intValue());
	    }

	  } else if (rhs.operation() != ArithExpr.ADD) {
	    value = null;
	  }

	  if (value != null && var.def() != null) {
	    int incr = value.intValue();

	    if ((short) incr == incr) {
	      // Only generate an iinc if the increment
	      // fits in a short
	      IGNode rnode = (IGNode) ig.getNode(var.def());
	      copies.add(new IGNode[] {lnode, rnode});
	    }
	  }
	}
      }

      public void visitInitStmt(InitStmt stmt) {
	stmt.visitChildren(this);

	// The initialized variables are precolored.
	LocalExpr[] t = stmt.targets();

	for (int i = 0; i < t.length; i++) {
	  precolor.add(t[i]);
	}
      }
    });

    // Coalesce move related nodes, maximum weight first.
    while (copies.size() > 0) {
      // We want the copy (v <- w) with the maximum:
      //    weight(v) + weight(w)
      //    ---------------------
      //        size(union)
      // where union is the intersection of the nodes that conflict
      // with v and the nodes that conflict with w.  This equation
      // appears to be in conflict with the one given on page 38 of
      // Nate's thesis.

      HashSet union;  // The union of neighboring nodes

      int max = 0;

      IGNode[] copy = (IGNode[]) copies.get(max);

      float maxWeight = copy[0].weight + copy[1].weight;
      union = new HashSet();
      union.addAll(ig.succs(copy[0]));
      union.addAll(ig.succs(copy[1]));
      maxWeight /= union.size();

      for (int i = 1; i < copies.size(); i++) {
	copy = (IGNode[]) copies.get(i);

	float weight = copy[0].weight + copy[1].weight;
	union.clear();
	union.addAll(ig.succs(copy[0]));
	union.addAll(ig.succs(copy[1]));
	weight /= union.size();

	if (weight > maxWeight) {
	  // The ith copy has the maximum weight
	  maxWeight = weight;
	  max = i;
	}
      }

      // Remove the copy with the max weight from the copies list.  He
      // does it in a rather round-about way.
      copy = (IGNode[]) copies.get(max);
      copies.set(max, copies.get(copies.size()-1));
      copies.remove(copies.size()-1);

      if (! ig.hasEdge(copy[0], copy[1])) {
	// If the variables involved in the copy do not interfere with
	// each other, they are coalesced. 

	if (CodeGenerator.DEBUG) {
	  System.out.println("coalescing " + copy[0] + " " + copy[1]);
	  System.out.println("    0 conflicts " + ig.succs(copy[0]));
	  System.out.println("    1 conflicts " + ig.succs(copy[1]));
	}

	ig.succs(copy[0]).addAll(ig.succs(copy[1]));
	ig.preds(copy[0]).addAll(ig.preds(copy[1]));

	copy[0].coalesce(copy[1]);

	if (CodeGenerator.DEBUG) {
	  System.out.println("    coalesced " + copy[0]);
	  System.out.println("    conflicts " + ig.succs(copy[0]));
	}

	// Remove coalesced node from the IG
	ig.removeNode(copy[1].key);

	iter = copies.iterator();

	// Examine all copies.  If the copy involves the node that was
	// coalesced, the copy is no longer interesting.  Remove it.
	while (iter.hasNext()) {
	  IGNode[] c = (IGNode[]) iter.next();

	  if (c[0] == copy[1] || c[1] == copy[1]) {
	    iter.remove();
	  }
	}
      }
    }

    // Create a list of uncolored nodes.
    ArrayList uncoloredNodes = new ArrayList();

    Iterator nodes = ig.nodes().iterator();

    while (nodes.hasNext()) {
      IGNode node = (IGNode) nodes.next();

      ArrayList p = new ArrayList(precolor);
      p.retainAll(node.defs);

      // See if any node got coalesced with a precolored node.  
      if (p.size() == 1) {
	// Precolored
	node.color = ((LocalExpr) p.get(0)).index();

	if (CodeGenerator.DEBUG) {
	  System.out.println("precolored " + node + " " + node.color);
	}

      } else if (p.size() == 0) {
	// Uncolored (i.e. not coalesced with any of the pre-colored
	// nodes.
	node.color = -1;
	uncoloredNodes.add(node);

      } else {
	// If two or more pre-colored nodes were coalesced, we have a
	// problem.
	throw new RuntimeException("coalesced pre-colored defs " + p);
      }
    }

    // Sort the uncolored nodes, by decreasing weight.  Wide nodes
    // have half their original weight since they take up two indices
    // and we want to put color nodes with the lower indices.

    Collections.sort(uncoloredNodes, new Comparator() {
      public int compare(Object a, Object b)
	{
	  IGNode na = (IGNode) a;
	  IGNode nb = (IGNode) b;

	  float wa = na.weight / ig.succs(na).size();
	  float wb = nb.weight / ig.succs(nb).size();

	  if (na.wide) {
	    wa /= 2;
	  }

	  if (nb.wide) {
	    wb /= 2;
	  }

	  if (wb > wa) {
	    return 1;
	  }

	  if (wb < wa) {
	    return -1;
	  }

	  return 0;
	}
    });

    nodes = uncoloredNodes.iterator();

    while (nodes.hasNext()) {
      IGNode node = (IGNode) nodes.next();

      if (CodeGenerator.DEBUG) {
	System.out.println("coloring " + node);
	System.out.println("    conflicts " + ig.succs(node));
      }

      // Make sure node has not been colored
      Assert.isTrue(node.color == -1);

      // Determine which colors have been assigned to the nodes
      // conflicting with the node of interest
      BitSet used = new BitSet();

      Iterator succs = ig.succs(node).iterator();

      while (succs.hasNext()) {
	IGNode succ = (IGNode) succs.next();

	if (succ.color != -1) {
	  used.set(succ.color);

	  if (succ.wide) {
	    used.set(succ.color+1);
	  }
	}
      }

      // Find the next available color
      for (int i = 0; node.color == -1; i++) {
	if (! used.get(i)) {
	  if (node.wide) {
	    // Wide variables need two colors
	    if (! used.get(i+1)) {
	      node.color = i;

	      if (CodeGenerator.DEBUG) {
		System.out.println("    assigning color " +
				   i + " to " + node);
	      }

	      if (i+1 >= colorsUsed) {
		colorsUsed = i+2;
	      }
	    }

	  } else {
	    node.color = i;

	    if (CodeGenerator.DEBUG) {
	      System.out.println("    assigning color " +
				 i + " to " + node);
	    }

	    if (i >= colorsUsed) {
	      colorsUsed = i+1;
	    }
	  }
	}
      }
    }

    nodes = ig.nodes().iterator();

    while (nodes.hasNext()) {
      IGNode node = (IGNode) nodes.next();

      // Make sure each node has been colored
      Assert.isTrue(node.color != -1, "No color for " + node);

      iter = node.defs.iterator();

      // Set the index of the variable and all of its uses to be the
      // chosen color.
      while (iter.hasNext()) {
	LocalExpr def = (LocalExpr) iter.next();
	def.setIndex(node.color);

	Iterator uses = def.uses().iterator();

	while (uses.hasNext()) {
	  LocalExpr use = (LocalExpr) uses.next();
	  use.setIndex(node.color);
	}
      }
    }

    if (CodeGenerator.DEBUG) {
      System.out.println("After allocating locals--------------------");
      cfg.print(System.out);
      System.out.println("End print----------------------------------");
    }
  }

  /** 
   * Returns the maximum number of local variables used by the cfg
   * after its "registers" (local variables) have been allocated.  
   */
  public int maxLocals() {
    return colorsUsed;
  }

  /** 
   * Creates a new local variable in this method (as modeled by the
   * cfg).  Updates the number of local variables appropriately.  
   */
  public LocalVariable newLocal(Type type) {
    // Why don't we add Type information to the LocalVariable?  Are we
    // assuming that type checking has already been done and so its a
    // moot point?

    LocalVariable var = new LocalVariable(colorsUsed);
    colorsUsed += type.stackHeight();
    return var;
  }

  /** 
   * IGNode is a node in the interference graph.  Note that this node
   * is different from the one in Liveness.  For instance, this one
   * stores information about a node's color, its weight, etc.
   * Because nodes may be coalesced, an IGNode may represent more than
   * one LocalExpr.  That's why there is a list of definitions.  
   */
  class IGNode extends GraphNode
  {
    Set defs;       
    LocalExpr key; 
    int color;       
    boolean wide;   // Is the variable wide?
    float weight;

    public IGNode(LocalExpr def) {
      color = -1;
      key = def;
      defs = new HashSet();
      defs.add(def);
      wide = def.type().isWide();
      computeWeight();
    }

    /** 
     * Coalesce two nodes in the interference graph.  The weight of
     * the other node is added to that of this node.  This node also
     * inherits all of the definitions of the other node.  
     */
    void coalesce(IGNode node) {
      Assert.isTrue(wide == node.wide);

      weight += node.weight;

      Iterator iter = node.defs.iterator();

      while (iter.hasNext()) {
	LocalExpr def = (LocalExpr) iter.next();
	defs.add(def);
      }
    }

    public String toString() {
      return "(color=" + color + " weight=" + weight + " " +
	defs.toString() + ")";
    }

    /** 
     * Calculates the weight of a Block based on its loop depth.  If
     * the block does not exceed the MAX_DEPTH, then the weight is
     * LOOP_FACTOR raised to the depth.  
     */
    private float blockWeight(Block block) {
      int depth = cfg.loopDepth(block);

      if (depth > MAX_DEPTH) {
	return MAX_WEIGHT;
      }

      float w = 1.0F;

      while (depth-- > 0) {
	w *= LOOP_FACTOR;
      }

      return w;
    }

    /** 
     * Computes the weight of a node in the interference graph.  The
     * weight is based on where the variable represented by this node
     * is used.  The method blockWeight is used to determine the
     * weight of a variable used in a block based on the loop depth of
     * the block.  Special care must be taken if the variable is used
     * in a PhiStmt.  
     */
    private void computeWeight() {
      weight = 0.0F;

      Iterator iter = defs.iterator();

      // Look at all(?) of the definitions of the IGNode
      while (iter.hasNext()) {
	LocalExpr def = (LocalExpr) iter.next();

	weight += blockWeight(def.block());

	Iterator uses = def.uses().iterator();

	// If the variable is used as an operand to a PhiJoinStmt,
	// find the predacessor block to the PhiJoinStmt in which the
	// variable occurs and add the weight of that block to the
	// running total weight.
	while (uses.hasNext()) {
	  LocalExpr use = (LocalExpr) uses.next();

	  if (use.parent() instanceof PhiJoinStmt) {
	    PhiJoinStmt phi = (PhiJoinStmt) use.parent();

	    Iterator preds = cfg.preds(phi.block()).iterator();

	    while (preds.hasNext()) {
	      Block pred = (Block) preds.next();
	      Expr op = phi.operandAt(pred);

	      if (use == op) {
		weight += blockWeight(pred);
		break;
	      }
	    }

	  } else if (use.parent() instanceof PhiCatchStmt) {
	    // If the variable is used in a PhiCatchStmt, add the
	    // weight of the block in which the variable is defined to
	    // the running total.
	    weight += blockWeight(use.def().block());

	  } else {
	    // Just add in the weight of the block in which the
	    // variable is used.
	    weight += blockWeight(use.block());
	  }
	}
      }
    }
  }
}
