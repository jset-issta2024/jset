package test;

import com.braxisltd.calculator.*;
import gov.nasa.jpf.symbc.Debug;
import java.io.StringReader;

public class TestCalculator {

	private ArithmeticParser parser;

	public static void main(String[] args) throws ParseException {
		new TestCalculator().start();
	}

	public void start() throws ParseException {
		String s= "999 + 1.0 - 999 + 1.0 - 999 + 1.0 - 999 + 1.0";

//		s = FileUtils.readString("inputs/Calculator.input");

		s = Debug.makeSymbolicString(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader sr = new StringReader(s);
			parser = new ArithmeticParser(sr);
			parser.parse();
			Value v = parser.evaluate();
		} catch (TokenMgrError er){

		}
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
	}
}
