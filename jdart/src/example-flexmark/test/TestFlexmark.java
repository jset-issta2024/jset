package test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.internal.DocumentParser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.CharArrayReader;
import java.io.IOException;

public class TestFlexmark {
	public static void main(String[] args) throws IOException {
		String s = "# head\n **test**";
		char[] data;
		data=s.toCharArray();

		start(data);
	}
	public static void start(char[] data) throws IOException {

//		String str = new String(data);
		
//		System.out.println("--------------start--------------");
		MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

//		Node document = parser.parse(str);
        Node document = parser.parseReader(new CharArrayReader(data));
        String html = renderer.render(document); 
        
        //System.out.println(html);
	}
}
