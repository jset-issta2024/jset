package demoFloat;

public class testFloat {
    public static void main(String[] args) {
        double a[][] = new double[][] {
                { 5.0, 10.0 },
                { 15.0, -20.0 }
        };
        double b[][] = new double[][] {
                { 21.0, -37.0 },
                { 6.0, -12.0 },
        };

        for (int i = 0 ; i < a.length; i++) {
            for (int j = 0 ; j < a[i].length; j++) {
                a[i][j] = makeConcolicDouble(a[i][j]);
            }
        }
        for (int i = 0 ; i < b.length; i++) {
            for(int j = 0; j < b[i].length; j++)
                b[i][j] = makeConcolicDouble(b[i][j]);
        }
    }

    private static double makeConcolicDouble(double v) {
        return v;
    }
}
