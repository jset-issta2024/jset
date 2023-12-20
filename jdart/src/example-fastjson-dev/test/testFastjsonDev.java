package test;

import com.alibaba.fastjson.JSON;

public class testFastjsonDev {

	static String json = "{ \"age\" : 22 ,\"flag\" : [ true , false ] , \" temp \" : "
			+ "[0,{\\\"1\\\":{\\\"2\\\":{\\\"3\\\":{\\\"4\\\":[5,{\\\"6\\\":7}]}}}}]"
			+ " \" temp2 \": [0,{\\\"1\\\":{\\\"2\\\":{\\\"3\\\":{\\\"4\\\":[5,{\\\"6\\\":7}]}}}}] "
			+ " \" temp3 \": [0,{\\\"1\\\":{\\\"2\\\":{\\\"3\\\":{\\\"4\\\":[5,{\\\"6\\\":7}]}}}}] " +
			"}";
	static Object res;

	public static void main(String[] args) throws Exception {

		char[] data = json.toCharArray();
		start(data);

	}

	public static void start(char[] data) {

		String jsonSym = new String(data);
		res = JSON.parseObject(jsonSym, Object.class);
		JSON.toJSONString(res);

	}

}
