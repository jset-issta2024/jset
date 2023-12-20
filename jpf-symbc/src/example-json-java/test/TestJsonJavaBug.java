package test;

import org.json.JSONObject;

public class TestJsonJavaBug {
	//123 91 49 44 50 44 51 93 58 102 115 102 44 34 115 34 58 102 59
	public static char[] bug1 ={(int)123,(int)91 ,(int)45 ,(int)44};// ,(int)45 ,(int)44 ,(int)130 ,(int)93 ,(int)0 ,(int)155 ,(int)32 ,(int)32 ,(int)44 ,(int)34 ,(int)2 ,(int)34 ,(int)32 ,(int)59, (int)40 };
	//sym_cnf_char1:123 sym_cnf_char2:91 sym_cnf_char3:128 sym_cnf_char4:44 sym_cnf_char5:45 sym_cnf_char6:44 sym_cnf_char7:130 sym_cnf_char8:93 sym_cnf_char9:0 
	public static void main(String[] args){
		String s = new String(bug1);
		System.out.println(String.class.getClassLoader());
		new JSONObject(s);
	}
}
