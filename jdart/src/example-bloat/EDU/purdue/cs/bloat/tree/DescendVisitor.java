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


import java.util.*;

/**
 * DecsendVisitor is the superclass of a few private classes of
 * Type0Visitor and Type1Visitor. It descends the tree, keeping track
 * of the number of right links that have been taken.  
 */
public abstract class DescendVisitor extends TreeVisitor {

    Hashtable useInfoMap;
    Hashtable defInfoMap;
    boolean found;
    Node beginNode;        //where this visitor starts its search from
    LocalExpr start;   //where the original search began
    int exchangeFactor;


    public DescendVisitor(Hashtable useInfoMap, Hashtable defInfoMap) {
	this.useInfoMap = useInfoMap;
	this.defInfoMap = defInfoMap;
    }
    
    public boolean search(Node beginNode, LocalExpr start) {
	this.beginNode = beginNode;
	this.start = start;
	exchangeFactor = 0;
	found = false;
	
	beginNode.visit(this);

	return found;
    }
    
    
    public void visitExprStmt(ExprStmt stmt)  {
	stmt.expr().visit(this);
    }

    
    public void visitIfStmt(IfStmt stmt)  {
	
	if (stmt instanceof IfCmpStmt)
	    visitIfCmpStmt((IfCmpStmt) stmt);
	else if (stmt instanceof IfZeroStmt)
	    visitIfZeroStmt((IfZeroStmt) stmt);
    }
    
    public void visitIfCmpStmt(IfCmpStmt stmt)  {
	stmt.left().visit(this);     // search the left branch
	if (!found) {              // if nothing has been found
	    exchangeFactor++;        // increase the exchange factor,
	    if (stmt.left().type().isWide()) exchangeFactor++; //twice to get
                                     	    // around wides
	    if (exchangeFactor < 3)
		stmt.right().visit(this);  // search the right branch.
	}
	
    }
    
    public void visitIfZeroStmt(IfZeroStmt stmt)  {
	
	stmt.expr().visit(this);
	
    }
    
    public void visitInitStmt(InitStmt stmt)  {
	
	// would have been checked by the Type0Visitor
	
    }
    
    public void visitGotoStmt(GotoStmt stmt)  {
	
	
    }
    
    public void visitLabelStmt(LabelStmt stmt)  {
	
	
    }
    
    public void visitMonitorStmt(MonitorStmt stmt)  {
	
	
    }
    
    public void visitPhiStmt(PhiStmt stmt)  {
	if (stmt instanceof PhiCatchStmt)
	    visitPhiCatchStmt((PhiCatchStmt) stmt);
	else if (stmt instanceof PhiJoinStmt)
	    visitPhiJoinStmt((PhiJoinStmt) stmt);
	/*
	  else if (stmt instanceof PhiReturnStmt)
	  visitPhiReturnStmt((PhiReturnStmt) stmt);
	*/
    }
    
    public void visitCatchExpr(CatchExpr expr)   {
	
    }
    
    public void visitDefExpr(DefExpr expr)  {
	if (expr instanceof MemExpr)
	    visitMemExpr((MemExpr) expr);
	
    }
    
    public void visitStackManipStmt(StackManipStmt stmt)  {
	
	
    }
    
    public void visitPhiCatchStmt(PhiCatchStmt stmt)  {
	
	
    }
    
    public void visitPhiJoinStmt(PhiJoinStmt stmt)  {
	
    }
    
    public void visitRetStmt(RetStmt stmt)  {
	
    }
    
    public void visitReturnExprStmt(ReturnExprStmt stmt)  {
	
    }
    
    public void visitReturnStmt(ReturnStmt stmt)  {
	
    }
    
    public void visitAddressStoreStmt(AddressStoreStmt stmt)  {
	
    }
    
    // StoreExprs are very difficult because they represent several
    // types of expressions. What we do will depend on what the target
    // of the store is: ArrayRefExpr, FieldExpr, StaticFieldExpr,
    // or LocalExpr
    
