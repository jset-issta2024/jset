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

package EDU.purdue.cs.bloat.file;

import EDU.purdue.cs.bloat.reflect.*;
import java.util.*;
import java.io.*;

/**
 * Code is used to store the Code attribute of a method in a class file.
 * The Code attribute stores the raw bytecode of the method, the maximum
 * stack height and maximum number of locals used by the method, and the
 * exception handlers used in the method.  Code may have several 
 * attributes.  The local variable table and the line number table are 
 * modeled explicitly.  All other attributes are modeled as generic 
 * attributes.
 *
 * @see EDU.purdue.cs.bloat.reflect.Catch Catch
 * @see GenericAttribute
 * @see EDU.purdue.cs.bloat.reflect.LineNumberAttribute LineNumberAttribute
 * @see LocalVariableTable
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Code extends Attribute
{
  private ClassInfo classInfo;
  private int maxStack;
  private int maxLocals;
  private byte[] code;
  private Catch[] handlers;
  private LineNumberTable lineNumbers;
  private LocalVariableTable locals;
  private Attribute[] attrs;

  /**
   * Constructor for creating a <code>Code</code> from scratch
   *
   * @param codeIndex
   *        The index in the constant pool for the UTF8 "Code"
   */
  Code(ClassInfo classInfo, int codeIndex) {
    // Don't know length!
    super(codeIndex, -1);

    this.classInfo = classInfo;
    
    // These should get set during a commit
    this.maxStack = -1;
    this.maxLocals = -1;
    this.code = new byte[0];
    this.handlers = new Catch[0];
    this.lineNumbers = null;      // It's okay for these to be null
    this.locals = null;
    this.attrs = new Attribute[0];
  }

  /**
   * Constructor.  Create a Code attribute from a data stream.
   *
   * @param in
   *        The data stream containing the class file.
   * @param index
   *        The index into the constant pool of the name of the attribute.
   * @param len
   *        The length of the attribute, excluding the header.
   * @exception IOException
   *        If an error occurs while reading.
   */
  public Code(ClassInfo classInfo, DataInputStream in,
	      int index, int len) throws IOException
  {
    super(index, len);

    this.classInfo = classInfo;

    maxStack = in.readUnsignedShort();
    maxLocals = in.readUnsignedShort();

    int codeLength = in.readInt();

    code = new byte[codeLength];

    for (int read = 0; read < codeLength;) {
      read += in.read(code, read, codeLength - read);
    }

    int numHandlers = in.readUnsignedShort();

    handlers = new Catch[numHandlers];

    for (int i = 0; i < numHandlers; i++) {
      handlers[i] = readCatch(in);
    }

    int numAttributes = in.readUnsignedShort();

    attrs = new Attribute[numAttributes];

    for (int i = 0; i < numAttributes; i++) {
      int nameIndex = in.readUnsignedShort();
      int length = in.readInt();

      Constant name = classInfo.constants()[nameIndex];

      if (name != null) {
	if ("LineNumberTable".equals(name.value())) {
	  lineNumbers = new LineNumberTable(in, nameIndex, length);
	  attrs[i] = lineNumbers;
	}
	else if ("LocalVariableTable".equals(name.value())) {
	  locals = new LocalVariableTable(in, nameIndex, length);
	  attrs[i] = locals;
	}
      }

      if (attrs[i] == null) {
	attrs[i] = new GenericAttribute(in, nameIndex, length);
      }
    }
  }

  /**
   * Write the attribute to a data stream.
   *
   * @param out
   *        The data stream of the class file.
   * @exception IOException
   *        If an error occurs while writing.
   */
  public void writeData(DataOutputStream out) throws IOException
  {
    out.writeShort(maxStack);
    out.writeShort(maxLocals);

    out.writeInt(code.length);
    out.write(code, 0, code.length);

    out.writeShort(handlers.length);

    for (int i = 0; i < handlers.length; i++) {
      writeCatch(out, handlers[i]);
    }

    out.writeShort(attrs.length);

    for (int i = 0; i < attrs.length; i++) {
      out.writeShort(attrs[i].nameIndex());
      out.writeInt(attrs[i].length());
      attrs[i].writeData(out);
    }
  }

  /**
   * Read an exception handler attribute.
   *
   * @param in
   *        The data stream of the class file.
   * @return
   *        A Catch attribute for the handler.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private Catch readCatch(DataInputStream in)
    throws IOException
  {
    int startPC = in.readUnsignedShort();
    int endPC = in.readUnsignedShort();
    int handlerPC = in.readUnsignedShort();
    int catchType = in.readUnsignedShort();

    return new Catch(startPC, endPC, handlerPC, catchType);
  }

  /**
   * Write an exception handler attribute.
   *
   * @param out
   *        The data stream of the class file.
   * @param c
   *        A Catch attribute for the handler.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeCatch(DataOutputStream out, Catch c)
    throws IOException
  {
    int startPC = c.startPC();
    int endPC = c.endPC();
    int handlerPC = c.handlerPC();
    int catchType = c.catchTypeIndex();

    out.writeShort(startPC);
    out.writeShort(endPC);
    out.writeShort(handlerPC);
    out.writeShort(catchType);
  }

  /**
   * Set the maximum height of the operand stack used by the code.
   *
   * @param maxStack
   *        The maximum height of the stack.
   */
  public void setMaxStack(int maxStack)
  {
    this.maxStack = maxStack;
  }

  /**
   * Set the maximum number of locals used by the code.
   *
   * @param maxStack
   *        The maximum number of locals.
   */
  public void setMaxLocals(int maxLocals)
  {
    this.maxLocals = maxLocals;
  }

  /**
   * Get the maximum height of the operand stack used by the code.
   *
   * @return
   *        The maximum number of locals.
   */
  public int maxStack()
  {
    return maxStack;
  }

  /**
   * Get the maximum number of locals used by the code.
   *
   * @return
   *        The maximum number of locals.
   */
  public int maxLocals()
  {
    return maxLocals;
  }

  /**
   * Set the exception handlers in the method.
   *
   * @param handlers
   *        The handlers.
   */
  public void setExceptionHandlers(Catch[] handlers)
  {
    this.handlers = handlers;
  }

  /**
   * Get the length of the attribute.
   *
   * @return
   *        The length of the attribute.
   */
  public int length()
  {
    int length = 2 + 2 + 4 + code.length + 2 + handlers.length * 8 + 2;

    for (int i = 0; i < attrs.length; i++) {
      length += 2 + 4 + attrs[i].length();
    }

    return length;
  }

  /**
   * Get the line number debug info for the code.
   *
   * @return
   *        The line number debug info for the code.
   */
  public LineNumberDebugInfo[] lineNumbers()
  {
    if (lineNumbers != null) {
      return lineNumbers.lineNumbers();
    }

    return new LineNumberDebugInfo[0];
  }

  /**
   * Get the local variable debug info for the code.
   *
   * @return
   *        The local variable debug info for the code.
   */
  public LocalDebugInfo[] locals()
  {
    if (locals != null) {
      return locals.locals();
    }

    return new LocalDebugInfo[0];
  }

  /**
   * Set the line number debug info for the code.
   *
   * @param lineNumbers
   *        The line number debug info for the code.
   */
  public void setLineNumbers(LineNumberDebugInfo[] lineNumbers)
  {
    if (lineNumbers == null) {
      for (int i = 0; i < attrs.length; i++) {
	if (this.lineNumbers == attrs[i]) {
	  Attribute[] a = attrs;
	  attrs = new Attribute[a.length-1];
	  System.arraycopy(a, 0, attrs, 0, i);
	  System.arraycopy(a, i+1, attrs, i, attrs.length-i);
	  break;
	}
      }

      this.lineNumbers = null;
    }
    else if (this.lineNumbers != null) {
      this.lineNumbers.setLineNumbers(lineNumbers);
    }
  }

  /**
   * Set the local variable debug info for the code.
   *
   * @param locals
   *        The local variable debug info for the code.
   */
  public void setLocals(LocalDebugInfo[] locals)
  {
    if (locals == null) {
      for (int i = 0; i < attrs.length; i++) {
	if (this.locals == attrs[i]) {
	  Attribute[] a = attrs;
	  attrs = new Attribute[a.length-1];
	  System.arraycopy(a, 0, attrs, 0, i);
	  System.arraycopy(a, i+1, attrs, i, attrs.length-i);
	  break;
	}
      }

      this.locals = null;
    }
    else if (this.locals != null) {
      this.locals.setLocals(locals);
    }
  }

  /**
   * Get the exception handlers in the method.
   *
   * @return
   *        The handlers.
   */
  public Catch[] exceptionHandlers()
  {
    return handlers;
  }

  /**
   * Get the bytes of the code.
   *
   * @return
   *        The code.
   */
  public byte[] code()
  {
    return code;
  }

  /**
   * Return the length of the code array
   */
  public int codeLength() {
    return(code.length);
  }

  /**
   * Set the bytes of the code.
   *
   * @param code
   *        The code.
   */
  public void setCode(byte[] code)
  {
    this.code = code;
  }

  /**
   * Private constructor for cloning.
   */
  private Code(Code other) {
    super(other.nameIndex, other.length);

    this.classInfo = other.classInfo;
    this.maxStack = other.maxStack;
    this.maxLocals = other.maxLocals;

    this.code = new byte[other.code.length];
    System.arraycopy(other.code, 0, this.code, 0, other.code.length);
    this.handlers = new Catch[other.handlers.length];
    for(int i = 0; i < other.handlers.length; i++) {
      this.handlers[i] = (Catch) other.handlers[i].clone();
    }

    if(other.lineNumbers != null) {
      this.lineNumbers = (LineNumberTable) other.lineNumbers.clone();
    }

    if(other.locals != null) {
      this.locals = (LocalVariableTable) other.locals.clone();
    }

    this.attrs = new Attribute[other.attrs.length];
    for(int i = 0; i < other.attrs.length; i++) {
      this.attrs[i] = (Attribute) other.attrs[i];
    }
  }

  public Object clone() {
    return(new Code(this));
  }

  /**
   * Returns a string representation of the attribute.
   */
  public String toString()
  {
    String x = "";

    if (handlers != null) {
      for (int i = 0; i < handlers.length; i++) {
	x += "\n        " + handlers[i];
      }
    }

    /*
      for (int i = 0; i < attrs.length; i++) {
      x += "\n        " + attrs[i];
      }
      */

    return "(code " +
      maxStack + " " + maxLocals + " " + code.length + x + ")";
  }
}
