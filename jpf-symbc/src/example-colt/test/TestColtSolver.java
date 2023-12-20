package test;

//import gov.nasa.jpf.jdart.Debug;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.SingularValueDecomposition;

public class TestColtSolver {
	public static void main(String[] args){

		double[][] a = new double[][] {
				{ 0.1, 0.1, 0.1},
				{ -1.1, 1.1, 1.1},
				{ 2.1, 2.1, 2.1},
				{ 3.1, -3.1, 3.1},
		};
		double[][] b = a;

		start(a,b);
	}
	public static void start(double[][] a, double[][] b){
		DoubleMatrix2D matrix1,matrix2;
		matrix1 = new DenseDoubleMatrix2D(4,3);
		matrix2 = new DenseDoubleMatrix2D(4,3);

//		for (int i = 0 ; i < a.length; i++) {
//			for (int j = 0 ; j < a[i].length; j++) {
//				a[i][j] = Debug.makeConcolicDouble("a_" + i + "_" + j, ""+a[i][j]);
//			}
//		}
//		 for (int i = 0 ; i < b.length; i++) {
//	        	for(int j = 0; j < b[i].length; j++)
//	            b[i][j] = Debug.makeConcolicDouble("b_" + i + "_" + j, ""+b[i][j]);
//	        }
		 
		matrix1.assign(a);
		matrix2.assign(b);
		Algebra al = new Algebra();
		al.solve(matrix1, matrix2);
	}
}