    public void visitStoreExpr(StoreExpr expr)  {
	MemExpr target = expr.target();
	
	if (target instanceof ArrayRefExpr) {
	    // ArrayRefExpr: the store will be something like an astore
	    // which manipulates the stack like
	    //   arrayref, index, val => ...
	    // so, think of the tree like
	    //         (StoreExpr)
	    //         /          \
	    //     Array Ref       .
	    //                   /   \
	    //              index     value
	    // This is unlike the structure of the tree BLOAT uses for
	    // intermediate representation, but it better relates to what's
	    // on the stack at what time
	    
	    
	    ((ArrayRefExpr) target).array().visit(this); // visit the
	                   //left child
	    if (!found) {                 //if match wasn't found
		exchangeFactor++;     // take the right branch
		if (exchangeFactor < 3) {  // (an array ref isn't wide)
		           //visit next left child
		    ((ArrayRefExpr) target).index().visit(this);
		    if (!found) {              // if match wasn't found
			exchangeFactor++;       
			if (exchangeFactor < 3)  // (an index isn't wide)   
			    expr.expr().visit(this); //search the right branch
		    }  //end seaching RR 
		}  
	    } //end searching R
	} //end case where target is ArrayRefExpr
	
	else if (target instanceof FieldExpr) {
	    // FieldExpr: the store will be like a putfield
	    // which manipulates the stack like
	    //    objref, val => ...
	    // so, think of the tree like
	    //           (StoreExpr)
	    //           /          \
	    //   Object Ref        value
	    
	    ((FieldExpr) target).object().visit(this); // visit the left child
	    
	    if (!found) {
		exchangeFactor++;     // (an object ref isn't wide)
		if (exchangeFactor < 3) {
		    expr.expr().visit(this);
		}
	    }
	}  //end case where target is FieldRef
	
	else if (target instanceof StaticFieldExpr) {
	    // StaticFieldExpr: the store will be like a putstatic
	    // which manipulates the stack like
	    //    val => ...
	    // so, think of the tree like
	    //       (StoreExpr)
	    //         /
	    //     value
	    
	    expr.expr.visit(this);
	}
	
	
	else if (target instanceof LocalExpr) {
	    // LocalExpr: the store will be like istore/astore/etc.
	    // which manipulates the stack like
	    //     val => ...
	    // so, think of the tree like
	    //       (StoreExpr)
	    //         /
	    //     value
	    
	    expr.expr.visit(this);
	}
    }
    
    public void visitJsrStmt(JsrStmt stmt)  {
	
    }
    
    public void visitSwitchStmt(SwitchStmt stmt)  {
    
    }

    public void visitThrowStmt(ThrowStmt stmt)  {

    }
  
    public void visitStmt(Stmt stmt)  {

    }
  
     public void visitSCStmt(SCStmt stmt)  {
	 
    }
 
    public void visitSRStmt(SRStmt stmt)  {
	
    }
  
    public void visitArithExpr(ArithExpr expr)  {  //important one
	expr.left().visit(this);    // visit the left branch
	
	if (!found) {            // if a match isn't found yet
	    exchangeFactor++;      // increase the exchange factor
	    if (expr.left().type().isWide()) exchangeFactor++; //twice if wide
	    if (exchangeFactor < 3) {   
		expr.right().visit(this);   // visit right branch
	    }
	}
    }
  
    public void visitArrayLengthExpr(ArrayLengthExpr expr)  {
	expr.array().visit(this);
    }
  
     public void visitMemExpr(MemExpr expr)  {
	if (expr instanceof LocalExpr)
	    visitLocalExpr((LocalExpr) expr);
    }
 
    public void visitMemRefExpr(MemRefExpr expr)  {
	
    }
 
    public void visitArrayRefExpr(ArrayRefExpr expr)  {
	
    }

