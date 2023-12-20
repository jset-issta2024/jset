package analysis;

import ca.mcgill.sable.util.*;
import node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object in);
    Object getOut(Node node);
    void setOut(Node node, Object out);

    void caseStart(Start node);
    void caseAIdentifierExpression(AIdentifierExpression node);

    void caseTIdentifier(TIdentifier node);
    void caseEOF(EOF node);
}
