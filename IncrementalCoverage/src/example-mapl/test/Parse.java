package test;
import java.io.FileNotFoundException;
import java.io.FileReader;

import mapl.parser.MaplParser;

/**
 * A harness to test the Mapl mapl.parser.
 */
public class Parse {

    public static void main(String[] args) throws Throwable {
    	StringBuffer buffer = new StringBuffer();
  	  	String file = "src/example-mapl/mapl.examples/xor.mapl";
  	  	FileReader freader = null;
  	  	try {
  	  		freader = new FileReader(file);
  	  	} catch (FileNotFoundException e1) {
  	  		e1.printStackTrace();
  	  	}
    	
        MaplParser parser  = new MaplParser(freader);
        System.out.println("parsing...");
        parser.nt_Program();
        System.out.println("...parse completed.");
        /*
        try {
            if (args.length == 0) {
                // Read program to be parsed from standard input
                mapl.parser = new MaplParser(System.in);
            } else {
                // Read program to be parsed from file
                try {
                    mapl.parser = new MaplParser(new java.io.FileInputStream(args[0]));
                } catch (java.io.FileNotFoundException e) {
                    System.err.println("Unable to read file " + args[0]);
                    return;
                }
            }
            System.out.println("parsing...");
            mapl.parser.nt_Program();
            System.out.println("...parse completed.");
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
    }
}
