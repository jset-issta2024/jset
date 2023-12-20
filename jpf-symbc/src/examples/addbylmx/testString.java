package addbylmx;

import gov.nasa.jpf.symbc.Debug;

public class testString {
    public static void main(String[] args) {
        String string = "test";
        System.out.println("execution 1");
        start(string.toCharArray());
        System.out.println("execution 3");
    }

    private static void start(char[] input) {


        for(int i = 0; i < input.length; i++){
            input[i] = Debug.makeSymbolicChar("symC"+i);
        }

        char[] data = input;
        System.out.println("execution 2");
        if (data[0] == 'a') {
            System.out.println(1);
        }
        if (data[1] == 'b') {
            System.out.println(2);
        }
        if (data[2] == 'c') {
            System.out.println(3);
        }
        if (data[3] == 'd') {
            System.out.println(4);
        }
    }
}
