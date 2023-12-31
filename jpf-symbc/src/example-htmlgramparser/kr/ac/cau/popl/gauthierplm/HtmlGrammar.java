/* Generated By:JavaCC: Do not edit this line. HtmlGrammar.java */
package kr.ac.cau.popl.gauthierplm;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileWriter;

public class HtmlGrammar implements HtmlGrammarConstants {
  public static String studentId = "50171352";
  public static TagRecord tr;

  public static void main(String args []) throws ParseException
  {
    //String file;
    //if (args.length == 1) file = args [0];
    //else file = "";
    tr = new TagRecord();
    String s = "<!doctype html> <!doctype html>"
			+ "<html><head><title>First parse</title></head>"
			+ "<!-- Region: {view-rendered} 	 Module: {view-rendered}  -->"
			  + "<body><p>Parsed HTML into a doc.</p></body></html>";
    InputStream  inputStream = new ByteArrayInputStream(s.getBytes());
    //InputStream i = System.in;
    /*try
    {
      i = new FileInputStream(new File(file));
    }
    catch (IOException e)
    {
      System.out.println("Use stdin, press CTRL+D (Linux and Mac) / CTRL+Z (Windows) to end input.");
    }
    finally
    {*/
      HtmlGrammar parser = new HtmlGrammar(inputStream);
      try
      {
        parser.file();
        System.out.println("Parsing Complete!");
      }
      catch (ParseException e)
      {
        System.err.println("Parsing error");
      }
      //File output = new File(studentId + ".txt");
    }
  //}

  static final public void file() throws ParseException {
    elementSequence();
    jj_consume_token(0);
  }

  static final public void elementSequence() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EOL:
      case TAG_START:
      case ENDTAG_START:
      case COMMENT_START:
      case DECL_START:
      case TEXT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      element();
    }
  }

  static final public void element() throws ParseException {
  Token text;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TAG_START:
      tag();
      break;
    case ENDTAG_START:
      endTag();
      break;
    case COMMENT_START:
      comment();
      break;
    case DECL_START:
      decl();
      break;
    case TEXT:
      text();
      break;
    case EOL:
      jj_consume_token(EOL);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void tag() throws ParseException {
  Token t, et;
    jj_consume_token(TAG_START);
    t = jj_consume_token(TAG_NAME);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ATTR_NAME:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      attribute();
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TAG_END:
      et = jj_consume_token(TAG_END);
      break;
    case TAG_SLASHEND:
      et = jj_consume_token(TAG_SLASHEND);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    if (et.kind == TAG_SLASHEND)
    {
      tr.record(t);
    }
    else
    {
      tr.record(t);
    }
  }

  static final public void endTag() throws ParseException {
    jj_consume_token(ENDTAG_START);
    jj_consume_token(TAG_NAME);
    jj_consume_token(TAG_END);
  }

  static final public void attribute() throws ParseException {
    jj_consume_token(ATTR_NAME);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ATTR_EQ:
      attribute_value();
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
  }

  static final public void attribute_value() throws ParseException {
    jj_consume_token(ATTR_EQ);
    jj_consume_token(ATTR_VAL);
  }

  static final public void text() throws ParseException {
    jj_consume_token(TEXT);
  }

  static final public void comment() throws ParseException {
    jj_consume_token(COMMENT_START);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DASH:
      case COMMENT_EOL:
      case COMMENT_WORD:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DASH:
        jj_consume_token(DASH);
        break;
      case COMMENT_EOL:
        jj_consume_token(COMMENT_EOL);
        break;
      case COMMENT_WORD:
        jj_consume_token(COMMENT_WORD);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 0:
      jj_consume_token(0);
      break;
    case COMMENT_END:
      jj_consume_token(COMMENT_END);
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void decl() throws ParseException {
    jj_consume_token(DECL_START);
    jj_consume_token(DECL_ANY);
    jj_consume_token(DECL_END);
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public HtmlGrammarTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xfc000,0xfc000,0x400000,0x1800000,0x2000000,0xe0000000,0xe0000000,0x10000001,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  /** Constructor with InputStream. */
  public HtmlGrammar(java.io.InputStream stream) {
     this(stream, null);
     
     try {
    	 elementSequence();
		 jj_consume_token(0);
	} catch (ParseException e) {
		e.printStackTrace();
	}
  }
  /** Constructor with InputStream and supplied encoding */
  public HtmlGrammar(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new HtmlGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public HtmlGrammar(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new HtmlGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public HtmlGrammar(HtmlGrammarTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(HtmlGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
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


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[34];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 34; i++) {
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

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
