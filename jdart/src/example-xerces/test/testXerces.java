package test;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class testXerces {
	public static void main(String[] args) throws Exception{
		String xmlStringBuilder = "<?xml version = \"1.0\"?> <class> </class>";
		char[] data = xmlStringBuilder.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws Exception{

		String xmlStringBuilder = data.toString();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		ByteArrayInputStream input =  new ByteArrayInputStream(
		   xmlStringBuilder.getBytes("UTF-8"));
		Document doc = builder.parse(input);
		Element root = doc.getDocumentElement();
		
	}	
}
