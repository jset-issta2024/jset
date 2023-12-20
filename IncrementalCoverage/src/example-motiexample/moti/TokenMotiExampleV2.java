package moti;
//import gov.nasa.jpf.jdart.SymbolicString;

import java.io.Reader;
import java.io.StringReader;
public class TokenMotiExampleV2 {
    public static String input = "12+13";
    static Reader inputReader;
    public static void main(String[] args) throws Exception {
        start();
    }
    public static void start() throws Exception {
//        input = SymbolicString.makeConcolicString(input);
        System.out.println(input);
        inputReader = new StringReader(input);
        parseExpr();
        if (input.charAt(input.length() - 1) == 'z'){
            assert (false);
        }
    }
    static void parseExpr() throws Exception {
        int token = SymbolicToken();
        if (token == T_NUMBER){
            parseOp();
            return;
        }else if (token == T_ID){
            if (SymbolicToken() == T_EOF){
                return;
            }
        }
        throw new Exception();
    }
    static void parseOp() throws Exception {
        int token = SymbolicToken();
        if (token == T_OP){
            parseExpr();
            return;
        }else if (token == T_EOF){
            return;
        }
        throw new Exception();
    }
    static int SymbolicToken() throws Exception {
        return getNextTokenRaw();
    }

    private static int getNextTokenRaw() throws Exception {
        int res =  inputReader.read();
        if (res == -1){
            return T_EOF;
        }
        char c = (char) res;
        if (c >= '*' && c <= '+') {
            return T_OP;
        }else if (c >= 'a' && c <= 'z'){
            return T_ID;
        }else if (c >= '0' && c <= '9'){
            char next_c = (char) inputReader.read();
            if (next_c >= '0' && next_c <= '9'){
                return T_NUMBER;
            }
        }
        throw new Exception();
    }

    public static int T_ID = 1;
    public static int T_NUMBER = 2;
    public static int T_OP = 3;
    public static int T_EOF = 0;
}
