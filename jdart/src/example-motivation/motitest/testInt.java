package motitest;

//import gov.nasa.jpf.jdart.Debug;

import java.util.HashMap;
import java.util.Map;

public class testInt {

    public static class Data {
        private int x;
        private final int y;

        public Data(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }


    private int i64;

    public testInt(int i) {
        if(i > 10) {
            System.err.println("\n-------- In <Init>(int)! i > 10");
        } else {
            System.err.println("\n-------- In <Init>(int! i <= 10");
        }
        this.i64 = 64;
    }

    public int foo(int i) {
        System.err.println("\n-------- In foo! Parameter = " + i);

        assert i64 > 0;
        if (i > i64) {
            System.err.printf("%s\n", "i > 64");
            if ( 5 * i <= 325) {
                System.err.printf("%s\n", "5 * i <= 325");
                if (i != 65) {
                    System.err.printf("%s\n", "i != 65");
                } else {
                    System.err.printf("%s\n", "i == 65");
                }
                return i + 3;
            } else if ( i != 66 ) {
                System.err.printf("%s\n", "5 * i > 325 && i != 66");
                return i + 5;
            } else {
                System.err.printf("%s\n", "5 * i > 325 && i == 66");
                assert false : "foo failed!";
            }
        } else if ((i & 7) == 7) {
            System.err.printf("%s\n", "i & 5 == 5");
        }
        System.err.printf("%s\n", "i <= 64");

        this.i64 = i;

        return i;
    }

    public double bar(double d) {
        System.err.println("\n-------- In bar! Parameter = " + d);

        if (d >= 3.141) {
            System.err.printf("%s\n", "d >= 3.141");
        }

        double as = Math.asin(d);
        System.err.println("asin returned " + as);
        if(Double.isNaN(as))
            System.err.println("-------- NaN");//throw new ArithmeticException("NaN");
        return as;
    }

    public static void main(String[] args) {
        System.out.println("-------- In main!");
        motitest.testInt inst = new motitest.testInt(64);
        try {
            inst.foo(-1024);
        } catch(AssertionError ex) {}
        inst.bar(1.732);
    }

}
