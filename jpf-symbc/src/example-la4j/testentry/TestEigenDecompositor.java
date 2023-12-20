package testentry;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositorTest;
import org.la4j.decomposition.MatrixDecompositor;

import static org.la4j.M.m;
import static org.la4j.M.ms;

public class TestEigenDecompositor {

    public void start() {
        double a[][] = new double[][] {
        		{14.0, 6.0, 9.9},
                {4.0, 6.0, 9.9},
                {14.0, 76.0, 8.9},
        };

        double c[][][]= new double[][][]{
                {
                    {1.0, 0.0},
                    {0.5, 1.0}
            },
            {
                    {14.0, 6.0},
                    {0.0, 15.0}
            },
            {
                    {1.0, 0.0},
                    {0.0, 1.0}
            }
        };

        double b[] = new double[] { 21.0, -37.0 };

//        for (int i = 0 ; i < a.length; i++) {
//            for (int j = 0 ; j < a[i].length; j++) {
//                a[i][j] = Debug.makeConcolicDouble("a_" + i + "_" + j, ""+a[i][j]);
//            }
//        }

//        for (int i = 0 ; i < b.length; i++) {
//            b[i] = Debug.makeConcolicDouble("b_" + i , ""+b[i]);
//        }

      new EigenDecompositorTest().performTest(a, c);
//        performTest(a, c);

    }

    public static void main(String args[]) {
        new TestEigenDecompositor().start();
    }

    public void performTest(double[][] input, double[][][] output, MatrixDecompositor decompositor){
        for (Matrix a: ms(input)) {

            Matrix[] decomposition = decompositor.decompose();

            //Assert.assertEquals(output.length, decomposition.length);
            if (output.length != decomposition.length) {
            	System.err.println("must equal");
            }

            for (int i = 0; i < decomposition.length; i++) {
                //Assert.assertTrue(m(output[i]).equals(decomposition[i], 1e-3));
            	if (!m(output[i]).equals(decomposition[i], 1e-3)) {
            		System.err.println("must equal");
                }
            }
        }
    }

}
