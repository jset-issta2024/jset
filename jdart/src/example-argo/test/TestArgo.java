package test;

import argo.jdom.JdomParser;
import argo.saj.InvalidSyntaxException;

public class TestArgo {
	public static void main(String[] args) throws Exception{
		String s = "[true, false]";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws Exception{

		try {
			String str = data.toString();
			new JdomParser().parse(str);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}

	}
}
