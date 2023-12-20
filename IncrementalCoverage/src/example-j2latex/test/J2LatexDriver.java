package test;

import com.github.situx.compiler.parser.C1;
import com.github.situx.compiler.parser.NullWriter;
import com.github.situx.compiler.parser.ParseException;
import com.github.situx.compiler.treej.Node;
import com.github.situx.compiler.visitorj.AsHTML;
import com.github.situx.compiler.visitorj.AsLatex;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

public class J2LatexDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.github.situx.compiler";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new J2LatexDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
            stage3();
        }catch (Throwable e){}
    }

    private Node i;
    private void stage1_2(String s) {
        StringReader reader = new StringReader(s);

        C1 parser = new C1(reader);
        try {
            i = parser.program();
        } catch (ParseException e) {
        }
    }

    public void stage3(){
        Writer w = new NullWriter();
        AsHTML html = new AsHTML(w,"null.file", 4,0);
        i.welcome(html);
        Writer wL = new NullWriter();
        AsLatex latex = new AsLatex(wL,"null.file",4,0);
        i.welcome(latex);
    }
}
