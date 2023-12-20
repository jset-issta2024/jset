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

import gov.nasa.jpf.symbc.Debug;
import javascriptInterpreter.parser.Javascript;
import javascriptInterpreter.parser.ParseException;
import javascriptInterpreter.parser.TokenMgrError;
import javascriptInterpreter.tree.SimpleNode;
import javascriptInterpreter.visitors.Context;
import javascriptInterpreter.visitors.ExecutionVisitor;

import java.io.*;

public class TestJsijcc extends Object {

	private SimpleNode sn;

	public static void main(String[] args) throws ParseException {
	  new TestJsijcc().start();
	}

	public void start() throws ParseException {

		String s = "var a = 5 for(var i = 0; i < 10;) { i = 10 ;} for(var i = 0; i < 10;) { i = 10 ;} " +
				"for(var i = 0; i < 10;) { i = 10 ;} " +
				"false && true false && true ";


//		String s = "for(var i = 10; i < 10;) { i = 10 ;} var a = 5 for(var i = 10; i < 10;) { i = 10 ;}";
//		java.lang.NullPointerException: Calling 'getIdentifierName()Ljava/lang/String;' on null object
//		at javascriptInterpreter.visitors.EvaluationVisitor.visit(EvaluationVisitor.java:546)

//		An exception happens 1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: java.lang.ClassCastException : javascriptInterpreter.tree.ASTmultiplicativeExpression cannot be cast to javascriptInterpreter.tree.ASTadditiveExpression
//		at javascriptInterpreter.visitors.EvaluationVisitor.visit(EvaluationVisitor.java:315)
//		1 + 1
//		String s = "1111111111 * 1111111111";
//		~ { }

//		An exception happens 1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: java.lang.ClassCastException : javascriptInterpreter.visitors.JavascriptType$JavascriptObject cannot be cast to java.lang.Double
//		at javascriptInterpreter.visitors.JavascriptType.getDouble(JavascriptType.java:161)
//		s = "var a = 5";

//		String s = FileUtils.readString("inputs/jsijcc.input");

		s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader reader = new StringReader(s);
			Javascript parser = new Javascript(reader);
			sn = parser.program();

			ExecutionVisitor v = new ExecutionVisitor();
			Context scope = new Context();
			sn.jjtAccept(v, scope);
//		System.out.println("Successfully executed the program");

		} catch (TokenMgrError e) {

		}

//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
	}

}