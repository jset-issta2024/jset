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
import java.util.*;

/**
 * IfCmpStmt consists of a comparison expression (a left-hand expression, a
 * comparison operator, and a right-hand expression) that is to be evaluated.
 */
public class IfCmpStmt extends IfStmt
{
  Expr left;
  Expr right;

  /**
   * Constructor.
   *
   * @param comparison
   *        Comparison operator for this if statement.
   * @param left
   *        Expression on the left side of the comparison.
   * @param right
   *        Expression on the right side of the comparison.
   * @param trueTarget
   *        Block executed if comparison evaluates to true.
   * @param falseTarget
   *        Block executed if comparison evaluates to false.
   */  
  public IfCmpStmt(int comparison, Expr left, Expr right,
		   Block trueTarget, Block falseTarget)
  {
    super(comparison, trueTarget, falseTarget);
    this.left = left;
    this.right = right;
    left.setParent(this);
    right.setParent(this);
  }
  
  public Expr left()
  {
    return left;
  }
  
  public Expr right()
  {
    return right;
  }
  
  public void visitForceChildren(TreeVisitor visitor)
  {
    if (visitor.reverse()) {
      right.visit(visitor);
      left.visit(visitor);
    }
    else {
      left.visit(visitor);
      right.visit(visitor);
    }
  }
  
  public void visit(TreeVisitor visitor)
  {
    visitor.visitIfCmpStmt(this);
  }
  
  public Object clone()
  {
    return copyInto(new IfCmpStmt(comparison,
				  (Expr) left.clone(), (Expr) right.clone(),
				  trueTarget, falseTarget));
  }
}
