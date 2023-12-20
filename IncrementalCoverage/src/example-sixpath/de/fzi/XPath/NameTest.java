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
 * NameTest class.
 */
public class NameTest extends NodeTest
{
  /**
   * Constructor
   */

  private String prefix;
  private String name;
  private int type;

  /**
   * Invalid name test
   */
  public static final int INVALID = -1;
  /**
   * Any principal node.
   */
  public static final int ANY = 0;
  /**
   * Any principal node in given namespace.
   */
  public static final int PREFIX_ANY = 1;
  /**
   * Principal node with given name.
   */
  public static final int NAME = 2;
  /**
   * Principal node with given name in given namespace.
   */
  public static final int PREFIX_NAME = 3;

  /**
   * Instantiates NameTest for given name.
   * @param thename name.
   */
  public NameTest(String thename)
  {
    if (thename.equals("*"))
    {
      name="*";
      type=ANY;
    }
    else
    {
      name=thename;
      type=NAME;
    }
  }

  /**
   * Instantiates NameTest for given name.
   * @param preix prefix.
   * @param thename name.
   */
  public NameTest(String theprefix, String thename)
  {
    if (thename.equals("*"))
    {
      prefix=theprefix;
      name="*";
      type=PREFIX_ANY;
    }
    else
    {
      prefix=theprefix;
      name=thename;
      type=PREFIX_NAME;
    }
  }

  /**
   * Creates a NameTest from given QName.
   * @param qname QName.
   * @return Created NameTest.
   */
  public static NameTest parseQName(String qname)
  {
    int delimiter = qname.indexOf(":");
    if (delimiter<0)
    {
      return new NameTest(qname);
    }
    else
    {
      String prefix = qname.substring(0, delimiter);
      String name = qname.substring(delimiter+1, qname.length());
      return new NameTest(prefix, name);
    }
  }

  /**
   * Return name test type of this instance.
   * @return Name test type code.
   */
  public int getNameTestType()
  {
    return type;
  }

  /**
   * Return String representation of this instance.
   * @return String representation of this instance.
   */
  public String toString()
  {
    String res = "";
    if (getNameTestType() == PREFIX_ANY || getNameTestType() == PREFIX_NAME)
      res = prefix + ":";

    if (getNameTestType() == NAME || getNameTestType() == PREFIX_NAME)
      res = res + name;

    if (getNameTestType() == ANY || getNameTestType() == PREFIX_ANY)
      res = res + "*";

    return res;
  }
}

