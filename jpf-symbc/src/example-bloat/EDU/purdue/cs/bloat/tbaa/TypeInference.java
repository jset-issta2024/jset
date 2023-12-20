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

package EDU.purdue.cs.bloat.tbaa;

import EDU.purdue.cs.bloat.reflect.*;
import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.tree.*;
import EDU.purdue.cs.bloat.ssa.*;
import EDU.purdue.cs.bloat.util.*;
import java.util.*;

/** 
 * Typed-based alias analysis requires that we know the types of all
 * entities in a method.  However, local variables and stack variables
 * do not have declared types.  Thus, we have to infer them.
 *
 * <p>
 *
 * <tt>TypeInference</tt> uses a simpilified version of everyone's
 * favorite type inference algorithm from <u>Object-Oriented Type
 * Systems</u> by Palsberg and Schwartzbach.  It is simplified in that
 * no interprocedural type information is calculated.
 *
 * <p>
 *
 * Here's how it works.  Entities such as method arguments, fields,
 * and constants have a known type.  This known type is expressed in a
 * constraint that constraints the entity to its type.  Operations
 * involving assignment (also phi-statements and stack operations)
 * propagate these constraints to the targets of the assignments.  
 */
public class TypeInference {
  public static boolean DEBUG = false; 

  static final Type UNDEF  = Type.getType("Lundef!;");

  /**
   * Traverses the control flow graph and with the help of a number of 
   * vistors performs type-based alias analysis.
   *
   * @param cfg
   *        The control flow graph on which to perform TBAA.
   * @param hier
   *        A class heirarchy used for getting information about Types
   *        (classes).
   *
   * @see SSAGraph
   * @see ComponentVisitor
   * @see TreeVisitor
   *
   */
  public static void transform(FlowGraph cfg, ClassHierarchy hier) {

    // First go through the CFG and determine the type of the local
    // variables corresponding to method arguments.  Assign those
    // types to all uses of those local variables.
    cfg.visit(new TreeVisitor() {
       public void visitInitStmt(InitStmt stmt) {
	// a := INIT
	// [declared param type] <= [a]

	MethodEditor method = stmt.block().graph().method();

	LocalExpr[] t = stmt.targets();

	for (int i = 0; i < t.length; i++) {
	  LocalExpr var = t[i];

	  int index = var.index();

	  // If the method is static, there is no this pointer in the
	  // local variable table, so decrement index to point to the
	  // indexth local variable.  This is needed with indexing
	  // into method.type().indexedParamTypes().
	  if (! method.isStatic()) {
	    index--;
	  }

	  Type type;

	  if (index == -1) {
	    // If the index is -1, then we're dealing with local
	    // variable 0 of a non-static method.  This local variable
	    // is the this pointer.  Its type is the type of the class
	    // in which the method is declared.
	    type = method.declaringClass().type();

	  } else {
	    // One of the method's arguments is being initialized.
	    // Figure out its type.
	    type = method.type().indexedParamTypes()[index];
	  }

	  var.setType(type);

	  Iterator uses = var.uses().iterator();

	  // Set the type of the uses of the local variable, also.
	  while (uses.hasNext()) {
	    LocalExpr use = (LocalExpr) uses.next();
	    use.setType(type);
	  }
	}
      }

      public void visitExpr(Expr expr) {
	expr.visitChildren(this);

	// We don't know the type of the expression yet.
	expr.setType(UNDEF);
      }
    });

    SSAGraph ssaGraph = new SSAGraph(cfg);

    final TypeInferenceVisitor visitor =
      new TypeInferenceVisitor(hier, ssaGraph);

    // Visit each strong connected component in the SSAGraph and
    // compute the type information using the TypeInferenceVisitor.
    ssaGraph.visitComponents(new ComponentVisitor() {
      public void visitComponent(List scc) {
	  visitor.changed = true; 

	  while (visitor.changed) {
	    visitor.changed = false;

	    Iterator iter = scc.iterator();

	    while (iter.hasNext()) {
	      Node node = (Node) iter.next();
	      node.visit(visitor);
	    }
	  }
	}
    });

    // Convert POS_SHORT types back into SHORT types and POS_BYTE
    // types back into BYTE types.
    cfg.visit(new TreeVisitor() {
      public void visitExpr(Expr expr) {
	expr.visitChildren(this);

	if (expr.type().equals(ClassHierarchy.POS_SHORT)) {
	  expr.setType(Type.SHORT);

	} else if (expr.type().equals(ClassHierarchy.POS_BYTE)) {
	  expr.setType(Type.BYTE);
	}
      }
    });

    if (DEBUG) {
      // Print out all the type information and do some checking.
      cfg.visit(new TreeVisitor() {
	public void visitExpr(Expr expr) {
	  expr.visitChildren(this);

	  System.out.println("typeof(" + expr + ") = " + 
			     expr.type());

	  if (expr.type().equals(UNDEF)) {
	    System.out.println("WARNING: typeof(" + expr +
			       ") = UNDEF");
	  }

	  Assert.isFalse(expr.type().equals(ClassHierarchy.POS_SHORT));
	  Assert.isFalse(expr.type().equals(ClassHierarchy.POS_BYTE));
	}
      });
    }
  }
}

