package org.mwnorman.json.test;
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

import org.mwnorman.json.JSONParser;

import java.io.StringReader;
import java.util.Map;

public class TestJsonParserMwnorman extends Object {
	static final String COLON = ":";
    static final String COMMA = ",";
    static final String DOLLAR_SIGN = "$";
    static final String QUOTE = "\"";
    static final String OPEN_BRACE = "{";
    static final String CLOSE_BRACE = "}";
    static final String OPEN_BRACKET = "[";
    static final String CLOSE_BRACKET = "]";
    static final String DOUBLE_SLASH = "\\\\";
    static final String C_COMMENT_START = "/*";
    static final String C_COMMENT_END = "*/";
    static final String HTML_COMMENT_START = "<!--";
    static final String HTML_COMMENT_END = "-->";
    static final String KEY = "key";
    static final String VALUE = "value";
    
  public static void main(String[] args){
	  new TestJsonParserMwnorman().start();
	}

	public void start() {
  		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
		stage3();
	}

	void stage3() {}

  public void stage1_2(){
	  String ATTRS = "Attrs.Attr";
	  String s="{'" + ATTRS + "':{'" + DOLLAR_SIGN + "all" +
	            "':[{\"" + DOLLAR_SIGN + "elemMatch\":{'Name':'srv','Value':'srvId'}}]}}";
//	  s = SymbolicString.makeConcolicString(s);
	  System.out.println(s);
	  StringReader reader = new StringReader(s);
	  JSONParser parser = new JSONParser(reader);
	  try {
		parser.parse();
	} catch (org.mwnorman.json.ParseException e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
	}
  }
}