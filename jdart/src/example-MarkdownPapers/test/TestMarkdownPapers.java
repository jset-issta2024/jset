package test;



import java.io.*;

import org.tautua.markdownpapers.HtmlEmitter;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.ast.Document;
import org.tautua.markdownpapers.ast.Visitor;
import org.tautua.markdownpapers.parser.ParseException;
import org.tautua.markdownpapers.parser.Parser;

public class TestMarkdownPapers {
	public static void main(String[] args) throws Exception{
		String s = "#hello\n **test**";
		start(s.toCharArray());
	}
	public static void start(char[] data) throws Exception {
		//Reader in = new FileReader(System.getProperty("user.dir")+"/src/example-MarkdownPapers/test/"+"in.md");

		CharArrayReader in = new CharArrayReader(data);
//		Writer out = new FileWriter(new File("out.html"));
		StringBuilder out=new StringBuilder();
		Visitor v = new HtmlEmitter(out);
		
		Parser parser = new Parser(in);
		Document doc = parser.parse();
		
//		doc.accept(v);
		//System.out.println(v.toString());
//		 out.flush();
//	     out.close();
	}
}