/** 
 * Does most of the type inference work.  It generates the type
 * constraints for the expressions in the CFG.
 */
class TypeInferenceVisitor extends TreeVisitor {
  public boolean changed;
  public ClassHierarchy hier;
  SSAGraph ssaGraph;

  public TypeInferenceVisitor(ClassHierarchy hier, SSAGraph ssaGraph) {
    this.hier = hier;
    this.ssaGraph = ssaGraph;
  }

  public void visitExpr(Expr expr) {
    throw new RuntimeException(expr + " not supported");
  }

  // Make a new start constraint for the expression being shifted.  
  public void visitShiftExpr(ShiftExpr expr) {
    if (expr.expr().type().isIntegral() ||
	expr.expr().type().equals(ClassHierarchy.POS_SHORT) ||
	expr.expr().type().equals(ClassHierarchy.POS_BYTE)) {

      start(expr, Type.INTEGER);

    } else {
      prop(expr, expr.expr());
    }
  }

  // If either of the ArithExpr's operands has type INTEGER, than the
  // entire expression must have type INTEGER.  Otherwise, the type of
  // one of the operands must be undefined, or both operands have the
  // same type.  If the ArithExpr is one of the compare operations,
  // then it has an INTEGER type.
  public void visitArithExpr(ArithExpr expr) {
    if (expr.left().type().isIntegral() ||
	expr.left().type().equals(ClassHierarchy.POS_SHORT) ||
	expr.left().type().equals(ClassHierarchy.POS_BYTE)) {

      start(expr, Type.INTEGER);

    } else if (expr.right().type().isIntegral() ||
	       expr.right().type().equals(ClassHierarchy.POS_SHORT) ||
	       expr.right().type().equals(ClassHierarchy.POS_BYTE)) {

      start(expr, Type.INTEGER);

    } else {
      Assert.isTrue(
		    expr.left().type().equals(TypeInference.UNDEF) ||
		    expr.right().type().equals(TypeInference.UNDEF) ||
		    expr.left().type().equals(expr.right().type()),
		    expr.left() + ".type() = " + expr.left().type() + " != " +
		    expr.right() + ".type() = " + expr.right().type());

      if (expr.operation() == ArithExpr.CMP ||
	  expr.operation() == ArithExpr.CMPL ||
	  expr.operation() == ArithExpr.CMPG) {

	start(expr, Type.INTEGER);

      } else {
	prop(expr, expr.left());
      }
    }
  }

  // If the expression being negated has an integral type, then the
  // type of the expression in an INTEGER.
  public void visitNegExpr(NegExpr expr) {
    if (expr.expr().type().isIntegral() ||
	expr.expr().type().equals(ClassHierarchy.POS_SHORT) ||
	expr.expr().type().equals(ClassHierarchy.POS_BYTE)) {

      start(expr, Type.INTEGER);

    } else {
      prop(expr, expr.expr());
    }
  }

  public void visitReturnAddressExpr(ReturnAddressExpr expr) {
    start(expr, Type.ADDRESS);
  }

  public void visitCheckExpr(CheckExpr expr) {
    prop(expr, expr.expr());
  }

  // Why does it start the expression with an INTEGER type???
  public void visitInstanceOfExpr(InstanceOfExpr expr) {
    start(expr, Type.INTEGER);
  }

  public void visitArrayLengthExpr(ArrayLengthExpr expr) {
    start(expr, Type.INTEGER);
  }

