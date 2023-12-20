package test;

import interpreter.ParseException;
import interpreter.Parser;
import interpreter.Tokenizer;
import interpreter.TokenizerException;

public class TestInterpreter {

    public static void main(String[] args) throws TokenizerException, ParseException {

        String src = "AbC ( 123 ) ";
        char[] data = src.toCharArray();
        start(data);

    }

    public static void start(char[] data) throws TokenizerException, ParseException {
    	
//    	src = SymbolicString.makeConcolicString(src);
//    	System.out.println(src);
//        char[] data=src.toCharArray();
//        for(int i=0;i<src.length();i++){
//            if(((char)data[i])!=' '){
//                data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//                System.out.print(""+(int)data[i]+", ");
//            }
//        }

        try {
            Tokenizer tokenizer = new Tokenizer(data.toString());
            Parser parser = new Parser(tokenizer);
            parser.parse();
            if (parser.semanticCheck()){
                System.out.println("Semantic success!!!");
            }else
                System.out.println("Semantic fail!!!");
        } catch (TokenizerException e) {
            e.printStackTrace();
        }

    }

}
