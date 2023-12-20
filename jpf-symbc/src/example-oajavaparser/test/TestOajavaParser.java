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

import com.viaoa.javaparser.JavaParser;
import com.viaoa.javaparser.JavaParserTreeConstants;
import com.viaoa.javaparser.ParseException;
import com.viaoa.javaparser.SimpleNode;
import gov.nasa.jpf.symbc.Debug;

import java.io.StringReader;
import java.util.LinkedList;

public class TestOajavaParser extends Object {

	private JavaParser parser;
	SimpleNode root;

	public static void main(String[] args) throws ParseException {
		new TestOajavaParser().start();
	}

	public void start() throws ParseException {

		String s="public class ScannerTest { "+
				"public static void main ( String [ ] args ) { "+
				"Scanner scanner = new Scanner ( System . in );"+
				"System . out . println ( ) ; "+
				"int a = scanner . nextInt () ; "+
				"System . out . printf ( a * a ) ; } }";

//	  s = FileUtils.readString("inputs/OAjavaparser.input");
//		s = SymbolicString.makeConcolicString(s);
		s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader reader = new StringReader(s);
			parser = new JavaParser(reader);
			parser.CompilationUnit();

			root = (SimpleNode) parser.jjtree.rootNode();
			SimpleNode node = root.getChild(JavaParserTreeConstants.JJTCOMPILATIONUNIT);
			node.getChild(JavaParserTreeConstants.JJTPACKAGEDECLARATION);
			node.getChildren(JavaParserTreeConstants.JJTIMPORTDECLARATION);
			if (node == null) {
				return;
			}
			node = node.getChild(JavaParserTreeConstants.JJTTYPEDECLARATION);
			if (node == null) {
				return;
			}
			node = node.getChild(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
			if (node == null) {
				return;
			}
			node = node.getChild(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY);

			if (node == null) {
				return;
			}
			SimpleNode[] nodes = node.getChildren(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

			LinkedList<SimpleNode> list = new LinkedList<SimpleNode>();
			for (int i = 0; nodes != null && i < nodes.length; i++) {
				for (int j = 0; nodes[i].children != null && j < nodes[i].children.length; j++) {
					if (((SimpleNode) nodes[i].children[j]).getId() == JavaParserTreeConstants.JJTFIELDDECLARATION) {
						list.add(nodes[i]);
						break;
					}
				}
			}
			SimpleNode[] ns = new SimpleNode[list.size()];
			list.toArray(ns);

		} catch (ParseException e) {

		}

	}

}