  // If a VarExpr does not define a variable, then propagate the type
  // of the expression that does define the variable to the VarExpr.
  public void visitVarExpr(VarExpr expr) {
    if (! expr.isDef()) {
      if (expr.def() != null) {
	prop(expr, expr.def());
      }
    }
  }

  // Not surprisingly, StackManipStmts are ugly looking.  The type
  // information is propagated from the "before" stack variable to the
  // appropriate "after" stack variable.  As seen in other places, the
  // transformation is given by an integer array.
  public void visitStackManipStmt(StackManipStmt stmt) {
    // a := b
    // [b] <= [a]

    StackExpr[] target = stmt.target();
    StackExpr[] source = stmt.source();

    switch (stmt.kind()) {
    case StackManipStmt.SWAP:
      // 0 1 -> 1 0
      Assert.isTrue(source.length == 2 && target.length == 2,
		    "Illegal statement: " + stmt);
      manip(source, target, new int[] {1, 0});
      break;
    case StackManipStmt.DUP:
      // 0 -> 0 0
      Assert.isTrue(source.length == 1 && target.length == 2,
		    "Illegal statement: " + stmt);
      manip(source, target, new int[] {0, 0});
      break;
    case StackManipStmt.DUP_X1:
      // 0 1 -> 1 0 1
      Assert.isTrue(source.length == 2 && target.length == 3,
		    "Illegal statement: " + stmt);
      manip(source, target, new int[] {1, 0, 1});
      break;
    case StackManipStmt.DUP_X2:
      if (source.length == 3) {
	// 0 1 2 -> 2 0 1 2
	Assert.isTrue(source.length == 3 && target.length == 4,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {2, 0, 1, 2});
      }
      else {
	// 0-1 2 -> 2 0-1 2
	Assert.isTrue(source.length == 2 && target.length == 3,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {1, 0, 1});
      }
      break;
    case StackManipStmt.DUP2:
      if (source.length == 2) {
	// 0 1 -> 0 1 0 1
	Assert.isTrue(target.length == 4,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {0, 1, 0, 1});
      }
      else {
	// 0-1 -> 0-1 0-1
	Assert.isTrue(source.length == 1 && target.length == 2,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {0, 0});
      }
      break;
    case StackManipStmt.DUP2_X1:
      if (source.length == 3) {
	// 0 1 2 -> 1 2 0 1 2
	Assert.isTrue(target.length == 5,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {1, 2, 0, 1, 2});
      }
      else {
	// 0 1-2 -> 1-2 0 1-2
	Assert.isTrue(source.length == 2 && target.length == 3,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {1, 0, 1});
      }
      break;
    case StackManipStmt.DUP2_X2:
      if (source.length == 4) {
	// 0 1 2 3 -> 2 3 0 1 2 3
	Assert.isTrue(target.length == 6,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {2, 3, 0, 1, 2, 3});
      }
      else if (source.length == 3) {
	if (target.length == 5) {
	  // 0-1 2 3 -> 2 3 0-1 2 3
	  manip(source, target, new int[] {1, 2, 0, 1, 2});
	}
	else {
	  // 0 1 2-3 -> 2-3 0 1 2-3
	  Assert.isTrue(target.length == 4,
			"Illegal statement: " + stmt);
	  manip(source, target, new int[] {2, 0, 1, 2});
	}
      }
      else {
	// 0-1 2-3 -> 2-3 0-1 2-3
	Assert.isTrue(source.length == 2 && target.length == 3,
		      "Illegal statement: " + stmt);
	manip(source, target, new int[] {1, 0, 1});
      }
      break;
    }

    stmt.visitChildren(this);
  }

  private void manip(StackExpr[] source, StackExpr[] target, int[] s) {
    for (int i = 0; i < s.length; i++) {
      prop(target[i], source[s[i]]);
    }
  }

  // The type of the expression being stored flows into the target of
  // the store.  The type of the target flows into the StoreExpr.
  public void visitStoreExpr(StoreExpr expr) {
    // a := b
    // [b] <= [a]

    prop(expr.target(), expr.expr());
    prop(expr, expr.target());
  }

  // The type of the exception being caught flows into the type of the
  // CatchExpr.
  public void visitCatchExpr(CatchExpr expr) {
    // Catch(t)
    // t <= [Catch(t)]

    Type catchType = expr.catchType();

    if (catchType == Type.NULL) {
      catchType = Type.THROWABLE;
    }

    start(expr, catchType);
  }

