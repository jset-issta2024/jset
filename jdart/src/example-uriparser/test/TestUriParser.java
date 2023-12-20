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

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.FileUtils;
//import gov.nasa.jpf.jdart.SymbolicString;
import uri.Parser;
import uri.Program;

import java.io.*;

public class TestUriParser extends Object {

  public static void main(String[] args){

	  String s = "function f ( a , b )  {\n" +
			  "  return a + \" < \" + b + \": \" + ( a  < b ) ;\n " +
			  "} "
			  + "\n function f ( a , b )  {\n" +
			  "  return a + \" < \" + b + \": \" + ( a  < b ) ;\n " +
			  "} "
			  + "\n function f ( a , b )  {\n" +
			  "  return a + \" < \" + b + \": \" + ( a  < b ) ;\n " +
			  "} "
			  + "\n function f ( a , b )  {\n" +
			  "  return a + \" < \" + b + \": \" + ( a  < b ) ;\n " +
			  "} "
			  ;
	  char[] data = s.toCharArray();

	  start(data);
	}

	public static void start(char[] data) {
  		String s = data.toString();

//		s = FileUtils.readString("inputs/uriparser.input");
//
//		s = SymbolicString.makeConcolicString(s);
		StringReader reader = new StringReader(s);

		try {
			Program p = new Parser(reader).translationUnit();
		} catch (uri.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Debug.setValidInputTrue();
	}

}