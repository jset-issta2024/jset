package test;

import gov.nasa.jpf.symbc.Debug;
import parser.JSON;
import parser.ParseException;
import parser.TokenMgrError;

import java.io.StringReader;

public class TestJsonRaupachz {
	public static void main(String[] args) throws ParseException {
		start();
	}
	public static void start() throws ParseException {
		String s = "{\"account\": {\"name\": \"ilya\", " + 
        "\"created_at\": \"2011/07/20 23:11:17 +0800\", " +
        "\"updated_at\": \"2011/10/03 13:16:06 +0800\", " +
        "\"third_level_domain\": \"ilya\", " +
        "\"owner_id\": 2,\"id\": 2,\"suspended\": false, " +
        "\"time_zone\": \"International Date Line West\",\"plan_id\": 24}}";

//		s = FileUtils.readString("inputs/JsonRaupachz.input");

	    s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader sr = new StringReader(s);
			JSON json = new JSON(sr);
			Object obj = json.parse();
		} catch (TokenMgrError e) {

		}

	}
}
