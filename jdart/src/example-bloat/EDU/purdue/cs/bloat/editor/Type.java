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

package EDU.purdue.cs.bloat.editor;

import EDU.purdue.cs.bloat.util.*;
import java.util.*;

/**
 * Type represents a type descriptor in a classes constant pool.  A type
 * descriptor describes the type of a field of method using a funky
 * encoding which is described in the Java Virtual Machine 
 * Specification.  For example, the field <tt>int x[]</tt> has the type
 * descriptor:
 * <p>
 * <center>[I</center>
 * <p>
 * The method <tt>String f(int a, boolean b, Object c)</tt> has the type
 * descriptor:
 * <p>
 * <center>(IZLjava/lang/Object;)Ljava/lang/String;</center>
 * <p>
 *
 * @see EDU.purdue.cs.bloat.reflect.Constant Constant
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Type {
  public static final char ARRAY_CHAR = '[';
  public static final char BOOLEAN_CHAR = 'Z';
  public static final char BYTE_CHAR = 'B';
  public static final char CHARACTER_CHAR = 'C';
  public static final char CLASS_CHAR = 'L';
  public static final char DOUBLE_CHAR = 'D';
  public static final char FLOAT_CHAR = 'F';
  public static final char INTEGER_CHAR = 'I';
  public static final char LONG_CHAR = 'J';
  public static final char SHORT_CHAR = 'S';
  public static final char VOID_CHAR = 'V';
  public static final char ADDRESS_CHAR = 'A';  // return address
  
  public static final int BOOLEAN_CODE = 4;
  public static final int CHARACTER_CODE = 5;
  public static final int FLOAT_CODE = 6;
  public static final int DOUBLE_CODE = 7;
  public static final int BYTE_CODE = 8;
  public static final int SHORT_CODE = 9;
  public static final int INTEGER_CODE = 10;
  public static final int LONG_CODE = 11;
  
  public static final Type OBJECT;
  public static final Type STRING;
  public static final Type CLASS;
  public static final Type THROWABLE;
  public static final Type CLONEABLE;
  public static final Type SERIALIZABLE;
  public static final Type NULL;
  public static final Type BOOLEAN;
  public static final Type CHARACTER;
  public static final Type FLOAT;
  public static final Type DOUBLE;
  public static final Type BYTE;
  public static final Type SHORT;
  public static final Type INTEGER;
  public static final Type LONG;
  public static final Type VOID;
  public static final Type ADDRESS;

  /**
   * Print truncated (abbreviated) type names.
   */
  public static boolean PRINT_TRUNCATED = false;
  private static Map types;   // All types in existence

  static {
    types        = new TreeMap();  // log(n) get

    OBJECT       = getType("Ljava/lang/Object;");
    STRING       = getType("Ljava/lang/String;");
    CLASS        = getType("Ljava/lang/Class;");
    THROWABLE    = getType("Ljava/lang/Throwable;");
    CLONEABLE    = getType("Ljava/lang/Cloneable;");
    SERIALIZABLE = getType("Ljava/lang/Serializable;");
    NULL         = getType("Lnull!;");
    BOOLEAN      = getType(BOOLEAN_CHAR);
    CHARACTER    = getType(CHARACTER_CHAR);
    FLOAT        = getType(FLOAT_CHAR);
    DOUBLE       = getType(DOUBLE_CHAR);
    BYTE         = getType(BYTE_CHAR);
    SHORT        = getType(SHORT_CHAR);
    INTEGER      = getType(INTEGER_CHAR);
    LONG         = getType(LONG_CHAR);
    VOID         = getType(VOID_CHAR);
    ADDRESS      = getType(ADDRESS_CHAR);
  }
  
  String desc;            // The descriptor of a Type.  
  // A descriptor is a String representation of a field or method.
  // Descriptors of basics types consist of a single character code
  // such as B (BYTE_CHAR) for byte and D (DOUBLE_CHAR) for double.
  // An object type is represented by L<classname>;  Array 
  // descriptors have a [ for each dimension followed by the character
  // describing the type (e.g. [[[D for double d[][][]).
  // Method descriptors consist of the descriptors of the method's 
  // parameters delimited by parentheses followed by the descript for
  // the method's return type.  For example, the descriptor for:
  //                   Object m(int, double, Thread)
  // is:
  //                   (IDLjava/lang/Thread;)Ljava/lang/Object;

  // If this is a type descriptor for a method, these are important
  Type[] paramTypes;       // Desriptors of parameters to a method
  Type returnType;         // Descriptor of the return type of a method
  
  /**
   * Returns a <tt>Type</tt> of a given descriptor.  Equals
   * descriptors will result in the same <tt>Type</tt>.
   */
  public static Type getType(String desc) {
    // It might be the case that we're passed a string already in the
    // form of a type descriptor.  Remove any trailing ';' and leading
    // 'L'
//      if(desc.endsWith(";") && desc.startsWith("L")) {
//        desc = desc.substring(1, desc.length() - 1);
//      }

    Type type = (Type) types.get(desc);
    if(type == null) {
      type = new Type(desc);
      types.put(desc, type);
    }
    return(type);
  }

  /**
   * Returns a <code>Type</code> that represents a given
   * <code>Class</code>. 
   */
  public static Type getType(Class c) {
    if(c.equals(Boolean.TYPE)) {
      return(BOOLEAN);

    } else if(c.equals(Character.TYPE)) {
      return(CHARACTER);

    } else if(c.equals(Float.TYPE)) {
      return(FLOAT);

    } else if(c.equals(Double.TYPE)) {
      return(DOUBLE);

    } else if(c.equals(Byte.TYPE)) {
      return(BYTE);

    } else if(c.equals(Short.TYPE)) {
      return(SHORT);

    } else if(c.equals(Integer.TYPE)) {
      return(INTEGER);

    } else if(c.equals(Long.TYPE)) {
      return(LONG);

    } else if(c.equals(Void.TYPE)) {
      return(VOID);
    } 

    return(getType("L" + c.getName().replace('.', '/') + ";"));
  }

  /**
   * Returns the Type for a method with the given parameter and return
   * types
   */
  public static Type getType(Type[] paramTypes, Type returnType) {
    StringBuffer sb = new StringBuffer("(");
    for(int i = 0; i < paramTypes.length; i++) {
      sb.append(paramTypes[i].descriptor());
    }
    sb.append(")");
    sb.append(returnType.descriptor());
    return(getType(sb.toString()));
  }

  /**
   * Constructor.  Creates a type descriptor from a String.
   */
  private Type(String desc) {
    ArrayList types = new ArrayList();
    String t = "";
    boolean method = false;
    
    this.desc = desc.intern();
    
    int state = 0;
    int i = 0;
    
    // Basically, we're parsing the desc String to get information about
    // the descriptor.  For instance, method descriptors begin with '('.

    if (desc.charAt(i) == '(') {
      method = true;
      i++;
    }
    
    for (; i < desc.length(); i++) {
      switch (state) 
	{
	case 0:
	  // Parameter descriptor
	  switch (desc.charAt(i)) 
	    {
	    case BYTE_CHAR:
	    case CHARACTER_CHAR:
	    case DOUBLE_CHAR:
	    case FLOAT_CHAR:
	    case INTEGER_CHAR:
	    case LONG_CHAR:
	    case SHORT_CHAR:
	    case VOID_CHAR:
	    case BOOLEAN_CHAR:
	      types.add(t + desc.charAt(i));
	      t = "";
	      break;
	    case CLASS_CHAR:
	      t += desc.charAt(i);
	      state = 1;
	      break;
	    case ARRAY_CHAR:
	      t += desc.charAt(i);
	      break;
	    case ')':
	      if (! method) {
                String s = "Invalid char in type descriptor: )\n" +
                  "Descriptor was: " + desc;
		throw new IllegalArgumentException(s);
	      }
	      break;
	    default:
	      throw new IllegalArgumentException("Invalid char in " + 
						 "type descriptor (" +
						 desc + "): " +
						 desc.charAt(i));
	    }
	  break;
	  
	case 1:       // Class descriptor
	  t += desc.charAt(i);
	  if (desc.charAt(i) == ';') {  // ';' terminates class descriptor
	    types.add(t);
	    t = "";
	    state = 0;
	  }
	  break;
	}
    }
    
    if (method) {
      // Set up the parameter and return Type fields for a method

      int sizeM1 = types.size()-1;
      returnType = getType((String) types.get(sizeM1));
      paramTypes = new Type[sizeM1];
      for (i = 0; i < sizeM1; i++) {
	String s = (String) types.get(i);
	if(s != null)
	  paramTypes[i] = getType(s);
      }
    }
    else {
      if (types.size() != 1) {
	throw new IllegalArgumentException("More than one type in " + 
					   "the type descriptor: " + desc);
      }
    }
  }
  
  /**
   * Returns a <tt>Type</tt> for a primitive type based on its integer
   * "type code".
   */
  public static Type getType(int typeCode) {
    /* This method is called by codegen.Codegen */
    String desc = null;

    switch (typeCode) {
      case BOOLEAN_CODE:
	desc = "" + BOOLEAN_CHAR;
	break;
      case CHARACTER_CODE:
	desc = "" + CHARACTER_CHAR;
	break;
      case FLOAT_CODE:
	desc = "" + FLOAT_CHAR;
	break;
      case DOUBLE_CODE:
	desc = "" + DOUBLE_CHAR;
	break;
      case BYTE_CODE:
	desc = "" + BYTE_CHAR;
	break;
      case SHORT_CODE:
	desc = "" + SHORT_CHAR;
	break;
      case INTEGER_CODE:
	desc = "" + INTEGER_CHAR;
	break;
      case LONG_CODE:
	desc = "" + LONG_CHAR;
	break;
      default:
	throw new IllegalArgumentException("Invalid type code: " + typeCode);
      }

    Type type = (Type) types.get(desc);
    if(type == null) {
      type = new Type();
      type.setDesc(desc);
      types.put(desc, type);
    }
    return(type);
  }

  /**
   * Empty constructor.
   */
  private Type() {
    this.desc = null;
  }

  private void setDesc(String desc) {
    this.desc = desc;
  }
  
  /**
   * Returns a <tt>Type</tt> of a primitive type based on a
   * one-character type descriptor.
   */
  public static Type getType(char typeChar) {
    String desc = null;

    switch (typeChar) {
    case BOOLEAN_CHAR:
    case CHARACTER_CHAR:
    case FLOAT_CHAR:
    case DOUBLE_CHAR:
    case BYTE_CHAR:
    case SHORT_CHAR:
    case INTEGER_CHAR:
    case LONG_CHAR:
    case ADDRESS_CHAR:
    case VOID_CHAR:
      desc = "" + typeChar;
      desc = desc.intern();
      break;
    default:
      throw new IllegalArgumentException("Invalid type descriptor: " +
					 typeChar);
    }

    Type type = (Type) types.get(desc);
    if(type == null) {
      type = new Type();
      type.setDesc(desc);
      types.put(desc, type);
    }
    return(type);
  }
  
  /**
   * Get the type code of the type (which must be a primitive type).
   *
   * @return
   *        The type code of the type.
   * @exception IllegalArgumentException
   *        If the type is not primitive.
   */
  public int typeCode()
  {
    if (desc.length() == 1) {
      switch (desc.charAt(0)) 
	{
	case BOOLEAN_CHAR:
	  return BOOLEAN_CODE;
	case CHARACTER_CHAR:
	  return CHARACTER_CODE;
	case FLOAT_CHAR:
	  return FLOAT_CODE;
	case DOUBLE_CHAR:
	  return DOUBLE_CODE;
	case BYTE_CHAR:
	  return BYTE_CODE;
	case SHORT_CHAR:
	  return SHORT_CODE;
	case INTEGER_CHAR:
	  return INTEGER_CODE;
	case LONG_CHAR:
	  return LONG_CODE;
	}
    }
    
    throw new IllegalArgumentException("Invalid type descriptor: " + desc);
  }
  
  /**
   * Get a one character name for the type.
   */
  public String shortName()
  {
    if (isIntegral()) {
      return "" + INTEGER_CHAR;
    }
    
    // Use R rather than L for readability.
    if (isReference()) {
      return "R";
    }
    
    Assert.isTrue(desc.length() == 1, "Short name too long: " + desc);
    
    return desc;
  }
  
  /**
   * Get a simplification of the type.  All integral types become INTEGER.
   * All reference types (objects, arrays, returnAddress) become OBJECT.
   *
   * @return
   *        The simplified type.
   */
  public Type simple()
  {
    if (isIntegral()) {
      return Type.INTEGER;
    }
    
    if (isReference()) {
      return Type.OBJECT;
    }
    
    return this;
  }
  
  /**
   * Get a descriptor of the type.
   *
   * @return
   *        The type descriptor.
   */
  public String descriptor()
  {
    return desc;
  }
  
  /**
   * Check if the type is a method type.
   *
   * @return
   *        true if a method type, false if not.
   */
  public boolean isMethod()
  {
    return returnType != null;
  }
  
  /**
   * Check if the type is a null type.
   *
   * @return
   *        true if a null type, false if not.
   */
  public boolean isNull()
  {
    return equals(NULL);
  }
  
  /**
   * Check if the type is a void type.
   *
   * @return
   *        true if a void type, false if not.
   */
  public boolean isVoid()
  {
    return equals(VOID);
  }
  
  /**
   * Check if the type is a primitive type.
   *
   * @return
   *        true if a primitive type, false if not.
   */
  public boolean isPrimitive()
  {
    return ! isReference() && ! isMethod() && ! isVoid();
  }
  
  /**
   * Check if the type is an integral type.
   * Integral contains BOOLEAN as far as the JVM is concerned.
   *
   * @return
   *        true if an integral type, false if not.
   */
  public boolean isIntegral()
  {
    return desc.charAt(0) == BYTE_CHAR ||
           desc.charAt(0) == SHORT_CHAR ||
           desc.charAt(0) == INTEGER_CHAR ||
           desc.charAt(0) == CHARACTER_CHAR ||
           desc.charAt(0) == BOOLEAN_CHAR;
  }

  /**
   * Check if the type is an array type.
   *
   * @return
   *        true if an array type, false if not.
   */
  public boolean isArray()
  {
    return desc.charAt(0) == ARRAY_CHAR;
  }
  
  /**
   * Check if the type is an object type (not array).
   *
   * @return
   *        true if an object type, false if not.
   */
  public boolean isObject()
  {
    return desc.charAt(0) == CLASS_CHAR;
  }
  
  /**
   * Check if the type takes of 2 local variable or stack positions.
   *
   * @return
   *        true if a wide type, false if not.
   */
  public boolean isWide()
  {
    return desc.charAt(0) == LONG_CHAR || desc.charAt(0) == DOUBLE_CHAR;
  }
  
  /**
   * Check if the type is a returnAddress.
   *
   * @return
   *        true if a address type, false if not.
   */
  public boolean isAddress()
  {
    return desc.charAt(0) == ADDRESS_CHAR;
  }
  
  /**
   * Check if the type is an array or object.
   *
   * @return
   *        true if a reference type, false if not.
   */
  public boolean isReference()
  {
    return desc.charAt(0) == ARRAY_CHAR || desc.charAt(0) == CLASS_CHAR;
  }
  
  /**
   * Get the type descriptor of a class from a string representation..
   * For example "java/lang/String" becomes "Ljava/lang/String;"
   *
   * @param name
   *        The name of the class.
   * @return
   *        The type descriptor of the class.
   */
  public static String classDescriptor(String name)
  {
    // The name of the class may arrive as the name of the class file 
    // in which in resides.  In this case, we should remove the .class
    // file extension.
    if(name.endsWith(".class")) {
      name = name.substring(0, name.lastIndexOf('.'));
    }
    
    name = name.replace('.', '/');

    if (name.charAt(0) == ARRAY_CHAR) {
      return name;
    }
    
    return CLASS_CHAR + name + ";";
  }
  
  /**
   * Get the class name of the type.
   * For example if the class descriptor is "Ljava/lang/String;", the 
   * class name is "java/lang/String".
   *
   * @return
   *        The class name of the type.
   */
  public String className()
  {
    if (desc.charAt(0) == ARRAY_CHAR || this.isPrimitive()) {
      return desc;
    }
    
    return desc.substring(1, desc.lastIndexOf(';'));
  }
  
  /**
   * Get the qualifier of the type.
   * For example if the class descriptor is "Ljava/lang/String;", the
   * qualifier is "java/lang/".
   *
   * @return
   *        The qualifier of the type.
   */
  public String qualifier()
  {
    Assert.isTrue(desc.charAt(0) == CLASS_CHAR, "No qualifier for " + desc);
    
    int index = desc.lastIndexOf('/');
    
    if (index >= 0) {
      return desc.substring(1, index);
    }
    
    return desc.substring(1, desc.lastIndexOf(';'));
  }
  
  /**
   * Get the number of dimensions of an array type.
   */
  public int dimensions()
  {
    for (int i = 0; i < desc.length(); i++) {
      if (desc.charAt(i) != ARRAY_CHAR) {
	return i;
      }
    }
    
    throw new IllegalArgumentException(desc +
				       " does not have an element type.");
  }
  
  /**
   * Get a Type representing an array of this type.
   */
  public Type arrayType() {
    return getType("" + ARRAY_CHAR + desc);
  }
  
  /**
   * Create a Type representing a multidimensional array of this type.
   */
  public Type arrayType(int dimensions) {
    String d = "";
    
    while (dimensions-- > 0) {
      d += ARRAY_CHAR;
    }
    
    return getType(d + desc);
  }
  
  /**
   * Get the element type of this array type.
   *
   * @param dimensions
   *        The number of times to index into the array.
   * @return
   *        The element type.
   * @exception IllegalArgumentException
   *        If the type is not an array.
   */
  public Type elementType(int dimensions) {
    for (int i = 0; i < dimensions; i++) {
      if (desc.charAt(i) != ARRAY_CHAR) {
	throw new IllegalArgumentException(desc + " is not an array");
      }
    }
    
    return getType(desc.substring(dimensions));
  }
  
  /**
   * Get the element type of an array type.
   *
   * @return
   *        The element type.
   * @exception IllegalArgumentException
   *        If the type is not an array.
   */
  public Type elementType()
  {
    return elementType(1);
  }
  
  /**
   * Get a return type of a method type.
   *
   * @return
   *        The return type.
   */
  public Type returnType()
  {
    return this.returnType;
  }
  
  /**  
   * If this Type is a method type, get the parameter types of the
   * method, including empty positions for the second word of wide
   * types.  This method is good for figuring out which local
   * variables go with parameters.  Recall that wide data takes up two
   * local variables.
   */
  public Type[] indexedParamTypes()
  {
    if (paramTypes == null) {
      return null;
    }
    
    ArrayList p = new ArrayList(paramTypes.length*2);
    
    // Count longs and doubles as 2.
    for (int i = 0; i < paramTypes.length; i++) {
      p.add(paramTypes[i]);
      if (paramTypes[i].isWide()) {
	p.add(null);
      }
    }
    
    Object[] a = p.toArray();
    Type[] types = new Type[a.length];
    System.arraycopy(a, 0, types, 0, a.length);
    return types;
  }
  
  /**
   * Get the parameter types of the method, not including empty positions
   * for the second word of wide types.
   *
   * @return
   *        The parameter types.
   */
  public Type[] paramTypes()
  {
    return this.paramTypes;
  }
  
  /**
   * Returns the number of slots in the stack that this Type takes up
   * on the JVM stack.  This type descriptor is intended to represent
   * the parameters of a method.  If there are no parameters, 0 is
   * returned.  Else return count each parameter as 1 except wide
   * parameters (longs and doubles) as 2.
   * <p>
   * It also give you an idea of how may parameters a method has.
   */
  public int stackHeight()
  {
    if (isVoid()) {
      return 0;
    }
    
    if (isWide()) {
      return 2;
    }
    
    if (paramTypes != null) {
      int numParams = 0;
      
      // Count longs and doubles as 2.
      for (int i = 0; i < paramTypes.length; i++) {
	if (paramTypes[i].isWide()) {
	  numParams++;
	}
	numParams++;
      }
      
      return numParams;
    }
    
    return 1;
  }
  
  /**
   * Hash the type.
   *
   * @return
   *        The hash code.
   */
  public int hashCode() {
    return desc.hashCode();
  }
  
  /**
   * Returns <tt>true</tt> if two <tt>Type</tt>s are equal.  Equal
   * <tT>Type</tt>s have equal descriptors.
   */
  public boolean equals(Object obj) {
    return obj != null &&
      obj instanceof Type &&
      ((Type) obj).desc.equals(desc);
  }
  
  private static Comparator comparator = null;
  /**
   * Returns a <tt>Comparator</tt> used to compare <tt>Type</tt>s.
   */
  public static Comparator comparator() {
    if(comparator != null)
      return(comparator);

    comparator = new Comparator() {
	public int compare(Object o1, Object o2) {
	  // o1 < o2   :  -1
	  // o1 == o2  :   0
	  // o1 > o2   :   1

	  if(!(o1 instanceof Type))
	    throw new IllegalArgumentException(o1 + " not a Type");

	  if(!(o2 instanceof Type))
	    throw new IllegalArgumentException(o2 + " not a Type");

	  Type t1 = (Type) o1;
	  Type t2 = (Type) o2;

	  String d1 = t1.descriptor();
	  String d2 = t2.descriptor();

	  return(d1.compareToIgnoreCase(d2));

	}

	public boolean equals(Object o) {
	  if(o == this)
	    return(true);
	  else
	    return(false);
	}
      };
    return(comparator);
  }

  private static Comparator printComparator = null;
  /**
   * Returns a <tt>Comparator</tt> that compares <tt>Type</tt>s based
   * on how they are displayed.
   */
  public static Comparator printComparator() {
    if(printComparator != null)
      return(printComparator);

    comparator = new Comparator() {
	public int compare(Object o1, Object o2) {
	  // o1 < o2   :  -1
	  // o1 == o2  :   0
	  // o1 > o2   :   1

	  if(!(o1 instanceof Type))
	    throw new IllegalArgumentException(o1 + " not a Type");

	  if(!(o2 instanceof Type))
	    throw new IllegalArgumentException(o2 + " not a Type");

	  Type t1 = (Type) o1;
	  Type t2 = (Type) o2;

	  String d1 = t1.toString();
	  String d2 = t2.toString();

	  return(d1.compareToIgnoreCase(d2));

	}

	public boolean equals(Object o) {
	  if(o == this)
	    return(true);
	  else
	    return(false);
	}
      };
    return(comparator);
  }

  /**
   * Returns a string representing the truncated name of a
   * <tt>Type</tt>.  For instance <tt>java/lang/String</tt> would just
   * return <tt>String</tt>.  
   */
  public static String truncatedName(Type type) {
    // Sometimes type can be null!  I don't know why.
    if(type == null)
      return("");

    //    System.out.println("Truncating type: " + type.desc);

    if(type.isPrimitive() || type.isVoid() || type.isNull())
      return(type.desc);  // Don't invoke toString!

    if(type.isArray())
      return("[" + truncatedName(type.elementType()));

    if(type.isMethod()) {
      StringBuffer sb = new StringBuffer();
      sb.append('(');
      Type[] params = type.indexedParamTypes();
      for(int i = 0 ; i < params.length; i++) {
	sb.append(truncatedName(params[i]));
	if(i < params.length - 1)
	  sb.append(',');
      }
      sb.append(')');
      sb.append(truncatedName(type.returnType()));
      return(sb.toString());
    }

    // Otherwise its a normal type such as java/lang/String.
    // First, chop off the package name.
    String longName = type.className();
    int lastSlash = longName.lastIndexOf('/');
    if(lastSlash == -1)
      lastSlash = longName.lastIndexOf('.');
    String className = longName.substring(lastSlash + 1);
    
    if(className.length() > 8) {
      // Come up with an abbreviated name.  Find each capital letter
      // in the name.  If there are fewer than 8 capital letters, then
      // use characters after the last capital for a total of 8
      // characters.  For instance, StringBuffer becomes SBuffer.
      
      // Count capitals
      StringBuffer caps = new StringBuffer();
      int nameLength = className.length();
      for(int i = 0; i < nameLength; i++) {
	char c = className.charAt(i);
	if(Character.isUpperCase(c)) {
	  caps.append(c);
	}
      }

      if(caps.length() > 8) {
	// This is an UGLY class name.  Use the first four caps and
	// the last four caps.
	int capsLength = caps.length();
	for(int i = 4; i < capsLength - 4; i++) {
	  caps.deleteCharAt(i);
	}
	return(caps.toString());
      }

      if(caps.length() <= 0) {
	// Grumble.  No caps in name.  Return first 8 characters
	return(className.substring(0, 8) );
      }

      char lastCap = caps.charAt(caps.length() - 1);
      int indexLastCap = className.lastIndexOf(lastCap);
      int capsLength = caps.length();
      int end;

      if(capsLength +(nameLength - indexLastCap) > 8)
	end = indexLastCap + 1 + (8 - capsLength);
      else
	end = nameLength;

      String endOfName = className.substring(indexLastCap + 1, end);

      caps.append(endOfName);
      return(caps.toString());
    }

    // Just return name of class
    return(className);

  }


  /**
   * Convert the type to a string.
   *
   * @return
   *        A string representation of the type.
   */
  public String toString() {
    if(Type.PRINT_TRUNCATED)
      return(Type.truncatedName(this));
    else
      return desc;
  } 

  /**
   * Test truncatedName.
   */
  public static void main(String args[]) {
    Type.truncatedName(getType("(D)V"));

    // Print the truncated name of each argument
    for(int i = 0; i < args.length; i++) {
      System.out.println("Truncated name of " + args[i] + ": " + 
			 Type.truncatedName(getType(args[i])));
      
    }
  }
}