    public void visitCallExpr(CallExpr expr)  {
	if (expr instanceof CallMethodExpr)
	    visitCallMethodExpr((CallMethodExpr) expr);
	else if (expr instanceof CallStaticExpr) 
	    visitCallStaticExpr((CallStaticExpr) expr);
    }
 
    public void visitCallMethodExpr(CallMethodExpr expr)  {
	// Method invocations are to be thought of, in terms of 
	// binary expression trees, as
	//          (CallMethodExpr)
	//          /               \
	//      receiver             .
	//                         /   \
	//                     arg1      .
	//                             /   \
	//                         arg2     .
	//                                /   \
	//                            arg3     ...
	// This might be the opposite of what one would think in terms
	// of currying (ie, one might think of currying in terms of 
	// left associativity), but this gives a better picture of what
	// happens to the stack when invokestatic or invokevirtual is called:
	//   objectref, [arg1, [arg2 ...]] => ...


	expr.receiver().visit(this);
	Expr[] params = expr.params();
	if (!found && exchangeFactor < 2 && params.length > 0) {
	    exchangeFactor++;  // (reciever won't be wide)
	    params[0].visit(this);
	}
	    
    }
  
  
    public void visitCallStaticExpr(CallStaticExpr expr)  {
	
	Expr[] params = expr.params();
	if (params.length > 0)
	    params[0].visit(this);
	if (!found && exchangeFactor < 2 && params.length > 1) {
	    exchangeFactor++;
	    params[1].visit(this);
	}
    }

    public void visitCastExpr(CastExpr expr)  {
	expr.expr().visit(this);
    }

    public void visitConstantExpr(ConstantExpr expr)  {
	
    }

    public void visitFieldExpr(FieldExpr expr)  {
	expr.object.visit(this);
    }
 
    public void visitInstanceOfExpr(InstanceOfExpr expr)  {
	expr.expr().visit(this);
    }
  
    /* needs to be different for Type0 and Type1 */
    public abstract void visitLocalExpr(LocalExpr expr);

 
    public void visitNegExpr(NegExpr expr)  {
	expr.expr().visit(this);
    }

    public void visitNewArrayExpr(NewArrayExpr expr)  {
	expr.size().visit(this);
    }

    public void visitNewExpr(NewExpr expr)  {

    }

    public void visitNewMultiArrayExpr(NewMultiArrayExpr expr)    {
	// Think of the tree like
	//      (NewMultiArrayExpr)
	//       /               \
	//     count1             .
	//                      /   \
	//                 count2   etc.
	// since multianewarray manipulates the stack like
	//     count1, [count1 ...] => ...

	Expr[] dims = expr.dimensions();
	if (dims.length > 0)
	    dims[0].visit(this);
	if (!found && exchangeFactor < 2 && dims.length > 1) {
	    exchangeFactor++;  // (count1 won't be wide)
	    dims[1].visit(this);
	}
    }
  
    public void visitCheckExpr(CheckExpr expr)  {
	if (expr instanceof ZeroCheckExpr)
	    visitZeroCheckExpr((ZeroCheckExpr) expr);
	else if (expr instanceof RCExpr)
	    visitRCExpr((RCExpr) expr);
	else if (expr instanceof UCExpr)
	    visitUCExpr((UCExpr) expr);
    }

    public void visitZeroCheckExpr(ZeroCheckExpr expr)  {
	// perhaps add something here
    }

    public void visitRCExpr(RCExpr expr)  {

    }
    
    public void visitUCExpr(UCExpr expr)  {

    }

    public void visitReturnAddressExpr(ReturnAddressExpr expr)  {

	
    }

    public void visitShiftExpr(ShiftExpr expr)  {

    }

  
    public void visitVarExpr(VarExpr expr)  {
	if (expr instanceof LocalExpr)
	    visitLocalExpr((LocalExpr) expr);
    }
  
    public void visitStaticFieldExpr(StaticFieldExpr expr)  {
	
    }
  
    public void visitExpr(Expr expr)  {
	
    }
  
}
