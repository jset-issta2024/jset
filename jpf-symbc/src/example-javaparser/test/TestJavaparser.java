package test;

import gov.nasa.jpf.symbc.Debug;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.StringReader;

public class TestJavaparser implements Runnable{

	private static CompilationUnit compilationUnit;

	public static void main(String[] args) throws ParseException {
		start();
	}

	private static void start() throws ParseException {

		String s="public class ScannerTest { "+
				"public static void main ( String [ ] args ) { "+
				"Scanner scanner = new Scanner ( System . in );"+
				"System . out . println ( ) ; "+
				"int a = scanner . nextInt () ; "+
				"System . out . printf ( a * a ) ; } }";

		s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader reader = new StringReader(s);
			compilationUnit = JavaParser.parse(reader, false);

			Visitor visitor = new Visitor();
			visitor.visit(compilationUnit,null);
			compilationUnit.getAllContainedComments();
		} catch (ParseException e) {

		}

//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();

	}

	public static void start_old() throws Exception {
		//InputStream is = new ByteArrayInputStream("class A {  }".getBytes());
		String s="class A { double a='a'; }";
//		String s="class";

//		s = SymbolicString.makeConcolicString(s);
		s = Debug.makeSymbolicString(s);
		System.out.println(s);
		StringReader reader = new StringReader(s);
//		if(JavaparserConfig.SYMB_FLAG)
//			Debug.printCurrentPC();
//		Statement expression = JavaParser.parseStatement(" \"\" ;");
		try {
			CompilationUnit compilationUnit = JavaParser.parse(reader, false);
			System.out.println("valid input!!!");
//			new Visitor().visit(compilationUnit,null);
		}catch (Throwable e){
//			Debug.ProcessPC();
		}finally {
//			Debug.ProcessPC();
		}
    }

	@Override
	public void run() {
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class Visitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodDeclaration n, Object arg){
			System.out.println(n.getName());
		}
	}

}


