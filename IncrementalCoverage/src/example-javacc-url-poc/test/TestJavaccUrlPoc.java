package test;

import io.mikael.poc.ParseException;
import io.mikael.poc.UrlParser;

import java.io.StringReader;

public class TestJavaccUrlPoc {
	public static void main(String[] args){
		start();
	}
	public static void start(){
	    String s= "http://a_host:12345/a_path?k1=v1&k2=v2#/a_fragment?f1_p=f1_v&f2_p=f2_v";

//	    s = SymbolicString.makeConcolicString(s);
	    StringReader sr = new StringReader(s);
//		System.out.println(s);
		try {
			UrlParser.Result r = UrlParser.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
}
