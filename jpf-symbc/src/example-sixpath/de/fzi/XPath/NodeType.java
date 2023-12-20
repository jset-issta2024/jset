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
 * NodeType class.
 */
public class NodeType extends NodeTest
{
  private static final int NodeTypeCount = 4;
  /**
   * Invalid node type.
   */
  public static final int INVALID = -1;
  /**
   * Comment node type.
   */
  public static final int COMMENT = 0;
  /**
   * Text node type.
   */
  public static final int TEXT = 1;
  /**
   * PI node type.
   */
  public static final int PROCESSING_INSTRUCTION = 2;
  /**
   * Any node type.
   */
  public static final int NODE = 3;

  private static final String[] NodeTypeNames =
    { "comment",
      "text",
      "processing-instruction",
      "node" };

  private int type;
  private String name;
  private String value;

  /**
   * Constructs <code>NodeType</code> for given name.
   * @param newName name of node type.
   */

  public NodeType(String newName)
  {
    name = newName;
    type = getNodeType(newName);
  }

  /**
   * Constructs <code>NodeType</code> for given name and value (a case of
   * <code>processing-instruction</code>).
   * @param newName name of node type (should be
   * <code>"processing-instruction"</code>).
   * @param newValue value of node type.
   */
  public NodeType(String newName, String newValue)
  {
    name = newName;
    type = getNodeType(newName);
    value = newValue;
  }

  /**
   * Returns node type code for given name.
   * @param NodeTypeName name of node type.
   * @return Returns node type code.
   */
  public static int getNodeType (String NodeTypeName)
  {
    int i=0;
    for (i=0; !NodeTypeName.equals(NodeTypeNames[i]) && i < NodeTypeCount; i++);
    if (i >= NodeTypeCount) return INVALID;
    else return i;
  }

  /**
   * Returns node type name for given node type code.
   * @param node type code.
   * @return Returns node type name.
   */
  public static String getNodeTypeName(int nodetype)
  {
    if (nodetype >= 0 && nodetype < NodeTypeCount) return NodeTypeNames[nodetype];
    else return null;
  }

  /**
   * Returns node type name for this NodeType instance.
   * @return Returns node type name.
   */
  public String getNodeTypeName()
  {
    return name;
  }

  /**
   * Returns node type code for this NodeType instance.
   * @return Returns node type code.
   */
  public int getNodeType()
  {
    return type;
  }

  /**
   * Returns string representation of this NodeType instance.
   * @return String representation.
   */
  public String toString()
  {
    if (value == null)
      return name + "()";
    else
      return name + "(" + value + ")";
  }
}


