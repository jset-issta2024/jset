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

import java.io.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

import EDU.purdue.cs.bloat.reflect.*;

/**
 * Does a lot of the same stuff as <tt>ClassFileLoader</tt>, but
 * classes are committed to a JAR file instead of regular files.
 */
public class JarFileCommitter extends ClassFileLoader {

  private FunkyJar funky;

  /**
   * Constructor.
   *
   * @param file
   *        <tt>File</tt> representing JAR file
   * @param compress
   *        If <tt>true</tt>, contents of JAR file is compressed
   * @param version
   *        Version for the JAR file's manifest
   * @param author
   *        Author string from JAR file's manifest
   */
  public JarFileCommitter(File file, boolean compress, String version,
			  String author) throws IOException {

    funky = new FunkyJar(file, compress, version, author);
  }
  
  protected OutputStream outputStreamFor(String name) 
    throws IOException {

    funky.newEntry(name);
    return funky;    
  }

  public OutputStream outputStreamFor(ClassInfo info) throws IOException {
    // This is funky.  Recall that a JarOutputStream is also an output
    // stream.  So, we just return it.  This is why we have to
    // override the write, etc. methods.
    
    // Make a new entry based on the class name
    String name = info.name() + ".class";
    return outputStreamFor(name);
  }
  
  /**
   * Signifies that we are finished with this
   * <tt>JarFileCommitter</tt>.
   */
  public void done() throws IOException {
    funky.done();
  }
}

/**
 * We subclass JarOutputStream so that we can return an OutputStream
 * to which a BLOATed class file will be written.  In order to
 * accomodate non-compression, we have to perform the checksum along
 * the way.  Bletch.
 */
class FunkyJar extends JarOutputStream {

  private static final String MANIFEST = JarFile.MANIFEST_NAME;
  private static final String MANIFEST_DIR = "META-INF/";
  private static final CRC32 crc32 = new CRC32();

  private boolean compress;
  private JarEntry currEntry;
  private Size size;
  
  class Size {
    long value = 0;
  }
 
  /**
   * Constructor.
   */
  public FunkyJar(File file, boolean compress, String version,
		  String author) throws IOException {
    super(new FileOutputStream(file));
  
    this.compress = compress;

    if(compress)
      this.setMethod(JarOutputStream.DEFLATED);
    else
      this.setMethod(JarOutputStream.STORED);

    Manifest manifest = new Manifest();
    Attributes global = manifest.getMainAttributes();
    if (global.getValue(Attributes.Name.MANIFEST_VERSION) == null) {
      global.put(Attributes.Name.MANIFEST_VERSION, version);
    }

    if (global.getValue(new Attributes.Name("Created-By")) == null) {
      global.put(new Attributes.Name("Created-By"), author);
    }

    // Add directory for manifest
    JarEntry entry = new JarEntry(MANIFEST_DIR);
    entry.setTime(System.currentTimeMillis());
    entry.setSize(0);   // Directories have size 0
    entry.setCrc(0);    // Checksum is 0
    this.putNextEntry(entry);

    // Add manifest
    entry = new JarEntry(MANIFEST);
    entry.setTime(System.currentTimeMillis());
    if(!compress) {
      // Have to compute checksum ourselves.  Use an ugly anonymous
      // inner class.  Influenced by CRC32OutputStream in
      // sun.tools.jar.Main.  Please don't sue me.  I have no money.
      // Maybe you could give me a job instead.  Of course, then I'd
      // have money and you would sue me.  Hmm.
      final Size size = new Size();
      crc32.reset();
      manifest.write(new OutputStream() {
	  public void write(int r) throws IOException {
	    crc32.update(r);
	    size.value++;
	  }

	  public void write(byte[] b) throws IOException {
	    crc32.update(b, 0, b.length);
	    size.value += b.length;
	  }
	  
	  public void write(byte[] b, int off, int len) throws IOException {
	    crc32.update(b, off, len);
	    size.value += len - off;
	  }
	});
      entry.setSize(size.value);
      entry.setCrc(crc32.getValue());
    }
    this.putNextEntry(entry);
    manifest.write(this);     // Write the manifest to JAR file
    this.closeEntry();
  }

  public void newEntry(String name) throws IOException {
    makeDirs(name);

    currEntry = new JarEntry(name);
    currEntry.setTime(System.currentTimeMillis());
    if (compress) {
      currEntry.setMethod(JarEntry.DEFLATED);
    } else {
      currEntry.setMethod(JarEntry.STORED);
    }
    this.putNextEntry(currEntry);
    this.crc32.reset();
    this.size = new Size();
  }

  private Set dirs;

  /**
   * look at the path name specified by key and create zip entries for
   * each directory level not already added.
   */
  private void makeDirs( String key ) 
    throws IOException {
    if ( dirs == null ) dirs = new HashSet();
    int idx = 0;
    int last = 0;
    while( (last = key.indexOf( '/', idx + 1 ) ) != -1 ) {
      String aDir = key.substring( 0, last + 1 );
      if ( !dirs.contains( aDir ) ) {
	dirs.add( aDir );
	this.putNextEntry( new ZipEntry( aDir ) );
	this.closeEntry( );
      }
      idx = last;
    }
  }

  public void write(int r) throws IOException {
    super.write(r);

    if(!compress && size != null) {
      crc32.update(r);
      size.value++;
    }
  }
  
  public void write(byte[] b) throws IOException {
    super.write(b);

    if(!compress && size != null) {
      crc32.update(b, 0, b.length);
      size.value += b.length;
    }
  }

  public void write(byte[] b, int off, int len) throws IOException {
    super.write(b, off, len);

    if(!compress && size != null) {
      crc32.update(b, off, len);
      size.value += len - off;
    }
  }

  public void close() throws IOException {
    // Okay, everythings is done.  Set some values for the entry,
    // cross your fingers, and run away.
    if(!compress && size != null) {
      currEntry.setSize(size.value);
      currEntry.setCrc(crc32.getValue());
    }

    currEntry = null;
    size = null;
    this.closeEntry();

    // Note that we don't invoke the super class method.
  }

  /**
   * Signifies that we are finished with this
   * <tt>JarFileCommitter</tt>.
   */
  public void done() throws IOException {
    super.close();
  }
}
