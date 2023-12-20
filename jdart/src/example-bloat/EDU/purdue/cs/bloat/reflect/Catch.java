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

package EDU.purdue.cs.bloat.reflect;

import java.util.*;
import java.io.*;

/**
 * Catch stores information about a protected block and an exception handler
 * in a method.  The startPC, endPC, and handlerPC are indices into the
 * bytecode of the method where the protected block begins and ends and the
 * catch block begins, respectively.  They are indices into the code array.
 *
 * @see EDU.purdue.cs.bloat.file.Code#code
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Catch
{
  private int startPC;
  private int endPC;
  private int handlerPC;
  private int catchType;
	
  /**
   * Constructor.
   *
   * @param startPC
   *        The start PC of the protected block.
   * @param endPC
   *        The PC of the instruction after the end of the protected block.
   * @param handlerPC
   *        The start PC of the exception handler.
   * @param catchType
   *        The type of exception to catch.
   */
  public Catch(int startPC, int endPC, int handlerPC, int catchType)
  {
    this.startPC = startPC;
    this.endPC = endPC;
    this.handlerPC = handlerPC;
    this.catchType = catchType;
  }

  /**
   * Get the start PC of the protected block.
   *
   * @return
   *        The start PC of the protected block.
   * @see Catch#setStartPC
   */
  public int startPC()
  {
    return startPC;
  }

  /**
   * Set the start PC of the protected block.
   *
   * @param pc
   *        The start PC of the protected block.
   * @see Catch#startPC
   */
  public void setStartPC(int pc)
  {
    startPC = pc;
  }
    
  /**
   * Get the end PC of the protected block.
   *
   * @return
   *        The PC of the instruction after the end of the protected block.
   * @see Catch#setEndPC
   */
  public int endPC()
  {
    return endPC;
  }

  /**
   * Set the end PC of the protected block.
   *
   * @param pc
   *        The PC of the instruction after the end of the protected block.
   * @see Catch#endPC
   */
  public void setEndPC(int pc)
  {
    endPC = pc;
  }

  /**
   * Get the start PC of the exception handler.
   *
   * @return
   *        The start PC of the exception handler.
   * @see Catch#setHandlerPC
   */
  public int handlerPC()
  {
    return handlerPC;
  }

  /**
   * Set the start PC of the exception handler.
   *
   * @param pc
   *        The start PC of the exception handler.
   * @see Catch#handlerPC
   */
  public void setHandlerPC(int pc)
  {
    handlerPC = pc;
  }
    
  /**
   * Get the index into the constant pool of the type of exception to catch.
   *
   * @return
   *        Index of the type of exception to catch.
   * @see Catch#setCatchTypeIndex
   */
  public int catchTypeIndex()
  {
    return catchType;
  }

  /**
   * Set the index into the constant pool of the type of exception to catch.
   *
   * @param index
   *        Index of the type of exception to catch.
   * @see Catch#catchTypeIndex
   */
  public void setCatchTypeIndex(int index)
  {
    this.catchType = index;
  }

  public Object clone() {
    return(new Catch(this.startPC, this.endPC, this.handlerPC,
		     this.catchType));
  }

  /**
   * Returns a string representation of the catch information.
   */
  public String toString()
  {
    return "(try-catch " + startPC + " " + endPC + " " +
      handlerPC + " " + catchType + ")";
  }
}
