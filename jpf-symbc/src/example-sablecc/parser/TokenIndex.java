package parser;

import node.*;
import analysis.*;

class TokenIndex extends AnalysisAdapter
{
    int index;

    public void caseTIdentifier(TIdentifier node)
    {
        index = 0;
    }

    public void caseEOF(EOF node)
    {
        index = 1;
    }
}
