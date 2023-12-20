/* Generated By:JJTree&JavaCC: Do not edit this line. ParserTokenManager.java */
package org.tautua.markdownpapers.parser;


/** Token Manager. */
public class ParserTokenManager implements ParserConstants
{

   /** Debug output. */
   public  java.io.PrintStream debugStream = System.out;
   /** Set debug output. */
   public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
   private final int jjStopStringLiteralDfa_0(int pos, long active0)
   {
      switch (pos)
      {
         case 0:
            if ((active0 & 0x400L) != 0L)
               return 4;
            if ((active0 & 0x200L) != 0L)
               return 10;
            return -1;
         default :
            return -1;
      }
   }
   private final int jjStartNfa_0(int pos, long active0)
   {
      return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
   }
   private int jjStopAtPos(int pos, int kind)
   {
      jjmatchedKind = kind;
      jjmatchedPos = pos;
      return pos + 1;
   }
   private int jjMoveStringLiteralDfa0_0()
   {
      switch(curChar)
      {
         case 9:
            return jjStopAtPos(0, 2);
         case 32:
            return jjStopAtPos(0, 1);
         case 33:
            return jjStopAtPos(0, 12);
         case 34:
            return jjStopAtPos(0, 16);
         case 35:
            return jjStopAtPos(0, 27);
         case 38:
            return jjStartNfaWithStates_0(0, 9, 10);
         case 39:
            return jjStopAtPos(0, 28);
         case 40:
            return jjStopAtPos(0, 21);
         case 41:
            return jjStopAtPos(0, 26);
         case 42:
            return jjStopAtPos(0, 30);
         case 43:
            return jjStopAtPos(0, 24);
         case 45:
            jjmatchedKind = 23;
            return jjMoveStringLiteralDfa1_0(0x4000L);
         case 46:
            return jjStopAtPos(0, 17);
         case 47:
            return jjStopAtPos(0, 29);
         case 58:
            return jjStopAtPos(0, 15);
         case 60:
            jjmatchedKind = 22;
            return jjMoveStringLiteralDfa1_0(0x2000L);
         case 61:
            return jjStopAtPos(0, 18);
         case 62:
            return jjStopAtPos(0, 19);
         case 91:
            return jjStopAtPos(0, 20);
         case 92:
            return jjStartNfaWithStates_0(0, 10, 4);
         case 93:
            return jjStopAtPos(0, 25);
         case 95:
            return jjStopAtPos(0, 31);
         case 96:
            return jjStopAtPos(0, 11);
         default :
            return jjMoveNfa_0(0, 0);
      }
   }
   private int jjMoveStringLiteralDfa1_0(long active0)
   {
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) {
         jjStopStringLiteralDfa_0(0, active0);
         return 1;
      }
      switch(curChar)
      {
         case 33:
            return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
         case 45:
            return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
         default :
            break;
      }
      return jjStartNfa_0(0, active0);
   }
   private int jjMoveStringLiteralDfa2_0(long old0, long active0)
   {
      if (((active0 &= old0)) == 0L)
         return jjStartNfa_0(0, old0);
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) {
         jjStopStringLiteralDfa_0(1, active0);
         return 2;
      }
      switch(curChar)
      {
         case 45:
            return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
         case 62:
            if ((active0 & 0x4000L) != 0L)
               return jjStopAtPos(2, 14);
            break;
         default :
            break;
      }
      return jjStartNfa_0(1, active0);
   }
   private int jjMoveStringLiteralDfa3_0(long old0, long active0)
   {
      if (((active0 &= old0)) == 0L)
         return jjStartNfa_0(1, old0);
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) {
         jjStopStringLiteralDfa_0(2, active0);
         return 3;
      }
      switch(curChar)
      {
         case 45:
            if ((active0 & 0x2000L) != 0L)
               return jjStopAtPos(3, 13);
            break;
         default :
            break;
      }
      return jjStartNfa_0(2, active0);
   }
   private int jjStartNfaWithStates_0(int pos, int kind, int state)
   {
      jjmatchedKind = kind;
      jjmatchedPos = pos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return pos + 1; }
      return jjMoveNfa_0(state, pos + 1);
   }
   static final long[] jjbitVec0 = {
           0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
   };
   static final long[] jjbitVec2 = {
           0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
   };
   private int jjMoveNfa_0(int startState, int curPos)
   {
      int startsAt = 0;
      jjnewStateCnt = 21;
      int i = 1;
      jjstateSet[0] = startState;
      int kind = 0x7fffffff;
      for (;;)
      {
         if (++jjround == 0x7fffffff)
            ReInitRounds();
         if (curChar < 64)
         {
            long l = 1L << curChar;
            do
            {
               switch(jjstateSet[--i])
               {
                  case 10:
                     if (curChar == 35)
                        jjAddStates(0, 1);
                     break;
                  case 0:
                     if ((0x88001030ffffd9ffL & l) != 0L)
                     {
                        if (kind > 7)
                           kind = 7;
                        jjCheckNAdd(5);
                     }
                     else if ((0x3ff000000000000L & l) != 0L)
                     {
                        if (kind > 8)
                           kind = 8;
                        jjCheckNAdd(6);
                     }
                     else if ((0x2400L & l) != 0L)
                     {
                        if (kind > 3)
                           kind = 3;
                     }
                     else if (curChar == 38)
                        jjAddStates(2, 3);
                     if (curChar == 13)
                        jjstateSet[jjnewStateCnt++] = 1;
                     break;
                  case 1:
                     if (curChar == 10 && kind > 3)
                        kind = 3;
                     break;
                  case 2:
                     if (curChar == 13)
                        jjstateSet[jjnewStateCnt++] = 1;
                     break;
                  case 4:
                     if ((0x40006f0a00000000L & l) != 0L && kind > 6)
                        kind = 6;
                     break;
                  case 5:
                     if ((0x88001030ffffd9ffL & l) == 0L)
                        break;
                     if (kind > 7)
                        kind = 7;
                     jjCheckNAdd(5);
                     break;
                  case 6:
                     if ((0x3ff000000000000L & l) == 0L)
                        break;
                     if (kind > 8)
                        kind = 8;
                     jjCheckNAdd(6);
                     break;
                  case 7:
                     if (curChar == 38)
                        jjAddStates(2, 3);
                     break;
                  case 9:
                     if (curChar == 59 && kind > 4)
                        kind = 4;
                     break;
                  case 11:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAddTwoStates(12, 13);
                     break;
                  case 12:
                     if (curChar == 59 && kind > 5)
                        kind = 5;
                     break;
                  case 13:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAddStates(4, 6);
                     break;
                  case 14:
                  case 19:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAdd(12);
                     break;
                  case 15:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAddTwoStates(14, 12);
                     break;
                  case 17:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAddTwoStates(12, 18);
                     break;
                  case 18:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAddStates(7, 9);
                     break;
                  case 20:
                     if ((0x3ff000000000000L & l) != 0L)
                        jjCheckNAddTwoStates(19, 12);
                     break;
                  default : break;
               }
            } while(i != startsAt);
         }
         else if (curChar < 128)
         {
            long l = 1L << (curChar & 077);
            do
            {
               switch(jjstateSet[--i])
               {
                  case 10:
                  case 8:
                     if ((0x7fffffe07fffffeL & l) != 0L)
                        jjCheckNAddTwoStates(8, 9);
                     break;
                  case 0:
                     if ((0xfffffffe47ffffffL & l) != 0L)
                     {
                        if (kind > 7)
                           kind = 7;
                        jjCheckNAdd(5);
                     }
                     else if (curChar == 92)
                        jjstateSet[jjnewStateCnt++] = 4;
                     break;
                  case 3:
                     if (curChar == 92)
                        jjstateSet[jjnewStateCnt++] = 4;
                     break;
                  case 4:
                     if ((0x28000001b8000000L & l) != 0L && kind > 6)
                        kind = 6;
                     break;
                  case 5:
                     if ((0xfffffffe47ffffffL & l) == 0L)
                        break;
                     if (kind > 7)
                        kind = 7;
                     jjCheckNAdd(5);
                     break;
                  case 16:
                     if (curChar == 120)
                        jjstateSet[jjnewStateCnt++] = 17;
                     break;
                  case 17:
                     if ((0x7e0000007eL & l) != 0L)
                        jjCheckNAddTwoStates(12, 18);
                     break;
                  case 18:
                     if ((0x7e0000007eL & l) != 0L)
                        jjCheckNAddStates(7, 9);
                     break;
                  case 19:
                     if ((0x7e0000007eL & l) != 0L)
                        jjCheckNAdd(12);
                     break;
                  case 20:
                     if ((0x7e0000007eL & l) != 0L)
                        jjCheckNAddTwoStates(19, 12);
                     break;
                  default : break;
               }
            } while(i != startsAt);
         }
         else
         {
            int hiByte = (int)(curChar >> 8);
            int i1 = hiByte >> 6;
            long l1 = 1L << (hiByte & 077);
            int i2 = (curChar & 0xff) >> 6;
            long l2 = 1L << (curChar & 077);
            do
            {
               switch(jjstateSet[--i])
               {
                  case 0:
                  case 5:
                     if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                        break;
                     if (kind > 7)
                        kind = 7;
                     jjCheckNAdd(5);
                     break;
                  default : break;
               }
            } while(i != startsAt);
         }
         if (kind != 0x7fffffff)
         {
            jjmatchedKind = kind;
            jjmatchedPos = curPos;
            kind = 0x7fffffff;
         }
         ++curPos;
         if ((i = jjnewStateCnt) == (startsAt = 21 - (jjnewStateCnt = startsAt)))
            return curPos;
         try { curChar = input_stream.readChar(); }
         catch(java.io.IOException e) { return curPos; }
      }
   }
   static final int[] jjnextStates = {
           11, 16, 8, 10, 14, 12, 15, 19, 12, 20,
   };
   private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
   {
      switch(hiByte)
      {
         case 0:
            return ((jjbitVec2[i2] & l2) != 0L);
         default :
            if ((jjbitVec0[i1] & l1) != 0L)
               return true;
            return false;
      }
   }

   /** Token literal values. */
   public static final String[] jjstrLiteralImages = {
           "", "\40", "\11", null, null, null, null, null, null, "\46", "\134", "\140",
           "\41", "\74\41\55\55", "\55\55\76", "\72", "\42", "\56", "\75", "\76", "\133", "\50",
           "\74", "\55", "\53", "\135", "\51", "\43", "\47", "\57", "\52", "\137", };

   /** Lexer state names. */
   public static final String[] lexStateNames = {
           "DEFAULT",
   };
   protected SimpleCharStream input_stream;
   private final int[] jjrounds = new int[21];
   private final int[] jjstateSet = new int[42];
   protected char curChar;
   /** Constructor. */
   public ParserTokenManager(SimpleCharStream stream){
      if (SimpleCharStream.staticFlag)
         throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
      input_stream = stream;
   }

   /** Constructor. */
   public ParserTokenManager(SimpleCharStream stream, int lexState){
      this(stream);
      SwitchTo(lexState);
   }

   /** Reinitialise parser. */
   public void ReInit(SimpleCharStream stream)
   {
      jjmatchedPos = jjnewStateCnt = 0;
      curLexState = defaultLexState;
      input_stream = stream;
      ReInitRounds();
   }
   private void ReInitRounds()
   {
      int i;
      jjround = 0x80000001;
      for (i = 21; i-- > 0;)
         jjrounds[i] = 0x80000000;
   }

   /** Reinitialise parser. */
   public void ReInit(SimpleCharStream stream, int lexState)
   {
      ReInit(stream);
      SwitchTo(lexState);
   }

   /** Switch to specified lex state. */
   public void SwitchTo(int lexState)
   {
      if (lexState >= 1 || lexState < 0)
         throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
      else
         curLexState = lexState;
   }

   protected Token jjFillToken()
   {
      final Token t;
      final String curTokenImage;
      final int beginLine;
      final int endLine;
      final int beginColumn;
      final int endColumn;
      String im = jjstrLiteralImages[jjmatchedKind];
      curTokenImage = (im == null) ? input_stream.GetImage() : im;
      beginLine = input_stream.getBeginLine();
      beginColumn = input_stream.getBeginColumn();
      endLine = input_stream.getEndLine();
      endColumn = input_stream.getEndColumn();
      t = Token.newToken(jjmatchedKind, curTokenImage);

      t.beginLine = beginLine;
      t.endLine = endLine;
      t.beginColumn = beginColumn;
      t.endColumn = endColumn;

      return t;
   }

   int curLexState = 0;
   int defaultLexState = 0;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;

   /** Get the next Token. */
   //for token symbolic
   public static int token_index = 0;
   public static String token_image = "";
   public static int oldtoken = -1;
   public static int oldpos = -1;
   public Token getNextToken()
   {
      Token matchedToken;
      int curPos = 0;

      EOFLoop :
      for (;;)
      {
         try
         {
            curChar = input_stream.BeginToken();
         }
         catch(java.io.IOException e)
         {
            jjmatchedKind = 0;
            matchedToken = jjFillToken();
//				    return matchedToken;
            if(matchedToken.kind==0) // unsymb EOF
               return matchedToken;
            return matchedToken;
         }

         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         if (jjmatchedKind != 0x7fffffff)
         {
            if (jjmatchedPos + 1 < curPos)
               input_stream.backup(curPos - jjmatchedPos - 1);
            matchedToken = jjFillToken();
//				    return matchedToken;
            if(matchedToken.kind==0) // unsymb EOF
               return matchedToken;

            return matchedToken;
         }
         int error_line = input_stream.getEndLine();
         int error_column = input_stream.getEndColumn();
         String error_after = null;
         boolean EOFSeen = false;
         try { input_stream.readChar(); input_stream.backup(1); }
         catch (java.io.IOException e1) {
            EOFSeen = true;
            error_after = curPos <= 1 ? "" : input_stream.GetImage();
            if (curChar == '\n' || curChar == '\r') {
               error_line++;
               error_column = 0;
            }
            else
               error_column++;
         }
         if (!EOFSeen) {
            input_stream.backup(1);
            error_after = curPos <= 1 ? "" : input_stream.GetImage();
         }
         throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
      }
   }

   private void jjCheckNAdd(int state)
   {
      if (jjrounds[state] != jjround)
      {
         jjstateSet[jjnewStateCnt++] = state;
         jjrounds[state] = jjround;
      }
   }
   private void jjAddStates(int start, int end)
   {
      do {
         jjstateSet[jjnewStateCnt++] = jjnextStates[start];
      } while (start++ != end);
   }
   private void jjCheckNAddTwoStates(int state1, int state2)
   {
      jjCheckNAdd(state1);
      jjCheckNAdd(state2);
   }

   private void jjCheckNAddStates(int start, int end)
   {
      do {
         jjCheckNAdd(jjnextStates[start]);
      } while (start++ != end);
   }

}