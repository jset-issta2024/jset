package test;


import antlr.TokenStreamHiddenTokenFilter;
import com.antlr.instr.InstrLexer;
import com.antlr.instr.InstrParser;

import java.io.StringReader;

public class TestInstr {
    public static void main(String[] args) {
        //String s= "4 / (5+6) ++";
        start();
    }

    public static void start(){
        String s= "a = 2;" ;

        StringReader sr = new StringReader(s);
        System.out.println(s);
        try {
            InstrLexer lexer= new InstrLexer(sr);
            lexer.setTokenObjectClass("antlr.CommonHiddenStreamToken");
            TokenStreamHiddenTokenFilter filter = new TokenStreamHiddenTokenFilter(lexer);
            filter.hide(InstrParser.WS);
            filter.hide(InstrParser.SL_COMMENT);
            InstrParser parser = new InstrParser(filter);
            parser.setASTNodeClass("antlr.CommonASTWithHiddenTokens");
            parser.slist();

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
