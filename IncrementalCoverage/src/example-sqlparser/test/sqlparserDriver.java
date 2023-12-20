package test;

import coverage.SubjectExecutor;
import sql.ParseException;
import sql.Parser;

import java.io.IOException;

public class sqlparserDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "sql";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new sqlparserDriver().wrapExecute();
    }


    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
        }catch (Throwable e){}
    }

    void stage1_2(String s) throws Exception {
        //		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);
        String rst = parse(s);
    }

    private String parse(String input) throws Exception {
        String rst = "";
        rst = Parser.parse(input);

        return rst;
    }
}
