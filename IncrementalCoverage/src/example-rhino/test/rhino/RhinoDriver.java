package test.rhino;

import coverage.SubjectExecutor;
import mozilla.javascript.IRFactory;
import mozilla.javascript.Parser;
import mozilla.javascript.Source;
import mozilla.javascript.TokenStream;

import java.io.IOException;
import java.io.StringReader;

public class RhinoDriver extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "mozilla";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }

        new RhinoDriver().wrapExecute();
    }

    @Override
    public void execute(String input) {
        try {
            running(input);
        }catch (Exception e){}
    }

    private void running(String src) {
        StringReader sr = new StringReader(src);
        //StringReader sr = new StringReader("{1:2}");
        TokenStream ts = new TokenStream(sr, null, 1);

        try {
            //Context.enter();
            IRFactory irf = new IRFactory(ts);
            Parser p = new Parser(irf);
//            Object o = p.parse(ts);
            Object o = p.primaryExpr(ts, new Source());
//            Iterator i = null;
//            String s = ((Node)o).toString();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
        }
    }
}
