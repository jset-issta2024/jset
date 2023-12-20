package test;
/*========================================================================*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License.
 * 
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/ 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * See the License for the specific language governing rights and
 * limitations under the License. 
 * 
 * The Original Code is all this file. 
 * 
 * The Initial Developer of the Original Code is
 * Aleksei Valikov, Forchungszentrum Informatik (valikov@fzi.de).
 * 
 * Portions created by Ingo Macherius (<macherius@gmd.de>) are
 * Copyright (C) 1999 GMD. All Rights Reserved.
 * 
 * Contributor(s): none.
 *========================================================================*/

//import gov.nasa.jpf.jdart.FileUtils;
//import gov.nasa.jpf.jdart.SymbolicString;
import gov.nasa.jpf.symbc.Debug;
import rong.CMMParser;
import rong.ParseException;

import java.io.StringReader;

public class TestCmmparser extends Object {

	public static void main(String[] args) throws ParseException {
		new TestCmmparser().start();
	}

	public void stage1_2() throws ParseException {
//		String s="void main(){ int a,b;int result = 3;} "
//				+ "void test2(){ int a,b;int result = 3;} "
//				+ "void test1(){ int a,b;int result = 3;} ";
//		String s = "int A = 1 < 0";
//		String s = "int A = 1";

//		java.lang.NullPointerException: Calling 'trim()Ljava/lang/String;' on null object
//		at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1017)
//		at java.lang.Float.valueOf(Float.java:417)
//		at rong.CMMParser.polynomial(CMMParser.java:337)
//		int clR = 04 - clR

//		String s = "int AAAAAAAAA = 1 < 1111111111111";


//		An exception happens 1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: java.lang.NumberFormatException : For input string: "-null"
//		at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1250)
//		at java.lang.Float.valueOf(Float.java:417)
//		at rong.CMMParser.term(CMMParser.java:374)
		//		int ff36 = ( 3 / - ff36 <>
		String s = "real ff36 = 0 - 0 - 5 ;";

//		s = FileUtils.readString("inputs/Cmmparser.input");

//		String s = "int vha = <>";

//		s = SymbolicString.makeConcolicString(s);
		s = Debug.makeSymbolicString(s);
		System.out.println(s);
		StringReader reader = new StringReader(s);
		CMMParser parser = new CMMParser(reader);
		parser.procedure();

	}
	public void start() throws ParseException {
		stage1_2();
	}

	public void stage3() {}

}