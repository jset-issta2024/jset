package test;

import argo.jdom.JdomParser;
import argo.saj.InvalidSyntaxException;

public class TestArgoBug {
	public static char[] bug1 ={(int)123,(int)34 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)2 ,(int)34 ,(int)58 ,(int)123 ,(int)34 ,(int)2 ,(int)34 ,(int)58 ,(int)123 ,(int)34 ,(int)2 ,(int)92 ,(int)117};
	public static char[] bug  = {(int)123,(int)34,(int)58 ,(int)34 ,(int)58 ,(int)123 ,(int)34 ,(int)2 ,(int)34 ,(int)58 ,(int)123 ,(int)34 ,(int)2 ,(int)92 ,(int)117};
	public static void main(String[] args) throws InvalidSyntaxException{
		String s = new String(bug);
		String t = "[\"f\"\\u";
		new JdomParser().parse(s);
	}
}
