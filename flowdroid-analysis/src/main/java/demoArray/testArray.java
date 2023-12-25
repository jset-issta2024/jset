package demoArray;

import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;
import taintDemo.testcase.SourceSink;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class testArray {
    public static void main(String[] args) throws IOException {

        String input = "Hi, FlowDroid!";
        char[] in = input.toCharArray();
        in[0] = makeSymbolicChar(in[0]);

        String a = new String(in);
        System.out.print(in[0]);
//        SourceSink.sink(a);

        byte[] b = a.getBytes(StandardCharsets.UTF_8);
        String c = new String(b);
        System.out.print(in[0]);
//        SourceSink.sink(c);

//        StringReader d = new StringReader(a);
//        String e = IOUtils.toString(d);
//        System.out.print(in[0]);
//        SourceSink.sink(e);

        CharSequence cs = a;
        final StringBuilder sb = new StringBuilder(cs.length());
        sb.append(cs);
        String f = sb.toString();
        System.out.print(in[0]);
        SourceSink.sink(f);

        if (in[0] > 0) {
            System.out.println("taint succ");
        }

//        for (int i = 0; i < input.length(); i++) {
//            in[i] = makeSymbolicChar(in[i]);
//        }
//        char a = makeSymbolicChar('b');
//        System.out.println(a);
//        String b = makeSymbolicString("Hi!FlowDroid!");
//        System.out.println(b);
//        for (int i = 0; i < input.length(); i++) {
//            System.out.print(in[i]);
//        }


//        test(in);
//        // added by czb
//        int i = makeSymbolic("i", 0);
//        test(i);

    }

//    private static void test(char[] in) {
//        for (int i = 0; i < 1; i++) {
//            System.out.println(in[i]+1);
//        }
//    }

//    // added by czb
//    public static int test(int i) {
//        int[] a = {0, 1, 2};
//        if (a[i] > 1) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }

//    <demoArray.testArray: char makeSymbolicChar(char)>
    private static char makeSymbolicChar(char c) {
        char temp = (char) (c + 1);
        return temp;
    }

//    <demoArray.testArray: java.lang.String makeSymbolicString(java.lang.String)>
//    private static String makeSymbolicString(String c) {
//        char[] temp = new char[c.length()];
//        for (int i = 0; i < c.length(); i++) {
//            temp[i] = (char) (c.indexOf(i) + (char)1);
//        }
//        return temp.toString();
//    }
}
