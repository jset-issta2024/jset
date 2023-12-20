import coverage.SubjectExecutor;
import kr.ac.cau.popl.gauthierplm.HtmlGrammar;
import kr.ac.cau.popl.gauthierplm.ParseException;
import kr.ac.cau.popl.gauthierplm.TagRecord;

import java.io.IOException;
import java.io.StringReader;

public class TestHtmlGramParser extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "kr.ac.cau.popl.gauthierplm";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestHtmlGramParser().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
        }catch (Throwable throwable){}
    }

    public void stage1_2(String s) throws ParseException {
        System.out.println(s);

        TagRecord tr= new TagRecord();
        HtmlGrammar parser = new HtmlGrammar(new StringReader(s));
        HtmlGrammar.tr = tr;
        parser.file();
    }
}
