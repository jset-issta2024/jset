package com.github.situx.compiler.sample;

import com.github.situx.compiler.parser.C1;
import com.github.situx.compiler.treej.Node;
import com.github.situx.compiler.visitorj.AsHTML;
import com.github.situx.compiler.visitorj.AsLatex;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;


public class Test {
	  public static void main(String[] args)throws Exception{
		    String file="src/example-j2latex/com/github/situx/compiler/sample/Sample.javatest";
		    FileReader reader=new FileReader(file);
		  	C1 parser = new C1(reader);
		  	Node i = parser.program(); 
		  	
		  	Writer whtml = new FileWriter("src/example-j2latex/output/Sample.html");
		  	Writer wlatex = new FileWriter("src/example-j2latex/output/Sample.tex");
		  	AsHTML html = new AsHTML(whtml,file,4,0);
		  	i.welcome(html);
		  	html.w.close();
		  	AsLatex latex = new AsLatex(wlatex,file,4,0);
		  	i.welcome(latex);
		  	latex.w.close();
		  	/*
		  	file="src/stuff/SampleInter.java";
		    reader=new FileReader(file);
		  	parser = new C1(reader);
		  	i = parser.program();
		  	whtml = new FileWriter("src/example-j2latex/output/SampleInter.html");
		  	wlatex = new FileWriter("src/example-j2latex/output/SampleInter.tex");
		  	html = new AsHTML(whtml,file,4,0);
		  	i.welcome(html);
		  	html.w.close();
		  	latex = new AsLatex(wlatex,file,4,0);
		  	i.welcome(latex);
		  	latex.w.close();
		  	file="src/stuff/SampleInter2.java";
		    reader=new FileReader(file);
		  	parser = new C1(reader);
		  	i = parser.program();
		  	whtml = new FileWriter("outputs/SampleInter2.html");
		  	wlatex = new FileWriter("outputs/SampleInter2.tex");
		  	html = new AsHTML(whtml,file,4,0);
		  	i.welcome(html);
		  	html.w.close();
		  	latex = new AsLatex(wlatex,file,4,0);
		  	i.welcome(latex);
		  	latex.w.close();
		    /*Writer wxml = new FileWriter("Prog.xml");
			AsXML xml = new AsXML(wxml,file);
			i.welcome(xml);
			xml.w.close();*/
/*		 	Rechne rechner=new Rechne();
		 	i.welcome(rechner);
		  	System.out.println(rechner.result); 
			GenStackCode gen = new GenStackCode(); 
			i.welcome(gen);
			Instruction[] code =gen.result.toArray(new Instruction[0]);
			StackMachine machine = new StackMachine(code);
			machine.run();*/
		  }

}
