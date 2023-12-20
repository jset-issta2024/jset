package ca.ubc.cs411.aejcc.ast;

public class Num extends AE {
    public final int n;
    public Num(int n) { this.n = n; }

    public String prettyPrint() { return String.valueOf(n); }

    public int interp() { return n; }

    public String toString() { return "new Num(" + n + ")"; }
}
