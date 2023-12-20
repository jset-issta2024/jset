package test;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Statistic;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.CholeskyDecomposition;
import cern.colt.matrix.linalg.EigenvalueDecomposition;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.colt.matrix.linalg.QRDecomposition;
import cern.colt.matrix.linalg.SingularValueDecomposition;

public class TestColt {
	
	public static void main(String[] args){

		double a[][] = new double[][] {
				{ 5.0, 10.0 },
				{ 15.0, -20.0 }
		};
		double b[][] = new double[][] {
				{ 21.0, -37.0 },
				{ 6.0, -12.0 },
		};


		start(a,b);
	}
	
	public static void start(double[][] a, double[][] b){
		int n = 4;
		int m = 3;
		DoubleMatrix2D matrix1,matrix2;
		matrix1 = new DenseDoubleMatrix2D(2,2);
		matrix2 = new DenseDoubleMatrix2D(2,2);
		
//		double[][] mat = new double[n][n];
//		for(int i = 0; i<n; i++){
//			for(int j = 0; j<n; j++){
//				mat[i][j] = Debug.makeConcolicDouble("mat"+i*10+j, "1");
//			}
//		}

//        for (int i = 0 ; i < a.length; i++) {
//            for (int j = 0 ; j < a[i].length; j++) {
//                a[i][j] = Debug.makeConcolicDouble("a_" + i + "_" + j, ""+a[i][j]);
//            }
//        }
//        for (int i = 0 ; i < b.length; i++) {
//        	for(int j = 0; j < b[i].length; j++)
//            b[i][j] = Debug.makeConcolicDouble("b_" + i + "_" + j, ""+b[i][j]);
//        }
		matrix1.assign(a);
		matrix2.assign(b);
		
//		Algebra al = new Algebra();
//		al.toVerboseString(matrix1);
		
		Algebra al = new Algebra();
		al.solve(matrix1, matrix2);
		
//		Algebra al = new Algebra();
//		al.rank(matrix1);
		
		
//		LUDecomposition lu = null;
//		lu = new LUDecomposition(matrix1);
//		
//		QRDecomposition qr = null;
//		qr = new QRDecomposition(matrix1); 
//		
//		EigenvalueDecomposition eig = null;
//		eig = new EigenvalueDecomposition(matrix1);
//		
//		SingularValueDecomposition svd = null;
//		svd = new SingularValueDecomposition(matrix1);
//		
//		CholeskyDecomposition chol = null;
//		chol = new CholeskyDecomposition(matrix1);
		System.out.println("---------start-------------");
	}
	
}
