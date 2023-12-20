package test;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.StringReader;
import java.util.List;


public class TestJavaparser implements Runnable{

	public static void main(String[] args) throws Exception{
		start();
	}
	
	public static void start() throws Exception {
		//InputStream is = new ByteArrayInputStream("class A {  }".getBytes());
		String s="//";
		System.out.println(s);
		StringReader reader = new StringReader(s);
		try {
			CompilationUnit compilationUnit = JavaParser.parse(reader, false);
			System.out.println("valid input!!!");
		}catch (Throwable e){
		}finally {
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


