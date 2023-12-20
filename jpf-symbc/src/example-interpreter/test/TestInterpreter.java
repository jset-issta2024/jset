package test;

//import gov.nasa.jpf.jdart.Debug;
import gov.nasa.jpf.symbc.Debug;
import interpreter.Parser;
import interpreter.Tokenizer;

public class TestInterpreter {
    public static String src = "AbC ( 123 ) ";
//    static String src = "1";
    public static void start() throws Exception {
    	
//    	src = SymbolicString.makeConcolicString(src);
    	
    	System.out.println(src);

        char[] data=src.toCharArray();
        for(int i=0;i<src.length();i++){
            if(((char)data[i])!=' '){
//                data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
                data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
                System.out.print(""+(int)data[i]+", ");
            }
        }

        Tokenizer tokenizer = new Tokenizer(data.toString());
        Parser parser = new Parser(tokenizer);


        parser.parse();
        
//        Debug.setValidInputTrue();
        if (parser.semanticCheck()){
            System.out.println("Semantic success!!!");
        }else
            System.out.println("Semantic fail!!!");
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
