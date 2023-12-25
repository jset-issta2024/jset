package demoLoop;

public class testLoop {

    public static void main(String[] args) {
        int x = secret();
        int y = 6;
        System.out.println(y);
        int t = 3;
        y = foo(x) + t;
        System.out.println(y);
        testLoop(y);
    }

    private static int secret() {
        return 8;
    }

    private static int foo(int p) {
        return p;
    }

    private static void testLoop(int num) {
        // num is taint
        int z = 0;
        for (int l = 0; l < num; l++) {
            z += l;
        }
        if (z > 5) {
            System.out.println("the res = " + z);
        }
    }

}

