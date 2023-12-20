package test;

import com.antlr.exprast.ExprLexer;
import com.antlr.exprast.ExprParser;

import java.io.StringReader;

public class TestExpr {
    public static void main(String[] args) {
        //String s= "4 / (5+6) ++";
        start();
    }

    public static void start(){
        String s= "4 / (5+6) ++";
        //String s = FileUtils.readString("inputs/expr.input");
        //System.out.println(System.getProperty("java.classpath"));

        StringReader sr = new StringReader(s);
        System.out.println(s);
        try {
            ExprLexer lexer = new ExprLexer(sr);
            ExprParser parser = new ExprParser(lexer);
            parser.expr();


//            antlr.CommonAST ast = (antlr.CommonAST)parser.getAST();
//            if (ast != null) {
//                System.out.println(ast.toStringList());
//            } else {
//                System.out.println("null AST");
//            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
