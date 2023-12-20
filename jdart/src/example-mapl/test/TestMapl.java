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

import parser.MaplParser;
import parser.ParseException;
import staticanalysis.SymbolTable;
import staticanalysis.SymbolTableBuilder;
import staticanalysis.TypeChecker;
import syntaxtree.Program;

import java.io.*;

public class TestMapl extends Object {

	public static void main(String[] args) throws ParseException {

		String s = "proc main ( int x ) { memo = new arrayof ( int ) [ max + 1 ] ; outchar 13 ; }";
		char[] data = s.toCharArray();
		start(data);

	}

	public static void start(char[] data) throws ParseException {
//		s = SymbolicString.makeConcolicString(s);

		try {
			Program root;
			String s = data.toString();
			StringReader reader = new StringReader(s);
			MaplParser parser  = new MaplParser(reader);
			root = parser.nt_Program();
			SymbolTableBuilder stvisit = new SymbolTableBuilder();
			root.accept(stvisit);
			SymbolTable symTab = stvisit.getSymTab();
			root.accept(new TypeChecker(symTab));
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}

}