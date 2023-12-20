package analysis;

import ca.mcgill.sable.util.*;
import node.*;

public class DepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn(Node node)
    {
    }

    public void defaultOut(Node node)
    {
    }

    public void caseStart(Start node)
    {
        inStart(node);
        node.getPExpression().apply(this);
        node.getEOF().apply(this);
        outStart(node);
    }

    public void inAIdentifierExpression(AIdentifierExpression node)
    {
        defaultIn(node);
    }

    public void outAIdentifierExpression(AIdentifierExpression node)
    {
        defaultOut(node);
    }

    public void caseAIdentifierExpression(AIdentifierExpression node)
    {
        inAIdentifierExpression(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAIdentifierExpression(node);
    }
}
