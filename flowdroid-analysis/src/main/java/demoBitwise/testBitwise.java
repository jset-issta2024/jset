package demoBitwise;

public class testBitwise {

    public static void main(String[] args) {

        int x = 2;
        int t = 3;
        int y = x + t;
        int z = 6;

        y = makeConcolicInt(y);
        z = makeConcolicInt(z);
        System.out.println(y);
        System.out.println(z);

        int num1 = y;
        int num2 = z;

        //        //0101
//        int num1 = 5;
//        //1001
//        int num2 = 9;
//        0001, result is 1

        // num1 is taint
        // num2 is no taint

        int res1 = num1 & num2;
        int res12 = res1;
        System.out.println(res12);
        if (res1 > 0) {
            System.out.println(res1);
        }
        int res2 = num1 | num2;
        if (res2 > 0) {
            System.out.println(res2);
        }

        int res3 = num1 ^ num2;
        if (res3 > 0) {
            System.out.println(res3);
        }

        int res4 = ~num1;
        if (res4 > 0) {
            System.out.println(res4);
        }
        //or, result is 1101, print result is 3

        // x or, result is 1100, print result is 12
        System.out.println();
        // not, result is 11111111111111111111111111111010, print result is -6
        System.out.println();

        //1111;
        int i = 15;
        // right, result is 0011, print result is 3
        System.out.println(i >> 2);
        // left, result is 111100, print result is 60
        System.out.println(i << 2);

        // right
        int j = -10;
        System.out.println(j >>> 2);


        int t1 = 100;
        long t2 = 100;
        int t3 = t1 >>> t2;
        System.out.println(t3);

        long t11 = 100;
        long t12 = 100;
        long t13 = t11 >>> t12;
        System.out.println(t13);

//        long t21 = 100;
//        int t22 = 100;
//        long t23 = t21 >>> t22;
//        System.out.println(t23);


//        testBitwiseOperator(y,z);

    }

    private static int makeConcolicInt(int a) {
        return a;
    }

    private static void testBitwiseOperator(int num1, int num2 ) {
    }

}