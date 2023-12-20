package test.awkparser;

import awk.ParseException;
import awk.SENTENCE;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;

public class awkDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "awk";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new awkDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
        }catch (Throwable e){}
    }

    public void stage1_2(String s) throws ParseException {
        //	  s = SymbolicString.makeConcolicString(s);
//        System.out.println(s);

//	  InputStream  inputStream = new ByteArrayInputStream(s.getBytes());
        SENTENCE sentence = new SENTENCE(new StringReader(s));
        sentence.Parse();
    }
}
