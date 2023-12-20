package test;

import com.cloudability.bling.ast.BlingParser;
import com.cloudability.bling.ast.Expression;

import java.io.StringReader;

public class TestBling {
	public static void main(String[] args){
		start();
	}
	public static void start(){
	    String s= "2.5e2 / 1.25e-2";

//	    s = SymbolicString.makeConcolicString(s);
	    StringReader sr = new StringReader(s);
//		System.out.println(s);
		BlingParser parser = new BlingParser(sr);
    	Expression e = parser.parse();
	}
}
