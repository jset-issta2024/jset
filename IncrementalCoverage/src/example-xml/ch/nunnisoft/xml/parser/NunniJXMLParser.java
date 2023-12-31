/*
 * $Id: NunniJXMLParser.java,v 1.2 2004/02/07 00:22:04 nunnari Exp $
 *
 * Copyright (c) 2004 Roberto Nunnari - roberto.nunnari@nunnisoft.ch
 * All rights reserved.
 *
 *  This file is part of NunniMJAX.
 *
 *  NunniMJAX is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  NunniMJAX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with NunniFSMGen; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * this file was generated by NunniFSMGen
 */

package ch.nunnisoft.xml.parser;

//import gov.nasa.jpf.jdart.Debug;

import java.io.*;
import java.util.Hashtable;
import java.util.Stack;

abstract class NunniJXMLParser
{
    public abstract void openbracket( char o ) throws LogicError;
    public abstract void closebracket( char o ) throws LogicError;
    public abstract void opensqbracket( char o ) throws LogicError;
    public abstract void closesqbracket( char o ) throws LogicError;
    public abstract void questionmark( char o ) throws LogicError;
    public abstract void escalmatmark( char o ) throws LogicError;
    public abstract void minus( char o ) throws LogicError;
    public abstract void whitespace( char o ) throws LogicError;
    public abstract void slash( char o ) throws LogicError;
    public abstract void equal( char o ) throws LogicError;
    public abstract void squote( char o ) throws LogicError;
    public abstract void dquote( char o ) throws LogicError;
    public abstract void literal( char o ) throws LogicError;
    public abstract void other( char o ) throws LogicError;
    public abstract void end( char o ) throws LogicError;


    private ContentHandler m_handler = null;
    private char[]          m_charArray = new char[1];
    private boolean         m_start = true;
    private boolean         m_rootFound = false;
    private StringBuffer    m_tagname = null;
    private StringBuffer    m_lvalue = null;
    private StringBuffer    m_rvalue = null;
    private StringBuffer    m_dtda = null;
    private StringBuffer    m_cdata = null;
    private StringBuffer    m_cdataa = null;
    private Hashtable       m_args = null;
    private Stack           m_stack = null;


    protected NunniJXMLParser() {
        initNJAX();
    }


    protected NunniJXMLParser(Object o) {
        initNJAX();
    }


    private void initNJAX() {
        m_tagname = new StringBuffer();
        m_lvalue = new StringBuffer();
        m_rvalue = new StringBuffer();
        m_dtda = new StringBuffer();
        m_cdataa = new StringBuffer();
        m_cdata = new StringBuffer();
    }


    protected void reset() {
    }
    
    public static int count = 0;
    
    public int read(Reader reader) throws IOException {
    	int c = reader.read(); //buffer read
    	if (c != -1) {
//    		char r = Debug.makeConcolicChar("sym_char_" + count, "" + c);
    		count++;
//    		return (int)r;
            return (int)c;
    	}
    	return c;
    }

    public void parse( Reader reader, ContentHandler handler ) throws LogicError, IOException {
        m_handler = handler;
        m_start = true;
        m_rootFound = false;
        m_tagname.setLength( 0 );
        m_lvalue.setLength( 0 );
        m_rvalue.setLength( 0 );
        m_dtda.setLength( 0 );
        m_stack = new Stack();
        count = 0;
        int ci;
        while ( ( ci = read(reader) ) != -1 ) {  // read make the element of buffer symbolic!
//        while ( ( ci = reader.read() ) != -1 ) {
            char c = (char)ci;
            switch ( c ) {

                case '<':
                    openbracket( c );
                    break;

                case '>':
                    closebracket( c );
                    break;

                case '[':
                    opensqbracket( c );
                    break;

                case ']':
                    closesqbracket( c );
                    break;

                case '?':
                    questionmark( c );
                    break;

                case '!':
                    //((NunniJXMLParserFSM)this).escalmatmark2( c );
                	   escalmatmark( c );
                    break;

                case '-':
                    minus( c );
                    break;

                case ' ':
                    whitespace( c );
                    break;

                case '/':
                    slash( c );
                    break;

                case '=':
                    equal( c );
                    break;

                case '\'':
                    squote( c );
                    break;

                case '"':
                    dquote( c );
                    break;

                default: // above is 12 condtions!
                    //if ( Character.isWhitespace( c ) ) {
                	if ( isWhiteSpace( c ) ) { // ==32
                        whitespace( c );
                        break;
                    }
                    //else if ( Character.isLetterOrDigit( c ) || c=='.' || c=='_' || c==':' ) {
                    else if ( isLetterOrDigit( c ) || c=='.' || c=='_' || c==':' ) {
                        literal( c );
                        break;
                    }
                    else {
                        other( c );
                        break;
                    }
            }
        }
        end( 'a' );
        reader.close();
    }
    
    public boolean isWhiteSpace(char c) {
    	return c == ' ';
    }

