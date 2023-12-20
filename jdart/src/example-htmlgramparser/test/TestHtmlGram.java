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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.*;

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;
//import gov.nasa.jpf.symbc.Symbolic;
import kr.ac.cau.popl.gauthierplm.*;

public class TestHtmlGram extends Object {

  public static void main(String[] args) throws ParseException {

	  String s="<!doctype html> <!doctype html>"
			  + "<html><head><title>First parse</title></head>"
			  + "<!-- Region: {view-rendered} Module: {view-rendered}  -->"
			  + "<body><p>Parsed HTML into a doc.</p></body></html>";

	  char[] data = s.toCharArray();

	  start(data);
	}

	public static void start(char[] data) throws ParseException {


//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);

		String s = data.toString();

//	  InputStream  inputStream = new ByteArrayInputStream(s.getBytes());
		TagRecord tr= new TagRecord();
		HtmlGrammar parser = new HtmlGrammar(new StringReader(s));
		HtmlGrammar.tr = tr;
		parser.file();

//		Debug.setValidInputTrue();
	}

}