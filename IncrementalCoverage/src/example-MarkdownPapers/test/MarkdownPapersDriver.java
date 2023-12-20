package test;

import coverage.SubjectExecutor;
import org.tautua.markdownpapers.HtmlEmitter;
import org.tautua.markdownpapers.ast.Document;
import org.tautua.markdownpapers.ast.Visitor;
import org.tautua.markdownpapers.parser.ParseException;
import org.tautua.markdownpapers.parser.Parser;

import java.io.IOException;
import java.io.StringReader;

public class MarkdownPapersDriver extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.tautua.markdownpapers";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new MarkdownPapersDriver().wrapExecute();

//        inputFileName = "src/example-MarkdownPapers/TestMDPParser-TokenSymb-genTokenString-TokenLengthBound3.saveInput";
//        new MarkdownPapersDriver().wrapExecute();
    }

    @Override
    public void execute(String input) {
        try {
            running(input);
        }catch (Throwable e){}
    }

    void running(String s) throws ParseException {
        StringReader in = new StringReader(s);
        StringBuilder out=new StringBuilder();
        Visitor v = new HtmlEmitter(out);
        Parser parser = new Parser(in);
        Document doc = parser.parse();
        doc.accept(v);
    }

}
