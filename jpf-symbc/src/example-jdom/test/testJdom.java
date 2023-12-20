package test;

//import gov.nasa.jpf.jdart.Debug;

import gov.nasa.jpf.symbc.Debug;
import org.jdom.input.SAXBuilder;

import java.io.StringReader;

public class testJdom {

	public static void main(String[] args) throws Exception {
		start();
	}

	public static void start() throws Exception {
//		SAXBuilder saxBuilder = new SAXBuilder();
//		Document document;
//		document = saxBuilder.build(new File("src/example-jdom/xml/basic.xml"));
//		List<Content> list = document.getContent();
////		List results = path.selectNodes(domNode);
//		System.out.println(list);
		
		String xml = "<message>HELLO!</message>";
//		String xml="";
		String s=xml;
		char[] data={60, 1, 0, 0, 0, 0, 0, 0, 62, 130, 128, 128, 128, 32, 1, 60, 47, 109, 101, 115, 115, 97, 103, 101, 62, }; 
		data=s.toCharArray();
		for(int i=0;i<s.length();i++){
			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
				System.out.print(""+(int)data[i]+", ");
			}
		}
		xml = new String(data);
		System.out.println();
		System.out.println(xml);
		
		org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom.Document doc = saxBuilder.build(new StringReader(xml));
	    String message = doc.getRootElement().getText();
	    System.out.println(message);
		
    }	

}
