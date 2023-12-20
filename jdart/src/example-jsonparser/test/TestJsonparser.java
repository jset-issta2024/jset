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

import jsonparser.JSONObject;
import jsonparser.JSONParser;
import jsonparser.ParseException;
import jsonparser.TokenMgrError;
import java.io.StringReader;

public class TestJsonparser extends Object {

	public static void main(String[] args) throws ParseException {
		String jsonstr = "{\"firstName\": \"John\",     \"lastName\": \"Smith\",     \"sex\": \"third\",     \"age\": 25     }";
		char[] data = jsonstr.toCharArray();
		start(data);
	}
	
	private static void start(char[] data) {

//		jsonstr = FileUtils.readString("inputs/Jsonparser.input");
//		jsonstr = SymbolicString.makeConcolicString(jsonstr);

		try {
			String jsonstr = data.toString();
			System.out.println(jsonstr);
			StringReader sr = new StringReader(jsonstr);
			JSONObject res = JSONParser.parse(sr);
		} catch (TokenMgrError | ParseException e) {
			e.printStackTrace();
		}
	}
}