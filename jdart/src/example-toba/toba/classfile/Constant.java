//  Constant.java -- values from a Java classfile "constant" table

package toba.classfile;

import java.io.*;
import java.util.*;



public class Constant {		// constant table entry

    public int index;		// entry index
    public int tag;		// constant type
    public Object value;	// resolved constant value



// tag types

public static final int CLASS		=  7;
public static final int FIELD		=  9;
public static final int METHOD		= 10;
public static final int INTERFACE	= 11;
public static final int STRING		=  8;
public static final int INTEGER		=  3;
public static final int FLOAT		=  4;
public static final int LONG		=  5;
public static final int DOUBLE		=  6;
public static final int NAMETYPE	= 12;
public static final int UTF8		=  1;
public static final int UNICODE		=  2;

private static final String TagToName [] = new String [13];
static {
  TagToName [CLASS] = "CLASS";
  TagToName [FIELD] = "FIELD";
  TagToName [METHOD] = "METHOD";
  TagToName [INTERFACE] = "INTERFACE";
  TagToName [STRING] = "STRING";
  TagToName [INTEGER] = "INTEGER";
  TagToName [FLOAT] = "FLOAT";
  TagToName [LONG] = "LONG";
  TagToName [DOUBLE] = "DOUBLE";
  TagToName [NAMETYPE] = "NAMETYPE";
  TagToName [UTF8] = "UTF8";
  TagToName [UNICODE] = "UNICODE";
}

/** This is like java.io.DataInputStream.readUTF, except it doesn't fall
  * through to the error case on three-byte UTF codes.  That bug was
  * fixed in jdk1.1.1. 
  * @return the String.
  */
private final static String readUTF(ClassFile in) throws IOException {
        int utflen = in.readUnsignedShort(); // can generate new symbolic!
        char str[] = new char[utflen];
	int count = 0;
	int strlen = 0;
	while (count < utflen) {
	    int c = in.readUnsignedByte();
	    int char2, char3;
	    switch (c >> 4) { 
	        case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
		    // 0xxxxxxx
		    count++;
		    str[strlen++] = (char)c;
		    break;
	        case 12: case 13:
		    // 110x xxxx   10xx xxxx
		    count += 2;
		    if (count > utflen) 
			throw new UTFDataFormatException();		  
		    char2 = in.readUnsignedByte();
		    if ((char2 & 0xC0) != 0x80)
			throw new UTFDataFormatException();		  
		    str[strlen++] = (char)(((c & 0x1F) << 6) | (char2 & 0x3F));
		    break;
	        case 14:
//	        in.close(); // bug injected maybe
	     // 1110 xxxx  10xx xxxx  10xx xxxx
		    count += 3;
		    if (count > utflen) 
			throw new UTFDataFormatException();		  
		    char2 = in.readUnsignedByte();
		    char3 = in.readUnsignedByte();
		    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
			throw new UTFDataFormatException();		  
		    str[strlen++] = (char)(((c & 0x0F) << 12) |
					   ((char2 & 0x3F) << 6) |
					   ((char3 & 0x3F) << 0));
                    break; // DON'T FALL THROUGH
	        default:
		    // 10xx xxxx,  1111 xxxx
		    throw new UTFDataFormatException();		  
		}
	}
        return new String(str, 0, strlen);
    }

//  load(d) -- read and crack constant table from stream d.

static Constant[] load (ClassFile d)
    throws ClassFormatError, IOException
{
    // allocate constant table; first entry is unused
	//int length=d.readUnsignedShort();
	//Constant[] table = new Constant[length];
  Constant[] table = new Constant[d.readUnsignedShort()];
//    table[0] = null; 

    // read constant table entries
  for (int i = 1; i < table.length; i++) {
   //for (int i = 1; i < length; i++) {
	Constant c = new Constant();
	table[i] = c;
	c.index = i;
	c.tag = d.readByte(); //c.tag can generate new symbolic value!
	switch (c.tag) {
	  case UTF8:	   
		c.value = readUTF(d).intern(); // intern strings as they come in
		break;
	    case UNICODE:
//		d.close(); // bug injected maybe	    	
		int len = d.readUnsignedShort();
		char s[] = new char[len];
		for (int j = 0; j < len; j++)
		    s[j] = (char)d.readUnsignedShort();
		c.value = new String(s).intern(); // intern strings as they come in
		break;
	    case INTEGER:
		c.value = new Integer(d.readInt());
		break;
	    case FLOAT:
		c.value = new Float(d.readFloat());
		break;
	    case LONG:
		c.value = new Long(d.readLong());
		if ((i+1) < table.length) table[++i] = null;	// following entry unused
		break;
	    case DOUBLE:
		c.value = new Double(d.readDouble());
		if ((i+1) < table.length) table[++i] = null;	// following entry unused
		break;
	    case CLASS:
	    case STRING:
		c.value = new Integer(d.readUnsignedShort());
		break;
	    case FIELD:
	    case METHOD:
	    case INTERFACE:
	    case NAMETYPE:
		Pair p = new Pair();
		p.i1 = d.readUnsignedShort();
		p.i2 = d.readUnsignedShort();
		c.value = p;
		break;
	    default:
		throw new ClassFormatError(
		    "unrecognized constant tag " + c.tag);
	}
    }

    // Now we need to patch up references between constant table
    // entries to a useable form.

    // ----first, replace strings with actual strings, not ints
   for (int i = 1; i < table.length; i++) {
	Constant c = table[i];
	if ((c != null) && (c.tag == STRING) && ((Integer)c.value).intValue() < table.length) { // bug fix by zhenbang
	    Constant c2 = table[((Integer)c.value).intValue()];
	    if (c2 == null) continue;
	    if ((c2.tag != UTF8) && (c2.tag != UNICODE))
		throw new 
		    ClassFormatError("Invalid string in constant pool (" 
				     + i + ")");
	    c.value = c2.value;
	}
    }

    // -----next, replace classes with ClassRefs
    for (int i = 1; i < table.length; i++) {
	Constant c = table[i];
	if ((c != null) && (c.tag == CLASS) && ((Integer)c.value).intValue() < table.length) { // bug fix by zhenbang
		if (table[((Integer)c.value).intValue()] == null) continue; // added by zhenbang to avoid bug
		if (table[((Integer)c.value).intValue()].value instanceof String) {
	    String s = (String)table[((Integer)c.value).intValue()].value;
	    c.value = ClassRef.byName (s.replace('/', '.'));
		}
	}
    }

    // -----then, fill in fields of reference entries
for (int i = 1; i < table.length; i++) {
	Constant c = table[i];
	Pair p, p2;
	if (c != null) switch (c.tag) {
	    case FIELD:
	    case METHOD:
	    case INTERFACE:
		p = (Pair)c.value;
		if (p.i2 >= table.length) break;
		if (p.i1 >= table.length) break;
		if (table[p.i2] == null || !(table[p.i2].value instanceof Pair)) break;
		if (table[p.i1] == null || !(table[p.i1].value instanceof ClassRef)) break;
		p2 = (Pair)table[p.i2].value;
		ClassRef cref = (ClassRef)table[p.i1].value;
		if (p2.i2 >= table.length) break;
		if (p2.i1 >= table.length) break;
		if (table[p2.i2] == null || !(table[p2.i2].value instanceof String)) break;
		if (table[p2.i1] == null || !(table[p2.i1].value instanceof String)) break;
		String name = (String)table[p2.i1].value;
		String sig = (String)table[p2.i2].value;
		if (c.tag == FIELD)
		    c.value = new VariableRef(cref, name, sig);
		else if (c.tag == METHOD)
		    c.value = new MethodRef(cref, name, sig);
		else if (c.tag == INTERFACE)
		    c.value = new InterfaceRef(cref, name, sig);

		break;
	}
    }

    return table;
}

public String
toString ()
{
    return TagToName [tag] + "/" + value;
}

//  dump(d, table) -- dump a constant table

public static void dump(PrintWriter d, Constant table[])
{
    d.println();
    d.println("/*  Constant table:");
    for (int i = 1; i < table.length; i++) {
	Constant c = table[i];

	if (c == null)				// if unused entry
	    continue;

	d.print("    c" + i + ".  (" + c.tag + ")  ");
	d.println(c.value);
    }
    d.println("*/");
    d.println();
}



} // class Constant
