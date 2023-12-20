/**
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

package EDU.purdue.cs.bloat.trans;

import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.tree.*;
import EDU.purdue.cs.bloat.ssa.*;
import EDU.purdue.cs.bloat.tbaa.*;
import EDU.purdue.cs.bloat.util.*;
import java.util.*;

/**
 * Performs copy and constant propagation on the blocks in a control
 * flow graph.
 */
public class ExprPropagation {
  public static boolean DEBUG = false;

  FlowGraph cfg;
  boolean changed;             // Did the cfg change?

  /**
   * Constructor.
   *
   * @param cfg
   *        The control flow graph on which expression propagation is being
   *        performed.
   */
  public ExprPropagation(FlowGraph cfg) {
    this.cfg = cfg;
  }

  /**
   * Performs the propagation.
   */
  public void transform() {
    changed = true;

    while (changed) { 
      changed = false;
      propagate();
    }
  }

  /**
   * Propagate expressions through the control flow graph in hopes of
   * reducing the number of local variables.
   */
  private void propagate() {
    cfg.visit(new TreeVisitor() {
      Iterator iter;

      public void visitTree(Tree tree) {
	iter = tree.stmts().iterator();

	while (iter.hasNext()) {
	  Stmt stmt = (Stmt) iter.next();
	  stmt.visit(this);
	}
      }

      public void visitStoreExpr(StoreExpr expr) {
	expr.visitChildren(this);

	if (! (expr.target() instanceof LocalExpr)) {
	  // If we're not assigning to a local variable, fergit it
	  return;
	}

	LocalExpr lhs = (LocalExpr) expr.target();
	Expr rhs = expr.expr();

	// L := (M := E)
	// use L
	// use M
	//
	// -->
	//
	// L := E
	// use L
	// use L
	//
	// Since we've already visited (M := E), M could not be
	// eliminated.  So, after propagating M to L, we won't be
	// able to eliminate L either, so don't even try.
	//
	if (rhs instanceof StoreExpr) {
	  StoreExpr store = (StoreExpr) rhs;

	  MemExpr rhsLHS = store.target();
	  Expr rhsRHS = store.expr();

	  if (rhsLHS instanceof LocalExpr) {
	    // Replace uses of M with L.
			
	    // We need to make a copy of the lhs since it is a
	    // def an consequently does not contain a pointer to
	    // a def. 	
	    LocalExpr copy = (LocalExpr) lhs.clone();
	    copy.setDef(lhs);

	    if (propExpr(expr.block(), (LocalExpr) rhsLHS, copy)) {
	      // If all uses of the rhsRHS local variable were
	      // replaced, replace all occurrences of the rhs with the
	      // local variable of rhsRHS.
	      changed = true;

	      expr.visit(new ReplaceVisitor(rhs, rhsRHS));
	      rhsLHS.cleanup();
	      rhs.cleanupOnly();
	    }

	    // Be sure to cleanup the copy.
	    copy.cleanup();
	  }

	} 
	// This next part is awful and comented out. Propagating
	// local variables like this fails to take into account
	// the live ranges. When we have L := M, it replaces L with
	// M after M has been overwritten. Arg.
	/*
	else if (rhs instanceof LeafExpr) {     
	  if (propExpr(expr.block(), lhs, rhs)) {
	    // If all uses of the local variable in the lhs were
	    // replaced with the LeafExpr in the rhs, then the store
	    // (L := X) is useless.  Replace it with (eval X) so it
	    // can be removed later.
	    changed = true;

	    // Replace eval (L := X) with eval X
	    // Dead code elimination will remove it.
	    if (expr.parent() instanceof ExprStmt)
	      iter.remove();
	    else
	      expr.replaceWith((Expr) rhs.clone());
	  }
	} 
	*/
      } 

      public void visitPhiStmt(PhiStmt stmt) {
	Expr lhs = stmt.target();

	if (! (lhs instanceof LocalExpr)) {
	  // If we're not assigning into a local variable, fergit it
	  return;
	}

	// Look at all of the operands of the PhiStmt.  If all of the
	// operands are either the same local variable or the same
	// constant, then propagate an operand (doesn't matter which
	// one because they're all the same value) to all uses of the
	// target of the PhiStmt.
	Iterator e = stmt.operands().iterator();

	if (! e.hasNext()) {
	  return;
	}

	Expr rhs = (Expr) e.next();

	if (! (rhs instanceof LeafExpr)) {
	  return;
	}

	while (e.hasNext()) {
	  Expr operand = (Expr) e.next();

	  if (rhs instanceof LocalExpr) {
	    if (operand instanceof LocalExpr) {
	      if (rhs.def() != operand.def()) {
		return;
	      }
	    } else {
	      return;
	    }

	  } else if (rhs instanceof ConstantExpr) {
	    if (! rhs.equalsExpr(operand)) {
	      return;
	    }

	  } else {
	    return;
	  }
	}

	if (propExpr(stmt.block(), (LocalExpr) lhs, rhs)) {
	  // If all uses of the PhiStmt's target were replaced, remove
	  // it from the expression tree.
	  changed = true;
	  iter.remove();
	}
      }
    });
  }

  /**
   * Propagates the expression in rhs to all uses of the lhs.  Returns
   * true, if all of the uses of the lhs were replaced.
   */
  boolean propExpr(Block block, LocalExpr lhs, Expr rhs) {
    if (DEBUG) {
      System.out.println("prop " + rhs + " to uses of " + lhs);
      System.out.println("    uses of lhs = " + lhs.uses());
    }

    if (rhs instanceof LocalExpr) {
      // We can't prop a local to a PhiStmt operand, so don't bother
      // doing the propagation at all.
       Iterator e = lhs.uses().iterator();

      while (e.hasNext()) {
	LocalExpr use = (LocalExpr) e.next();
	    
	if (use.parent() instanceof PhiStmt) {
	  return false;
	}
      }


      // Replaces all uses of the lhs with the rhs.  Both are local
      // variables.
      e = lhs.uses().iterator();

      while (e.hasNext()) {
	LocalExpr use = (LocalExpr) e.next();
	use.replaceWith((Expr) rhs.clone());
      }

      return true;

    } else {
      boolean replacedAll = true;

      Iterator e = lhs.uses().iterator();

      while (e.hasNext()) {
	LocalExpr use = (LocalExpr) e.next();
	    
	if (use.parent() instanceof PhiCatchStmt) {
	  replacedAll = false;
	}
	else {
	  use.replaceWith((Expr) rhs.clone());
	}
      }

      return replacedAll;
    }
  }
}