  // The type information of each of the PhiStmt's operands flows into
  // the target.  If an operand is undefined, the type of the target
  // flows into that operand so that it will have a type.
  public void visitPhiStmt(PhiStmt stmt) {
    // a := Phi(b, c)
    // [b] <= [a]
    // [c] <= [a]

    List back = new ArrayList(stmt.operands().size());

    Iterator e = stmt.operands().iterator();

    while (e.hasNext()) {
      Expr expr = (Expr) e.next();

      if (expr instanceof VarExpr && expr.def() == null) {
	back.add(expr);

      } else {
	prop(stmt.target(), expr);
      }
    }

    // Propagate the type back to the operands which are undefined.
    // Otherwise, they won't be able to get a type.
    e = back.iterator();

    while (e.hasNext()) {
      Expr expr = (Expr) e.next();

      if (expr.def() == null) {
	prop(expr, stmt.target());
      }
    }
  }

  // If the ArrayRefExpr is not a definition, then the type of the
  // elements in the array flows into the type of the ArrayRefExpr.
  // This is all contingent upon the type of the array elements being
  // defined, non-object, not serializable, not clonable, and not
  // null.
  public void visitArrayRefExpr(ArrayRefExpr expr) {
    // a[i]
    // [a[i]] <= [element type of a]

    Expr array = expr.array();
    Expr index = expr.index();

    if (! expr.isDef()) {
      if (! array.type().equals(TypeInference.UNDEF) &&
	  ! array.type().equals(Type.OBJECT) &&
	  ! array.type().equals(Type.SERIALIZABLE) &&
	  ! array.type().equals(Type.CLONEABLE) &&
	  ! array.type().isNull()) {

	Assert.isTrue(array.type().isArray(),
		      array + " in " + expr + " (" +
		      array.type() + ") is not an array");
	start(expr, expr.array().type().elementType());
      }
    }
  }

  // The return type of the method flow into the CallMethodExpr.
  public void visitCallMethodExpr(CallMethodExpr expr) {
    // x.m(a), m in class C
    // [x] <= C 
    // [a] <= [declared param type]
    // [declared return type] <= [x.m(a)]

    MemberRef method = expr.method();

    Type[] paramTypes = method.type().paramTypes();

    Type returnType = method.type().returnType();

    start(expr, returnType);
  }

  // The return type of the method flows into the CallStaticExpr.
  public void visitCallStaticExpr(CallStaticExpr expr) {
    // m(a)
    // [a] <= [declared param type]
    // [declared return type] <= [m(a)]

    MemberRef method = expr.method();

    Type[] paramTypes = method.type().paramTypes();

    Type returnType = method.type().returnType();

    start(expr, returnType);
  }

  // The type to which an expression is cast flows into the CastExpr.
  public void visitCastExpr(CastExpr expr) {
    // (C) a
    // [(C) a] <= C 

    start(expr, expr.castType());
  }

  // The type of the constant flows into the type of the ConstantExpr.
  public void visitConstantExpr(ConstantExpr expr) {
    // "a"
    // String <= ["a"]

    Object value = expr.value();

    if (value == null) {
      start(expr, Type.NULL);
      return;
    }

    if (value instanceof String) {
      start(expr, Type.STRING);
      return;
    }

    if (value instanceof Boolean) {
      start(expr, Type.BOOLEAN);
      return;
    }

    if (value instanceof Integer) {
      start(expr, Type.INTEGER);
      return;
    }

    if (value instanceof Long) {
      start(expr, Type.LONG);
      return;
    }

    if (value instanceof Float) {
      start(expr, Type.FLOAT);
      return;
    }

    if (value instanceof Double) {
      start(expr, Type.DOUBLE);
      return;
    }

    int v;

    if (value instanceof Byte) {
      v = ((Byte) value).byteValue();

    } else if (value instanceof Short) {
      v = ((Short) value).shortValue();

    } else if (value instanceof Character) {
      v = ((Character) value).charValue();

    } else {
      throw new RuntimeException();
    }

    if (v >= 0) {
      if (v <= 1) {
	start(expr, Type.BOOLEAN);   // It'll fit in a BOOLEAN

      } else if (v <= Byte.MAX_VALUE) {
	start(expr, ClassHierarchy.POS_BYTE);

      } else if (v <= Short.MAX_VALUE) {
	start(expr, ClassHierarchy.POS_SHORT);

      } else if (v <= Character.MAX_VALUE) {
	start(expr, Type.CHARACTER);

      } else {
	start(expr, Type.INTEGER);
      }

    } else {
      // The constant's value is negative
      if (Byte.MIN_VALUE <= v) {
	start(expr, Type.BYTE);   // It'll fit in a BYTE

      } else if (Short.MIN_VALUE <= v) {
	start(expr, Type.SHORT);

      } else {
	start(expr, Type.INTEGER);
      }
    }
  }

