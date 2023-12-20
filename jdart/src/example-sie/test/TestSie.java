package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.*;  

import sie.Expr;
import sie.Parser;
import sie.Scanner;

public class TestSie {  

	public static void main(String args[]) throws Throwable {
		String str = "1+1";
		char[] data = str.toCharArray();
		start(data);
	} // main

	public static void start(char[] data){
		String str = data.toString();
		try{


//		if(SieConfig.SYMB_FLAG && SieConfig.TOKEN_SYMB)
//			Debug.setInput(str, "10");

			InputStream   inputStream   =   new   ByteArrayInputStream(str.getBytes());

			Parser parser = new Parser(new Scanner(
					new DataInputStream(inputStream)));
			try {
				parser.run( );
//			Debug.SetGlobalParsed();
				Expr.eval();
			} catch (Exception e) {
//			Debug.SetGlobalParsed();
				// TODO Auto-generated catch block
				//			Debug.printCurrentPC();
			}
			System.out.println("Done");

//		Debug.printCurrentToken2PC();
//		Debug.printCurrentPC();
//		Debug.printConstraintTree();

		}catch (Exception e){

		}
		if(SieConfig.SYMB_FLAG && SieConfig.TOKEN_SYMB){
//			Debug.splitPC();
//			Debug.ExitIfBenchmark();
		}

	}


} // class Test  

