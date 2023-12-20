package test;

import html.grammarparser.MyNewGrammar;
import html.grammarparser.ParseException;

import java.io.StringReader;

public class TestHtmlparser2 {
    public static void main(String[] args) throws ParseException {

        String s="<!doctype html> <!doctype html>"
                + "<html><head><title>First parse</title></head>"
                + "<!-- Region: {view-rendered} 	 Module: {view-rendered}  -->"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        char[] data = s.toCharArray();
        start(data);
    }

    static void start(char[] data) throws ParseException {

//        s = SymbolicString.makeConcolicString(s);
//        System.out.println(s);

        try {
            String s = data.toString();
            StringReader reader = new StringReader(s);
            new MyNewGrammar(reader).exp();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
