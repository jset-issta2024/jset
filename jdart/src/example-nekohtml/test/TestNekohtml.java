package test;

import org.cyberneko.html.parsers.DOMParser;
import org.cyberneko.html.xercesbridge.XercesBridge_2_2;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;

public class TestNekohtml {
	public static void main(String[] args){
		System.out.println(XercesBridge_2_2.getInstance().getVersion());
		String s= "<html><head><title>foo</title></head>"
				+ "<body>"
				+ "test"
				+ "</body></html>";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){
	    String s = data.toString();
		DOMParser parser = new DOMParser();
		try {
			parser.parse(s);
			print(parser.getDocument(), "");
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void print(Node node, String indent) {
        System.out.println(indent+node.getClass().getName());
        Node child = node.getFirstChild();
        while (child != null) {
            print(child, indent+" ");
            child = child.getNextSibling();
        }
    }
}
