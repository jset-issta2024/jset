package test;

import java.io.StringReader;
import com.cloudability.bling.ast.*;
import gov.nasa.jpf.symbc.Debug;

public class TestBling {
	public static void main(String[] args){
		start();
	}
	public static void start(){
	    String s= "- 2.5e2 / 1.25e-2 - 2.5e2 / 1.25e-2 - 2.5e2 / 1.25e-2";
//		String s = FileUtils.readString("inputs/Bling.input");

		s = Debug.makeSymbolicString(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader sr = new StringReader(s);
			BlingParser parser = new BlingParser(sr);
			Expression e = parser.parse();
		}
		catch (TokenMgrError e) {

		}
	}
}
