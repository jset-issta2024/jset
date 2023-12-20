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
 * Class <code>Axis</code> holds axis codes and provides two static functions,
 * {@link #getAxis getAxis} and {@link #getAxisName getAxisName} which convert
 * axis name to integer code and vice versa.
 */
public final class Axis extends Object
{

  private static final int AxesCount = 13;
  /**
   * Invalid axis.
   */
  public static final int INVALID = -1;
  /**
   * <code>"ancestor"</code> axis.
   */
  public static final int ANCESTOR = 0;
  /**
   * <code>"ancestor-or-self"</code> axis.
   */
  public static final int ANCESTOR_OR_SELF = 1;
  /**
   * <code>"attribute"</code> axis.
   */
  public static final int ATTRIBUTE = 2;
  /**
   * <code>"child"</code> axis.
   */
  public static final int CHILD = 3;
  /**
   * <code>"descendant"</code> axis.
   */
  public static final int DESCENDANT = 4;
  /**
   * <code>"descendant-or-self"</code> axis.
   */
  public static final int DESCENDANT_OR_SELF = 5;
  /**
   * <code>"following"</code> axis.
   */
  public static final int FOLLOWING = 6;
  /**
   * <code>"following-sibling"</code> axis.
   */
  public static final int FOLLOWING_SIBLING = 7;
  /**
   * <code>"namespace"</code> axis.
   */
  public static final int NAMESPACE = 8;
  /**
   * <code>"parent"</code> axis.
   */
  public static final int PARENT = 9;
  /**
   * <code>"preceding"</code> axis.
   */
  public static final int PRECEDING = 10;
  /**
   * <code>"preceding-sibling"</code> axis.
   */
  public static final int PRECEDING_SIBLING = 11;
  /**
   * <code>"self"</code> axis.
   */
  public static final int SELF = 12;

  private static final String[] AxesNames =
    { "ancestor",
      "ancestor-or-self",
      "attribute",
      "child",
      "descendant",
      "descendant-or-self",
      "following",
      "following-sibling",
      "namespace",
      "parent",
      "preceding",
      "preceding-sibling",
      "self" };

  /**
   * @return Returns integer axis code for a given <code>axisname</code>.<P>
   * @param axisname name of the axis. should look like <code>"axis::"</code>.<BR>
   * If <code>axisname</code> is <code>null</code> or not a valid axis name,
   * {@link #getAxis getAxis} returns {@link #INVALID INVALID}.<BR>
   * If <code>axisname</code> is empty string, result is {@link #CHILD CHILD}
   * axis.<BR>
   * If <code>axisname</code> is <code>"@"</code>, result is
   * {@link #ATTRIBUTE ATTRIBUTE} axis.<BR>
   * TODO: implement both <code>"axis::"</code> and <code>"axis"</code>
   * argument support.<BR>
   */
  public static int getAxis(String axisname)
  {
    if (axisname == null) return INVALID;
    if (axisname.equals("")) return CHILD;
    if (axisname.equals("@")) return ATTRIBUTE;
    int i=0;
    for (i=0; !axisname.equals(AxesNames[i]+"::") && i < AxesCount; i++);
    if (i >= AxesCount) return INVALID;
    else return i;
  }

  /**
   * @return Returns the string representation a given <code>axis</code>.<P>
   * String representations are always
   * returned in full non-abbreviated form,
   * for example <code>Axis.getAxisName(Axis.CHILD)</code> returns
   * <code>"child"</code>.
   * @param axis integer code of the axis.
   */

  public static String getAxisName(int axis)
  {
    if (axis >= 0 && axis < AxesCount) return AxesNames[axis];
    else return null;

  }
}
