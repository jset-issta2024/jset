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

import javascriptInterpreter.parser.Javascript;
import javascriptInterpreter.parser.ParseException;
import javascriptInterpreter.parser.TokenMgrError;
import javascriptInterpreter.tree.SimpleNode;
import javascriptInterpreter.visitors.Context;
import javascriptInterpreter.visitors.ExecutionVisitor;

import java.io.*;

public class TestJsijcc extends Object {

	public static void main(String[] args) throws ParseException {
		String s = "var a = 5 for(var i = 0; i < 10;) { i = 10 ;} for(var i = 0; i < 10;) { i = 10 ;} " +
				"for(var i = 0; i < 10;) { i = 10 ;} " +
				"false && true false && true ";
		char[] data = s.toCharArray();
		start(data);
	}

	public static void start(char[] data) throws ParseException {

//		String s = FileUtils.readString("inputs/jsijcc.input");
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);

		try {
			SimpleNode sn;
			String s = data.toString();
			StringReader reader = new StringReader(s);
			Javascript parser = new Javascript(reader);
			sn = parser.program();
			ExecutionVisitor v = new ExecutionVisitor();
			Context scope = new Context();
			sn.jjtAccept(v, scope);
		} catch (TokenMgrError e) {
			e.printStackTrace();
		}

//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();

	}
}