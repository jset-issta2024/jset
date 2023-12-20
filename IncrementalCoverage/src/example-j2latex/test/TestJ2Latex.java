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

import com.github.situx.compiler.parser.C1;
import com.github.situx.compiler.parser.NullWriter;
import com.github.situx.compiler.parser.ParseException;
import com.github.situx.compiler.treej.Node;
import com.github.situx.compiler.visitorj.AsHTML;
import com.github.situx.compiler.visitorj.AsLatex;

import java.io.*;

public class TestJ2Latex extends Object {

	private Node i;

	public static void main(String[] args){
	  new TestJ2Latex().start();
	}

	public void start() {
		stage1_2();
//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
		stage3();
  }

	public void stage3(){
  		Writer w = new NullWriter();
  		AsHTML html = new AsHTML(w,"null.file", 4,0);
  		i.welcome(html);
  		Writer wL = new NullWriter();
  		AsLatex latex = new AsLatex(wL,"null.file",4,0);
  		i.welcome(latex);
	}

  public void stage1_2_old(){
	  
	  StringBuffer buffer = new StringBuffer();
	  String file = "src/example-j2latex/com/github/situx/compiler/sample/Sample.javatest";
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
//      	String s = buffer.toString();
	  stage1_2();
	  	

//	  	Writer whtml = new FileWriter("outputs/Sample.html");
//	  	Writer wlatex = new FileWriter("outputs/Sample.tex");
//	  	AsHTML html = new AsHTML(whtml,file,4,0);
//	  	i.welcome(html);
//	  	html.w.close();
//	  	AsLatex latex = new AsLatex(wlatex,file,4,0);
//	  	i.welcome(latex);
//	  	latex.w.close();
//
//	  	file="src/stuff/SampleInter.java";
//	    reader=new FileReader(file);
//	  	parser = new C1(reader);
//	  	i = parser.program();
//	  	whtml = new FileWriter("outputs/SampleInter.html");
//	  	wlatex = new FileWriter("outputs/SampleInter.tex");
//	  	html = new AsHTML(whtml,file,4,0);
//	  	i.welcome(html);
//	  	html.w.close();
//	  	latex = new AsLatex(wlatex,file,4,0);
//	  	i.welcome(latex);
//	  	latex.w.close();
//
//
//	  	file="src/stuff/SampleInter2.java";
//	    reader=new FileReader(file);
//	  	parser = new C1(reader);
//	  	i = parser.program();
//	  	whtml = new FileWriter("outputs/SampleInter2.html");
//	  	wlatex = new FileWriter("outputs/SampleInter2.tex");
//	  	html = new AsHTML(whtml,file,4,0);
//	  	i.welcome(html);
//	  	html.w.close();
//	  	latex = new AsLatex(wlatex,file,4,0);
//	  	i.welcome(latex);
//	  	latex.w.close();
  }

	private void stage1_2() {
		String s = "public abstract class Sample < T , E > extends Object implements SampleInter < T , E > { public Sample () {\n" +
				"super ();} }";
//		s = SymbolicString.makeConcolicString(s);
		System.out.println(s);
		StringReader reader = new StringReader(s);

		C1 parser = new C1(reader);
		try {
			i = parser.program();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}