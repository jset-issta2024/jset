/*
 * $Id: Run.java,v 1.1.1.1 2004/02/01 19:23:43 nunnari Exp $
 *
 * Created on 24. gennaio 2004, 20:22
 */

package test.xml;

import ch.nunnisoft.xml.parser.NunniJXMLParserFSM;
import ch.nunnisoft.xml.parser.LogicError;

import java.io.*;


/**
 *
 * @author  Roberto Nunnari
 */
public class TestXml {
    
    /** Creates a new instance of Run */
    public TestXml() {
    }
    
    public static String file = "jdart/src/example-xml/test/xml/Structure.xml";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String input = "!T!Z</T>";

        start(input.toCharArray());
    	
    }


    public static char[] bug_input_char_array = {(char)60, (char)63, (char)63, (char)62, (char)60, (char)63, (char)0, (char)29};
    
    public static String bug_input = new String(bug_input_char_array);
    
    public static void start(char[] data){
    	NunniJXMLParserFSM parser = new NunniJXMLParserFSM( null );
        try {

        	BufferedReader fr = new BufferedReader(new CharArrayReader(data));
//        	BufferedReader fr = new BufferedReader(new StringReader(bug_input));
            DocHandler handler = new DocHandler();

    		parser.parse( fr, handler );

            
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        catch( LogicError e ) {
            e.printStackTrace();
        } 

    }  

}
