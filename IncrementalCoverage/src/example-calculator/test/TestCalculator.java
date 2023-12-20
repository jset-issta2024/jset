package test;

import com.braxisltd.calculator.ArithmeticParser;
import com.braxisltd.calculator.ParseException;
import com.braxisltd.calculator.Value;

import java.io.StringReader;

public class TestCalculator {

	private ArithmeticParser parser;

	public static void main(String[] args){
		new TestCalculator().start();
	}
	public void start(){
		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
		stage3();
	}

	private void stage3() {
		Value v = parser.evaluate();
	}

	private void stage1_2() {
		String s= "999 + 1.0";

//		s = SymbolicString.makeConcolicString(s);
		StringReader sr = new StringReader(s);
//		System.out.println(s);
		parser = new ArithmeticParser(sr);
		try {
			parser.parse();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
}
