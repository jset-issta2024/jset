package test;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.StringReader;

public class TestJavaparser implements Runnable{

	private static CompilationUnit compilationUnit;
	String s="public class ScannerTest { "+
			"public static void main ( String [ ] args ) { "+
			"Scanner scanner = new Scanner ( System . in );"+
			"System . out . println ( ) ; "+
			"int a = scanner . nextInt () ; "+
			"System . out . printf ( a * a ) ; } }";

	public static void main(String[] args) throws Exception{

		String s="public class ScannerTest { "+
				"public static void main ( String [ ] args ) { "+
				"Scanner scanner = new Scanner ( System . in );"+
				"System . out . println ( ) ; "+
				"int a = scanner . nextInt () ; "+
				"System . out . printf ( a * a ) ; } }";
		char[] data = s.toCharArray();
		start(data);

	}

	private static void start(char[] data) throws ParseException {
//		s = FileUtils.readString("inputs/javaparser.java");
//		s = SymbolicString.makeConcolicString(s);

		try{
			String s = data.toString();
			System.out.println(s);
			StringReader reader = new StringReader(s);
			compilationUnit = JavaParser.parse(reader, false);
			Visitor visitor = new Visitor();
			visitor.visit(compilationUnit,null);
			compilationUnit.getAllContainedComments();
		} catch(ParseException e) {
			e.printStackTrace();
		}


//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();

	}

	@Override
	public void run() {
		try {
			start(s.toCharArray());
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


