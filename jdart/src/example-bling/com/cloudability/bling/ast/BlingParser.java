/* Generated By:JavaCC: Do not edit this line. BlingParser.java */
package com.cloudability.bling.ast;

import java.io.BufferedReader;
import java.io.StringReader;

public class BlingParser implements BlingParserConstants {

    public BlingParser(String text) {
      this(new BufferedReader(new StringReader(text)));
    }

    public Expression parse() {
      Expression expression = null;
      try {
        expression = this.Expression();
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
      return expression;
    }

/* **********************************  SYNTACTIC GRAMMAR STARTS HERE  ********************************** */
  final public Expression Expression() throws ParseException {
  Expression e = null;
    e = AdditiveExpression();
    jj_consume_token(0);
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Expression AdditiveExpression() throws ParseException {
  Expression lhs = null;
  Operator op = null;
  Expression rhs = null;
    lhs = MultiplicativeExpression();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
        op = Operator.PLUS;
        break;
      case MINUS:
        jj_consume_token(MINUS);
        op = Operator.MINUS;
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      rhs = MultiplicativeExpression();
      lhs = new BinaryExpression(lhs, op, rhs);
    }
    {if (true) return lhs;}
    throw new Error("Missing return statement in function");
  }

  final public Expression MultiplicativeExpression() throws ParseException {
  Expression lhs = null;
  Operator op = null;
  Expression rhs = null;
    lhs = ExponentialExpression();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STAR:
      case SLASH:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STAR:
        jj_consume_token(STAR);
        op = Operator.MULTIPLY;
        break;
      case SLASH:
        jj_consume_token(SLASH);
        op = Operator.DIVIDE;
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      rhs = ExponentialExpression();
      lhs = new BinaryExpression(lhs, op, rhs);
    }
    {if (true) return lhs;}
    throw new Error("Missing return statement in function");
  }

  final public Expression ExponentialExpression() throws ParseException {
  Expression lhs = null;
  Operator op = null;
  Expression rhs = null;
    lhs = NegatableExpression();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      jj_consume_token(CARAT);
      op = Operator.POWER;
      rhs = NegatableExpression();
      lhs = new BinaryExpression(lhs, op, rhs);
    }
    {if (true) return lhs;}
    throw new Error("Missing return statement in function");
  }

  final public Expression NegatableExpression() throws ParseException {
  Operator op = null;
  Expression e = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
      op = Operator.MINUS;
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    e = PrimeExpression();
    if (op == null) {
      {if (true) return e;}
    } else {
      {if (true) return new UnaryExpression(e, op);}
    }
    throw new Error("Missing return statement in function");
  }

  final public Expression PrimeExpression() throws ParseException {
  Expression e = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      e = NumberLiteral();
      break;
    case LPAREN:
      e = GroupedExpression();
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Expression NumberLiteral() throws ParseException {
  Token t = null;
    t = jj_consume_token(NUMBER);
    {if (true) return new NumberLiteral(Double.parseDouble(t.image));}
    throw new Error("Missing return statement in function");
  }

  final public Expression GroupedExpression() throws ParseException {
  Expression e = null;
    jj_consume_token(LPAREN);
    e = AdditiveExpression();
    jj_consume_token(RPAREN);
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public BlingParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x60,0x60,0x180,0x180,0x200,0x40,0x1400,};
   }

  /** Constructor with InputStream. */
  public BlingParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public BlingParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new BlingParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public BlingParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new BlingParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public BlingParser(BlingParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(BlingParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
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


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[13];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 13; i++) {
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
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
