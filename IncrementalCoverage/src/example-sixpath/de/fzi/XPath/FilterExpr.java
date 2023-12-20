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
 * Class <code>FilterExpr</code> represents a filtering expression.
 */
public class FilterExpr extends Expr
{
  Expr primary;
  private java.util.ArrayList predicates;
  LocationPath path;

  /**
   * Constructs new expression.
   * @param theprimary primary expression that is filtered and queried.
   */
  public FilterExpr(Expr theprimary)
  {
    primary = theprimary;
    predicates = new java.util.ArrayList(1);
  }

  /**
   * Adds predicate expression.
   * @param predicate predicate expression to be added.
   */

  public void addPredicate(Expr predicate)
  {
    predicates.add(predicate);
  }

  /**
   * Returns predicate expression at given position.
   * @return Returns predicate at given position or null if no predicate at
   * given position.
   * @param index position of predicate.
   */
  public Expr getPredicate(int index)
  {
    if (index >=0 && index <predicates.size())
      return (Expr) predicates.get(index);
    else return null;
  }

  /**
   * Returns count of predicates.
   * @return Returns count of predicates of this filter expression.
   */
  public int getPredicatesCount()
  {
    return predicates.size();
  }

  /**
   * Returns string representation of filter expression.
   * @return Returns string representation of filter expression.
   */
  public String toString()
  {
    String res = super.toString();
    if (primary instanceof Literal ||
        primary instanceof de.fzi.XPath.Number ||
        primary instanceof Variable ||
        primary instanceof FunctionCall)
    res = res + primary;
    else
    res = res + "(" + primary + ")";
    for (int i=0; i < getPredicatesCount(); i++)
    {
      res = res + "[" + getPredicate(i) +"]";
    }
    if (getLocationPath()!=null) res = res + "/" + path;
    return res;
  }

  /**
   * Sets location path following that follows after this filter expression.
   * @param lp location path.
   */
  public void setLocationPath(LocationPath lp)
  {
    path = lp;
  }

  /**
   * Returns location path of this expression.
   * @return Returns location path of this expression. Null if was not set.
   */
  public LocationPath getLocationPath()
  {
    return path;
  }
}

