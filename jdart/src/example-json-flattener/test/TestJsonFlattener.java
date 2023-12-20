package test;

import java.io.IOException;
import java.util.Map;

import com.github.wnameless.json.flattener.JsonFlattener;

public class TestJsonFlattener {
	public static void main(String[] args) throws Exception{
		String s = "{ \"a\" : { \"b\" : 1, \"c\": null, \"d\": [false, true] }, \"e\": \"f\", \"g\":2.3 }";
		start(s.toCharArray());
	}

	public static void start(char[] data) throws IOException {

//		String json = "{ \"a\" : { \"b\" : 1, \"c\": null, \"d\": [false, true] }, \"e\": \"f\", \"g\":2.3 }";
		//Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(json);

		System.out.println("start with"+data.toString());
		Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(data);

		//System.out.println(flattenJson);
//		String jsonStr = JsonFlattener.flatten(str);
//		System.out.println(jsonStr);

	}
}
