package test;
import static org.la4j.M.m;
import static org.la4j.M.ms;

import org.la4j.Matrix;
import org.la4j.decomposition.CholeskyDecompositorTest;
import org.la4j.decomposition.EigenDecompositorTest;
import org.la4j.decomposition.LUDecompositorTest;
import org.la4j.decomposition.MatrixDecompositor;
import org.la4j.decomposition.QRDecompositorTest;
import org.la4j.decomposition.RawQRDecompositor;
import org.la4j.decomposition.SingularValueDecompositorTest;
import org.la4j.inversion.AbstractInverterTest;
import org.la4j.inversion.GaussJordanInverterTest;
import org.la4j.inversion.NoPivotGaussInverterTest;
import org.la4j.linear.ForwardBackSubstitutionSolverTest;
import org.la4j.linear.GaussianSolverTest;
import org.la4j.linear.JacobiSolverTest;
import org.la4j.linear.LeastSquaresSolverTest;
import org.la4j.linear.SeidelSolverTest;
import org.la4j.linear.SquareRootSolverTest;
import org.la4j.linear.SweepSolverTest;

public class TestNoPivotGaussInverter {

    public void start(double[][] a) {

//        for (int i = 0 ; i < a.length; i++) {
//            for (int j = 0 ; j < a[i].length; j++) {
//                a[i][j] = Debug.makeConcolicDouble("a_" + i + "_" + j, ""+a[i][j]);
//            }
//        }

       NoPivotGaussInverterTest tt= new NoPivotGaussInverterTest();
       tt.performTest(a,tt.inverterFactory());

    }

    public static void main(String args[]) {

        double a[][] = new double[][] {
                {14.0, 6.0},
                {7.0, 18.0}
        };

        new TestNoPivotGaussInverter().start(a);
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
