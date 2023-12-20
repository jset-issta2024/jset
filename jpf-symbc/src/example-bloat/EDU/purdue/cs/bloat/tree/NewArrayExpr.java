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
 * NewArrayExpr represents the <tt>newarray</tt> opcode which creates a 
 * new array of a specified length and element type.
 */
public class NewArrayExpr extends Expr
{
  // newarray
  
  Expr size;
  Type elementType;

  /**
   * Constructor.
   *
   * @param size
   *        Expression representing the size of the array.
   * @param elementType
   *        The type of the elements in the array.
   * @param type
   *        The type of this expression.
   */  
  public NewArrayExpr(Expr size, Type elementType, Type type)
  {
    super(type);
    this.size = size;
    this.elementType = elementType;
    
    size.setParent(this);
  }
  
  public Expr size()
  {
    return size;
  }
  
  public Type elementType()
  {
    return elementType;
  }
  
  public void visitForceChildren(TreeVisitor visitor)
  {
    if (visitor.reverse()) {
      size.visit(visitor);
    }
    else {
      size.visit(visitor);
    }
  }
  
  public void visit(TreeVisitor visitor)
  {
    visitor.visitNewArrayExpr(this);
  }
  
  public int exprHashCode()
  {
    return 15 + size.exprHashCode();
  }
  
  public boolean equalsExpr(Expr other)
  {
    return false;
  }
  
  public Object clone()
  {
    return copyInto(new NewArrayExpr((Expr) size.clone(),
				     elementType, type));
  }
}
