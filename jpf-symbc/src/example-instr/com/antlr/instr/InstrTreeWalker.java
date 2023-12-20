package com.antlr.instr;// $ANTLR 2.7.7 (20060906): "instr.g" -> "InstrTreeWalker.java"$

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class InstrTreeWalker extends TreeParser       implements InstrParserTokenTypes
 {

	/** walk list of hidden tokens in order, printing them out */
	public static void dumpHidden(antlr.CommonHiddenStreamToken t) {
	  for ( ; t!=null ; t=InstrMain.filter.getHiddenAfter(t) ) {
	    System.out.print(t.getText());
	  }
	}

	private void pr(AST p) {
		System.out.print(p.getText());
		dumpHidden(
			((antlr.CommonASTWithHiddenTokens)p).getHiddenAfter()
		);
	}
public InstrTreeWalker() {
	tokenNames = _tokenNames;
}

	public final void slist(AST _t) throws RecognitionException {
		
		AST slist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			dumpHidden(InstrMain.filter.getInitialHiddenToken());
			{
			int _cnt45=0;
			_loop45:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					stat(_t);
					_t = _retTree;
				}
				else {
					if ( _cnt45>=1 ) { break _loop45; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt45++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void stat(AST _t) throws RecognitionException {
		
		AST stat_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST t = null;
		AST e = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LBRACE:
			{
				AST __t47 = _t;
				AST tmp1_AST_in = (AST)_t;
				match(_t,LBRACE);
				_t = _t.getFirstChild();
				pr(tmp1_AST_in);
				{
				int _cnt49=0;
				_loop49:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_tokenSet_0.member(_t.getType()))) {
						stat(_t);
						_t = _retTree;
					}
					else {
						if ( _cnt49>=1 ) { break _loop49; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt49++;
				} while (true);
				}
				AST tmp2_AST_in = (AST)_t;
				match(_t,RBRACE);
				_t = _t.getNextSibling();
				pr(tmp2_AST_in);
				_t = __t47;
				_t = _t.getNextSibling();
				break;
			}
			case LITERAL_if:
			{
				AST __t50 = _t;
				i = _t==ASTNULL ? null :(AST)_t;
				match(_t,LITERAL_if);
				_t = _t.getFirstChild();
				pr(i);
				expr(_t);
				_t = _retTree;
				t = (AST)_t;
				match(_t,LITERAL_then);
				_t = _t.getNextSibling();
				pr(t);
				stat(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_else:
				{
					e = (AST)_t;
					match(_t,LITERAL_else);
					_t = _t.getNextSibling();
					pr(e);
					stat(_t);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t50;
				_t = _t.getNextSibling();
				break;
			}
			case ASSIGN:
			{
				AST __t52 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				AST tmp4_AST_in = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				pr(tmp4_AST_in); pr(tmp3_AST_in);
				expr(_t);
				_t = _retTree;
				AST tmp5_AST_in = (AST)_t;
				match(_t,SEMI);
				_t = _t.getNextSibling();
				pr(tmp5_AST_in);
				_t = __t52;
				_t = _t.getNextSibling();
				break;
			}
			case CALL:
			{
				call(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void expr(AST _t) throws RecognitionException {
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case PLUS:
			{
				AST __t54 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				pr(tmp6_AST_in);
				expr(_t);
				_t = _retTree;
				_t = __t54;
				_t = _t.getNextSibling();
				break;
			}
			case STAR:
			{
				AST __t55 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,STAR);
				_t = _t.getFirstChild();
				expr(_t);
				_t = _retTree;
				pr(tmp7_AST_in);
				expr(_t);
				_t = _retTree;
				_t = __t55;
				_t = _t.getNextSibling();
				break;
			}
			case INT:
			{
				AST tmp8_AST_in = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				pr(tmp8_AST_in);
				break;
			}
			case ID:
			{
				AST tmp9_AST_in = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				pr(tmp9_AST_in);
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void call(AST _t) throws RecognitionException {
		
		AST call_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			
					// add instrumentation about call; manually call rule
					callDumpInstrumentation(call_AST_in);
					
			AST __t57 = _t;
			AST tmp10_AST_in = (AST)_t;
			match(_t,CALL);
			_t = _t.getFirstChild();
			AST tmp11_AST_in = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			pr(tmp11_AST_in);
			AST tmp12_AST_in = (AST)_t;
			match(_t,LPAREN);
			_t = _t.getNextSibling();
			pr(tmp12_AST_in);
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			case PLUS:
			case STAR:
			case INT:
			{
				expr(_t);
				_t = _retTree;
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			AST tmp13_AST_in = (AST)_t;
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			pr(tmp13_AST_in);
			AST tmp14_AST_in = (AST)_t;
			match(_t,SEMI);
			_t = _t.getNextSibling();
			
					  // print SEMI manually; need '}' between it and whitespace
					  System.out.print(tmp14_AST_in.getText());
					  System.out.print("}"); // close {...} of instrumentation
					  dumpHidden(
						((antlr.CommonASTWithHiddenTokens)tmp14_AST_in).getHiddenAfter()
					  );
					
			_t = __t57;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
/** Dump instrumentation for a call statement.
 *  The reference to rule expr prints out the arg
 *  and then at the end of this rule, we close the
 *  generated called to dbg.invoke().
 */
	public final void callDumpInstrumentation(AST _t) throws RecognitionException {
		
		AST callDumpInstrumentation_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST e = null;
		
		try {      // for error handling
			AST __t60 = _t;
			AST tmp15_AST_in = (AST)_t;
			match(_t,CALL);
			_t = _t.getFirstChild();
			id = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			System.out.print("{dbg.invoke(\""+id.getText()+"\", \"");
			AST tmp16_AST_in = (AST)_t;
			match(_t,LPAREN);
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			case PLUS:
			case STAR:
			case INT:
			{
				e = _t==ASTNULL ? null : (AST)_t;
				expr(_t);
				_t = _retTree;
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			AST tmp17_AST_in = (AST)_t;
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			AST tmp18_AST_in = (AST)_t;
			match(_t,SEMI);
			_t = _t.getNextSibling();
			System.out.print("\"); ");
			_t = __t60;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
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
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2224L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
