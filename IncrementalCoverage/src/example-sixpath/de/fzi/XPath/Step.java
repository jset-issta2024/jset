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
 * Class Step represents single location step.
 */
public class Step extends Object
{
  private int axis;
  private NodeTest nodetest;
  private java.util.ArrayList predicates;

  /**
   * Constructor. Initializes a step with empty list of predicates.
   * @param theaxis integer axis code of this location step.<BR>
   * TODO: no axis = Axis.CHILD axis.
   * @param thenodetext nodetest of this location step.
   * @see Axis
   * @see NodeTest
   */
  public Step(int theaxis, NodeTest thenodetest)
  {
    axis = theaxis;
    nodetest = thenodetest;
    predicates = new java.util.ArrayList();
  }

  /**
   * Returns axis code of this location step.
   * @return Returns axis code of this location step.
   * @see Axis
   */
  public int getAxis()
  {
    return axis;
  }

  /**
   * Returns axis code of this location step.
   * @return Returns axis code of this location step.
   * @see Axis
   */
  public NodeTest getNodeTest()
  {
    return nodetest;
  }

  /**
   * Adds predicate to this step.
   * @param predicate predicate to be added. Predicate itself is simply another
   * expression.
   */
  public void addPredicate(Expr predicate)
  {
    predicates.add(predicate);
  }

  /**
   * Returns predicate at given position.
   * @return Predicate at position <code>index</code> or null if no predicate
   * at this position.
   * @param index position of the predicate.
   * @see #getPredicatesCount
   */
  public Expr getPredicate(int index)
  {
    if (index >= 0 && index < getPredicatesCount())
      return (Expr) predicates.get(index);
    else
    return null;
  }

  /**
   * Returns count of predicates in this location step.
   * @return Returns count of predicates of this location step. If no
   * predicates, will return <code>0</code>.
   */
  public int getPredicatesCount()
  {
    return predicates.size();
  }

  /**
   * Returns string representation of this location step.
   * @return Returns string representation of this location step in form<BR>
   * <code>"<em>axis</em>::<em>nodetest</em>[<em>predicate</em>]...</code><BR>
   * where predicates may be omitted. Axis is always in full non-abbreviated
   * form.
   */
  public String toString()
  {
    String res = Axis.getAxisName(getAxis()) + "::" + getNodeTest();
    for (int i=0; i < getPredicatesCount(); i++)
    {
      res = res + "[" + getPredicate(i) +"]";
    }
    return res;
  }
}
