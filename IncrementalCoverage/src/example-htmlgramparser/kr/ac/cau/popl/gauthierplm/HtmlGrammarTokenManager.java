/* HtmlGrammarTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. HtmlGrammarTokenManager.java */
package kr.ac.cau.popl.gauthierplm;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileWriter;

/** Token Manager. */
@SuppressWarnings("unused")public class HtmlGrammarTokenManager implements HtmlGrammarConstants {

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_5(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0xeL) != 0L)
         {
            jjmatchedKind = 19;
            return 4;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_5(int pos, long active0){
   return jjMoveNfa_5(jjStopStringLiteralDfa_5(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_5(){
   switch(curChar)
   {
      case 60:
         jjmatchedKind = 15;
         return jjMoveStringLiteralDfa1_5(0x70000L);
      case 61:
         return jjMoveStringLiteralDfa1_5(0xeL);
      default :
         return jjMoveNfa_5(5, 0);
   }
}
private int jjMoveStringLiteralDfa1_5(long active0){
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_5(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x2L) != 0L)
            return jjStopAtPos(1, 1);
         break;
      case 13:
         if ((active0 & 0x8L) != 0L)
         {
            jjmatchedKind = 3;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_5(active0, 0x4L);
      case 33:
         if ((active0 & 0x40000L) != 0L)
         {
            jjmatchedKind = 18;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_5(active0, 0x20000L);
      case 47:
         if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(1, 16);
         break;
      default :
         break;
   }
   return jjStartNfa_5(0, active0);
}
private int jjMoveStringLiteralDfa2_5(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_5(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_5(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(2, 2);
         break;
      case 45:
         return jjMoveStringLiteralDfa3_5(active0, 0x20000L);
      default :
         break;
   }
   return jjStartNfa_5(1, active0);
}
private int jjMoveStringLiteralDfa3_5(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_5(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_5(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 45:
         if ((active0 & 0x20000L) != 0L)
            return jjStopAtPos(3, 17);
         break;
      default :
         break;
   }
   return jjStartNfa_5(2, active0);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_5(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 5;
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
               case 5:
                  if ((0xefffffffffffdbffL & l) != 0L)
                  {
                     if (kind > 19)
                        kind = 19;
                     { jjCheckNAdd(4); }
                  }
                  else if ((0x2400L & l) != 0L)
                  {
                     if (kind > 14)
                        kind = 14;
                  }
                  if ((0x100000200L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  else if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 0:
                  if ((0x100000200L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 1:
                  if (curChar == 10 && kind > 14)
                     kind = 14;
                  break;
               case 2:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 3:
                  if ((0x2400L & l) != 0L && kind > 14)
                     kind = 14;
                  break;
               case 4:
                  if ((0xefffffffffffdbffL & l) == 0L)
                     break;
                  if (kind > 19)
                     kind = 19;
                  { jjCheckNAdd(4); }
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
               case 5:
               case 4:
                  kind = 19;
                  { jjCheckNAdd(4); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 5:
               case 4:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 19)
                     kind = 19;
                  { jjCheckNAdd(4); }
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
      if ((i = jjnewStateCnt) == (startsAt = 5 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_3(int pos, long active0){
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_3(int pos, long active0){
   return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_3(){
   switch(curChar)
   {
      case 47:
         return jjMoveStringLiteralDfa1_3(0x1000000L);
      case 61:
         jjmatchedKind = 25;
         return jjMoveStringLiteralDfa1_3(0xeL);
      case 62:
         return jjStopAtPos(0, 23);
      default :
         return jjMoveNfa_3(1, 0);
   }
}
private int jjMoveStringLiteralDfa1_3(long active0){
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_3(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x2L) != 0L)
            return jjStopAtPos(1, 1);
         break;
      case 13:
         if ((active0 & 0x8L) != 0L)
         {
            jjmatchedKind = 3;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_3(active0, 0x4L);
      case 62:
         if ((active0 & 0x1000000L) != 0L)
            return jjStopAtPos(1, 24);
         break;
      default :
         break;
   }
   return jjStartNfa_3(0, active0);
}
private int jjMoveStringLiteralDfa2_3(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_3(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_3(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(2, 2);
         break;
      default :
         break;
   }
   return jjStartNfa_3(1, active0);
}
private int jjMoveNfa_3(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 3;
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
               case 1:
               case 0:
                  if ((0x100002600L & l) == 0L)
                     break;
                  kind = 21;
                  { jjCheckNAdd(0); }
                  break;
               case 2:
                  if ((0x7ff600000000000L & l) == 0L)
                     break;
                  kind = 22;
                  jjstateSet[jjnewStateCnt++] = 2;
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
               case 1:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  { jjCheckNAdd(2); }
                  break;
               case 2:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  { jjCheckNAdd(2); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
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
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_2(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0xeL) != 0L)
         {
            jjmatchedKind = 27;
            return 1;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_2(int pos, long active0){
   return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_2(){
   switch(curChar)
   {
      case 61:
         return jjMoveStringLiteralDfa1_2(0xeL);
      default :
         return jjMoveNfa_2(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_2(long active0){
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_2(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x2L) != 0L)
            return jjStopAtPos(1, 1);
         break;
      case 13:
         if ((active0 & 0x8L) != 0L)
         {
            jjmatchedKind = 3;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_2(active0, 0x4L);
      default :
         break;
   }
   return jjStartNfa_2(0, active0);
}
private int jjMoveStringLiteralDfa2_2(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_2(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_2(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(2, 2);
         break;
      default :
         break;
   }
   return jjStartNfa_2(1, active0);
}
private int jjMoveNfa_2(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 8;
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
               case 0:
                  if ((0xbfffff7affffd9ffL & l) != 0L)
                  {
                     if (kind > 27)
                        kind = 27;
                     { jjCheckNAdd(1); }
                  }
                  else if ((0x100002600L & l) != 0L)
                  {
                     if (kind > 26)
                        kind = 26;
                  }
                  else if (curChar == 39)
                     { jjCheckNAddTwoStates(6, 7); }
                  else if (curChar == 34)
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 1:
                  if ((0xbfffff7affffd9ffL & l) == 0L)
                     break;
                  if (kind > 27)
                     kind = 27;
                  { jjCheckNAdd(1); }
                  break;
               case 2:
                  if (curChar == 34)
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 3:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(3, 4); }
                  break;
               case 4:
                  if (curChar == 34 && kind > 27)
                     kind = 27;
                  break;
               case 5:
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(6, 7); }
                  break;
               case 6:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(6, 7); }
                  break;
               case 7:
                  if (curChar == 39 && kind > 27)
                     kind = 27;
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
               case 0:
               case 1:
                  if (kind > 27)
                     kind = 27;
                  { jjCheckNAdd(1); }
                  break;
               case 3:
                  { jjAddStates(3, 4); }
                  break;
               case 6:
                  { jjAddStates(5, 6); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
               case 1:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 27)
                     kind = 27;
                  { jjCheckNAdd(1); }
                  break;
               case 3:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(3, 4); }
                  break;
               case 6:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(5, 6); }
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
      if ((i = jjnewStateCnt) == (startsAt = 8 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_4(int pos, long active0){
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_4(int pos, long active0){
   return jjMoveNfa_4(jjStopStringLiteralDfa_4(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_4(){
   switch(curChar)
   {
      case 61:
         return jjMoveStringLiteralDfa1_4(0xeL);
      default :
         return jjMoveNfa_4(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_4(long active0){
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_4(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x2L) != 0L)
            return jjStopAtPos(1, 1);
         break;
      case 13:
         if ((active0 & 0x8L) != 0L)
         {
            jjmatchedKind = 3;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_4(active0, 0x4L);
      default :
         break;
   }
   return jjStartNfa_4(0, active0);
}
private int jjMoveStringLiteralDfa2_4(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_4(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_4(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(2, 2);
         break;
      default :
         break;
   }
   return jjStartNfa_4(1, active0);
}
private int jjMoveNfa_4(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 2;
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
               case 1:
                  if ((0x7ff600000000000L & l) == 0L)
                     break;
                  kind = 20;
                  jjstateSet[jjnewStateCnt++] = 1;
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
               case 0:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  { jjCheckNAdd(1); }
                  break;
               case 1:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  { jjCheckNAdd(1); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
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
      if ((i = jjnewStateCnt) == (startsAt = 2 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0xeL) != 0L)
         {
            jjmatchedKind = 32;
            return 7;
         }
         return -1;
      case 1:
         if ((active0 & 0xeL) != 0L)
            return 7;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 61:
         return jjMoveStringLiteralDfa1_0(0xeL);
      case 62:
         return jjStopAtPos(0, 33);
      default :
         return jjMoveNfa_0(7, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x2L) != 0L)
            return jjStartNfaWithStates_0(1, 1, 7);
         break;
      case 13:
         if ((active0 & 0x8L) != 0L)
         {
            jjmatchedKind = 3;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x4L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x4L) != 0L)
            return jjStartNfaWithStates_0(2, 2, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 7;
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
               case 7:
                  if ((0xbfffffffffffffffL & l) != 0L)
                  {
                     if (kind > 32)
                        kind = 32;
                     { jjCheckNAddStates(7, 9); }
                  }
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(5, 6); }
                  else if (curChar == 34)
                     { jjCheckNAddTwoStates(2, 3); }
                  break;
               case 0:
                  if ((0xbfffffffffffffffL & l) == 0L)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddStates(7, 9); }
                  break;
               case 1:
                  if (curChar == 34)
                     { jjCheckNAddTwoStates(2, 3); }
                  break;
               case 2:
                  if ((0xfffffffbffffdbffL & l) != 0L)
                     { jjCheckNAddTwoStates(2, 3); }
                  break;
               case 3:
                  if (curChar != 34)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddStates(7, 9); }
                  break;
               case 4:
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(5, 6); }
                  break;
               case 5:
                  if ((0xffffff7fffffdbffL & l) != 0L)
                     { jjCheckNAddTwoStates(5, 6); }
                  break;
               case 6:
                  if (curChar != 39)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddStates(7, 9); }
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
               case 7:
               case 0:
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddStates(7, 9); }
                  break;
               case 2:
                  { jjAddStates(10, 11); }
                  break;
               case 5:
                  { jjAddStates(12, 13); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 7:
               case 0:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddStates(7, 9); }
                  break;
               case 2:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(10, 11); }
                  break;
               case 5:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(12, 13); }
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
      if ((i = jjnewStateCnt) == (startsAt = 7 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_1(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0x20000000L) != 0L)
            return 12;
         if ((active0 & 0xeL) != 0L)
         {
            jjmatchedKind = 31;
            return 3;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_1(int pos, long active0){
   return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
}
private int jjMoveStringLiteralDfa0_1(){
   switch(curChar)
   {
      case 45:
         return jjStartNfaWithStates_1(0, 29, 12);
      case 61:
         return jjMoveStringLiteralDfa1_1(0xeL);
      default :
         return jjMoveNfa_1(1, 0);
   }
}
private int jjMoveStringLiteralDfa1_1(long active0){
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_1(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x2L) != 0L)
            return jjStopAtPos(1, 1);
         break;
      case 13:
         if ((active0 & 0x8L) != 0L)
         {
            jjmatchedKind = 3;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_1(active0, 0x4L);
      default :
         break;
   }
   return jjStartNfa_1(0, active0);
}
private int jjMoveStringLiteralDfa2_1(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_1(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(IOException e) {
      jjStopStringLiteralDfa_1(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(2, 2);
         break;
      default :
         break;
   }
   return jjStartNfa_1(1, active0);
}
private int jjStartNfaWithStates_1(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(IOException e) { return pos + 1; }
   return jjMoveNfa_1(state, pos + 1);
}
private int jjMoveNfa_1(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 16;
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
               case 12:
                  if (curChar == 62)
                  {
                     if (kind > 28)
                        kind = 28;
                  }
                  else if (curChar == 45)
                     { jjCheckNAddTwoStates(13, 14); }
                  break;
               case 1:
                  if ((0xffffdf7bffffdbffL & l) != 0L)
                  {
                     if (kind > 31)
                        kind = 31;
                     { jjCheckNAdd(3); }
                  }
                  else if ((0x8400000000L & l) != 0L)
                  {
                     if (kind > 31)
                        kind = 31;
                  }
                  else if ((0x2400L & l) != 0L)
                  {
                     if (kind > 30)
                        kind = 30;
                  }
                  else if (curChar == 45)
                     { jjAddStates(14, 15); }
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(8, 9); }
                  else if (curChar == 34)
                     { jjCheckNAddTwoStates(5, 6); }
                  else if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 0:
                  if (curChar == 10 && kind > 30)
                     kind = 30;
                  break;
               case 2:
                  if ((0x2400L & l) != 0L && kind > 30)
                     kind = 30;
                  break;
               case 3:
                  if ((0xffffdf7bffffdbffL & l) == 0L)
                     break;
                  if (kind > 31)
                     kind = 31;
                  { jjCheckNAdd(3); }
                  break;
               case 4:
                  if (curChar == 34)
                     { jjCheckNAddTwoStates(5, 6); }
                  break;
               case 5:
                  if ((0xfffffffbffffdbffL & l) != 0L)
                     { jjCheckNAddTwoStates(5, 6); }
                  break;
               case 6:
                  if (curChar == 34 && kind > 31)
                     kind = 31;
                  break;
               case 7:
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(8, 9); }
                  break;
               case 8:
                  if ((0xffffff7fffffdbffL & l) != 0L)
                     { jjCheckNAddTwoStates(8, 9); }
                  break;
               case 9:
                  if (curChar == 39 && kind > 31)
                     kind = 31;
                  break;
               case 10:
                  if ((0x8400000000L & l) != 0L && kind > 31)
                     kind = 31;
                  break;
               case 11:
                  if (curChar == 45)
                     { jjAddStates(14, 15); }
                  break;
               case 13:
                  if (curChar == 32)
                     { jjCheckNAddTwoStates(13, 14); }
                  break;
               case 14:
                  if (curChar == 62 && kind > 28)
                     kind = 28;
                  break;
               case 15:
                  if (curChar == 62 && kind > 28)
                     kind = 28;
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
               case 1:
               case 3:
                  if (kind > 31)
                     kind = 31;
                  { jjCheckNAdd(3); }
                  break;
               case 5:
                  { jjAddStates(12, 13); }
                  break;
               case 8:
                  { jjAddStates(16, 17); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
               case 3:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 31)
                     kind = 31;
                  { jjCheckNAdd(3); }
                  break;
               case 5:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(12, 13); }
                  break;
               case 8:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(16, 17); }
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
      if ((i = jjnewStateCnt) == (startsAt = 16 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   0, 2, 3, 3, 4, 6, 7, 0, 1, 4, 2, 3, 5, 6, 12, 15, 
   8, 9, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, "\74", "\74\57", "\74\41\55\55", "\74\41", null, null, null, null, 
"\76", "\57\76", "\75", null, null, null, "\55", null, null, null, "\76", };
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

int curLexState = 5;
int defaultLexState = 5;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }

   switch(curLexState)
   {
     case 0:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       break;
     case 2:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_2();
       break;
     case 3:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_3();
       break;
     case 4:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_4();
       break;
     case 5:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_5();
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
           }
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (IOException e1) {
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

    /** Constructor. */
    public HtmlGrammarTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public HtmlGrammarTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
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
    for (i = 16; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit( SimpleCharStream stream, int lexState)
  {
  
    ReInit( stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 6 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }

/** Lexer state names. */
public static final String[] lexStateNames = {
   "LexDecl",
   "LexComment",
   "LexAttrVal",
   "LexInTag",
   "LexStartTag",
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 4, 1, 0, -1, 3, -1, -1, 5, 5, 
   2, -1, 3, 5, -1, -1, -1, -1, 5, 
};
static final long[] jjtoToken = {
   0x3fbdfc001L, 
};
static final long[] jjtoSkip = {
   0x420000eL, 
};
static final long[] jjtoSpecial = {
   0x4200000L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[16];
    private final int[] jjstateSet = new int[2 * 16];

    
    protected int curChar;
}
