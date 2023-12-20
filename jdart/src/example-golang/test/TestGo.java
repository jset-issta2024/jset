package test;

import com.bayoog.golang.Go;
import com.bayoog.golang.ParseException;
import com.bayoog.golang.TokenMgrError;

import java.io.StringReader;

public class TestGo {
    public static void main(String[] args){
        String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";
        char[] data = s.toCharArray();
        start(data);
    }
    public static void start(char[] data){
        String s = data.toString();

        StringReader sr = new StringReader(s);
        System.out.println(s);
        Go parser = new Go(sr);
        try {
            parser.one_line();

        } catch (TokenMgrError | ParseException e) {
            e.printStackTrace();
        }

    }
}
