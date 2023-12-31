package node;

import ca.mcgill.sable.util.*;
import analysis.*;

public final class Start extends Node
{
    private PExpression _pExpression_;
    private EOF _eof_;

    public Start()
    {
    }

    public Start(
        PExpression _pExpression_,
        EOF _eof_)
    {
        setPExpression(_pExpression_);
        setEOF(_eof_);
    }

    public Object clone()
    {
        return new Start(
            (PExpression) cloneNode(_pExpression_),
            (EOF) cloneNode(_eof_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseStart(this);
    }

    public PExpression getPExpression()
    {
        return _pExpression_;
    }

    public void setPExpression(PExpression node)
    {
        if(_pExpression_ != null)
        {
            _pExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _pExpression_ = node;
    }

    public EOF getEOF()
    {
        return _eof_;
    }

    public void setEOF(EOF node)
    {
        if(_eof_ != null)
        {
            _eof_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _eof_ = node;
    }

    void removeChild(Node child)
    {
        if(_pExpression_ == child)
        {
            _pExpression_ = null;
            return;
        }

        if(_eof_ == child)
        {
            _eof_ = null;
            return;
        }
    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_pExpression_ == oldChild)
        {
            setPExpression((PExpression) newChild);
            return;
        }

        if(_eof_ == oldChild)
        {
            setEOF((EOF) newChild);
            return;
        }
    }

    public String toString()
    {
        return "" +
            toString(_pExpression_) +
            toString(_eof_);
    }
}
