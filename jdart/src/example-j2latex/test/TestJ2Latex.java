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

import com.github.situx.compiler.parser.C1;
import com.github.situx.compiler.parser.NullWriter;
import com.github.situx.compiler.parser.ParseException;
import com.github.situx.compiler.treej.Node;
import com.github.situx.compiler.visitorj.AsHTML;
import com.github.situx.compiler.visitorj.AsLatex;

import java.io.*;

public class TestJ2Latex extends Object {

	public static void main(String[] args) {

		String s = "public abstract class Sample < T , E > extends Object implements SampleInter < T , E > { public Sample () { supeb ();} }";
		char[] data = s.toCharArray();
		start(data);
	}

	public static void start(char[] data) {

//		s = FileUtils.readString("inputs/J2latex.input");
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);

		Node i = null;
		String s = data.toString();
		StringReader reader = new StringReader(s);
		C1 parser = new C1(reader);
		try {
			i = parser.program();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Writer w = new NullWriter();
		AsHTML html = new AsHTML(w,"null.file", 4,0);
		i.welcome(html);
		Writer wL = new NullWriter();
		AsLatex latex = new AsLatex(wL,"null.file",4,0);
		i.welcome(latex);

  }

}