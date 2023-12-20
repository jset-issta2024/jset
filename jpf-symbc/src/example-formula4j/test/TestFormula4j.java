package test;

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;
import gov.nasa.jpf.symbc.Debug;
import hirondelle.formula.Decimal;
import hirondelle.formula.Formula;

import java.io.StringReader;
import java.util.Map;

public class TestFormula4j {

	private Formula formula;

	public static void main(String[] args){
		new TestFormula4j().start();
	}
	public void start(){
		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
//		stage3();
	}

//	private void stage3() {
//		formula.stage3();
//	}

	public void stage1_2() {
		String s= "(sum(3,5) + avg(10,20) - max(2,-100,50,9)) * cos(pi/2) + 1.523e-2 * (8%5) +round(pi, 2) - sqrt(sqrt(16))";

//		s = SymbolicString.makeConcolicString(s);
		s = Debug.makeSymbolicString(s);
		StringReader sr = new StringReader(s);
		System.out.println(s);
		formula = new Formula(s);
		Map<String, Decimal> actual = formula.getVariableValues();
	}


}
