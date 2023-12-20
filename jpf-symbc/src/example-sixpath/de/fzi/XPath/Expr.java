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
 * Class <code>Expr</code> correspons to XPath expression in general. It is a
 * base class for other types of expressions.
 */
public class Expr extends Object
{

  private static final int COUNT_OP = 14;
  /**
   * No operator code.
   */
  public static final int NOP_OP = -1;
  /**
   * <code>or</code> operator code.
   */
  public static final int OR_OP = 0;
  /**
   * <code>and</code> operator code.
   */
  public static final int AND_OP = 1;
  /**
   * <code>=</code> (equal) operator code.
   */
  public static final int EQ_OP = 2;
  /**
   * <code>!=</code> (not equal) operator code.
   */
  public static final int NEQ_OP = 3;
  /**
   * <code>&lt;</code> (less-than) operator code.
   */
  public static final int LT_OP = 4;
  /**
   * <code>&lt;=</code> (less-than or equal) operator code.
   */
  public static final int LTE_OP = 5;
  /**
   * <code>&gt;</code> (greater-than) operator code.
   */
  public static final int GT_OP = 6;
  /**
   * <code>&gt;=</code> (greater-than or equal) operator code.
   */
  public static final int GTE_OP = 7;
  /**
   * <code>+</code> (addition) operator code.
   */
  public static final int ADD_OP = 8;
  /**
   * <code>-</code> (substraction) operator code.
   */
  public static final int SUB_OP = 9;
  /**
   * <code>*</code> (multiplication) operator code.
   */
  public static final int MUL_OP = 10;
  /**
   * <code>div</code> (division) operator code.
   */
  public static final int DIV_OP = 11;
  /**
   * <code>mod</code> (modula) operator code.
   */
  public static final int MOD_OP = 12;
  /**
   * <code>|</code> (union) operator code.
   */
  public static final int U_OP = 13;

  private static final String[] OP_Names =
  {
    "OR",
    "AND",
    "=" , "!=",
    "<", "<=", ">", ">=",
    "+", "-",
    "*", "div", "mod",
    "|"
  };

  private int op = NOP_OP;
  private boolean positive = true;

  /**
   * Sets the sign of the expression.
   * @param thepositive if <code>true</code>, expression will become
   * positive, otherwise - negative.
   */
  public void setPositive(boolean thepositive)
  {
    positive = thepositive;
  }

  /**
   * Returns <code>true</code> if this expression is positive.
   * @return Returns <code>true</code> if this expression is positive.
   * Otherwise returns false.
   * @see #getNegative
   */
  public boolean getPositive()
  {
    return positive;
  }

  /**
   * Returns <code>true</code> if this expression is negative.
   * @return Returns <code>true</code> if this expression is negative.
   * Otherwise returns false.
   * @see #getPositive
   */
  public boolean getNegative()
  {
    return !positive;
  }

  /**
   * Returns string representation of expression.
   * @return Returns string representation of expression.
   */
  public String toString()
  {
    if (positive) return "";
    else return "-";
  }

  /**
   * Sets operator code of this expression.
   * @param operator code of operator (one of <code>CODE_OP</code> fields of this
   * class) that precedes this exception.<BR>
   * For example, if in an expression we have<BR>
   * <code>-a + b * - c</code><BR>
   * <code>-</code> will be the <em>sign</em> of expression <code>a</code>,
   * <code>+</code> will be the <em>operator</em> of expression <code>b</code>,
   * <code>*</code> will be the <em>operator</em> of expression <code>c</code>,
   * AND <code>-</code> will be the <em>sign</em> of expression
   * <code>c</code>.<BR>
   * @see #setPositive
   * @see #getPositive
   * @see #getNegative
   */
  public void setOperator(int operator)
  {
    op = operator;
  }

  /**
   * Returns operator code of this expression.
   * @return Returns code of operator  preceding this expression (one of
   * <code><em>CODE</em>_OP</code> fields of this class).
   */
  public int getOperator()
  {
    return op;
  }

  /**
   * Returns operator of this expression in string representation.
   * @return Returns string representing the operator preceding this expression
   * (one of<code><em>CODE</em>_OP</code> fields of this class).
   */
  public String getOperatorAsString()
  {
    return getOperatorName(op);
  }

  /**
   * Returns string representation of given Operator.
   * @param Operator Operator code.
   * @return Returns string representing given Operator or <code>""</code>
   * if Operator is not defined.
   */
  public static String getOperatorName(int operator)
  {
    if (operator >=0 && operator < COUNT_OP) return OP_Names[operator];
    else return "";
  }

}

