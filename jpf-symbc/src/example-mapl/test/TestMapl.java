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

import gov.nasa.jpf.symbc.Debug;
import parser.MaplParser;
import parser.ParseException;
import staticanalysis.SymbolTable;
import staticanalysis.SymbolTableBuilder;
import staticanalysis.TypeChecker;
import syntaxtree.Program;

import java.io.*;

public class TestMapl extends Object {

	private Program root;

	public static void main(String[] args) throws ParseException {
	  new TestMapl().start();
	}

	public void start() throws ParseException {

		String s = "proc main ( int x ) { memo = new arrayof ( int ) [ max + 1 ] ; outchar 13 ; }";

		s = Debug.makeSymbolicString(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader reader = new StringReader(s);
			MaplParser parser  = new MaplParser(reader);
			root = parser.nt_Program();
			SymbolTableBuilder stvisit = new SymbolTableBuilder();
			root.accept(stvisit);
			SymbolTable symTab = stvisit.getSymTab();
			root.accept(new TypeChecker(symTab));

		} catch(ParseException e) {

		}


//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
	}

	public void stage1_2_old(){
	  
	  StringBuffer buffer = new StringBuffer();
	  String file = "src/example-mapl/examples/xor.mapl";
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
		s = Debug.makeSymbolicString(s);
		StringReader reader = new StringReader(s);
		
		MaplParser parser  = new MaplParser(reader);
//        System.out.println("parsing...");
        try {
			parser.nt_Program();
		} catch (parser.ParseException e) {
//			e.printStackTrace();
		}
//        System.out.println("...parse completed.");
  }
}