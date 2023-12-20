import coverage.SubjectExecutor;
import nl.bigo.curta.Curta;
import nl.bigo.curta.ParseException;

import java.io.IOException;

public class TestCurtaParser extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "nl.bigo.curta";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestCurtaParser().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
            stage3();
        }catch (Throwable e){}
    }

    Curta curta = new Curta();

    public void stage1_2(String s) throws ParseException {
        //		s = SymbolicString.makeConcolicString(s);
        curta = new Curta();
        curta.stage1_2(s);
    }

    void stage3() {
        curta.stage3();
    }
}
