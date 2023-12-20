/*========================================================================*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License.
 *
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * See the License for the specific language governing rights and
 * limitations under the License.
 *
 * The Original Code is all this file.
 *
 * The Initial Developer of the Original Code is
 * Aleksei Valikov, Forchungszentrum Informatik (valikov@fzi.de).
 *
 * Contributor(s): none.
 *========================================================================*/
package de.fzi.XPath;

/**
 * <code>NaryExpr</code> represents an expression which is a superexpression
 * over other expressions involving operators of sam precedence. Thus,
 * <code>a * b div c mod d</code> requires only one superexpression which
 * would consist of four expressions associated from left to right. This
 * example equivalent to <code>((a * b) div c) mod d</code> but we will use
 * simplify the structure by not creating four superexpressions
 */
public class NaryExpr extends Expr
{

  protected java.util.ArrayList subs;

  /**
   * Constructor
   */
  public NaryExpr()
  {
    // Normally, expressions are binary.
    subs = new java.util.ArrayList(2);
  }

  /**
   * Returns subexpression with given position.
   * @param index index of an expression in subexpression list.<BR>
   * @return Returns subexpression with given position.
   * If there is no expression at given position, returns <code>null</code>.
   */

  public Expr getExpr(int index)
  {
    if (index >=0 && index < getExprsCount()) return (Expr) subs.get(index);
    else return null;
  }

  /**
   * Returns subexpression count.
   * @return Returns subexpression count.
   * This number should be normally greater than 1: n-ary expressions have
   * at least two operands.
   */
  public int getExprsCount()
  {
    return subs.size();
  }

  /**
   * Returns string representation of n-ary expression.
   * @return Returns string representation of n-ary expression.<BR>
   * It is a list of subexpressions delimited by their operations.
   */
  public String toString()
  {
    String res = super.toString();
    for (int i=0; i < getExprsCount(); i++)
    {
      if (getExpr(i).getOperator() != NOP_OP)
        res = res + " " + getExpr(i).getOperatorAsString() + " ";
      res = res + getExpr(i);
    }
    return res;
  }

}

