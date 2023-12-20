package mapl.syntaxtree;

import mapl.visitor.Visitor;

public abstract class Stm extends AST {

    public abstract <T> T accept(Visitor<T> v);
}
