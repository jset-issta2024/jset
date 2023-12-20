package test;

//import gov.nasa.jpf.jdart.FileUtils;
//import gov.nasa.jpf.jdart.SymbolicString;
//import util.Global;
//import gov.nasa.jpf.jdart.Debug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import gov.nasa.jpf.symbc.Debug;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.FileReader;
import java.io.IOException;

public class testFastjsonDev {

	public static void main(String[] args) throws Exception {
		start();

	}
	public void s(){};
	public void e(){};
	public static void start() {
		stage1_2();

//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();
//		Debug.printCurrentPC();
		stage3();
//		Debug.printCurrentPC();
//		Debug.finishStage3();
	}

	static String json="{ \"age\" : 22 ,\"flag\" : [ true , false ] , \" temp \" : "
			+ "[0,{\\\"1\\\":{\\\"2\\\":{\\\"3\\\":{\\\"4\\\":[5,{\\\"6\\\":7}]}}}}]"
			+  " \" temp2 \": [0,{\\\"1\\\":{\\\"2\\\":{\\\"3\\\":{\\\"4\\\":[5,{\\\"6\\\":7}]}}}}] "
			+  " \" temp3 \": [0,{\\\"1\\\":{\\\"2\\\":{\\\"3\\\":{\\\"4\\\":[5,{\\\"6\\\":7}]}}}}] " +
			"}";
	static Object res;
	static void stage1_2()
	{
//		char[] charArray = {91, 32, 32, 44, 32, 123, 32, 123, 32, 123, 32, 123, 32, 91, 32, 32, 44, 32, 93, 32, 57, 57, 48, 57, 45, 48, 57, 45, 50, 53, 32, 93, 32, 125, 32, 125, 32, 125, 32, 125, 32, 93, 32,};
//		String json = new String(charArray);
		
//		String json="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]"; //this one wrong in setInput

//		String json = " \"age\" : 22 ";

//		String json = "\" : [";
//		String json="{ : } : [ ] 1 } : , true : ] } [ } false [ ";  // this one can run

		StringBuilder contentBuilder = new StringBuilder();
		String filename = "/home/lmx/Documents/GitHub/jpf8/jpf-symbc/inputs/fastjson.json";

		try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
			{
				contentBuilder.append(sCurrentLine).append("\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		char[] data = contentBuilder.toString().toCharArray();

//		json = FileUtils.readString();

//		json = SymbolicString.makeConcolicString(json);
		for (int i = 0; i < data.length; i++) {
			data[i] = Debug.makeSymbolicChar(""+data[i]);
		}

		
		testFastjsonDev test = new testFastjsonDev();
		
		System.out.println(json);
		
//		JSONScanner lexer = new JSONScanner(json, JSON.DEFAULT_PARSER_FEATURE); //generate constraint sym_0 !=62357
//		char first = lexer.getCurrent();
//		if (first == '{'){ //generate constraint ASCII 123!=Sym_0
//			test.s();
//    	};
//		DefaultJSONParser parser = new DefaultJSONParser(lexer);
//        parser.parse();
//        char last = lexer.charAt(lexer.getBufferPosition() - 1);  // charAt make the value symbolic!
//        if (last == '}'){ //generate constraint ASCII 125!=Sym_12
//        	test.e();
//    	};
//        parser.close();

//		JSON.toJSONString(JSON.parseObject(json, Object.class));
		res = JSON.parseObject(data.toString(), Object.class);


	}

	static void stage3(){
		JSON.toJSONString(res);

//		JSONScanner lexer = new JSONScanner(json, JSON.DEFAULT_PARSER_FEATURE);
//		DefaultJSONParser parser = new DefaultJSONParser(lexer);
//        parser.parse();
//		parser.close();
	}
}
