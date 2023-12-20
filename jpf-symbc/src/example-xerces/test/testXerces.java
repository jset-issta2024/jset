package test;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


//import gov.nasa.jpf.jdart.Debug;

public class testXerces {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		String xmlStringBuilder = "<?xml version = \"1.0\"?> <class> </class>";
		ByteArrayInputStream input =  new ByteArrayInputStream(
		   xmlStringBuilder.getBytes("UTF-8"));
		Document doc = builder.parse(input);
		Element root = doc.getDocumentElement();
		
	}	
}
