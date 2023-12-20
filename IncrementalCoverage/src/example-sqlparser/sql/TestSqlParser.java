package sql;
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

public class TestSqlParser extends Object {

	public static void main(String[] args) throws Exception {
		new TestSqlParser().start();
	}

	public void start() throws Exception {
		stage1_2();

//		Debug.setValidInputTrue();
	}

	public void stage1_2() throws Exception {
//		String s="select e.Name, d.MName \n from Emp e, Dept d \n where d.Name = c.MName and (((c.Salary > 7000 and d.Name = \"James\" and c.Name = d.Name)))";
		String s = "select e.Name , d.MName , e.Name , d.MName , e.Name , d.MName from Emp e, Dept d where d.Name = c.MName and d.Name = c.MName and d.Name = c.MName and d.Name = c.MName and d.Name = c.MName and (((c.Salary > 7000 and d.Name = \"James\" and c.Name = d.Name)))";
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);
		String rst = parse(s);
	}

	private String parse(String input) throws Exception {
		String rst = "";
		rst = Parser.parse(input);

		return rst;
	}

}