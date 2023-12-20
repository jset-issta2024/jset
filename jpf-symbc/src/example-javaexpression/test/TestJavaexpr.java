package test;


import com.antlr.javaexpression.JavaExpressionLexer;
import com.antlr.javaexpression.JavaExpressionParser;

import java.io.StringReader;

public class TestJavaexpr {
    public static void main(String[] args) {
        //String s= "4 / (5+6) ++";
        start();
    }

    public static void start(){
        String s= "1+1==2" ;

        StringReader sr = new StringReader(s);
        System.out.println(s);
        try {
            JavaExpressionLexer lexer= new JavaExpressionLexer(sr);
            JavaExpressionParser parser = new JavaExpressionParser(lexer);
            parser.statement();
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
