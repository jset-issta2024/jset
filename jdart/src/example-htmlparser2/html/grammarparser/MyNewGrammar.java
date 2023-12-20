/* MyNewGrammar.java */
/* Generated By:JavaCC: Do not edit this line. MyNewGrammar.java */
package html.grammarparser;

import java.io.*;
import java.util.Scanner;

public class MyNewGrammar implements MyNewGrammarConstants {
    static final int HTML = 1, HEAD = 2, LINK = 3, BODY = 4, P = 5, SPAN = 6, IMG = 7, DIV = 8, TABLE = 9,
            TR = 10, TH = 11, TD = 12, FORM = 13, INPUT = 14, MAX = 200;
    static int[] tags, startingTags;
    static int result;
    static boolean valid = true;

    public static void main(String args[]) {
        tags = new int[MAX];
        startingTags = new int[MAX];

        try {
            File file = new File("C:\\Users\\CAU\\Desktop\\HW01\\ex06\\ex06.html"); //set your own file path
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                MyNewGrammar parser = new MyNewGrammar(System.in);
                result = (int) eval(sc.nextLine().replaceAll(" ", ""));

                countTags();

                //System.out.println(eval(sc.nextLine().replaceAll(" ", "")));
//			 System.out.println(result);
            }

            validateCheck();
            if (valid) {
                System.out.println("Parsing Complete!");
                showTagCounts();
            } else {
                System.out.println("Parsing Error!");
            }
        } catch (Exception e) {
            System.out.println("Parsing Error!");
        }
    }


    public static void validateCheck() {
        for (int i = 0; i < MAX / 4; i++) {
//		   System.out.println("start:" + i + " " + startingTags[i]);
            if (startingTags[i] != 0 && valid == true) {
                valid = false;
                break;
            }
            //valid = true;
        }
    }

    public static long eval(String state) {
        Reader reader = new StringReader(state);

        try {
            return new MyNewGrammar(reader).exp();

        } catch (Exception e) {
            return -1;
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
    }

    public static void showTagCounts() {
        for (int i = 1; i < MAX / 2; i++) {
            if (tags[i] != 0) {
                switch (i) {
                    case HTML:
                        System.out.print("<HTML> ");
                        System.out.println(tags[i]);
                        break;
                    case HEAD:
                        System.out.print("<HEAD> ");
                        System.out.println(tags[i]);
                        break;
                    case LINK:
                        System.out.print("<LINK> ");
                        System.out.println(tags[i]);
                        break;
                    case BODY:
                        System.out.print("<BODY> ");
                        System.out.println(tags[i]);
                        break;
                    case P:
                        System.out.print("<P> ");
                        System.out.println(tags[i]);
                        break;
                    case SPAN:
                        System.out.print("<SPAN> ");
                        System.out.println(tags[i]);
                        break;
                    case IMG:
                        System.out.print("<IMG> ");
                        System.out.println(tags[i]);
                        break;
                    case DIV:
                        System.out.print("<DIV> ");
                        System.out.println(tags[i]);
                        break;
                    case TABLE:
                        System.out.print("<TABLE> ");
                        System.out.println(tags[i]);
                        break;
                    case TR:
                        System.out.print("<TR> ");
                        System.out.println(tags[i]);
                        break;
                    case TH:
                        System.out.print("<TH> ");
                        System.out.println(tags[i]);
                        break;
                    case TD:
                        System.out.print("<TD> ");
                        System.out.println(tags[i]);
                        break;
                    case FORM:
                        System.out.print("<FROM> ");
                        System.out.println(tags[i]);
                        break;
                    case INPUT:
                        System.out.print("<INPUT> ");
                        System.out.println(tags[i]);
                        break;
                }
            }
        }
    }

    public static void countTags() {
        for (int i = 1; i < MAX / 4; i++) {
            if (result == i) {
                tags[i]++;
            } else if (result == (i + 100)) {
                startingTags[i]++;
            } else if (result == (i + 50) && startingTags[i] != 0) {
                tags[i]++;
                startingTags[i]--;
            } else if (result == (i + 50) && startingTags[i] == 0) {
                valid = false;
//			System.out.println("r: " + result);
//			System.out.println("s: " + startingTags[i]);
            }
        }
    }

    final public int exp() throws ParseException {
        Token html, htmlStart, htmlEnd;
        Token head, headStart, headEnd;
        Token link;
        Token body, bodyStart, bodyEnd;
        Token p, pStart, pEnd;
        Token span;
        Token img;
        Token div, divStart, divEnd;
        Token table, tableStart, tableEnd;
        Token tr, trStart, trEnd;
        Token th, td;
        Token form, formStart, formEnd;
        Token input;
        Token ignore;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case HTMLt: {
                html = jj_consume_token(HTMLt);
                jj_consume_token(0);
                {
                    if ("" != null) return 1;
                }
                break;
            }
            case HTMLSTART: {
                htmlStart = jj_consume_token(HTMLSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 101;
                }
                break;
            }
            case HTMLEND: {
                htmlEnd = jj_consume_token(HTMLEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 51;
                }
                break;
            }
            case HEADt: {
                head = jj_consume_token(HEADt);
                jj_consume_token(0);
                {
                    if ("" != null) return 2;
                }
                break;
            }
            case HEADSTART: {
                headStart = jj_consume_token(HEADSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 102;
                }
                break;
            }
            case HEADEND: {
                headEnd = jj_consume_token(HEADEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 52;
                }
                break;
            }
            case LINKt: {
                link = jj_consume_token(LINKt);
                jj_consume_token(0);
                {
                    if ("" != null) return 3;
                }
                break;
            }
            case BODYt: {
                body = jj_consume_token(BODYt);
                jj_consume_token(0);
                {
                    if ("" != null) return 4;
                }
                break;
            }
            case BODYSTART: {
                bodyStart = jj_consume_token(BODYSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 104;
                }
                break;
            }
            case BODYEND: {
                bodyEnd = jj_consume_token(BODYEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 54;
                }
                break;
            }
            case Pt: {
                p = jj_consume_token(Pt);
                jj_consume_token(0);
                {
                    if ("" != null) return 5;
                }
                break;
            }
            case PSTART: {
                pStart = jj_consume_token(PSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 105;
                }
                break;
            }
            case PEND: {
                pEnd = jj_consume_token(PEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 55;
                }
                break;
            }
            case SPANt: {
                span = jj_consume_token(SPANt);
                jj_consume_token(0);
                {
                    if ("" != null) return 6;
                }
                break;
            }
            case IMGt: {
                img = jj_consume_token(IMGt);
                jj_consume_token(0);
                {
                    if ("" != null) return 7;
                }
                break;
            }
            case DIVt: {
                div = jj_consume_token(DIVt);
                jj_consume_token(0);
                {
                    if ("" != null) return 8;
                }
                break;
            }
            case DIVSTART: {
                divStart = jj_consume_token(DIVSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 108;
                }
                break;
            }
            case DIVEND: {
                divEnd = jj_consume_token(DIVEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 58;
                }
                break;
            }
            case TABLESTART: {
                tableStart = jj_consume_token(TABLESTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 109;
                }
                break;
            }
            case TABLEEND: {
                tableEnd = jj_consume_token(TABLEEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 59;
                }
                break;
            }
            case TRSTART: {
                trStart = jj_consume_token(TRSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 110;
                }
                break;
            }
            case TREND: {
                trEnd = jj_consume_token(TREND);
                jj_consume_token(0);
                {
                    if ("" != null) return 60;
                }
                break;
            }
            case THt: {
                th = jj_consume_token(THt);
                jj_consume_token(0);
                {
                    if ("" != null) return 11;
                }
                break;
            }
            case TDt: {
                td = jj_consume_token(TDt);
                jj_consume_token(0);
                {
                    if ("" != null) return 12;
                }
                break;
            }
            case FORMSTART: {
                formStart = jj_consume_token(FORMSTART);
                jj_consume_token(0);
                {
                    if ("" != null) return 113;
                }
                break;
            }
            case FORMEND: {
                formEnd = jj_consume_token(FORMEND);
                jj_consume_token(0);
                {
                    if ("" != null) return 63;
                }
                break;
            }
            case INPUTt: {
                input = jj_consume_token(INPUTt);
                jj_consume_token(0);
                {
                    if ("" != null) return 14;
                }
                break;
            }
            case IGNORE: {
                ignore = jj_consume_token(IGNORE);
                jj_consume_token(0);
                {
                    if ("" != null) return 0;
                }
                break;
            }
            default:
                jj_la1[0] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /**
     * Generated Token Manager.
     */
    public MyNewGrammarTokenManager token_source;
    SimpleCharStream jj_input_stream;
    /**
     * Current token.
     */
    public Token token;
    /**
     * Next token.
     */
    public Token jj_nt;
    private int jj_ntk;
    private int jj_gen;
    final private int[] jj_la1 = new int[1];
    static private int[] jj_la1_0;
    static private int[] jj_la1_1;

    static {
        jj_la1_init_0();
        jj_la1_init_1();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{0xffffffe0,};
    }

    private static void jj_la1_init_1() {
        jj_la1_1 = new int[]{0x1,};
    }

    /**
     * Constructor with InputStream.
     */
    public MyNewGrammar(java.io.InputStream stream) {
        this(stream, null);
    }

    /**
     * Constructor with InputStream and supplied encoding
     */
    public MyNewGrammar(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new MyNewGrammarTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /**
     * Constructor.
     */
    public MyNewGrammar(java.io.Reader stream) {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new MyNewGrammarTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.Reader stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new SimpleCharStream(stream, 1, 1);
        } else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new MyNewGrammarTokenManager(jj_input_stream);
        }

        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /**
     * Constructor with generated Token Manager.
     */
    public MyNewGrammar(MyNewGrammarTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    /**
     * Reinitialise.
     */
    public void ReInit(MyNewGrammarTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 1; i++) jj_la1[i] = -1;
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }


    /**
     * Get the next Token.
     */
    final public Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /**
     * Get the specific Token.
     */
    final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    private int jj_ntk_f() {
        if ((jj_nt = token.next) == null)
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }

    private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    private int[] jj_expentry;
    private int jj_kind = -1;

    /**
     * Generate ParseException.
     */
    public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[33];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 1; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & (1 << j)) != 0) {
                        la1tokens[32 + j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 33; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    /**
     * Enable tracing.
     */
    final public void enable_tracing() {
    }

    /**
     * Disable tracing.
     */
    final public void disable_tracing() {
    }

}