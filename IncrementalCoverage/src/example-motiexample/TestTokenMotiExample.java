import coverage.SubjectExecutor;
import de.fzi.XPath.Parser.ParseException;
import de.fzi.XPath.Parser.XPathParser;
import moti.TokenMotiExampleV2;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class TestTokenMotiExample extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "moti";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestTokenMotiExample().wrapExecute();

//        inputFileName = "src/example-sixpath/TestSixpathParser-TokenSymb-genTokenString-TokenLengthBound3.saveInput";
//        new SixpathDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable{
        TokenMotiExampleV2.input = input;

        try {
            TokenMotiExampleV2.start();
        }catch (Exception e){

        }

//        Reader stream = new StringReader(input);
//        XPathParser xpp = new XPathParser(stream);
//        xpp.temp(); // can generate constraints! contained sensitive e1!
//        xpp.disable_tracing();
//
//        try
//        {
//            xpp.XPath(); // can generate constraints! contained sensitive e2!
//            //System.out.println("Parsed expression: " + expr);
//        }
//        catch (ParseException e) {
//        }
    }
}
