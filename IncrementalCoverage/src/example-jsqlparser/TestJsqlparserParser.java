import coverage.SubjectExecutor;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

import java.io.IOException;

public class TestJsqlparserParser extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "net.sf.jsqlparser";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestJsqlparserParser().wrapExecute();

//        inputFileName = "src/example-jsqlparser/TestJsqlparserParser-TokenSymb-genTokenString-TokenLengthBound3.saveInput";
//        new JsqlparserDriver().wrapExecute();
    }

    @Override
    public void execute(String input) {
        try {
            CCJSqlParserUtil.parseStatements(input);
        } catch (Throwable e) {
        }
    }
}
