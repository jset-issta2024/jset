package ca.ubc.cs411.aejcc.ast;

public class Sub extends AE {
    public final AE lhs, rhs;
    public Sub(AE lhs, AE rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String prettyPrint() { return "(- " + lhs.prettyPrint() + " " + rhs.prettyPrint() + ")"; }

    public int interp() {
        return lhs.interp() - rhs.interp();
    }

    public String toString() {
        return "new Sub(" + lhs + ", " + rhs + ")";
    }
}
