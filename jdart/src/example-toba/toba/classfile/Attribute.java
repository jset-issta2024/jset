//  Attribute.java -- Attribute handling

package toba.classfile;

import java.io.*;
import java.util.Stack;



public class
Attribute {		// field, method, or class attribute

    String name;
    byte[] data;

public String
toString ()
{
    return name + "[" + data.length + "bytes]";
}

//  new Attribute(stream, ctab) -- load attribute from class file

Attribute(ClassFile d, Constant c[]) throws IOException
{
	int j = d.readUnsignedShort();
    if (j < c.length && c[j] != null && c[j].value instanceof String)				//bug fixed by zhenbang
    	name = (String)c[j].value;
    else
    	name = null;
    //name = (String)c[d.readUnsignedShort()].value;
    int len = d.readInt();
//    System.out.println("---len : " + len);
//    new Exception().printStackTrace();
    if (len < 100 && len >= 0) //if (len < 10000000 && len >= 0)
    	d.readFully(data = new byte[len]); // this cannot generate any new symbolic value!!!
//    Stack s = new Stack();
//    s.pop();
}



//  find(a, s) -- find an attribute in an array

public static byte[] find(Attribute a[], String s)
{
    for (int i = 0; i < a.length; i++)
       if (a[i].name.equals(s))
	    return a[i].data;
    return null;
}



} // class Attribute
