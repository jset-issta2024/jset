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

import awk.ParseException;
//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;
import awk.SENTENCE;
import gov.nasa.jpf.symbc.Debug;
import gov.nasa.jpf.symbc.Symbolic;

public class TestAwkParser extends Object {

  public static void main(String[] args) throws ParseException {

	  try {
		  new TestAwkParser().start();
	  }catch (Error e) {
		  if (e instanceof awk.TokenMgrError) {

		  }
		  else {
			  System.out.println("!!!BUG!!!");
			  throw e;
		  }
	  }
	}

	public void start() throws ParseException {
  		stage1_2();
//		Debug.setValidInputTrue();
	}

  public void stage1_2() throws ParseException{
//	  String s="BEGIN { (print \"name,shell\")  (print $1\",\"$7) } END { (print \"blue,/bin/nosh\") }";
	  String s = "if for  if  for  if  for  if  for  if  for  if  for  if  for  if ";
//	  String s = "exit ( ( 134 ";
//	  s = SymbolicString.makeConcolicString(s);
	  s = Debug.makeSymbolicString(s);
	  System.out.println(s);

//	  InputStream  inputStream = new ByteArrayInputStream(s.getBytes());
	  SENTENCE sentence = new SENTENCE(new StringReader(s));
	  while (true){
		  sentence.Parse();
	  }
  }
}