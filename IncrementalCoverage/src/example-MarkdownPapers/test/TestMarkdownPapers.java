package test;




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;

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
	public static String parserName="MarkdownPapers";
	public static void start()throws Exception{
		//Reader in = new FileReader(System.getProperty("user.dir")+"/src/example-MarkdownPapers/test/"+"in.md");
//		char[] charArray = {35, 42, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 13, 10, 9,};
//		String s = new String(charArray);
		String s = "#hello\n **test**";
//		char[] data;
//		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//				System.out.print((int)data[i] + " ");
//			}
//		}
//		s = new String(data);

		System.out.println(s);

		StringReader  in = new StringReader(s);
//				Writer out = new FileWriter(new File("out.html"));
		StringBuilder out=new StringBuilder();
		Visitor v = new HtmlEmitter(out);
		Parser parser = new Parser(in);
		Document doc = parser.parse();
		doc.accept(v);

	}
}
