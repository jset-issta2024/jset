package mapl.syntaxtree;

import mapl.visitor.Visitor;

public class ExpFalse extends Exp {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
