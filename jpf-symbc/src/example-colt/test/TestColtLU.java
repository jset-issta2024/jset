package test;

//import gov.nasa.jpf.jdart.Debug;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;

public class TestColtLU {
	public static void main(String[] args){
		double a[][] = new double[][] {
				{ 5.0, 10.0 },
				{ 15.0, -20.0 }
		};

		start(a);
	}
	public static void start(double[][] a){
		DoubleMatrix2D matrix1,matrix2;
		matrix1 = new DenseDoubleMatrix2D(10,10);
		
//		double[][] a = TestData.a1;

//		for (int i = 0 ; i < a.length; i++) {
//			for (int j = 0 ; j < a[i].length; j++) {
//				a[i][j] = Debug.makeConcolicDouble("a_" + i + "_" + j, ""+a[i][j]);
//			}
//		}
		matrix1.assign(a);
		
		
		LUDecomposition lu = null;
		lu = new LUDecomposition(matrix1);
		
	}
}
