package test;

//import gov.nasa.jpf.jdart.Debug;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;
import gov.nasa.jpf.symbc.Debug;

import java.io.CharArrayReader;
import java.io.IOException;

public class TestFlexmark {
	public static void main(String[] args) throws IOException {
		start();
	}
	public static void start() throws IOException {
		String s = "# head\n **test**";
    	char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data);
		
		System.out.println("--------------start--------------");
		MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

//        Node document = parser.parse(str);
		Node document = parser.parseReader(new CharArrayReader(data));
        String html = renderer.render(document); 
        
        //System.out.println(html);
	}
}
