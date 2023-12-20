package test;

import com.bayoog.golang.Go;

import java.io.StringReader;

public class TestGo {
    public static void main(String[] args){
        start();
    }
    public static void start(){
        String s = "";


        StringReader sr = new StringReader(s);
        System.out.println(s);
        Go parser = new Go(sr);
        try {
            parser.one_line();

        } catch (Exception e) {
            e.printStackTrace();
        }


//		String s= "(+ ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) ( + (+ (+ 1 1 ) (- 5 8 )) (+ (+ 1 1 ) (- 5 8 )) ) )";


    }
}
