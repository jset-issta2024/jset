package test;



//import gov.nasa.jpf.jdart.Debug;

import java.io.*;

import gov.nasa.jpf.symbc.Debug;
//import nested.C;
import org.tautua.markdownpapers.HtmlEmitter;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.ast.Document;
import org.tautua.markdownpapers.ast.Visitor;
import org.tautua.markdownpapers.parser.ParseException;
import org.tautua.markdownpapers.parser.Parser;

public class TestMarkdownPapers {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws Exception {
		//Reader in = new FileReader(System.getProperty("user.dir")+"/src/example-MarkdownPapers/test/"+"in.md");
		String s = "#hello\n **test**";
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
//			data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
			data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			System.out.print("(int)" + (int)data[i] + ",");
		} 
		System.out.println();
		String str = new String(data);
		StringReader  in = new StringReader(str);
//		Writer out = new FileWriter(new File("out.html"));
		StringBuilder out=new StringBuilder();
		Visitor v = new HtmlEmitter(out);
		
		Parser parser = new Parser(new CharArrayReader(data));
		Document doc = parser.parse();
		
//		doc.accept(v);
		//System.out.println(v.toString());
//		 out.flush();
//	     out.close();
	}
}
