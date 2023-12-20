package ca.ubc.cs411.aejcc;

import ca.ubc.cs411.aejcc.ast.AE;
import ca.ubc.cs411.aejcc.parser.AEParser;
import ca.ubc.cs411.aejcc.parser.ParseException;
import ca.ubc.cs411.aejcc.parser.TokenMgrError;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to AE!\nType \"exit\" to end.");
        // (+ (+ 1 1) (- 5 8))
        AEParser aep = new AEParser(System.in);

        boolean done = false;
        do {
            try {
                System.out.print("> ");
                AE result = aep.REPLCmd();
                if (result != null) {
                    System.out.println("AST: " + result);
                    System.out.println("Pretty Print: " + result.prettyPrint());
                    System.out.println("Interp: " + result.interp());
                } else {
                    done = true;
                }
            } catch (ParseException e) {
                System.out.println("exception: " + e);
                aep.ReInit(System.in); // try again
            }  catch(TokenMgrError e) {
                System.out.println("error: " + e);
                aep.ReInit(System.in); // try again
            }
            // This would throw away  the rest of an input line after a legal parse
            // aep.ReInit(System.in);
        } while(!done);

        System.out.println("Goodbye!");
    }
}
