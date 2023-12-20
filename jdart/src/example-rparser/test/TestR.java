package test;

import com.garcel.parser.r.autogen.R;

import java.io.StringReader;

public class TestR {
    public static void main(String[] args){
        String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";
        char[] data = s.toCharArray();
        start(data);
    }
    public static void start(char[] data){
        String s=data.toString();
        StringReader sr = new StringReader(s);
        System.out.println(s);
        R parser = new R(sr);
        try {

            parser.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
