package mapl.syntaxtree;

import mapl.visitor.Visitor;

public class ExpTrue extends Exp {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
