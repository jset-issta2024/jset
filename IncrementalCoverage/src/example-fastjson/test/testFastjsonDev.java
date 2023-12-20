package test;

import com.alibaba.fastjson.JSON;

//import coverage.Utils;

public class testFastjsonDev {

//	static String json = "{ ,\"age\": 22 , \"flag\" : [ true , false ] }";

//	static String json = "{ \"age\" : 22 , \"flag\" : [ true , false ] }";

	static String json = "{ \"age\" : 22 ,\"flag\" : [ true , false ] }";

	public static void main(String[] args) throws Exception {
//		char[] data = {34, 92, 120, 115, };  // java.lang.ArrayIndexOutOfBoundsException: 115
//		char[] data = {91, 39, 33, 39, 123, 44, 44, 91, 45, 48, 48, 66, 45, 48, 48, 66, 44, 45, 48, 48, 66, 39, 33, 39, 44, 125, };
//		json = new String(data);


//		Utils.PackageName = "src/example-fastjson-dev/";
//		List<String> S_list= Utils.ReadFromFile();
//		for (String si : S_list)
//		{
//			JSONLexerBase.srcIdx = 0;
//			System.out.println(si);
//			json = si;
//			try {
//				start();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

//		System.out.println(json);
		start();

	}
	public void s(){};
	public void e(){};
	public static void start() throws Exception
	{
		char[] charArray = {91, 32, 32, 44, 32, 123, 32, 123, 32, 123, 32, 123, 32, 91, 32, 32, 44, 32, 93, 32, 57, 57, 48, 57, 45, 48, 57, 45, 50, 53, 32, 93, 32, 125, 32, 125, 32, 125, 32, 125, 32, 93, 32,};
//		String json = new String(charArray);
		
//		String json="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]"; //this one wrong in setInput

//		String json="{ : } : [ ] 1 } : , true : ] } [ } false [ ";  // this one can run
		
		JSON.toJSONString(JSON.parseObject(json, Object.class));

//		testFastjsonDev test = new testFastjsonDev();
//
//		JSONScanner lexer = new JSONScanner(json, JSON.DEFAULT_PARSER_FEATURE); //generate constraint sym_0 !=62357
//		char first = lexer.getCurrent();
//		if (first == '{'){ //generate constraint ASCII 123!=Sym_0
//			test.s();
//    	};
//		DefaultJSONParser parser = new DefaultJSONParser(lexer);
//        parser.parse();
////        char last = lexer.charAt(lexer.getBufferPosition() - 1);  // charAt make the value symbolic!
////        if (last == '}'){ //generate constraint ASCII 125!=Sym_12
////        	test.e();
////    	};
//        parser.close();
	}

}
