package test;


import gov.nasa.jpf.util.Global;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestCommonmarkJava {

	private static Map<String, Double> inputWithTime = new LinkedHashMap<>();

	public static void main(String[] args) throws Exception{
		String s = "This is ***TXTMARK***";
		System.out.println(s);
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws Exception{

//		String str = new String(data);
		Parser parser = Parser.builder().build();
		Node document = parser.parseReader(new CharArrayReader(data));
//		Node document = parser.parse(str);
		Global.isSaveInput = true;
		Global.baseOutputName = "Commonmark";

	}

}
