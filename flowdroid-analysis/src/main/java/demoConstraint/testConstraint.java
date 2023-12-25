package demoConstraint;

public class testConstraint {
    public static void main(String[] args) {

        double a = 3;
        double b = 5;

        a = makeSymbolic(a);
        b = makeSymbolic(b);

        double temp1 = a * b;

        double temp2 = a / b;

        if (temp1 + temp2 > 20) {
            System.out.println("big");
        }
        else {
            System.out.println("small");
        }

    }

    private static double makeSymbolic(double x) {
        double y = x + 1;
        return y;
    }
}
