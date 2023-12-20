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

package EDU.purdue.cs.bloat.tree;

import java.util.*;

/**
 * Type1Visitor...
 */
public class Type1Visitor extends AscendVisitor {

    Node turningPoint;     /* where we were when we started descending
			      the tree instead of ascending */
    boolean found;            /* have we found an earlier occurence */
    


    public Type1Visitor(Hashtable defInfoMap, Hashtable useInfoMap) {
	super(defInfoMap, useInfoMap);
    }


    public void search(LocalExpr start) {

	this.start = start;
	previous = this.start;
	found = false;
	start.parent().visit(this);
	if (!found) {

           if (turningPoint != null) {
	    (new Type1UpVisitor(defInfoMap,useInfoMap))
		.search(turningPoint, start);
	   }
	   else {  // search failed (one place I saw this was in 
	       // a ZeroCheckExpression)

	    ((DefInformation) defInfoMap.get(start.def())).type1s += 3;
	   }       

	}

    }

    
    public void check(Node node) {

	if (node instanceof Expr
	    && ((Expr) node).type().isWide()) {
	    turningPoint = null;
	    return;  // give up. We cannot swap around a wide value.
	}

        turningPoint = node;  

	if (node instanceof StoreExpr)  // if it's a StoreExpr, we need
	    check(((StoreExpr) node).expr()); // to search down the expression
                                            	// being stored

	//if it's a LocalExpr or Stmt, Type0Visitor would have inspected it.
	// otherwise...
	else if (!(node instanceof LocalExpr) && node instanceof Expr) 

	    found =
		(new Type1DownVisitor(useInfoMap, defInfoMap)).search(node,
								      start);

    }
}



class Type1UpVisitor extends AscendVisitor {


    Node turningPoint;
    boolean found;

    Type1UpVisitor(Hashtable defInfoMap, Hashtable useInfoMap) {
	super(defInfoMap, useInfoMap);
    }

    public void search(Node turningPoint, LocalExpr start) {

	found = false;
	this.start = start;
	previous = turningPoint;
	this.turningPoint = turningPoint;
	if (turningPoint.parent() != null 
	    && !(turningPoint.parent() instanceof Tree)) // we can't
                        	    // go more than one statement earlier
                      	        // because we don't know if the intermediate
	                         // statment leaves anything on the stack.
	    turningPoint.parent().visit(this);

        if (!found) {
	    // if we've found nothing by now, we won't find anything.
	    // setting the type1s of the definition to something 3 or
	    // greater insures the variable will be stored
	    ((DefInformation) defInfoMap.get(start.def())).type1s += 3;
	}

    }

    public void check(Node node) {

	if (node instanceof ExprStmt)      // if it's an ExprStmt, the expr
	    check(((ExprStmt) node).expr());  // might be something we want

	else if (node instanceof StoreExpr)   // if it's a StoreExpr, we need
	    check(((StoreExpr) node).target()); // to see if the target matches

	//if it's a LocalExpr...
	else if (node instanceof LocalExpr   
		 && ((LocalExpr) node).index() == start.index() //compare index
		 && ((LocalExpr) node).def() == start.def()) {  // and def
		//we've found a match
		//update information
		((UseInformation) useInfoMap.get(start)).type = 1;
		((UseInformation) useInfoMap.get(node)).type1s++;
		((DefInformation) defInfoMap.get(start.def())).type1s++;
		found = true;
		return; 
	}
	

    }	    


	
}
	
 

class Type1DownVisitor extends DescendVisitor {

    public Type1DownVisitor(Hashtable useInfoMap, Hashtable defInfoMap) {
	super(useInfoMap, defInfoMap);
    }
    
    public void visitLocalExpr(LocalExpr expr)  {  
	
	if (expr.index() == start.index()
	    && expr.def() == start.def()) {
	    //we've found a match
	    //update information
	    ((UseInformation) useInfoMap.get(start)).type = 1;
	    UseInformation ui = (UseInformation) useInfoMap.get(expr);
	    ui.type1s++;
	    if (exchangeFactor == 1) {
		ui.type1_x1s++;
	    }
	    if (exchangeFactor == 2)
		ui.type1_x2s++;
//System.err.println(expr.toString());
	    ((DefInformation) defInfoMap.get(expr.def())).type1s++;
	    found = true;
	}
    }

}
    
   


