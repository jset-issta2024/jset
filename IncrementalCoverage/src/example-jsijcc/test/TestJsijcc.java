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
  		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
		stage3();
	}

	private void stage1_2() throws ParseException {
		String s = "i<q"; //"var a = 5 for(var i = 0; i < 10;) {} false && true ";
//		String s = "5 % 3";
//		s = SymbolicString.makeConcolicString(s);
		System.out.println(s);
		StringReader reader = new StringReader(s);

		Javascript parser = new Javascript(reader);
//		SimpleNode sn = null;
		sn = parser.program();
	}

	void stage3(){
		ExecutionVisitor v = new ExecutionVisitor();
		Context scope = new Context();
		sn.jjtAccept(v, scope);
//		System.out.println("Successfully executed the program");
	}

	public void stage1_2_old(){
	  
	  StringBuffer buffer = new StringBuffer();
	  String file = "src/example-jsijcc/test/order.js";
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
		System.out.println(s);
		StringReader reader = new StringReader(s);
		
        Javascript parser = new Javascript(reader);
        SimpleNode sn = null;
		try {
			sn = parser.program();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("Successfully parsed the grammar");
        ExecutionVisitor v = new ExecutionVisitor();
        Context scope = new Context();
        sn.jjtAccept(v, scope);
        System.out.println("Successfully executed the program");
  }
}