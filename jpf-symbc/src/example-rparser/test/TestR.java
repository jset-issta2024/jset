package test;

import com.garcel.parser.r.autogen.R;

import java.io.StringReader;

public class TestR {
    public static void main(String[] args){
        start();
    }
    public static void start(){
//        String s="";
        String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";

        StringReader sr = new StringReader(s);
        System.out.println(s);
        R parser = new R(sr);
        try {

            parser.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }


//		String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";


    }


}
