package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.*;

import gov.nasa.jpf.symbc.Debug;
import sie.Expr;
import sie.Parser;
import sie.Scanner;

public class TestSie {  

	public static void main(String args[]) throws Throwable {  
		start();
	} // main

	public static void start(){
		try{
			driver();
		}catch (Exception e){

		}
		if(SieConfig.SYMB_FLAG && SieConfig.TOKEN_SYMB){
//			Debug.splitPC();
//			Debug.ExitIfBenchmark();
		}

	}

	public static void driver () {
		String str = "1+1";

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
	}
	
	public static void start2(){
		char c=1;
//		char a=Debug.makeConcolicChar("symb_cnf_char"+0, ""+(int)c);
		char a= Debug.makeSymbolicChar(""+(int)c);
		
		int k=1;
		if(a==1)
			k=0;
//		try{
//			test(a,k);
//		}catch (Exception e){
//			
//		}
		
//		Debug.printCurrentPC();
//		Debug.printConstraintTree();
		
		
	}
	
	static void test(char a,int k) throws Exception{
		if(a=='1')
			k=0;
		throw new Exception();
	}

} // class Test  

