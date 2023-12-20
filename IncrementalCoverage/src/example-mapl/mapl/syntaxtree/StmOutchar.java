package mapl.syntaxtree;

import mapl.visitor.Visitor;

public class StmOutchar extends Stm {

    public final Exp e;

    public StmOutchar(Exp ae) {
        e = ae;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
