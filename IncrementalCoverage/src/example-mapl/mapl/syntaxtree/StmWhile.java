package mapl.syntaxtree;

import mapl.visitor.Visitor;

public class StmWhile extends Stm {

    public final Exp e;
    public final StmBlock b;

    public StmWhile(Exp ae, StmBlock ab) {
        e = ae;
        b = ab;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
