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

package EDU.purdue.cs.bloat.context;

import EDU.purdue.cs.bloat.util.*;
import EDU.purdue.cs.bloat.reflect.*;
import EDU.purdue.cs.bloat.file.*;
import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.ssa.*;
import EDU.purdue.cs.bloat.inline.*;

import java.io.*;
import java.util.*;

/**
 * This abstract class is a central repository for all things that are
 * necessary for a BLOATing sessions.  Its subclasses implement
 * certain schemes for managing BLOAT data structures such as editors
 * and control flow graphs.  
 */
public abstract class BloatContext implements InlineContext {
  public static boolean DEBUG = Boolean.getBoolean("BloatContext.DEBUG");
  protected InlineStats inlineStats;

  // Ignore stuff for inlining
  protected Set ignorePackages = new HashSet();
  protected Set ignoreClasses = new HashSet();
  protected Set ignoreMethods = new HashSet();
  protected Set ignoreFields = new HashSet();
  protected boolean ignoreSystem = false;

  protected CallGraph callGraph;
  protected Set roots;         // Root methods of call graph

  protected static void db(String s) {
    if(DEBUG)
      System.out.println(s);
  }

  protected ClassInfoLoader loader;

  /**
   * Constructor.  Each <tt>BloatContext</tt> needs to know about a
   * <tt>ClassInfoLoader</tt>.  
   */
  public BloatContext(ClassInfoLoader loader) {
    this.loader = loader;
  }

  private static ClassLoader systemCL;
  static {
    String s = "";
    systemCL = s.getClass().getClassLoader();
  }

  /**
   * Returns <tt>true</tt> if the give type is a system class (that
   * is, has the same class loader as java.lang.String).
   */
  public static boolean isSystem(Type type) {
    Class c = null;
    try {
      c = Class.forName(type.className().replace('/', '.'));

    } catch(ClassNotFoundException ex) {
      System.err.println("** Could not find class " +
			 type.className());
      ex.printStackTrace(System.err);
      System.exit(1);
    }

    // Have to use == because class loader might be null
    return(c.getClassLoader() == systemCL);
  }

  public void setRootMethods(Set roots) {
    if(this.callGraph != null) {
      // Can't set the call graph roots after its been created
      throw new IllegalStateException("Cannot set roots after " + 
				      "call graph has been created");
    }

    this.roots = roots;
  }

  public CallGraph getCallGraph() {
    if(this.callGraph == null) {
      // Create a new CallGraph
      this.callGraph = new CallGraph(this, this.roots);
    }
    return(this.callGraph);
  }

  public InlineStats getInlineStats() {
    if(inlineStats == null) {
      inlineStats = new InlineStats();
    }
    return(inlineStats);
  }

  public void addIgnorePackage(String name) {
    name = name.replace('.', '/');
    ignorePackages.add(name);
  }

  public void addIgnoreClass(Type type) {
    ignoreClasses.add(type);
  }

  public void addIgnoreMethod(MemberRef method) {
    ignoreMethods.add(method);
  }

  public void addIgnoreField(MemberRef field) {
    ignoreFields.add(field);
  }

  public void setIgnoreSystem(boolean ignore) {
    this.ignoreSystem = ignore;
  }

  public boolean ignoreClass(Type type) {
    // First, check to see if we explicitly ignore it.  If not, check
    // to see if we ignore its package.  The ladies always seem to
    // ignore my package.
    if(ignoreClasses.contains(type)) {
      return(true);

    } else if(type.isPrimitive()) {
      addIgnoreClass(type);
      return(true);

    } else {
      if(this.ignoreSystem) {
	if(isSystem(type)) {
	  addIgnoreClass(type);
	  return(true);
	}
      }

      String packageName = type.className();
      int lastSlash = packageName.lastIndexOf('/');

      if(lastSlash == -1) {
	return(false);
      }

      packageName = packageName.substring(0, lastSlash);

      // If any ignore package is a prefix of the class's package,
      // then ignore it.  This makes our lives easier.
      Iterator packages = ignorePackages.iterator();
      while(packages.hasNext()) {
	String s = (String) packages.next();
	if(type.className().startsWith(s)) {
	  addIgnoreClass(type);
	  return(true);
	}
      }
      
      return(false);
    }
  }

  public boolean ignoreMethod(MemberRef method) {
    if(ignoreMethods.contains(method)) {
      return(true);

    } else if(ignoreClass(method.declaringClass())) {
	addIgnoreMethod(method);
	return(true);
    }
    return(false);
  }

  public boolean ignoreField(MemberRef field) {
    if(ignoreMethods.contains(field)) {
      return(true);

    } else if(ignoreClass(field.declaringClass())) {
      addIgnoreField(field);
      return(true);
    }
    return(false);
  }

  /**
   * Commits all classes, methods, and fields, that have been 
   * modified.
   */
  public abstract void commitDirty();

  /**
   * Test the ignore stuff.
   */
  public static void main(String[] args) {
    PrintWriter out = new PrintWriter(System.out, true);
    PrintWriter err = new PrintWriter(System.err, true);

    BloatContext context = 
      new CachingBloatContext(new ClassFileLoader(), new ArrayList(),
			      false);

    List types = new ArrayList();

    for(int i = 0; i < args.length; i++) {
      if(args[i].equals("-ip")) {
	if(++i >= args.length) {
	  err.println("** Missing package name");
	  System.exit(1);
	}

	out.println("Ignoring package " + args[i]);
	context.addIgnorePackage(args[i]);

      } else if(args[i].equals("-ic")) {
	if(++i >= args.length) {
	  err.println("** Missing class name");
	  System.exit(1);
	}

	out.println("Ignoring class " + args[i]);
	String type = args[i].replace('.', '/');
	context.addIgnoreClass(Type.getType("L" + type + ";"));
	
      } else {
	// A type
	String type = args[i].replace('.', '/');
	types.add(Type.getType("L" + type + ";"));
      }
    }

    out.println("");

    Iterator iter = types.iterator();
    while(iter.hasNext()) {
      Type type = (Type) iter.next();
      out.println("Ignore " + type + "? " + context.ignoreClass(type));
    }
  }

}

