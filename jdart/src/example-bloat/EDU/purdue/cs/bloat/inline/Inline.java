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

package EDU.purdue.cs.bloat.inline;

import java.io.*;
import java.util.*;

import EDU.purdue.cs.bloat.util.*;
import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.reflect.*;

/**
 * Inlines the code of non-virtual method call sites.  These sites
 * include calls to static methods and certain uses of the
 * <tt>invokespecial</tt> method.  There are certain metrics that can
 * be set to effect where and how inlining is performed.
 */
public class Inline {
  public static boolean DEBUG = false;

  private int maxCodeSize;   // Max number of instructions in method
  private int maxInlineSize; // Don't inline methods larger than this
  private int maxCallDepth;  // Max of height of call stack
  private boolean inlineExceptions; // Inline methods that throw exceptions
  private InlineContext context;

  private Map editors;       // Maps MemberRefs to their MethodEditors

  /**
   * Size of the largest method that can be inlined
   */
  public static int CALLEE_SIZE = 100000;

  private static void db(String s) {
    if(DEBUG) {
      System.out.println(s);
    }
  }

  /**
   * Constructor.  By default the first-level calls are only inlined
   * one level deep, there is no max size on methods to inline, and
   * methods that may throw exceptions are inlined.
   *
   * @param maxCodeSize
   *        The maximum number of instructions a method can grow to.
   */
  public Inline(InlineContext context, int maxCodeSize) {
    this.context = context;
    this.maxCodeSize = maxCodeSize;
    this.maxInlineSize = -1;
    this.maxCallDepth = 1;
    this.inlineExceptions = true;

    editors = new HashMap();
  }

  /**
   * Sets the maximum of size of a method that will be inlined.  No
   * method larger than this will be inlined.
   */
  public void setMaxInlineSize(int maxInlineSize) {
    this.maxInlineSize = maxInlineSize;
  }

  /**
   * Sets the maximum number of nested calls we inline.
   */
  public void setMaxCallDepth(int maxCallDepth) {
    this.maxCallDepth = maxCallDepth;
  }

  /**
   * Sets whether or not methods that may throw exceptions (that is,
   * have a non-empty "throws" declaration) are inlined.
   */
  public void setInlineExceptions(boolean inlineExceptions) {
    this.inlineExceptions = inlineExceptions;
  }

  /**
   * Scans a method and inlines non-virtual method calls according to
   * this <tt>Inline</tt>'s metrics.
   */
  public void inline(MethodEditor method) {
    // Go through the method and look for calls to inline
    StackHeightCounter stackHeight = new StackHeightCounter(method);
    List code = method.code();
    boolean firstCall = true;
    for(int i = 0; i < code.size(); i++) {
      Object o = code.get(i);
      if(o instanceof Instruction) {
	Instruction inst = (Instruction) o;
	if(inst.opcodeClass() == Opcode.opcx_invokestatic ||
	   inst.opcodeClass() == Opcode.opcx_invokespecial) {
	  MemberRef callee = (MemberRef) inst.operand();
	  Stack callStack = new Stack();
	  callStack.add(method.memberRef());

	  db("  Call: " + inst);

	  stackHeight.handle(inst);
	  int expectedHeight = stackHeight.height();
	  stackHeight.unhandle(inst);

	  int j = i;
	  i = inline(method, callee, i, callStack, stackHeight,
		     firstCall);

	  if(j == i) {
	    // Call was not inlined, add it to the stack
	    stackHeight.handle(inst);
	    db("  " + i + "." + stackHeight.height() + ") " + inst);
	  }

	  int newHeight = stackHeight.height();
	  // If an exception is thrown as the last thing in a method
	  // newHeight will equal 0.  Let's let this one slide.
	  Assert.isTrue(newHeight == 0 || newHeight == expectedHeight, 
			"Inlining did not get the stack heights right: " +
			"Expected " + expectedHeight + ", got " +
			newHeight);

	} else {
	  stackHeight.handle(inst);
	  db("  " + i + "." + stackHeight.height() + ") " + inst);
	}

	if(inst.isInvoke()) {
	  firstCall = false;
	}

      } else if(o instanceof Label) {
	Label label = (Label) o;
	stackHeight.handle(label);
	db("  " + i + "." + stackHeight.height() + ") " + label +
	   (label.startsBlock() ? " (starts block)" : ""));
      }

    }

    method.setCode(code);

    if(DEBUG) {
      stackHeight = new StackHeightCounter(method);
      db("\nNew Code for " + method.declaringClass().name() + "." + 
	 method.name() + method.type());
      code = method.code();
      for(int j = 0; j < code.size(); j++) {
	if(code.get(j) instanceof Label) {
	  Label label = (Label) code.get(j);

	  stackHeight.handle(label);

	  Iterator tryCatches = method.tryCatches().iterator();
	  while(tryCatches.hasNext()) {
	    TryCatch tryCatch = (TryCatch) tryCatches.next();
	    if(tryCatch.start().equals(label)) {
	      System.out.println(" Begin protected region" );

	    } 

	    if(tryCatch.end().equals(label)) {
	      System.out.println(" End protected region");

	    } 

	    // A Label can both end a protected region and begin catch
	    // block

	    if(tryCatch.handler().equals(label)) {
	      System.out.println(" Catch " + tryCatch.type());
	    }

	  }

	  System.out.println("  " + j + "." + stackHeight.height() + 
			     ") " + label +
			     (label.startsBlock() ? " (starts block)"
			      : ""));
	} else {
	  Instruction inst = (Instruction) code.get(j);
	  stackHeight.handle(inst);
	  System.out.println("  " + j + "." + stackHeight.height() + 
			     ") " + code.get(j));
	}
      }

      // Print try-catch information
      Iterator tryCatches = method.tryCatches().iterator();
      System.out.println("Exception information:");
      while(tryCatches.hasNext()) {
	TryCatch tryCatch = (TryCatch) tryCatches.next();
	System.out.println("  " + tryCatch);
      }
      System.out.println("");
    }

  }

