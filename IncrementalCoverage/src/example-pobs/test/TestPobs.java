package test;

import coverage.SubjectExecutor;
import example.calc.SolverVar;
import pobs.PContext;
import pobs.PDirective;
import pobs.PObject;
import pobs.PParser;
import pobs.directive.PDisableSkip;
import pobs.parser.*;
import pobs.scanner.PStringScanner;
import pobs.utility.POBS;

import java.io.IOException;

public class TestPobs extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "pobs";
        inputFileName = args[0];
        new TestPobs().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {

        pobs.PObject parser;
        SolverVar solver;

        solver = new SolverVar();

        /*
           	number		= sdouble | sint		TODO: Special double parser?
        	variable	= lexeme(alpha+)
        	args		= expr (',' expr)*
        	function	= lexeme(alpha+) '(' args ')'
        	expr		= term ( ('+' term) | ('-' term) )*
            term		= factor ( ('*' factor) | ('/' factor) )*
            factor		= number | function | variable | '(' expr ')'
         */

        PPointer p_term = new PPointer();
        PPointer p_factor = new PPointer();

        // terminals
        PParser number = new POr(POBS.SIGNED_FLOAT, POBS.SIGNED_INT); // parser or of two parser Single_float and single_int
        number.setMatchAction(solver.value);

        PParser var = new PMultiple(POBS.ALPHA).addControl(new PDisableSkip());
        var.setMatchAction(solver.variable);

        // non-terminals
        PParser expr_plus = new PSequence(new PChar('+'), p_factor);
        expr_plus.setMatchAction(solver.operand(solver.PLUS));

        PParser expr_minus = new PSequence(new PChar('-'), p_factor);
        expr_plus.setMatchAction(solver.operand(solver.MINUS));

        PObject expr =
                new PSequence(p_term, new PKleene(new POr(expr_plus, expr_minus)));

        PParser term_multiply = new PSequence(new PChar('*'), p_factor);
        term_multiply.setMatchAction(solver.operand(solver.MULTIPLY));

        PParser term_divide = new PSequence(new PChar('/'), p_factor);
        term_divide.setMatchAction(solver.operand(solver.DIVIDE));

        PObject term =
                new PSequence(
                        p_factor,
                        new PKleene(new POr(term_multiply, term_divide)));
        p_term.set(term);

        PObject args =
                new PSequence(
                        new PChar('('),
                        new PList(expr, new PChar(',')),
                        new PChar(')'));

        PParser function_max = new PSequence(new PToken("max"), args);


        PParser function_min = new PSequence(new PToken("mix"), args);


        PObject function = new POr(function_min, function_max);
        PObject factor =
                new POr(
                        number,
                        function,
                        var,
                        new PSequence(new PChar('('), expr, new PChar(')')) );
        p_factor.set(factor);

        parser = factor;

        long result = parser
                .process(   // this process function will return the matching information of parsing!
                        new PStringScanner(input), // PStringScanner make the value of string symbolic! it is just a traditional scanner of string: charAt. subString and so on.
                        0, // the PWhitespace is the match rule! ----skipper!
                        new PContext(new PDirective(new PWhitespace()))) // PContext contains 3 elements: PDirective, PTarget, errorHandler!  the last 2 are default ones!
                .getLength();
    }
}
