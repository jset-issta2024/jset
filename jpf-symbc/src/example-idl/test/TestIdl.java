package test;

import com.antlr.idl.IDLLexer;
import com.antlr.idl.IDLParser;
import java.io.StringReader;

public class TestIdl {
    public static void main(String[] args) {
        //String s= "4 / (5+6) ++";
        start();
    }

    public static void start(){
        String s= "module MainModule {typedef long mylong;};";
        StringReader sr = new StringReader(s);
        System.out.println(s);
        try {
            IDLLexer lexer= new IDLLexer(sr);
            IDLParser parser = new IDLParser(lexer);
            parser.specification();

//            antlr.CommonAST ast = (antlr.CommonAST)parser.getAST();
//            if (ast != null) {
//                System.out.println(ast.toStringList());
//            } else {
//                System.out.println("null AST");
//            }
        } catch(Exception e) {
            System.err.println("exception: "+e);
        }
    }
}
