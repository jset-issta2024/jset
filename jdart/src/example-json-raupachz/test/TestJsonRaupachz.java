package test;

import parser.JSON;
import parser.ParseException;
import parser.TokenMgrError;

import java.io.StringReader;

public class TestJsonRaupachz {
	public static void main(String[] args){
		String s = "{\"account\": {\"name\": \"ilya\", " +
				"\"created_at\": \"2011/07/20 23:11:17 +0800\", " +
				"\"updated_at\": \"2011/10/03 13:16:06 +0800\", " +
				"\"third_level_domain\": \"ilya\", " +
				"\"owner_id\": 2,\"id\": 2,\"suspended\": false, " +
				"\"time_zone\": \"International Date Line West\",\"plan_id\": 24}}";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){

//		s = FileUtils.readString("inputs/JsonRaupachz.input");
//	    s = SymbolicString.makeConcolicString(s);

	    String s = data.toString();
		StringReader sr = new StringReader(s);
		System.out.println(s);
		JSON json = new JSON(sr);
        try {
			Object obj = json.parse();
		} catch (TokenMgrError | ParseException e) {
			e.printStackTrace();
		}
	}
}