  /**
   * Helper method that does most of the work.  By calling this method
   * recursively, we can inline more than one call deep.
   *
   * @param caller
   *        The original caller that got all of this started.  Into
   *        this method we insert the code.
   * @param callee
   *        The method to be inlined
   * @param index
   *        Where in caller we insert inlined code
   * @param callStack 
   *        A stack of <tt>MemberRef</tt>s that represent the inlined
   *        methods that call other methods.  It is used to detect
   *        recursion.
   *
   * @return The index into the caller's code array of the instruction
   *         following the last inlinined instruction.  Start looking
   *         here after inline returns.
   */
  private int inline(MethodEditor caller, MemberRef callee, int index,
		     Stack callStack, StackHeightCounter stackHeight,
		     boolean firstCall) {
    
    Instruction newInst = null;

    // Do we ignore the method being inlined?
    if(context.ignoreMethod(callee)) {
      db("  Can't inline " + callee + ": it's ignored");
      return(index++);
    }

    // Can we inline this method
    if(callStack.size() > maxCallDepth) {
      db("  Can't inline " + callee + ": max call depth (" +
	 maxCallDepth + ") reached");
      return(index++);

    } else if(callStack.contains(callee)) {
      db("  Can't inline recursive call to " + callee);
      return(index++);
    } 

    // Make sure we're not inlining the static-ized version of a
    // method in the call stack.
    String name = callee.name();
    int b = name.indexOf("$$BLOAT");
    if(b != -1) {
      name = name.substring(0, b);

      // Get rid of first parameter
      Type[] oldParams = callee.type().paramTypes();
      StringBuffer sb = new StringBuffer("(");
      for(int p = 1; p < oldParams.length; p++) {
	sb.append(oldParams[p].descriptor());
      }
      sb.append(")" + callee.type().returnType());
      Type newType = Type.getType(sb.toString());

      MemberRef unBloated = 
	new MemberRef(callee.declaringClass(), 
		      new NameAndType(name, newType));

      if(callStack.contains(unBloated)) {
	db("  Can't inline recursive call to " + callee);
	return(index++);
      }
    }

    List code = caller.code();
    if(code.size() > maxCodeSize) {
      db("  Can't inline " + callee + ": max code size (" +
	 maxCodeSize + ") reached");
      return(index++);
    }

    MethodEditor calleeMethod = null;
    try {
      calleeMethod = context.editMethod(callee);

    } catch(NoSuchMethodException ex) {
      System.err.println("Couldn't find method " + callee);
      ex.printStackTrace(System.err);
      System.exit(1);
    }

    if(calleeMethod.isNative()) {
      db("  Can't inline " + callee + ": it's a native method");
      return(index++);
    }

    if(calleeMethod.isSynchronized()) {
      db("  Can't inline " + callee + ": it's synchronized");
      return(index++);
    }

    if(!inlineExceptions && 
       calleeMethod.methodInfo().exceptionTypes().length > 0) {
      db("  Can't inline " + callee + ": it may throw an exception");
      return(index++);
    }

    if(calleeMethod.code().size() > CALLEE_SIZE) {
      db("  Can't inline " + callee + ": it's too big");
      return(index++);
    }

    // Methods that catch exceptions are problematic.  When an
    // exception is thrown, it clears the stack.  Ordinarily this
    // isn't a problem.  However, now the stack of the caller is
    // cleared in addition to the stack of the callee.  This is bad.
    // The callee might catch the exception and deal with it.
    // However, the stack has still been cleared.  This really messes
    // things up for the code that appears after the inlined method.
    // So, if a method catches an exception, we can only inline it if
    // the stack contains nothing but the parameters to the call.
    if(calleeMethod.tryCatches().size() > 0) {
      if(stackHeight.height() > callee.type().stackHeight()) {
	db("  Can't inline " + callee + 
	   ": It catches an exception and there's stuff on the " +
	   "stack");
	return(index++);
      }
    }

    // If the callee method catches any of the same exceptions as the
    // protected region that we are currently in, then we can't inline
    // the method.
    Iterator tryCatches0 = calleeMethod.tryCatches().iterator();
    while(tryCatches0.hasNext()) {
      TryCatch tc1 = (TryCatch) tryCatches0.next();
      Iterator iter = stackHeight.tryCatches().iterator();
      while(iter.hasNext()) {
	TryCatch tc2 = (TryCatch) iter.next();
	Type t1 = tc1.type();
	Type t2 = tc2.type();
	if(t1 != null && t2 != null && t1.equals(t2)) {
	  db("  Can't inline " + callee + 
	     ": It catches the same type " + tc1.type().className() + 
	     " as the current protected region");
	  return(index++);
	}
      }
    }

    // If the caller is a constructor and this is the first
    // invokespecial we've seen in this callee method, we can inline
    // calls to the constructors of superclasses and other
    // constructors in this method.  So, if this IS the first call in
    // a method which IS a constructor, we can inline it.
    if(calleeMethod.isConstructor() && 
       (!firstCall || !caller.isConstructor())) {

      db("  Can't inline " + callee + 
	 ": It calls a normal constructor");
      return(index++);
    }

    // Local variables are problematic.  We cannot simply map the
    // callee's variables to new variables in the caller because we
    // cannot precisely determine the width of the variable the first
    // time we see it.  (For instance, Nate's generated code might use
    // a local variable as a non-wide initially and then use it as a
    // wide later.)  

    // Okay, we going to inline.  Remove the calling instruction.
    Instruction call = (Instruction) code.remove(index--);
    db("  Removing call: " + call);
    Assert.isTrue(call.opcodeClass() == Opcode.opcx_invokestatic ||
		  call.opcodeClass() == Opcode.opcx_invokespecial, 
		  "Removing the wrong call instruction:" + call);
    callStack.push(callee);

    db("  Inlining call (" + callStack.size() + ") to " +
       callee.declaringClass() + "." + callee.name() + callee.type());
    context.getInlineStats().noteInlined();

    // First we have to pop the arguments off the stack and store them
    // into the local variables.  Remember that wide types occupy two
    // local variables.
    Mapper mapper = new Mapper(caller);
    Type[] paramTypes = callee.type().indexedParamTypes();
    if(!calleeMethod.isStatic()) {
      // Constructors (and any other special methods we're inlining)
      // have a "this" pointer where static methods do not.
      Type[] newParams = new Type[paramTypes.length + 1];
      newParams[0] = callee.declaringClass();

      for(int i = 0; i < paramTypes.length; i++) {
	newParams[i+1] = paramTypes[i];
      }
      paramTypes = newParams;
    }

    LocalVariable[] params = new LocalVariable[paramTypes.length];

    db("  Indexed params:");
    for(int i = 0; i < params.length; i++) {
      params[i] = calleeMethod.paramAt(i);
      db("    " + i + ": " + params[i] + 
	 (params[i] != null ? " " + params[i].type() + " " : ""));
    }

    for(int i = params.length - 1; i >= 0; i--) {
      // Map the local variables containing the arguments to new
      // local variables.
      LocalVariable param = params[i];
      Type paramType = params[i].type();

      if(param.type() == null) {
	continue;
      }

      db("  Param " + i + ": " + param + " of type " + paramType);

      LocalVariable newVar = mapper.map(param, paramType);
      
      int opcode;
      
      if(paramType.isReference()) {
	opcode = Opcode.opcx_astore;
	
      } else {
	switch(paramType.typeCode()) {
	case Type.BOOLEAN_CODE:
	case Type.BYTE_CODE:
	case Type.CHARACTER_CODE:
	case Type.SHORT_CODE:
	  opcode = Opcode.opcx_istore;
	  break;
	  
	case Type.DOUBLE_CODE:
	  opcode = Opcode.opcx_dstore;
	  break;
	  
	case Type.LONG_CODE:
	  opcode = Opcode.opcx_lstore;
	  break;

	case Type.FLOAT_CODE:
	  opcode = Opcode.opcx_fstore;
	  break;
	  
	case Type.INTEGER_CODE:
	  opcode = Opcode.opcx_istore;
	  break;
	  
	default:
	  throw new IllegalArgumentException("What's a " + paramType +   
					     "doing as a method " +
					     "parameter");
	}
      }
      
      newInst = new Instruction(opcode, newVar);
      code.add(++index, newInst);
      stackHeight.handle(newInst);
      db("  " + index + "." + stackHeight.height() + "> " + newInst);
    }


    // Before we mess with the code, we have to patch up the try-catch
    // information from the inlined method to the caller method.
    Iterator tryCatches = calleeMethod.tryCatches().iterator();
    while(tryCatches.hasNext()) {
      TryCatch tryCatch = (TryCatch) tryCatches.next();

      Label start = mapper.map(tryCatch.start());
      Label end = mapper.map(tryCatch.end());
      Label handler = mapper.map(tryCatch.handler());

      TryCatch newTryCatch = new TryCatch(start, end, handler,
					  tryCatch.type());
      caller.addTryCatch(newTryCatch);

//        db("Try-catch");
//        db("  Before: " + tryCatch.start() + "\t" + tryCatch.end() +
//  	 "\t" + tryCatch.handler());
//        db("  After: " + newTryCatch.start() + "\t" + newTryCatch.end()
//  	 + "\t" + newTryCatch.handler());
    }

    // Go through the code in the callee method and inline it.  Handle
    // any calls by making a recursive call to this method.  Copy each
    // instruction to the method in which it is being inlined.  Along
    // the way convert references to local variables to their mapped
    // values.  Also remove return instructions.  Replace them with
    // loads as necessary.

    List inlineCode = calleeMethod.code();

    // We don't want to introduce a new end label because it confuses
    // BLOAT during CFG construction.  We designate the end label as
    // starting a new block in hopes that it will solve problems with
    // CFG construction.
    Object last = inlineCode.get(inlineCode.size() - 1);
    boolean addEndLabel;
    Label endLabel;
    if(last instanceof Label) {
      endLabel = mapper.map((Label) last);
      addEndLabel = false;

    } else {
      endLabel = caller.newLabel();
      addEndLabel = true;
    }
    endLabel.setStartsBlock(true);

    firstCall = true;

    for(int j = 0; j < inlineCode.size(); j++) {
      Object o = inlineCode.get(j);

      if(o instanceof Label) {
	Label label = (Label) o;
	Label newLabel = mapper.map(label);
	
	code.add(++index, newLabel);
	stackHeight.handle(newLabel);
	db("  " + index + "." + stackHeight.height() + "> " +
	   newLabel + (newLabel.startsBlock() ? " (starts block)" : ""));
	continue;
      }
      
      Assert.isTrue(o instanceof Instruction, "What is a " + o +
		    " doing in the instruction stream?");
      
      Instruction inst = (Instruction) inlineCode.get(j);
      Object operand = inst.operand();
      int opcode = inst.opcodeClass();
      
      if(operand instanceof LocalVariable) {
	// Map local variable in the callee method to local
	// variables in the caller method.
	LocalVariable local = mapper.map((LocalVariable) operand,
					 (inst.category() == 2 ? true : false));
	operand = local;
	
      } else if(operand instanceof Label) {
	// Map labels in the callee method to labels in the caller
	// method.
	Label label = mapper.map((Label) operand);
	operand = label;
	
      } else if(operand instanceof IncOperand) {
	// Map the local being incremented
	IncOperand inc = (IncOperand) operand;
	LocalVariable newLocal = 
	  mapper.map((LocalVariable) inc.var(), Type.INTEGER);
	operand = new IncOperand(newLocal, inc.incr());

      } else if(operand instanceof Switch) {
	// We have to patch up the Labels involved with the Switch
	Switch oldSwitch = (Switch) operand;
	
	Label newDefault = mapper.map(oldSwitch.defaultTarget());

	Label[] oldTargets = oldSwitch.targets();
	Label[] newTargets = new Label[oldTargets.length];
	for(int i = 0; i < newTargets.length; i++) {
	  Label newTarget = mapper.map(oldTargets[i]);
	  newTargets[i] = newTarget;
	}

	operand = new Switch(newDefault, newTargets,
			     oldSwitch.values());
      }

      if(inst.isReturn()) {
	// Insert a jump to the end of the inlined method.  Any
	// return value will be on top of the stack.  This is where
	// we want it.
	newInst = new Instruction(Opcode.opcx_goto, endLabel);
	code.add(++index, newInst);
	stackHeight.handle(newInst);
	db("  " + index + "." + stackHeight.height() + "> " + newInst);
	
      } else if(inst.opcodeClass() == Opcode.opcx_invokestatic ||
		inst.opcodeClass() == Opcode.opcx_invokespecial) {
	// Make a recursive call.  Note that this must be done after
	// we add the call instruction above.  But we only want to
	// visit the instruction with the stackHeight if the call was
	// not inlined.
	newInst = new Instruction(opcode, operand);
	code.add(++index, newInst);

	stackHeight.handle(newInst);
	int expectedHeight = stackHeight.height();
	stackHeight.unhandle(newInst);

	MemberRef nestedCall = (MemberRef) inst.operand();
	int oldIndex = index;
	index = inline(caller, nestedCall, index, callStack,
		       stackHeight, firstCall);

	if(index == oldIndex) {
	  stackHeight.handle(newInst);
	  db("  " + index + "." + stackHeight.height() + "> " + newInst);
	}

	int newHeight = stackHeight.height();
	Assert.isTrue(newHeight == 0 || newHeight == expectedHeight, 
		      "Inlining did not get the stack heights right: " +
		      "Expected " + expectedHeight + ", got " +
		      newHeight);

      } else {
	// Add the instruction
	newInst = new Instruction(opcode, operand);
	code.add(++index, newInst);
	stackHeight.handle(newInst);
	db("  " + index + "." + stackHeight.height() + "> " + newInst);
      }

      // We want to do this after we've made any recursive calls to
      // inline.
      if(inst.isInvoke()) {
	firstCall = false;
      }

    }

    if(addEndLabel) {
      // Done inlining.  Add end label.
      code.add(++index, endLabel);
      stackHeight.handle(endLabel);
      db("  " + index + "." + stackHeight.height() + "> " + endLabel +
	 (endLabel.startsBlock() ? " (starts block)" : ""));
    }

    caller.setDirty(true);
    callStack.pop();
    return(index);

  }
}

