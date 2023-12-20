package org.mwnorman.json.test;

import coverage.SubjectExecutor;
import org.mwnorman.json.JSONParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class jsonParserMwnormanDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.mwnorman.json";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }

        new jsonParserMwnormanDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
        }catch (Throwable e){}
    }

    public void stage1_2(String s){
        StringReader reader = new StringReader(s);
        JSONParser parser = new JSONParser(reader);
        try {
            parser.parse();
        } catch (org.mwnorman.json.ParseException e) {
            // TODO Auto-generated catch block
//		e.printStackTrace();
        }
    }
}
