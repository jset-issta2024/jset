package test;

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;
import hirondelle.formula.Decimal;
import hirondelle.formula.Formula;

import java.io.StringReader;
import java.util.Map;

public class TestFormula4j {

	public static void main(String[] args){
		String s= "(sum(3,5) + avg(10,20) - max(2,-100,50,9)) * cos(pi/2) + 1.523e-2 * (8%5) +round(pi, 2) - sqrt(sqrt(16))";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){
		Formula formula;
		String s = data.toString();
//		s = SymbolicString.makeConcolicString(s);
//		StringReader sr = new StringReader(s);
//		System.out.println(s);
		formula = new Formula(s);
		Map<String, Decimal> actual = formula.getVariableValues();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
//		stage3();
	}

//	private void stage3() {
//		formula.stage3();
//	}

}
