package test;

import ca.ubc.cs411.aejcc.ast.AE;
import ca.ubc.cs411.aejcc.parser.AEParser;
import ca.ubc.cs411.aejcc.parser.ParseException;
import ca.ubc.cs411.aejcc.parser.TokenMgrError;

import java.io.StringReader;

public class TestAejcc {

	public static void main(String[] args){
		String s = "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";
		char[] data = s.toCharArray();
		start(data);
	}

	public static void start(char[] data){
//		String s = FileUtils.readString("inputs/Aejcc.input");
//		String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";
//		s = SymbolicString.makeConcolicString(s);

		String s = data.toString();
		StringReader sr = new StringReader(s);
		System.out.println(s);
		AEParser aep = new AEParser(sr);
		try {
			AE result = aep.REPLCmd();
		} catch (TokenMgrError | ParseException e) {
			e.printStackTrace();
		}
	}
}