  // If the FieldExpr is a definition, the type of the field flows
  // into the type of the FieldExpr.
  public void visitFieldExpr(FieldExpr expr) {
    // a.f, f in class C
    // [a] <= C
    // [declared field type] <= [a.f]

    MemberRef field = expr.field();

    if (! expr.isDef()) {
      start(expr, field.type());
    }
  }

  // If the expression representing the type of the array being
  // created is defined, then and array of that type flows into the
  // type of the NewArrayExpr.
  public void visitNewArrayExpr(NewArrayExpr expr) {
    // new t[i]
    // array-1-of-t <= [new t[i]]

    if (! expr.elementType().equals(TypeInference.UNDEF)) {
      start(expr, expr.elementType().arrayType());
    }
  }

  // The type of the object being created flows into the NewExpr.
  public void visitNewExpr(NewExpr expr) {
    // new C
    // C <= [new C]

    start(expr, expr.objectType());
  }

  // If the type of the expression specifying the type of the array
  // being created is defined, then that type flows into the type of
  // the NewMultiArrayExpr.
  public void visitNewMultiArrayExpr(NewMultiArrayExpr expr) {
    // new t[i][j][k]
    // array-dim-of-t <= [new t[i][j][k]]

    if (! expr.elementType().equals(TypeInference.UNDEF)) {
      start(expr, expr.elementType().arrayType(expr.dimensions().length));
    }
  }

  // If the field reference is a definition, then the type of the
  // field flows into the type of the StaticFieldExpr.
  public void visitStaticFieldExpr(StaticFieldExpr expr) {
    // C.f
    // [declared field type] <= [C.f]

    MemberRef field = expr.field();

    if (! expr.isDef()) {
      start(expr, field.type());
    }
  }

  // Initializes the constraint propagation by assigning a type to a
  // given expression.  
  private void start(Expr expr, Type type) {
    if (TypeInference.DEBUG) {
      System.out.println("start " + expr + " <- " + type);
    }
      
    if (type.equals(TypeInference.UNDEF)) {
      return;
    }
      
    if (! expr.type().equals(TypeInference.UNDEF)) {
      if (TypeInference.DEBUG) {
	System.out.print("union of " + expr.type() + " and " + type);
      }
	
      if (! type.isIntegral() &&
	  ! type.equals(ClassHierarchy.POS_BYTE) &&
	  ! type.equals(ClassHierarchy.POS_SHORT)) {
	Assert.isTrue(type.simple().equals(expr.type().simple()));
	  
	if (type.isReference()) {
	  // The expr and type may have different types.  So, deal
	  // with the common supertype of both type and expr.type().
	  // That is, the type to which both belong.

	  type = hier.unionType(type, expr.type());
	}

      } else {
	// We're dealing with one of the integral types.  Take the
	// union of the two types and work with that.
	BitSet v1 = ClassHierarchy.typeToSet(type);
	BitSet v2 = ClassHierarchy.typeToSet(expr.type());
	v1.or(v2);
	type = ClassHierarchy.setToType(v1); 
      }
	
      if (TypeInference.DEBUG) {
	System.out.println(" is " + type);
      }
    }
      
    // Set the type of all nodes equivalent to expr to the type we're
    // working with.
    Iterator iter = ssaGraph.equivalent(expr).iterator();
      
    while (iter.hasNext()) {
      Node node = (Node) iter.next();
	
      if (node instanceof Expr) {
	Expr e = (Expr) node;
	  
	if (e.setType(type)) {
	  changed = true;
	}
      }
    }
  }

  // Propagates the type of the source expression to all expressions
  // equivalent to the other expr.
  private void prop(Expr expr, Expr source) {
    if (TypeInference.DEBUG) {
      System.out.println("prop " + expr + " <- " + source);
    }
    start(expr, source.type());
  }

}
