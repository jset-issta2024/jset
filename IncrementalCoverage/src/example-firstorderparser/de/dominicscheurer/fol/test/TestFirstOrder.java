package de.dominicscheurer.fol.test;
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

public class TestFirstOrder extends Object {

	private Formula formula;

	public static void main(String[] args) throws ParseException {
	  new TestFirstOrder().start();
	}

	public void start() throws ParseException {
  		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
		stage3();
	}

  public void stage1_2() throws ParseException{
	  String s="forall X. (p(X,f(g(Y))) & exists Y. (p(Y,f(X,Y)) -> (q(c) | !r)))";
//	  s = SymbolicString.makeConcolicString(s);
	  System.out.println(s);

	  formula = FOLParser.parse(s);
//      formula.substitute(new Term("d"), new Term("Y"));
  }

  void stage3(){
	  formula.substitute(new Term("d"), new Term("Y"));
  }
}