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

import de.dominicscheurer.fol.model.Formula;
import de.dominicscheurer.fol.model.Term;
import de.dominicscheurer.fol.parser.FOLParser;
import de.dominicscheurer.fol.parser.ParseException;
import de.dominicscheurer.fol.parser.TokenMgrError;

public class TestFirstOrder extends Object {

	public static void main(String[] args) throws ParseException {
		String s = "forall Z. ( forall X. ( p(X,f(g(Y))) & exists Y. (p(Y,f(X,Y)) -> (q(c) | !r)) ) & " +
				"forall X. ( p(X,f(g(Y))) & exists Y. (p(Y,f(X,Y)) -> (q(c) | !r)) ) )";
		char[] data = s.toCharArray();
		start(data);
	}

	public static void start(char[] data) throws ParseException {

//		String s="forall X. (p(X,f(g(Y))) & exists Y. (p(Y,f(X,Y)) -> (q(c) | !r)))";
//		String s = FileUtils.readString("inputs/FirstOrder.input");
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);

		try {
			Formula formula;
			String s = data.toString();
			formula = FOLParser.parse(s);
//      formula.substitute(new Term("d"), new Term("Y"));
			formula.substitute(new Term("d"), new Term("Y"));
		} catch (TokenMgrError e) {
			e.printStackTrace();
		}

//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
	}

}