    public boolean isLetterOrDigit(char c) {
    	if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) return true;
    	if ('0' <= c && c <= '9') return true;
    	return false;
    }
    
    boolean preOpenbracket( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".preOpenbracket(): " + o );
        if ( m_start ) {
            m_start = false;
            m_handler.startDocument();
            m_tagname.setLength( 0 );
        }
        return true;
    }


    boolean pretagstartLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".pretagstartLiteral(): " + o );
        m_tagname.setLength( 0 );
        m_args = new Hashtable();
        m_tagname.append( o );
        return true;
    }


    boolean dtdaWhitespace( char o ) throws LogicError {
        // add your code hereafter...
        String s = m_dtda.toString().trim();
        if ( !s.equals( "DOCTYPE" ) )
            throw new LogicError( "Expected 'DOCTYPE', found: '" + s + "'" );
        return true;
    }


    boolean dtdaLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_dtda.append( o );
        return true;
    }


    boolean cdataaOpensqbracket( char o ) throws LogicError {
        // add your code hereafter...
        String s = m_cdataa.toString().trim();
        if ( !s.equals( "CDATA" ) )
            throw new LogicError( "expected 'CDATA' but found '" +  s + "'" );
        return true;
    }


    boolean cdataaLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_cdataa.append( o );
        return true;
    }


    boolean cdataLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_charArray[0] = o;
        m_handler.characters( m_charArray, 0, 1 );
        return true;
    }


    boolean cdatabClosesqbracket( char o ) throws LogicError {
        // add your code hereafter...
        return true;
    }


    boolean cdatacLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_charArray[0] = ']';
        m_handler.characters( m_charArray, 0, 1 );
        m_charArray[0] = o;
        m_handler.characters( m_charArray, 0, 1 );
        return true;
    }


    boolean cdatacClosesqbracket( char o ) throws LogicError {
        // add your code hereafter...
        return true;
    }


    boolean cdatadClosebracket( char o ) throws LogicError {
        // add your code hereafter...
        return true;
    }


    boolean commentaOpensqbracket( char o ) throws LogicError {
        // add your code hereafter...
        m_cdataa.setLength( 0 );
        return true;
    }


    boolean commentaDTD( char o ) throws LogicError {
        // add your code hereafter...
        m_dtda.setLength( 0 );
//        m_dtdc.setLength( 0 );
//        m_dtde.setLength( 0 );
        m_dtda.append( o );
        return true;
    }


    boolean textLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".textLiteral(): " + o );
        if ( m_stack.isEmpty() && m_rootFound && !Character.isWhitespace( o ) )
            throw new LogicError( "Unexpected data after end of document!" );
        m_charArray[0] = o;
        m_handler.characters( m_charArray, 0, 1 );
        return true;
    }


    boolean textEnd( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".textEnd()" );
        if ( !m_stack.isEmpty() ) {
            String tag = (String)m_stack.pop();
            throw new LogicError( "Premature end of document: '" + tag + "' tag not closed" );
        }
        m_handler.endDocument();
        return true;
    }


    boolean tagstartSlash( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".tagstartSlash(): " + o );
        m_tagname.setLength( 0 );
        return true;
    }


    boolean tagstartLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".tagstartLiteral(): " + o );
        m_tagname.setLength( 0 );
        m_args = new Hashtable();
        m_tagname.append( o );
        return true;
    }


    boolean closetagnameClosebracket( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".closetagnameClosebracket(): " + o );
        if ( m_stack.isEmpty() )
            throw new LogicError( "No open tags left on stack!" );
        String tagname = m_tagname.toString();
        String otagname = (String)m_stack.pop();
        if ( !tagname.equals( otagname ) )
            throw new LogicError( "open and close tags do not match!" );
        m_handler.endElement( tagname );
        return true;
    }


    boolean closetagnameLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".closetagnameLiteral(): " + o );
        m_tagname.append( o );
        return true;
    }


    boolean tagnameClosebracket( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".tagnameClosebracket(): " + o );
        if ( m_stack.isEmpty() && m_rootFound )
            throw new LogicError( "Only one root element is allowed!" );
        m_rootFound = true;
        String tagname = m_tagname.toString();
        m_stack.push( tagname );
        m_handler.startElement( tagname, m_args );
        return true;
    }


    boolean tagnameLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".tagnameLiteral(): " + o );
        m_tagname.append( o );
        return true;
    }


    boolean tagargsLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".tagargsLiteral(): " + o );
        m_lvalue.setLength( 0 );
        m_lvalue.append( o );
        return true;
    }


    boolean lvalueLiteral( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".lvalueLiteral(): " + o );
        m_lvalue.append( o );
        return true;
    }


    boolean equalSquote( char o ) throws LogicError {
        // add your code hereafter...
        m_rvalue.setLength( 0 );
        return true;
    }


    boolean equalDquote( char o ) throws LogicError {
        // add your code hereafter...
        m_rvalue.setLength( 0 );
        return true;
    }


    boolean sqopenLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_rvalue.append( o );
        return true;
    }


    boolean sqopenQuote( char o ) throws LogicError {
        // add your code hereafter...
        addArg();
        return true;
    }


    boolean dqopenLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_rvalue.append( o );
        return true;
    }


    boolean dqopenQuote( char o ) throws LogicError {
        // add your code hereafter...
        addArg();
        return true;
    }


    boolean rvalueLiteral( char o ) throws LogicError {
        // add your code hereafter...
        m_rvalue.append( o );
        return true;
    }


    boolean rvalueQuote( char o ) throws LogicError {
        // add your code hereafter...
        addArg();
        return true;
    }


    private void addArg() throws LogicError {
        String lvalue = m_lvalue.toString().trim();
        String rvalue = m_rvalue.toString();
        if ( m_args.containsKey( lvalue ) )
            throw new LogicError( "arg <" + lvalue + "> exists already!" );
        m_args.put( lvalue, rvalue );
    }


    boolean singletagClosebracket( char o ) throws LogicError {
        // add your code hereafter...
        // System.out.println( getClass().getName() + ".singletagClosebracket(): " + o );
        m_handler.startElement( m_tagname.toString(), m_args );
        m_handler.endElement( m_tagname.toString() );
        return true;
    }
}
