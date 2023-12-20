package test;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class testDom4j {

	public static void main(String[] args) throws Exception {
		start();
	}
	public static void start() throws Exception{
		String xml="<?xml version = \"1.0\"?> <class> </class>";
		
		SAXReader saxReader = new SAXReader();  
		Document document = saxReader.read("src/example-dom4j/xml/basic.xml"); 
		Element root = document.getRootElement();
		System.out.println(root.asXML());
		
		Document doc = DocumentHelper.parseText(xml);
    }

}
