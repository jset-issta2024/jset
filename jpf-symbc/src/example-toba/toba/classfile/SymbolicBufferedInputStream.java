package toba.classfile;

//import gov.nasa.jpf.jdart.Debug;

import gov.nasa.jpf.symbc.Debug;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SymbolicBufferedInputStream extends BufferedInputStream {

	public SymbolicBufferedInputStream(InputStream in) {
		super(in);
	}

	public SymbolicBufferedInputStream(InputStream in, int classbuffersize) {
		super(in, classbuffersize);
	}
	
	public int read() throws IOException {
		int i = super.read();
		if (i != -1) {
			//int c = Debug.makeConcolicInteger("symb_byte_"+sym_count, ""+i);
//			char c = Debug.makeConcolicChar("symb_byte_"+sym_count, ""+i);
			char c = Debug.makeSymbolicChar(""+i);
			//System.out.println("-----symb_byte-----" + sym_count + "----" + (int)c);
			sym_count++;
			return (int)c;
		}
		return i;
	}
	
	public static int sym_count = 0 ;


}
