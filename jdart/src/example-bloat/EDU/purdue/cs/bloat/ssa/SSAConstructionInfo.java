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

package EDU.purdue.cs.bloat.ssa;

import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.tree.*;
import EDU.purdue.cs.bloat.util.*;
import java.util.*;

/**  
 * <tt>SSAConstructionInfo</tt> contains information needed to
 * convert a CFG into SSA form.  Each variable (VarExpr) has an
 * SSAConstructionInfo associated with it.  Each
 * <tt>SSAConstructionInfo</tt> keeps track of information such as the
 * <tt>PhiStmt</tt>s that define copies of the variable, the
 * <tt>Block</tt>s in which the variable is defined, and the
 * occurrences (uses) of the variable in both phi and non-phi
 * statements.  Note that no <tt>PhiStmt</tt> is really inserted into
 * a basic block.  We just keep track of the mapping.  It should also
 * be noted that once a phi statement for a given variable is
 * "inserted" into a block, no other phi statement for that variable
 * is inserted.  Thus, the order of insertion determines the
 * precedence of the phi statements: <tt>PhiReturnStmt</tt> &gt;
 * <tt>PhiCatchStmt</tt> &gt; <tt>PhiJoinStmt</tt>.
 *
 * <p>
 *
 * Additionally, <tt>SSAConstruction</tt> has methods to insert
 * various flavors of <tt>PhiStmt</tt>s whose targets are the variable
 * associated with the <tt>SSAConstruction</tt> into <tt>Block</tt>s.
 *
 * @see SSA
 * @see PhiStmt
 * @see PhiCatchStmt
 * @see PhiJoinStmt
 * @see PhiReturnStmt 
 */
public class SSAConstructionInfo {
  FlowGraph cfg;           // The cfg we're converting into SSA form
  VarExpr prototype;       // The variable we're converting into SSA form
  LinkedList[] reals;      // The real (non-phi) occurrences associated 
                           // with a given node (block)
  LinkedList allReals;     // All the real occurrences of the variable
  PhiStmt[] phis;          // Phi statement associated with a given block
  Set defBlocks;           // Blocks in which variable is defined

  /**
   * Constructor.
   *
   * @param cfg
   *        The control flow graph that is being converted to SSA form.
   * @param expr
   *        A variable in the CFG on which SSA analysis is being done.
   */
  public SSAConstructionInfo(FlowGraph cfg, VarExpr expr) {
    this.cfg = cfg;

    prototype = (VarExpr) expr.clone();
    prototype.setDef(null);

    reals = new LinkedList[cfg.size()];
    allReals = new LinkedList();

    defBlocks = new HashSet();

    phis = new PhiStmt[cfg.size()];
  }

  /** 
   * Returns the program variable associated with this
   * <tt>SSAConstructionInfo</tt>.
   */
  public VarExpr prototype() {
    return prototype;
  }

  /** 
   * Makes note of a <tt>Block</tt> in which the variable is defined
   * by a <tt>PhiStmt</tt>.  
   */
  public void addDefBlock(Block block) {
    defBlocks.add(block);
  }

  /** 
   * Returns the phi statement for the variable represented by this
   * SSAConstructionInfo at a given block in the CFG.  
   */
  public PhiStmt phiAtBlock(Block block) {
    return phis[cfg.preOrderIndex(block)];
  }

  /**
   * Removes the phi statement for this variable at a given block.
   */
  public void removePhiAtBlock(Block block) {
    PhiStmt phi = phis[cfg.preOrderIndex(block)];

    if (phi != null) {
      if (SSA.DEBUG) {
	System.out.println("  removing " + phi + " at " + block);
      }

      phi.cleanup();
      phis[cfg.preOrderIndex(block)] = null;
    }
  }

  /**
   * Adds a <tt>PhiJoinStmt</tt> for the variable represented by this
   * <tt>SSAConstructionInfo</tt> to a given <tt>Block</tt>.  
   */
  public void addPhi(Block block) {
    if (phis[cfg.preOrderIndex(block)] != null) {
      return;
    }

    VarExpr target = (VarExpr) prototype.clone();

    PhiJoinStmt phi = new PhiJoinStmt(target, block);
    phis[cfg.preOrderIndex(block)] = phi;

    if (SSA.DEBUG) {
      System.out.println("  place " + phi + " in " + block);
    }
  }

  /** 
   * Adds a <tt>PhiReturnStmt</tt> to all of the <tt>Block</tt>s that
   * are executed upon returning from a given <tt>Subroutine</tt>.
   *
   * @see PhiReturnStmt
   * @see Subroutine#paths 
   */
  public void addRetPhis(Subroutine sub) {
    Iterator paths = sub.paths().iterator();

    while (paths.hasNext()) {
      Block[] path = (Block[]) paths.next();
      addRetPhi(sub, path[1]);
    }
  }

  /** 
   * Inserts a <tt>PhiCatchStmt</tt> (whose target is the variable
   * represented by this <tt>SSAConstructionInfo</tt>) into a given
   * <tt>Block</tt>.
   *
   * @see PhiCatchStmt 
   */
  public void addCatchPhi(Block block) {
    if (phis[cfg.preOrderIndex(block)] != null) {
      return;
    }

    if (prototype instanceof LocalExpr) {
      LocalExpr target = (LocalExpr) prototype.clone();

      PhiCatchStmt phi = new PhiCatchStmt(target);
      phis[cfg.preOrderIndex(block)] = phi;

      if (SSA.DEBUG) {
	System.out.println("  place " + phi + " in " + block);
      }
    }
  }

  /** 
   * Adds a <tt>PhiReturnStmt</tt> associated with a given
   * <tt>Subroutine</tt>.  The <tt>PhiReturnStmt</tt> is placed in a
   * given block.
   *
   * @see PhiReturnStmt 
   */
  private void addRetPhi(Subroutine sub, Block block) {
    if (phis[cfg.preOrderIndex(block)] != null) {
      return;
    }

    VarExpr target = (VarExpr) prototype.clone();

    PhiReturnStmt phi = new PhiReturnStmt(target, sub);
    phis[cfg.preOrderIndex(block)] = phi;

    if (SSA.DEBUG) {
      System.out.println("  place " + phi + " in " + block);
    }
  }

  /** 
   * Notes a real occurrence (that is, a use that is not an operand
   * to a phi statement) of the variable represented by this
   * <tt>SSAConstructionInfo</tt>.
   *
   * @see PhiStmt 
   */
  public void addReal(VarExpr real) {
    if (real.stmt() instanceof PhiStmt) {
      return;
    }

    Block block = real.block();

    if (real.isDef()) {
      defBlocks.add(block);
    }

    Assert.isTrue(block != null, real + " not in a " + block);

    LinkedList l = reals[cfg.preOrderIndex(block)];

    if (l == null) {
      l = new LinkedList();
      reals[cfg.preOrderIndex(block)] = l;
    }

    l.add(real);
    allReals.add(real);
  }

  /**
   * Returns all of the real occurrences of this variable.
   */
  public Collection reals()
  {
    return allReals;
  }

  /** 
   * Returns all of the real occurrences of this variable in a given
   * block.  
   */
  public Collection realsAtBlock(Block block)
  {
    LinkedList l = reals[cfg.preOrderIndex(block)];

    if (l == null) {
      l = new LinkedList();
      reals[cfg.preOrderIndex(block)] = l;
    }

    return l;
  }

  /**
   * Returns the Blocks containing a definition of the variable represented
   * by this SSAConstruction info.
   */
  public Collection defBlocks()
  {
    return defBlocks;
  } 
}
