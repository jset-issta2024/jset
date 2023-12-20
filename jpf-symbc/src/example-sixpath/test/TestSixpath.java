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

import de.fzi.XPath.Parser.ParseException;
import de.fzi.XPath.Parser.TokenMgrError;
import de.fzi.XPath.Parser.XPathParser;
import gov.nasa.jpf.symbc.Debug;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class TestSixpath extends Object {

	public static String pattern ="f:r((a+q|a)[/]//b/c/d, 1)|a|node()|a|node()"
			+ "|a|node()|a|node()|a|node()|a|node()|a|node()|a|node()" ;

	//public static String pattern ="avb";
	//public static String pattern ="a:r((a+q|a)[/]//b/c/d, 1)|a|node()b";

	/**
	 * main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, IOException {
		new TestSixpath().start();
	}

	public void start() throws ParseException, IOException {

		String s="f:r((a+q|a)[/]//b/c/d, 1)|a|node()";

//	  s = FileUtils.readString("inputs/Sixpath.input");

		s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			Reader stream = new StringReader(s);
			XPathParser xpp = new XPathParser(stream);
			xpp.temp(); // can generate constraints! contained sensitive e1!
			xpp.disable_tracing();
			xpp.XPath(); // can generate constraints! contained sensitive e2!
		} catch (TokenMgrError e) {

		}

//		Debug.setValidInputTrue();

	}
}