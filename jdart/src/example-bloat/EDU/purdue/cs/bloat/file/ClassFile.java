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
import EDU.purdue.cs.bloat.util.*;
import java.io.*;
import java.util.*;

/**
 * ClassFile basically represents a Java classfile as it is found on 
 * disk.  The classfile is modeled according to the Java Virtual Machine
 * Specification.  Methods are provided to edit the classfile at a very
 * low level.
 *
 * @see Attribute
 * @see EDU.purdue.cs.bloat.reflect.Constant
 * @see Field
 * @see Method
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class ClassFile implements ClassInfo {
  private ClassInfoLoader loader;  // ClassInfoLoader that "owns" this class
  private List constants;       // The constant pool
  private int modifiers;              // This class's modifer bit field
  private int thisClass;              
  private int superClass;             
  private int[] interfaces;           
  private Field[] fields;
  private Method[] methods;
  private Attribute[] attrs;
  private File file;                  // (.class) File in which this class resides
  
  /**
   * Constructor.  This constructor parses the class file from the input
   * stream.
   *
   * @param file
   *        The file in which the class resides.
   * @param loader
   *        The class info loader which loaded the class.
   * @param in
   *        The data stream containing the class.
   * @exception ClassFormatError
   *        When the class could not be parsed.
   */
  public ClassFile(File file, ClassInfoLoader loader, DataInputStream in)
  {
    this.loader = loader;
    this.file = file;
//     Assert.isTrue(file != null, "Null file for class file");

//    System.out.println("------------------------" + file.getAbsolutePath());
    
    // Read in file contents from stream
    try {
      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading header");
      readHeader(in);

      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading constant pool");
      readConstantPool(in);

      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading access flags");
      readAccessFlags(in);

      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading class info");
      readClassInfo(in);

      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading fields");
      readFields(in);

      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading methods");
      readMethods(in);

      if(ClassFileLoader.DEBUG)
	System.out.println("ClassFile: Reading Attributes");
      readAttributes(in);
      in.close();
    }
    catch (IOException e) {
      throw new ClassFormatException(e.getMessage() + 
				     " (in file " + file + ")");
    }
//    catch (Exception e) {
//    	
//    }
  }
  
  /**
   * Creates a new <code>ClassFile</code> from scratch.  It has no
   * fields or methods.
   *
   * @param modifiers
   *        The modifiers describing the newly-created class
   * @param classIndex
   *        The index of the type of the newly-created class in its
   *        constant pool
   * @param superClassIndex
   *        The index of the type of the newly-created class's
   *        superclass in its constant pool
   * @param interfaceIndexes
   *        The indexes of the types of the interfaces that the
   *        newly-created class implements
   * @param constants
   *        The constant pool for the newly created class (a list of
   *        {@link Constant}s).
   */
  public ClassFile(int modifiers, int classIndex, int superClassIndex,
                   int[] interfaceIndexes, List constants,
                   ClassInfoLoader loader) {
    this.modifiers = modifiers;
    this.thisClass = classIndex;
    this.superClass = superClassIndex;
    this.interfaces = interfaceIndexes;
    this.constants = constants;
    this.loader = loader;

    this.fields = new Field[0];
    this.methods = new Method[0];
    this.attrs = new Attribute[0];
  }

  /**
   * Get the class info loader for the class.
   *
   * @return
   *        The class info loader for the class.
   */
  public ClassInfoLoader loader()
  {
    return loader;
  }
  
  /**
   * Get the name of the class, including the package name.
   *
   * @return
   *        The name of the class.
   */
  public String name()
  {
    Constant c = (Constant) constants.get(thisClass);
    Assert.isNotNull(c, "Null constant for class name");
    if (c.tag() == Constant.CLASS) {
      Integer nameIndex = (Integer) c.value();
      if (nameIndex != null) {
	c = (Constant) constants.get(nameIndex.intValue());
	if (c.tag() == Constant.UTF8) {
	  return (String) c.value();
	}
      }
    }
    
    throw new ClassFormatException("Couldn't find class name in file"); 
  }
  
  /**
   * Set the index into the constant pool of the name of the class.
   *
   * @param index
   *        The index of the name of the class.
   */
  public void setClassIndex(int index)
  {
    this.thisClass = index;
  }
  
  /**
   * Set the index into the constant pool of the name of the class's
   * superclass.
   *
   * @param index
   *        The index of the name of the superclass.
   */
  public void setSuperclassIndex(int index)
  {
    this.superClass = index;
  }
  
  /**
   * Set the indices into the constant pool of the names of the class's
   * interfaces.
   *
   * @param indices
   *        The indices of the names of the interfaces.
   */
  public void setInterfaceIndices(int[] indices)
  {
    this.interfaces = indices;
  }
  
  /**
   * Get the index into the constant pool of the name of the class.
   *
   * @return
   *        The index of the name of the class.
   */
  public int classIndex()
  {
    return thisClass;
  }
  
  /**
   * Get the index into the constant pool of the name of the class's
   * superclass.
   *
   * @return
   *        The index of the name of the superclass.
   */
  public int superclassIndex()
  {
    return superClass;
  }
  
  /**
   * Get the indices into the constant pool of the names of the class's
   * interfaces.
   *
   * @return
   *        The indices of the names of the interfaces.
   */
  public int[] interfaceIndices()
  {
    return interfaces;
  }
  
  /**
   * Set the modifiers of the class.
   * The values correspond to the constants in the Modifiers class.
   *
   * @param modifiers
   *        A bit vector of modifier flags for the class.
   * @see Modifiers
   */
  public void setModifiers(int modifiers)
  {
    this.modifiers = modifiers;
  }
  
  /**
   * Get the modifiers of the class.
   * The values correspond to the constants in the Modifiers class.
   *
   * @return
   *        A bit vector of modifier flags for the class.
   * @see Modifiers
   */
  public int modifiers()
  {
    return modifiers;
  }
  
  /**
   * Get an array of FieldInfo structures for each field in the class.
   *
   * @return
   *        An array of FieldInfo structures.
   */
  public FieldInfo[] fields()
  {
    return fields;
  }
  
  /**
   * Returns an array of MethodInfo structures for each method in the class.
   */
  public MethodInfo[] methods() {
    return methods;
  }

  /**
   * Sets the methods in this class.
   */
  public void setMethods(MethodInfo[] methods) {
    this.methods = new Method[methods.length]; 
    for(int i = 0; i < methods.length; i++) {
      this.methods[i] = (Method) methods[i];
    }

  }
  
  /**
   * Get an array of the constants in the constant pool.
   *
   * @return
   *        An array of Constants.
   */
  public Constant[] constants()
  {
    return (Constant[]) constants.toArray(new Constant[0]);
  }
  
  /**
   * Set all the constants in the constant pool.
   *
   * @param constants
   *        The array of Constants.
   */
  public void setConstants(Constant[] constants)
  {
    this.constants = new ArrayList(constants.length);
    for (int i = 0; i < constants.length; i++) {
      this.constants.add(i, constants[i]);
    }
  }
  
  /**
   * Returns the File from which this <code>ClassFile</code> was
   * created.  If this <code>ClassFile</code> was created from
   * scratch, <code>null</code> is returned.
   */
  public File file()
  {
    return file;
  }
  
  /**
   * Creates a new File object to hold this class.  It is placed in the output
   * directory and has the name of the class represented by this ClassFile
   * followed by the .class extension.
   *
   * @see #outputDir
   */
  public File outputFile()
  {
    File outputDir = ((ClassFileLoader) loader).outputDir();
    String fileName = this.name().replace('/', File.separatorChar);
    return new File(outputDir, fileName + ".class");
  }
  
  /** 
   * Commit any changes back to a file in the output directory.  The
   * output directory is determined from the ClassFileLoader.
   */
  public void commit() {
    try {      
      commitTo(loader.outputStreamFor(this));

    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  /**
   * Commit changes made to this class.  Write changes to an OutputStream.
   */
  void commitTo(OutputStream outStream) {
    try {
      DataOutputStream out = new DataOutputStream(outStream);

      writeHeader(out);
      writeConstantPool(out);
      writeAccessFlags(out);
      writeClassInfo(out);
      writeFields(out, null);
      writeMethods(out, null);
      writeAttributes(out);
      
      out.close();

    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  public void commitOnly(Set methods, Set fields) {
    try {
      OutputStream outStream = loader.outputStreamFor(this);
      DataOutputStream out = new DataOutputStream(outStream);

      writeHeader(out);
      writeConstantPool(out);
      writeAccessFlags(out);
      writeClassInfo(out);
      writeFields(out, fields);
      writeMethods(out, methods);
      writeAttributes(out);
      
      out.close();

    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
  
  /**
   * Write the class file header.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeHeader(DataOutputStream out)
       throws IOException
  {
    out.writeInt(0xCAFEBABE);
    out.writeShort(3);
    out.writeShort(45);
  }
  
  /**
   * Write the class's constant pool.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeConstantPool(DataOutputStream out)
       throws IOException
  {
    out.writeShort(constants.size());
    
    // Write the constants.  The first constant is reserved for
    // internal use by the JVM, so start at 1.
    for (int i = 1; i < constants.size(); i++) {
      writeConstant(out, (Constant) constants.get(i));
      
      switch (((Constant) constants.get(i)).tag()) {
      case Constant.LONG:
      case Constant.DOUBLE:
	// Longs and doubles take up 2 constant pool entries.
	i++;
	break;
      }
    }
  }
  
  /**
   * Read a constant from the constant pool.
   *
   * @param in
   *        The stream from which to read.
   * @return
   *        The constant.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private Constant readConstant(DataInputStream in)
       throws IOException
  {
    int tag = in.readUnsignedByte();
    Object value;
    
    switch (tag) 
      {
      case Constant.CLASS:
      case Constant.STRING:
	value = new Integer(in.readUnsignedShort());
	break;
      case Constant.FIELD_REF:
      case Constant.METHOD_REF:
      case Constant.INTERFACE_METHOD_REF:
      case Constant.NAME_AND_TYPE:
	value = new int[2];
	((int[]) value)[0] = in.readUnsignedShort();
	((int[]) value)[1] = in.readUnsignedShort();
	break;
      case Constant.INTEGER:
	value = new Integer(in.readInt());
	break;
      case Constant.FLOAT:
	value = new Float(in.readFloat());
	break;
      case Constant.LONG:
	// Longs take up 2 constant pool entries.
	value = new Long(in.readLong());
	break;
      case Constant.DOUBLE:
	// Doubles take up 2 constant pool entries.
	value = new Double(in.readDouble());
	break;
      case Constant.UTF8:
	value = in.readUTF();
	break;
      default:
	throw new ClassFormatException(file.getPath() +
				       ": Invalid constant tag: " + tag);
      }
    
    return new Constant(tag, value);
  }
  
  /**
   * Write a constant in the constant pool.
   *
   * @param out
   *        The stream to which to write.
   * @param constant
   *        The constant.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeConstant(DataOutputStream out, Constant constant)
       throws IOException
  {
    int tag = constant.tag();
    Object value = constant.value();
    
    out.writeByte(tag);
    
    switch (tag) 
      {
      case Constant.CLASS:
      case Constant.STRING:
	out.writeShort(((Integer) value).intValue());
	break;
      case Constant.INTEGER:
	out.writeInt(((Integer) value).intValue());
	break;
      case Constant.FLOAT:
	out.writeFloat(((Float) value).floatValue());
	break;
      case Constant.LONG:
	out.writeLong(((Long) value).longValue());
	break;
      case Constant.DOUBLE:
	out.writeDouble(((Double) value).doubleValue());
	break;
      case Constant.UTF8:
	out.writeUTF((String) value);
	break;
      case Constant.FIELD_REF:
      case Constant.METHOD_REF:
      case Constant.INTERFACE_METHOD_REF:
      case Constant.NAME_AND_TYPE:
	out.writeShort(((int[]) value)[0]);
	out.writeShort(((int[]) value)[1]);
	break;
      }
  }
  
  /**
   * Write the class's access flags.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeAccessFlags(DataOutputStream out)
       throws IOException
  {
    out.writeShort(modifiers);
  }
  
  /**
   * Write the class's name, superclass, and interfaces.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeClassInfo(DataOutputStream out)
       throws IOException
  {
    int index;
    
    out.writeShort(thisClass);
    out.writeShort(superClass);
    
    out.writeShort(interfaces.length);
    
    for (int i = 0; i < interfaces.length; i++) {
      out.writeShort(interfaces[i]);
    }
  }
  
  /**
   * Write the class's fields.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeFields(DataOutputStream out, Set onlyFields)
       throws IOException
  {
    out.writeShort(fields.length);
    
    for (int i = 0; i < fields.length; i++) {
      if(onlyFields != null && onlyFields.contains(fields[i]))
	continue;
      fields[i].write(out);
    }
  }
  
  /**
   * Write the class's methods.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeMethods(DataOutputStream out, Set onlyMethods)
       throws IOException
  {
    if(onlyMethods != null) {
      out.writeShort(onlyMethods.size());

    } else {
      if(Method.DEBUG) {
	System.out.println("Writing " + methods.length + " methods");
      }
      out.writeShort(methods.length);
    }
    
    for (int i = 0; i < methods.length; i++) {
      if(onlyMethods != null && onlyMethods.contains(methods[i]))
	continue;
      methods[i].write(out);
    }
  }
  
  /**
   * Write the class's attributes.  No attributes are written
   * by this method since none are required.
   *
   * @param out
   *        The stream to which to write.
   * @exception IOException
   *        If an error occurs while writing.
   */
  private void writeAttributes(DataOutputStream out)
       throws IOException
  {
    out.writeShort(attrs.length);
    
    for (int i = 0; i < attrs.length; i++) {
      out.writeShort(attrs[i].nameIndex());
      out.writeInt(attrs[i].length());
      attrs[i].writeData(out);
    }
  }
  
  /**
   * Read the class file header.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private void readHeader(DataInputStream in)
       throws IOException
  {
    int magic = in.readInt();

    if (magic != 0xCAFEBABE) {
      throw new ClassFormatError("Bad magic number.");
    }
    
    int major = in.readUnsignedShort();
    
    int minor = in.readUnsignedShort();
    
//    System.err.println("----------- readUnsignedShort 2 " + minor);    
  }
  
  /**
   * Read the class's constant pool.  Constants in the constant pool
   * are modeled by an array of <tt>reflect.Constant</tt>/
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   *
   * @see EDU.purdue.cs.bloat.reflect.Constant
   * @see #constants
   */
  private void readConstantPool(DataInputStream in)
       throws IOException
  {
    int count = in.readUnsignedShort();
    
    constants = new ArrayList(count);
    
    // The first constant is reserved for internal use by the JVM.
    constants.add(0, null);
    
    // Read the constants.
    for (int i = 1; i < count; i++) {
      constants.add(i, readConstant(in));
      
      switch (((Constant) constants.get(i)).tag()) 
	{
	case Constant.LONG:
	case Constant.DOUBLE:
	  // Longs and doubles take up 2 constant pool entries.
	  constants.add(++i, null);
	  break;
	}
    }
  }
  
  /**
   * Read the class's access flags.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private void readAccessFlags(DataInputStream in)
       throws IOException
  {
    modifiers = in.readUnsignedShort();
  }
  
  /**
   * Read the class's name, superclass, and interfaces.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private void readClassInfo(DataInputStream in)
       throws IOException
  {
    int index;
    
    thisClass = in.readUnsignedShort();
    superClass = in.readUnsignedShort();
    
    int numInterfaces = in.readUnsignedShort();
    
    interfaces = new int[numInterfaces];
    
    for (int i = 0; i < numInterfaces; i++) {
      interfaces[i] = in.readUnsignedShort();
    }
  }
  
  /**
   * Read the class's fields.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private void readFields(DataInputStream in)
       throws IOException
  {
    int numFields = in.readUnsignedShort();
    
    fields = new Field[numFields];
    
    for (int i = 0; i < numFields; i++) {
      fields[i] = new Field(in, this);
    }
  }
  
  /**
   * Read the class's methods.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private void readMethods(DataInputStream in)
       throws IOException
  {
    int numMethods = in.readUnsignedShort();
    
    methods = new Method[numMethods];
    
    for (int i = 0; i < numMethods; i++) {
      methods[i] = new Method(in, this);
    }
  }
  
  /**
   * Read the class's attributes.  Since none of the attributes
   * are required, just read the length of each attribute and
   * skip that many bytes.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  private void readAttributes(DataInputStream in)
       throws IOException
  {
    int numAttributes = in.readUnsignedShort();
    
    attrs = new Attribute[numAttributes];
    
    for (int i = 0; i < numAttributes; i++) {
      int nameIndex = in.readUnsignedShort();
      int length = in.readInt();
      attrs[i] = new GenericAttribute(in, nameIndex, length);
    }
  }

  /**
   * Creates a new field in this classfile
   */
  public FieldInfo addNewField(int modifiers, int typeIndex, 
                               int nameIndex) {
    Field field = new Field(this, modifiers, typeIndex, nameIndex);

    // Add the new field to the list of fields
    Field[] fields = new Field[this.fields.length + 1];
    for(int i = 0; i < this.fields.length; i++) {
      fields[i] = this.fields[i];
    }
    fields[this.fields.length] = field;
    this.fields = fields;

    return(field);
  }

  /**
   * Creates a new field in this classfile
   */
  public FieldInfo addNewField(int modifiers, int typeIndex, 
                               int nameIndex, int cvNameIndex,
                               int constantValueIndex) {
    Field field = 
      new Field(this, modifiers, typeIndex, nameIndex, cvNameIndex,
                constantValueIndex);

    // Add the new field to the list of fields
    Field[] fields = new Field[this.fields.length + 1];
    for(int i = 0; i < this.fields.length; i++) {
      fields[i] = this.fields[i];
    }
    fields[this.fields.length] = field;
    this.fields = fields;

    return(field);
  }

  /**
   * Removes the field whose name is at the given index in the
   * constant pool.  
   *
   * @throw IllegalArgumentException
   *        The class does not contain a field whose name is at the
   *        given index
   */
  public void deleteField(int nameIndex) {
    List newFields = new ArrayList();

    boolean foundIt = false;
    for (int i = 0; i < this.fields.length; i++) {
      Field field = this.fields[i];
      if (field.nameIndex() == nameIndex) {
        foundIt = true;

      } else {
        newFields.add(field);
      }
    }

    if (!foundIt) {
      String s = "No field with name index " + nameIndex + " in " +
        this.name();
      throw new IllegalArgumentException(s);

    } else {
      this.fields = (Field[]) newFields.toArray(new Field[0]);
    }
  }

    /**
   * Deletes a method from this class
   *
   * @param nameIndex
   *        Index in the constant pool of the name of the method to be
   *        deleted
   * @param typeIndex
   *        Index in the constant pool of the type of the method to be
   *        deleted
   *
   * @throw IllegalArgumentException
   *        The class modeled by this <code>ClassInfo</code> does not
   *        contain a method whose name and type are not at the given
   *        indices 
   */
  public void deleteMethod(int nameIndex, int typeIndex) {
    List newMethods = new ArrayList();

    boolean foundIt = false;
    for (int i = 0; i < this.methods.length; i++) {
      Method method = this.methods[i];
      if ((method.nameIndex() == nameIndex) &&
          (method.typeIndex() == typeIndex)) {
        foundIt = true;

      } else {
        newMethods.add(method);
      }
    }

    if (!foundIt) {
      String s = "No method with name index " + nameIndex + 
        " and type index " + typeIndex + " in " + this.name();
      throw new IllegalArgumentException(s);

    } else {
      this.methods = (Method[]) newMethods.toArray(new Method[0]);
    }
  }


  /**
   * Creates a new method in this class
   */
  public MethodInfo addNewMethod(int modifiers, int typeIndex, 
                                 int nameIndex, int exceptionIndex,
                                 int[] exceptionTypeIndices, 
                                 int codeIndex) {
    Exceptions exceptions = 
      new Exceptions(this, exceptionIndex, exceptionTypeIndices);
    Code code = new Code(this, codeIndex);  // code can't be null
    Attribute[] attributes = new Attribute[] { exceptions, code };

    Method method = new Method(this, modifiers, nameIndex, typeIndex,
                               attributes, code, exceptions);

    // Add the new method to the list of method
    Method[] methods = new Method[this.methods.length + 1];
    for(int i = 0; i < this.methods.length; i++) {
      methods[i] = this.methods[i];
    }
    methods[this.methods.length] = method;
    this.methods = methods;


    return(method);
  }
  
  /**
   * Prints a textual representation of this classfile to a PrintStream.
   * The text includes information such as the constants in the constant
   * pool, the name of the class's superclass, its modifier flags, its
   * fields, and its methods.
   *
   * @param out
   *        The stream to which to print.
   */
  public void print(PrintStream out) {
    print(new PrintWriter(out, true));
  }

  public void print(PrintWriter out) {
    out.print("(constants");
    for (int i = 0; i < constants.size(); i++) {
      out.print("\n    " + i + ": " + constants.get(i));
    }
    out.println(")");
    
    out.println("(class " + classIndex() + ")");
    out.println("(super " + superclassIndex() + ")");
    
    out.print("(interfaces");
    for (int i = 0; i < interfaces.length; i++) {
      out.print("\n    " + i + ": " + interfaces[i]);
    }
    out.println(")");
    
    out.print("(modifiers");
    if ((modifiers & Modifiers.PUBLIC) != 0)
      out.print(" PUBLIC");
    if ((modifiers & Modifiers.FINAL) != 0)
      out.print(" FINAL");
    if ((modifiers & Modifiers.SUPER) != 0)
      out.print(" SUPER");
    if ((modifiers & Modifiers.INTERFACE) != 0)
      out.print(" INTERFACE");
    if ((modifiers & Modifiers.ABSTRACT) != 0)
      out.print(" ABSTRACT");
    out.println(")");
    
    out.print("(fields");
    for (int i = 0; i < fields.length; i++) {
      out.print("\n    " + i + ": " + fields[i]);
    }
    out.println(")");
    
    out.print("(methods");
    for (int i = 0; i < methods.length; i++) {
      out.print("\n    " + i + ": " + methods[i]);
    }
    out.println(")");
  }
  
  public String toString()
  {
    return "(ClassFile " + name() + ")";
  }
}
