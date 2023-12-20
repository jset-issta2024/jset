package test;

import ca.ubc.cs411.aejcc.ast.AE;
import ca.ubc.cs411.aejcc.parser.AEParser;
import ca.ubc.cs411.aejcc.parser.ParseException;

import java.io.StringReader;

public class TestAejcc {
	public static void main(String[] args){
		start();
	}
	public static void start(){
	    String s= "(+ (+ 1 1) (- 5 8))";

//	    s = SymbolicString.makeConcolicString(s);
	    StringReader sr = new StringReader(s);
//		System.out.println(s);
		AEParser aep = new AEParser(sr);
		try {
			AE result = aep.REPLCmd();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
