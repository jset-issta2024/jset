package test;

import com.antlr.java.JavaLexer;
import com.antlr.java.JavaRecognizer;

import java.io.StringReader;

public class TestAntlrjava {
    public static void main(String[] args) {
        //String s= "4 / (5+6) ++";
        start();
    }

    public static void start(){
        String s= "module MainModule {typedef long mylong;};";
        //String s = FileUtils.readString("inputs/javaparser.java");
        //System.out.println(System.getProperty("java.classpath"));

        StringReader sr = new StringReader(s);
        System.out.println(s);
        try {
            JavaLexer lexer= new JavaLexer(sr);
            JavaRecognizer parser = new JavaRecognizer(lexer);
            parser.compilationUnit();


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
