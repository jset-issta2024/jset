package test;

import nl.bigo.curta.Curta;
import nl.bigo.curta.ParseException;

public class TestCurta {
	public static void main(String[] args) throws ParseException {
		start();
	}
	static Curta curta = new Curta();
	public static void start() throws ParseException {
//		String s="false || true";
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);
//
//		Curta curta = new Curta();
//		try {
//			curta.eval(s);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
		stage3();
	}

	public static void stage1_2() throws ParseException {
		String s="false || true + abs(-999) << cos(PI) * ~hypot(E) + abs(-999) / cos(PI) ^ ~hypot(E)";
//		s = SymbolicString.makeConcolicString(s);
		System.out.println(s);
		curta.stage1_2(s);
	}

	static void stage3() {
		curta.stage3();
	}
}
