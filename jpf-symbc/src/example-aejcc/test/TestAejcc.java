package test;

import ca.ubc.cs411.aejcc.ast.AE;
import ca.ubc.cs411.aejcc.parser.AEParser;
import ca.ubc.cs411.aejcc.parser.ParseException;
import gov.nasa.jpf.symbc.Debug;

import java.io.StringReader;

public class TestAejcc {
	public static void main(String[] args){

		try {
			start();
		}catch (Error e) {
			if (e instanceof ca.ubc.cs411.aejcc.parser.TokenMgrError) {

			}
			else {
				System.out.println("!!!BUG!!!");
				throw e;
			}
		}

	}
	public static void start(){
//		String s = FileUtils.readString("inputs/Aejcc.input");
		String s = "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";
//		s = SymbolicString.makeConcolicString(s);
		s = Debug.makeSymbolicString(s);
		StringReader sr = new StringReader(s);
		System.out.println(s);
		AEParser aep = new AEParser(sr);
		try {
			AE result = aep.REPLCmd();
		} catch (ParseException e) {
			e.printStackTrace();
		}


//		String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";


	}
}