/**
 * Utility class for mapping local variables and labels.  Note that
 * when mapping local variables we have to be careful.  We can't
 * assume that a variable will retain its "wideness" throughout the
 * method.  I learned this one the hard way.  So, we have to keep a
 * constant difference between the mapped variables.
 */
class Mapper {
  private Map varsMap;          // Maps local variables
  private Map labelsMap;        // Maps labels
  private MethodEditor method;  // Method into which things are mapped
  private int offset;           // Start numbering new locals here

  private static void db(String s) {
    if(Inline.DEBUG)
      System.out.println(s);
  }

  /**
   * Constructor.
   */
  public Mapper(MethodEditor method) {
    this.method = method;
    varsMap = new HashMap();
    labelsMap = new HashMap();
    offset = method.maxLocals() + 1;
  }

  public Label map(Label label) {
    Label newLabel = (Label) labelsMap.get(label);
    if(newLabel == null) {
      newLabel = this.method.newLabel();
      newLabel.setStartsBlock(label.startsBlock());
      labelsMap.put(label, newLabel);
      db("      " + label + " -> " + newLabel + 
	 (newLabel.startsBlock() ? " (starts block)" : ""));
    }
    return(newLabel);
  }

  public LocalVariable map(LocalVariable var, Type type) {
    LocalVariable newVar = (LocalVariable) varsMap.get(var);
    if(newVar == null) {
      newVar = this.method.localAt(var.index() + offset);
//        newVar = this.method.newLocal(type);
      varsMap.put(var, newVar);
      db("      " + var + " (" + var.index() + ") -> " + newVar + "("
	 + var.index() + "+" + offset + ")" +
	 (type.isWide() ? " (" + type + ")" : ""));
    }
    return(newVar);
  }

  public LocalVariable map(LocalVariable var, boolean isWide) {
    LocalVariable newVar = (LocalVariable) varsMap.get(var);
    if(newVar == null) {
      newVar = this.method.localAt(var.index() + offset);
//        newVar = this.method.newLocal(isWide);
      varsMap.put(var, newVar);
      db("      " + var + " (" + var.index() + ") -> " + newVar + "("
	 + var.index() + "+" + offset + ")" +
	 (isWide ? " (wide)" : ""));
    }
    return(newVar);
  }

}
