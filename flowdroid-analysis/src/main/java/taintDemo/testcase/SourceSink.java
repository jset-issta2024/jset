package taintDemo.testcase;

import java.io.StringReader;

public class SourceSink {

    static String source() {
        return new String();
    }

    static void sink() {
    }

    public static void sink(String s) {
    }

    public static void sink(StringReader s) {
    }

    static void sink(String s, int n) {
    }

    static void sink(String s1, String s2) {
    }

    static String sourceAndSink(String s1, String s2) {
        return new String();
    }
}
