package test;

import com.braxisltd.calculator.ArithmeticParser;
import com.braxisltd.calculator.ParseException;
import com.braxisltd.calculator.TokenMgrError;
import com.braxisltd.calculator.Value;

import java.io.StringReader;

public class TestCalculator {

	private ArithmeticParser parser;

	public static void main(String[] args){
		String s = "5 / 5"; // 5 / 8
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){

//		String s= "999 + 1.0 - 999 + 1.0 - 999 + 1.0 - 999 + 1.0";
//		String s = "5 / 5"; // 5 / 8
//		s = FileUtils.readString("inputs/Calculator.input");
//		s = SymbolicString.makeConcolicString(s);

		String s = data.toString();
		StringReader sr = new StringReader(s);
		System.out.println(s);
		try {
			ArithmeticParser parser = new ArithmeticParser(sr);
			parser.parse();
			Value v = parser.evaluate();
		} catch (TokenMgrError | ParseException e) {
			e.printStackTrace();
		}

	}
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
}
