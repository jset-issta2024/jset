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

public class TestGaussianSolver {

    public void start(double[][] d, double[] e) {


//       for (int i = 0 ; i < d.length; i++) {
//         for (int j = 0 ; j < d[i].length; j++) {
//             d[i][j] = Debug.makeConcolicDouble("d_" + i + "_" + j, ""+d[i][j]);
//         }
//     }

       new GaussianSolverTest().performTest(d,e);

    }

    public static void main(String args[]) {

        double d[][] = new double[][] {
                { 18.0, 4.0 },
                { 10.0, -2.0 }
        };

        double e[] = new double[] { 11.0, 4.0 };

        new TestGaussianSolver().start(d,e);
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
