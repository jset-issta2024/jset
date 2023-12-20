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

import clojure.ClojureParser;
import clojure.ParseException;

import java.io.*;

public class TestClojure extends Object {

  public static void main(String[] args){
	  new TestClojure().start();
	}

	public void start() {
  		stage1_2();
//		Debug.setValidInputTrue();
	}

	private void stage1_2() {
		String s = "(OPTIONS \"/my\" []" +
				" :no-doc true" +
				" (resp/not-found)) [nil nil] (. thess cho-ec?)";
//		s = SymbolicString.makeConcolicString(s);
		StringReader reader = new StringReader(s);

		try {
			new ClojureParser(reader).S();
		} catch (ParseException e) {
		}
	}

	public void stage1_2_old(){
	  
	  StringBuffer buffer = new StringBuffer();
	  String file = "src/example-javacc-clojure/test/sample.clj";
	  FileReader freader = null;
	  try {
		freader = new FileReader(file);
	  } catch (FileNotFoundException e1) {
		e1.printStackTrace();
	  }
      BufferedReader bf= new BufferedReader(freader);
      String temp = null;
      try {
		while((temp = bf.readLine())!=null){
		      buffer.append(temp.trim());
		  }
      } catch (IOException e1) {
    	  e1.printStackTrace();
      }
      	String s = buffer.toString();
//		s = SymbolicString.makeConcolicString(s);
		StringReader reader = new StringReader(s);
		
		try {
			new ClojureParser(reader).S();
		} catch (ParseException e) {
			e.printStackTrace();
		}
  }
}