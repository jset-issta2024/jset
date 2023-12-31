/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.scanner;

//import gov.nasa.jpf.jdart.Debug;

/**
 * Basic iterator for strings. Does nothing but pass allong the iterator
 * interface to the encapsuled String.
 * @author	Martijn W. van der Lee
 */
public class PStringScanner implements pobs.PScanner {
    private java.lang.String string;

    /**
     * Sole constructor.
     * @param	string	the source string for this iterator
     */
    public PStringScanner(java.lang.String string) {
        super();

        this.string = string;
    }

//    public char charAt(long index) throws IndexOutOfBoundsException {
//    	if (index > Integer.MAX_VALUE)
//            throw new StringIndexOutOfBoundsException();
//    	return this.string.charAt((int) index);
//    }
//    
    public char charAt(long index) throws IndexOutOfBoundsException {
        if (index > Integer.MAX_VALUE)
            throw new StringIndexOutOfBoundsException();
        char c = this.string.charAt((int) index);
//        char r = Debug.makeConcolicChar("sym_char_" + index, ""+(int)c);
       // System.out.println("--------" + index +"--"+ r + ":"+(int)r);
//        return r;
        return c;
    }

    public long length() {
        return this.string.length();
    }


    public java.lang.String substring(long beginIndex, long endIndex) {
        if (beginIndex > java.lang.Integer.MAX_VALUE
            || endIndex > java.lang.Integer.MAX_VALUE) {
            throw new StringIndexOutOfBoundsException();
        }

        return this.string.substring((int) beginIndex, (int) endIndex);
    }
}
