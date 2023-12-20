package test;

import java.io.StringReader;
import com.cloudability.bling.ast.*;

public class TestBling {
	public static void main(String[] args){
		String s= "- 2.5e2 / 1.25e-2 - 2.5e2 / 1.25e-2 - 2.5e2 / 1.25e-2";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){
		try {
//			String s = FileUtils.readString("inputs/Bling.input");
			String s = data.toString();
//			s = SymbolicString.makeConcolicString(s);
			StringReader sr = new StringReader(s);
			System.out.println(s);
			BlingParser parser = new BlingParser(sr);
			Expression e = parser.parse();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		}

	}
}
