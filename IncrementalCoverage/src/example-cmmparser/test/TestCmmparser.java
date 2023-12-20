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

import rong.CMMParser;

import java.io.StringReader;

public class TestCmmparser extends Object {

	public static void main(String[] args){
		new TestCmmparser().start();
	}

	public void stage1_2(){
		String s="void main(){ int a,b;int result = 3;}";
//		s = SymbolicString.makeConcolicString(s);
		System.out.println(s);
		StringReader reader = new StringReader(s);
		CMMParser parser = new CMMParser(reader);
		try {
			parser.procedure();
		} catch (Exception e) {}
	}
	public void start() {
		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();

		stage3();
	}

	public void stage3() {}

}