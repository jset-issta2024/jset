package test;

import coverage.SubjectExecutor;
import sie.Expr;
import sie.Parser;
import sie.Scanner;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestSie extends SubjectExecutor {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "sie";
		inputFileName = args[0];
		new TestSie().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {

		InputStream   inputStream   =   new   ByteArrayInputStream(input.getBytes());
		Parser parser = new Parser(new Scanner(
				new DataInputStream(inputStream)));
		try {
			parser.run( );
			Expr.eval();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
