package mapl.syntaxtree;

import java.util.List;
import mapl.visitor.Visitor;

public class ProcDecl extends MethodDecl {

    public ProcDecl(String id, List<Formal> fs,  List<Stm> ss) {
        super(id, fs, ss);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
