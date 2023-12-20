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

import EDU.purdue.cs.bloat.reflect.*;
import EDU.purdue.cs.bloat.util.*;
import java.util.*;

/**
 * A ClassEditor provides finer-grain access to a class than a CLassInfo
 * object does.  A ClassEditor takes a ClassInfo and extracts the 
 * class's constant pool, type, super class type, and the types of its
 * interfaces.  When editing is finished, changes are committed with the
 * commit method.
 *
 * @see ClassInfo
 * @see MethodEditor
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class ClassEditor {
  public static boolean DEBUG = Boolean.getBoolean("ClassEditor.DEBUG");

  private ConstantPool constants;  // A copy of the constant pool of the class 
                                   // being edited.
  private ClassInfo classInfo;     // (A representation of) the class being edited
  private Type type;               // An index into constant pool (descriptors) that
  private Type superclass;         // specifies the class, superclass, and interfaces  
  private Type[] interfaces;       // of the class being edited.
  private EditorContext context;   // Use to edit classes and methods
  private boolean dirty;           // Has the class been modified?
  
  /**
   * Constructor. Create a new ClassEditor based on information in a
   * ClassInfo object.  It extracts the class's constant pool, and the
   * types of the class, its superclass, and any interfaces it 
   * implements.
   *
   * @param context
   *        The <tt>EditorContext</tt> used to edit fields and methods.
   * @param classInfo
   *        The ClassInfo structure of the class to edit.
   *
   * @see EDU.purdue.cs.bloat.reflect.ClassInfo
   * @see ConstantPool
   * @see Type
   */
  public ClassEditor(EditorContext context, ClassInfo classInfo) {
    this.context = context;
    this.classInfo = classInfo;
    this.dirty = false;
    
    // Extract the constant pool from the ClassInfo
    constants = new ConstantPool(classInfo.constants());
    
    int index;

    // Load information (such as the indices of the class, superclass,
    // and the interfaces) about the class being edited from its
    // constant pool.
    
    index = classInfo.classIndex();
    type = (Type) constants.constantAt(index);
    
    index = classInfo.superclassIndex();
    superclass = (Type) constants.constantAt(index);
    
    int ifs[] = classInfo.interfaceIndices();
    interfaces = new Type[ifs.length];
    
    for (int i = 0; i < ifs.length; i++) {
      interfaces[i] = (Type) constants.constantAt(ifs[i]);
    }
    
    if (ClassEditor.DEBUG) {
      System.out.println("Editing class " + type);
    }

    this.setDirty(false);
  }

  /**
   * Creates a new <code>ClassEditor</code> for editing a class (or
   * interface) from scratch.  This constructor should not be invoked
   * direcly.  Use {@link EditorContext.newClass(int, String, Type,
   * Type[])} instead.
   */
  public ClassEditor(EditorContext context, int modifiers, 
                     String className, Type superType, 
                     Type[] interfaces) {

    if (className == null) {
      String s = "Cannot have a null class name";
      throw new IllegalArgumentException(s);
    }

    if (superType == null) {
      superType = Type.OBJECT;
    }

    if (interfaces == null) {
      interfaces = new Type[0];
    }

    if (ClassEditor.DEBUG) {
      System.out.println("Creating new class " + className + 
                         " extends " + superType.className());
    }

    this.context = context;
    this.superclass = superType;
    this.interfaces = interfaces;

    ConstantPool cp = new ConstantPool();
    this.constants = cp;

    this.type = Type.getType(Type.classDescriptor(className));
    int classNameIndex = cp.getClassIndex(this.type);
    int superTypeIndex = cp.getClassIndex(superType);
    int[] interfaceIndexes = new int[interfaces.length];
    for (int i = 0; i < interfaces.length; i++) {
      interfaceIndexes[i] = cp.getClassIndex(interfaces[i]);
    }

    this.classInfo = 
      context.newClassInfo(modifiers, classNameIndex, superTypeIndex,
                           interfaceIndexes, cp.getConstantsList());

    this.dirty = true;
  }

  /**
   * Returns <tt>true</tt> if the class has been modified.
   */
  public boolean isDirty() {
    return(this.dirty);
  }

  /**
   * Sets this class's dirty flag.  The dirty flag is <tt>true</tt>
   * if the class has been modified.
   */
  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }

  /**
   * Returns the name of the class represented by this 
   * <tt>ClassEditor</tt>.
   */
  public String name() {
    return(this.classInfo().name());
  }

  /**
   * Obtain the <tt>EditorContext</tt> for this ClassEditor.
   */  
  public EditorContext context() {
    return context;
  }
  
  /**
   * Get the ClassInfo object representing the class that being edited.
   */
  public ClassInfo classInfo() {
    return classInfo;
  }
  
  public boolean isPublic() {
    return (classInfo.modifiers() & Modifiers.PUBLIC) != 0;
  }
  
  public boolean isPrivate() {
    return (classInfo.modifiers() & Modifiers.PRIVATE) != 0;
  }
  
  public boolean isProtected() {
    return (classInfo.modifiers() & Modifiers.PROTECTED) != 0;
  }
  
  public boolean isStatic() {
    return (classInfo.modifiers() & Modifiers.STATIC) != 0;
  }
  
  public boolean isFinal() {
    return (classInfo.modifiers() & Modifiers.FINAL) != 0;
  }
  
  public boolean isSuper() {
    return (classInfo.modifiers() & Modifiers.SUPER) != 0;
  }
  
  public boolean isAbstract() {
    return (classInfo.modifiers() & Modifiers.ABSTRACT) != 0;
  }
  
  public boolean isInterface() {
    return (classInfo.modifiers() & Modifiers.INTERFACE) != 0;
  }
  
  public void setPublic(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.PUBLIC;
    }
    else {
      modifiers &= ~Modifiers.PUBLIC;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setPrivate(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.PRIVATE;
    }
    else {
      modifiers &= ~Modifiers.PRIVATE;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setProtected(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.PROTECTED;
    }
    else {
      modifiers &= ~Modifiers.PROTECTED;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setStatic(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.STATIC;
    }
    else {
      modifiers &= ~Modifiers.STATIC;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setFinal(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.FINAL;
    }
    else {
      modifiers &= ~Modifiers.FINAL;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setSuper(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.SUPER;
    }
    else {
      modifiers &= ~Modifiers.SUPER;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setAbstract(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.ABSTRACT;
    }
    else {
      modifiers &= ~Modifiers.ABSTRACT;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  public void setInterface(boolean flag) {
    int modifiers = classInfo.modifiers();
    
    if (flag) {
      modifiers |= Modifiers.INTERFACE;
    }
    else {
      modifiers &= ~Modifiers.INTERFACE;
    }
    
    classInfo.setModifiers(modifiers);
    this.setDirty(true);
  }
  
  /**
   * Sets the Type (descriptor) object for the class.
   *
   * @param type
   *        A Type.
   */
  public void setType(Type type) {
    this.type = type;
    Assert.isTrue(type.isObject(), "Cannot set class type to " + type);
    this.setDirty(true);
  }
  
  /**
   * Returns the Type (descriptor) for the class.
   */
  public Type type() {
    return type;
  }
  
  /**
   * Returns a Type object for the class's superclass.
   */
  public Type superclass() {
    return superclass;
  }
  
  /**
   * Adds an interface of the given class to the set of interfaces
   * that the class implements.
   *
   * @throw IllegalArgumentException
   *        <code>interfaceClass</code> is not an interface
   */
  public void addInterface(Class interfaceClass) {
    if(!interfaceClass.isInterface()) {
      String s = "Cannot add non-interface type: " +
        interfaceClass.getName(); 
      throw new IllegalArgumentException(s);
    }

    addInterface(Type.getType(interfaceClass));
  }

  /**
   * Adds an interface of a given Type to the set of interfaces that
   * the class implements.
   */
  public void addInterface(Type interfaceType) {
//     // The interface must have an index in the constant pool
//     this.constants().getClassIndex(interfaceType);

    Type[] interfaces = new Type[this.interfaces.length + 1];
    for(int i = 0; i < this.interfaces.length; i++) {
      interfaces[i] = this.interfaces[i];
    }
    interfaces[interfaces.length - 1] = interfaceType;
    this.setInterfaces(interfaces);
  }

  /**
   * Returns the interfaces the class implements.
   *
   * @param interfaces
   *        An array of Types.
   */
  public void setInterfaces(Type[] interfaces) {
    this.interfaces = interfaces;
    this.setDirty(true);
  }
  
  /**
   * Returns the interfaces the class implements.
   */
  public Type[] interfaces() {
    return interfaces;
  }
  
  /**
   * Returns the modifiers of the class.  The values correspond
   * to the constants in the <tt>Modifiers</tt> class.
   *
   * @return
   *        A bit vector of modifier flags for the class.
   * @see Modifiers
   */
  public int modifiers() {
    return classInfo.modifiers();
  }
  
  /**
   * Returns an array of <tt>FieldInfo</tt> structures for each
   * field in the class.
   */
  public FieldInfo[] fields() {
    return classInfo.fields();
  }
  
  /**
   * Returns an array of MethodInfo structures for each method in the
   * class.
   */
  public MethodInfo[] methods() {
    return classInfo.methods();
  }

  /**
   * Returns the constant pool for the class.
   */
  public ConstantPool constants() {
    return constants;
  }
  
  /**
   * Commit any changes to the class since creation time.  Note that
   * committal will occur regardless of whether or not the class is
   * dirty.
   */
  public void commit() {
    commitOnly(null, null);
  }

  /**
   * Commits only certain methods and fields.  Note that committal 
   * will occur regardless of whether or not the class is dirty.
   * 
   * @param methods
   *        Methods (<tt>MethodInfo</tt>s) to commit.  If
   *        <tt>null</tt>, all methods are committed.
   * @param fields
   *        Fields (<tt>FieldInfo</tt>s) to commit.  If <tt>null</tt>,
   *        all fields are committed.
   */
  public void commitOnly(Set methods, Set fields) {
    classInfo.setClassIndex(constants.addConstant(Constant.CLASS, type));
    classInfo.setSuperclassIndex(constants.addConstant(Constant.CLASS, 
						       superclass));
    
    int ifs[] = new int[interfaces.length];
    
    for (int i = 0; i < ifs.length; i++) {
      ifs[i] = constants.addConstant(Constant.CLASS, interfaces[i]);
    }
    
    classInfo.setInterfaceIndices(ifs);
    
    classInfo.setConstants(constants.constants());
    
    classInfo.commitOnly(methods, fields);

    // This class is no longer dirty
    this.setDirty(false);
  }

  /** 
   * This class is visited by an <tt>EditorVisitor</tt>.  First, this
   * <tt>ClassEditor</tt> itself is visited.  Then, all of this
   * class's fields (<tt>FieldEditor</tt>s) are visited.  Finally,
   * each of this class's methods (<tt>MethodEditor</tt>s) are
   * visited.  Constructors are visited before regular methods.
   */
  public void visit(EditorVisitor visitor) {
    // First visit ourself
    visitor.visitClassEditor(this);

    EditorContext context = this.context();

    // Visit each field
    FieldInfo[] fields = this.fields();
    for(int i = 0; i < fields.length; i++) {
      FieldEditor fieldEditor = context.editField(fields[i]);
      visitor.visitFieldEditor(fieldEditor);
      context.release(fields[i]);
    }

    // Visit each method
    ArrayList regularMethods = new ArrayList();
    MethodInfo[] methods = this.methods();
    for(int i = 0; i < methods.length; i++){
      MethodEditor methodEditor = context.editMethod(methods[i]);

      if(methodEditor.name().charAt(0) != '<') {
	regularMethods.add(methods[i]);

      } else {
	visitor.visitMethodEditor(methodEditor);
      }

      context.release(methods[i]);
    }

    Iterator iter = regularMethods.iterator();
    while(iter.hasNext()) {
      MethodInfo info = (MethodInfo) iter.next();
      MethodEditor me = context.editMethod(info);
      visitor.visitMethodEditor(me);
      context.release(info);
    }
  }

  /**
   * Two <tt>ClassEditor</tt>s are equal if they edit the same class.
   */
  public boolean equals(Object o) {
    if(o instanceof ClassEditor) {
      ClassEditor other = (ClassEditor) o;
      if(!other.type().equals(this.type()))
	 return(false);

      return(true);
    }

    return(false);
  }

  /**
   * A <tt>ClassEditor</tt>'s hash code is based upon the hash code of
   * the name of the class it edits.
   */
  public int hashCode() {
    return(this.name().hashCode());
  }

  public String toString() {
    return(this.type().toString());
  }

}
