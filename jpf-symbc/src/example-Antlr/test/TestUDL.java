package test;

import gov.nasa.jpf.symbc.Debug;
import org.antlr.runtime.*;
import test.antlr.UDLLexer;
import test.antlr.UDLParser;

public class TestUDL {
	
	
    public static void main(String args[]) throws Exception {
    	start();
    }
    
    public static void start() {

        String s = "{ header : 3 }";
        char[] data;
        data=s.toCharArray();
        for(int i=0;i<s.length();i++){
            if((data[i])!=' '){
                data[i] = Debug.makeSymbolicChar(""+data[i]);
            }
        }

        UDLLexer lex = new UDLLexer(new ANTLRStringStream(data.toString())); // make the element of string symbolic in ANTLRStringStream! element data-->char[]!
//        UDLLexer lex = new UDLLexer(new ANTLRStringStream("{ header : 3 }")); // make the element of string symbolic in ANTLRStringStream! element data-->char[]!
      //  UDLLexer lex = new UDLLexer(new ANTLRStringStream("{ feader ::3 }"));
    	//UDLLexer lex=new UDLLexer(new ANTLRStringStream("{ h }"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        UDLParser g = new UDLParser(tokens);
        try {
            //g.uid();
        	g.tableUid();
        } catch (RecognitionException e) {
        	System.out.println("stack trace of eception!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
}