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

import net.sf.jazzlib.ZipEntry;
import net.sf.jazzlib.ZipException;
import net.sf.jazzlib.ZipFile;

/**
 * ClassFileLoder provides an interface for loading classes from files.
 * The actual loading is done by the ClassFile itself.
 * <p>
 * Classes may be specified by their full package name
 * (<tt>java.lang.String</tt>), or by the name of their class file
 * (<tt>myclasses/Test.class</tt>).  The class path may contain
 * directories or Zip or Jar files.  Any classes that are written back to
 * disk ("committed") are placed in the output directory.
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class ClassFileLoader implements ClassInfoLoader {
  public static boolean DEBUG = false;
  public static boolean USE_SYSTEM_CLASSES = true;
  
  private File outputDir;      // Directory in which to write committed class files
  private String classpath;    // Path to search for classes
  private Map openZipFiles;    // zip files to search for class files
  private LinkedList cache;    // We keep a cache of CACHE_LIMIT class files
  private boolean verbose;
  private static final int CACHE_LIMIT = 10;
  
  /** 
   * Constructor.  The classpath initially consists of the contents
   * of the <tt>java.class.path</tt> and <tt>sun.boot.class.path</tt>
   * system properties.  
   */
  public ClassFileLoader() {
    outputDir = new File(".");
//    classpath = System.getProperty("java.class.path");
//    classpath += File.pathSeparator + 
    //classpath = System.getProperty("sun.boot.class.path");
//    if(USE_SYSTEM_CLASSES) {
//      classpath += File.pathSeparator + 
//	System.getProperty("java.sys.class.path");
//    }
    //added by zbchen for analysis
    classpath="./build/example-bloat:./src/example-bloat/rt.jar:.";
    openZipFiles = new HashMap();
    cache = new LinkedList();
    verbose = false;
  }
  
  public void setVerbose(boolean verbose)
  {
    this.verbose = verbose; 
  }
  
  /**
   * Sets the classpath.
   */
  public void setClassPath(String classpath)
  {
    this.classpath = classpath;
  }

  /**
   * Adds to the classpath (CLASSPATH = CLASSPATH + morePath).
   */
  public void appendClassPath(String morePath) {
    this.classpath += File.pathSeparator + morePath;
  }

  /**
   * Adds to the classpath (CLASSPATH = morePath + CLASSPATH).
   */
  public void prependClassPath(String morePath) {
    this.classpath = morePath + File.pathSeparator + this.classpath;
  }
  
  /**
   * Returns the path used to search for class files.
   */
  public String getClassPath() {
    return(this.classpath);
  }
  
  /**
   * Load the class from a stream.
   *
   * @param inputFile
   *        The file from which to load the class.
   * @param stream
   *        The stream from which to load the class.
   * @return
   *        A ClassInfo for the class.
   * @exception ClassNotFoundException
   *        The class cannot be found in the class path.
   */
  private ClassInfo loadClassFromStream(File inputFile, InputStream stream)
       throws ClassNotFoundException {
    
    DataInputStream in = new DataInputStream(stream);
    ClassFile file = new ClassFile(inputFile, this, in);
    
    return file;
  }
  
  /**
   * Load the class from the file.
   *
   * @param file
   *        The File from which to load a class.
   * @return
   *        A ClassInfo for the class.
   * @exception ClassNotFoundException
   *        The class cannot be found in the class path.
   */
  private ClassInfo loadClassFromFile(File file)
       throws ClassNotFoundException
  {
    try {
      InputStream in = new FileInputStream(file);
      
      ClassInfo info = loadClassFromStream(file, in);
      
      if (verbose) {
	System.out.println("[Loaded " + info.name() +
			   " from " + file.getPath() + "]");
      }
      
      try {
	in.close();
      }
      catch (IOException ex) {
      }
      
      return info;
    } catch (FileNotFoundException e) {
      throw new ClassNotFoundException(file.getPath());
    }
  }
  
  /** 
   * Loads all of the classes that are contained in a zip (or jar)
   * file.  Returns an array of the <tt>ClassInfo</tt>s for the
   * classes in the zip file.
   */
  public ClassInfo[] loadClassesFromZipFile(ZipFile zipFile) 
    throws ClassNotFoundException {
    ClassInfo[] infos = new ClassInfo[zipFile.size()];

    // Examine each entry in the zip file
    Enumeration entries = zipFile.entries();
    for(int i = 0; entries.hasMoreElements(); i++) {
      ZipEntry entry = (ZipEntry) entries.nextElement();
      if(entry.isDirectory() || !entry.getName().endsWith(".class"))
	continue;

      try {
	InputStream stream = zipFile.getInputStream(entry);
	File file = new File(entry.getName());

	infos[i] = loadClassFromStream(file, stream);

      } catch (IOException ex) {
	System.err.println("IOException: " + ex);
      }
    }

    return(infos);
  }

  public ClassInfo newClass(int modifiers, int classIndex, 
                            int superClassIndex, 
                            int[] interfaceIndexes, List constants) {
    return new ClassFile(modifiers, classIndex, superClassIndex,
                         interfaceIndexes, constants, this);
  }

  /**
   * Loads the class with the given name.
   * Searches the class path, including zip files, for the class and
   * then returns a data stream for the class file.
   *
   * @param name
   *        The name of the class to load, including the package name.
   * @return
   *        A ClassInfo for the class.
   * @exception ClassNotFoundException
   *        The class cannot be found in the class path.
   */
  public ClassInfo loadClass(String name)
       throws ClassNotFoundException {
	System.err.println(name);
	
    ClassInfo file = null;

    // Check to see if name ends with ".class".  If so, load the class from
    // that file.   Note that this is okay because we can never have a class
    // named "class" (i.e. a class named "class" with a lower-case 'c' can
    // never be specified in a fully-specified java class name) because
    // "class" is a reserved word.
    
    if (name.endsWith(".class")) {
      File nameFile = new File(name);
      
      if(!nameFile.exists()) {
	throw new ClassNotFoundException(name);

      } else
	return(loadClassFromFile(nameFile));
    }

    // Otherwise, we have a (possibly fully-specified) class name.
    name = name.replace('.', '/');
    
    // Check the cache for the class file.
    if (DEBUG) {
      System.out.println("  Looking for " + name + " in cache = " + cache); 
    }

    Iterator iter = cache.iterator();
    
    while (iter.hasNext()) {
      file = (ClassFile) iter.next();

      if (name.equals(file.name())) {
	if (DEBUG) {
	  System.out.println("  Found " + file.name() + " in cache");
	}
	
	// Move to the front of the cache.
	iter.remove();
	cache.addFirst(file);
	
	return file;
      }
    }

    file = null;

    String classFile = name.replace('/', File.separatorChar) + ".class";
    
    // For each entry in the class path, search zip files and directories
    // for classFile.  When found, open an InputStream and break
    // out of the loop to read the class file.
    String path = classpath + File.pathSeparator;

    if(DEBUG)
      System.out.println("CLASSPATH = " + path);
    
    int index = 0;
    int end = path.indexOf(File.pathSeparator, index);
    
  SEARCH:
    while (end >= 0) {
      String dir = path.substring(index, end);
      
      System.out.println(dir);
      
      File f = new File(dir);
      
      if (f.isDirectory()) {
	// The directory is really a directory.  If the class file
	// exists, open a stream and return.
	f = new File(dir, classFile);
	
	if (f.exists()) {
	  try {
	    InputStream in = new FileInputStream(f);
	    
	    if (verbose) {
	      System.out.println("  [Loaded " + name +
				 " from " + f.getPath() + "]");
	    }
	    
	    file = loadClassFromStream(f, in);
	    
	    try {
	      in.close();

	    } catch (IOException ex) {
	    }
	    
	    break SEARCH;

	  } catch (FileNotFoundException ex) {
	  }
	}

      } else if (dir.endsWith(".zip") || dir.endsWith(".jar")) {
	// Maybe a zip file?
	try {
	  ZipFile zip = (ZipFile) openZipFiles.get(dir);	
	  
	  if (zip == null) {
	    zip = new ZipFile(f);
	    openZipFiles.put(dir, zip);
	  }
	  
	  String zipEntry = classFile.replace(File.separatorChar, '/');
	  
	  ZipEntry entry = zip.getEntry(zipEntry);
	  
	  if (entry != null) {
	    // Found the class file in the zip file.
	    // Open a stream and return.
	    if (verbose) {
	      System.out.println("  [Loaded " + name +
				 " from " + f.getPath() + "]");
	    }
	    
	    InputStream in = zip.getInputStream(entry);
	    file = loadClassFromStream(f, in);
	    
	    try {
	      in.close();

	    } catch (IOException ex) {
	    }
	    break SEARCH;
	  }
	}
	catch (ZipException ex) {
	}
	catch (IOException ex) {
	}
      }
      
      index = end + 1;
      end = path.indexOf(File.pathSeparator, index);
    }

    if (file == null) {
      // The class file wasn't in the class path.  Try the currnet
      // directory.  If not there, give up.
      File f = new File(classFile);

      if (! f.exists()) {
	throw new ClassNotFoundException(name);
      }
      
      if (verbose) {
	System.out.println("  [Loaded " + name +
			   " from " + f.getPath() + "]");
      }
      
      try {
	InputStream in = new FileInputStream(f);
	file = loadClassFromStream(f, in);
	
	try {
	  in.close();
	}
	catch (IOException ex) {
	}
      }
      catch (FileNotFoundException ex) {
	throw new ClassNotFoundException(name);
      }
    }

    if (file == null) {
      throw new ClassNotFoundException(name);
    }
    
    // If we've reached the cache size limit, remove the oldest file
    // in the cache.  Then add the new file.
    if (cache.size() == CACHE_LIMIT) {
      cache.removeLast();
    }
    
    cache.addFirst(file);
    
    return file;
  }
  
  /**
   * Set the directory into which commited class files should be written.
   *
   * @param dir
   *        The directory.
   */
  public void setOutputDir(File dir) {
    outputDir = dir;
  }
  
  /**
   * Get the directory into which commited class files should be
   * written.
   */
  public File outputDir()
  {
    return outputDir;
  }

  /**
   * Writes a bunch of <code>byte</code>s to an output entry with the
   * given name.
   */
  public void writeEntry(byte[] bytes, String name) 
    throws IOException {
    OutputStream os = outputStreamFor(name);
    os.write(bytes);
    os.flush();
    os.close();
  }

  /**
   * Returns an <tt>OutputStream</tt> to which a class file should be
   * written.
   */
  public OutputStream outputStreamFor(ClassInfo info) throws IOException {
    // Format the name of the output file
    String name = info.name().replace('/', File.separatorChar) +
      ".class";
    return outputStreamFor(name);
  }

  /**
   * Returns an <code>OutputStream</code> to which somed named entity
   * is written.  Any forward slashes in the name are replaced by
   * <code>File.separatorChar</code>. 
   */
  protected OutputStream outputStreamFor(String name) 
    throws IOException {

    name = name.replace('/', File.separatorChar);
      
    File f = new File(outputDir, name);
      
    if (f.exists()) {
      f.delete();
    }
      
    File dir = new File(f.getParent());
    dir.mkdirs();
      
    if (! dir.exists()) {
      throw new RuntimeException("Couldn't create directory: " + dir);
    }

    return(new FileOutputStream(f));
  }

  /**
   * Signifies that we are done with this <code>ClassFileLoader</code>
   */
  public void done() throws IOException {
    // Nothing for this guy
  }
}
