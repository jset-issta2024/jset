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
 * Class <code>UnionExpr</code> represents a union of other expressions.
 */
public class UnionExpr extends NaryExpr
{
  /**
   * Adds expression to union.
   * @param expr expression to be added.<BR>
   * If the expression is another union expression, the function does not add
   * it as a subexpression. Instead, its subexpressions are added.<BR>
   * Example<BR>
   * <code>a | (b | c)</code> will be one union expression<BR>
   * <code>a | b | c</code>.
   */
  public void addExpr(Expr expr, int operator)
  {
    if (expr instanceof UnionExpr)
    {
      UnionExpr unionexpr = (UnionExpr) expr;
      Expr first = unionexpr.getExpr(0);
      first.setOperator(operator);
      subs.addAll(unionexpr.subs);
    }
    else
    {
      expr.setOperator(operator);
      subs.add(expr);
    }
  }

  /**
   * Adds expression to union.
   * @param expr expression to be added.<BR>
   * Equivalent to <code>addExpr(expr, Expr.U_OP);</code>
   */
  public void addExpr(Expr expr)
  {
    addExpr(expr, Expr.U_OP);
  }
}

