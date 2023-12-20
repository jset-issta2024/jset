package com.antlr.instr;// $ANTLR 2.7.7 (20060906): "instr.g" -> "InstrParser.java"$

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class InstrParser extends LLkParser       implements InstrParserTokenTypes
 {

protected InstrParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public InstrParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected InstrParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public InstrParser(TokenStream lexer) {
  this(lexer,2);
}

public InstrParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void slist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST slist_AST = null;
		
		try {      // for error handling
			{
			int _cnt3=0;
			_loop3:
			do {
				if ((LA(1)==LBRACE||LA(1)==LITERAL_if||LA(1)==ID)) {
					stat();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt3++;
			} while (true);
			}
			slist_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = slist_AST;
	}
	
	public final void stat() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stat_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LBRACE:
			{
				AST tmp19_AST = null;
				tmp19_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp19_AST);
				match(LBRACE);
				{
				int _cnt6=0;
				_loop6:
				do {
					if ((LA(1)==LBRACE||LA(1)==LITERAL_if||LA(1)==ID)) {
						stat();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						if ( _cnt6>=1 ) { break _loop6; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt6++;
				} while (true);
				}
				AST tmp20_AST = null;
				tmp20_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp20_AST);
				match(RBRACE);
				stat_AST = (AST)currentAST.root;
				break;
			}
			case LITERAL_if:
			{
				AST tmp21_AST = null;
				tmp21_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp21_AST);
				match(LITERAL_if);
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp22_AST = null;
				tmp22_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp22_AST);
				match(LITERAL_then);
				stat();
				astFactory.addASTChild(currentAST, returnAST);
				{
				if ((LA(1)==LITERAL_else) && (LA(2)==LBRACE||LA(2)==LITERAL_if||LA(2)==ID)) {
					AST tmp23_AST = null;
					tmp23_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp23_AST);
					match(LITERAL_else);
					stat();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				stat_AST = (AST)currentAST.root;
				break;
			}
			default:
				if ((LA(1)==ID) && (LA(2)==ASSIGN)) {
					AST tmp24_AST = null;
					tmp24_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp24_AST);
					match(ID);
					AST tmp25_AST = null;
					tmp25_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp25_AST);
					match(ASSIGN);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp26_AST = null;
					tmp26_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp26_AST);
					match(SEMI);
					stat_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==ID) && (LA(2)==LPAREN)) {
					call();
					astFactory.addASTChild(currentAST, returnAST);
					stat_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = stat_AST;
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		try {      // for error handling
			mexpr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop10:
			do {
				if ((LA(1)==PLUS)) {
					AST tmp27_AST = null;
					tmp27_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp27_AST);
					match(PLUS);
					mexpr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop10;
				}
				
			} while (true);
			}
			expr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
		returnAST = expr_AST;
	}
	
	public final void call() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST call_AST = null;
		
		try {      // for error handling
			AST tmp28_AST = null;
			tmp28_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp28_AST);
			match(ID);
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp29_AST);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case ID:
			case INT:
			{
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp30_AST = null;
			tmp30_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp30_AST);
			match(RPAREN);
			AST tmp31_AST = null;
			tmp31_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp31_AST);
			match(SEMI);
			call_AST = (AST)currentAST.root;
			call_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CALL,"CALL")).add(call_AST));
			currentAST.root = call_AST;
			currentAST.child = call_AST!=null &&call_AST.getFirstChild()!=null ?
				call_AST.getFirstChild() : call_AST;
			currentAST.advanceChildToEnd();
			call_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = call_AST;
	}
	
	public final void mexpr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mexpr_AST = null;
		
		try {      // for error handling
			atom();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop13:
			do {
				if ((LA(1)==STAR)) {
					AST tmp32_AST = null;
					tmp32_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp32_AST);
					match(STAR);
					atom();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop13;
				}
				
			} while (true);
			}
			mexpr_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
		returnAST = mexpr_AST;
	}
	
	public final void atom() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atom_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			{
				AST tmp33_AST = null;
				tmp33_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp33_AST);
				match(INT);
				atom_AST = (AST)currentAST.root;
				break;
			}
			case ID:
			{
				AST tmp34_AST = null;
				tmp34_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp34_AST);
				match(ID);
				atom_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
		returnAST = atom_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"CALL",
		"LBRACE",
		"RBRACE",
		"\"if\"",
		"\"then\"",
		"\"else\"",
		"ID",
		"ASSIGN",
		"SEMI",
		"PLUS",
		"STAR",
		"INT",
		"LPAREN",
		"RPAREN",
		"WS",
		"SL_COMMENT",
		"DIGIT"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 1762L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 102114L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 135424L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 143616L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 160000L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	
	}
