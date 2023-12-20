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

import kr.ac.cau.popl.gauthierplm.HtmlGrammar;
import kr.ac.cau.popl.gauthierplm.ParseException;
import kr.ac.cau.popl.gauthierplm.TagRecord;

import java.io.StringReader;

public class TestHtmlGram extends Object {

  public static void main(String[] args) throws ParseException {
	  new TestHtmlGram().start();
	}

	public void start() throws ParseException {
  		stage1_2();
//		Debug.setValidInputTrue();
	}

  public void stage1_2() throws ParseException{
//	  String s="<!doctype html> <!doctype html>"
//				+ "<html><head><title>First parse</title></head>"
//				+ "<!-- Region: {view-rendered} 	 Module: {view-rendered}  -->"
//				  + "<body><p>Parsed HTML into a doc.</p></body></html>";
	  String s = "a ";

//	  s = SymbolicString.makeConcolicString(s);
	  System.out.println(s);

//	  InputStream  inputStream = new ByteArrayInputStream(s.getBytes());
	  TagRecord tr= new TagRecord();
	  HtmlGrammar parser = new HtmlGrammar(new StringReader(s));
	  HtmlGrammar.tr = tr;
	  parser.file();
  }
}