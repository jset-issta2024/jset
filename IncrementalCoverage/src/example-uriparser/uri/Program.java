package uri;
import java.util.ArrayList;
import java.util.Arrays;

abstract class Node {
    public abstract String toString();
}

public class Program extends Node {
    String val = "Program";
    ArrayList<Function> functions;

    Program(ArrayList<Function> fcts) {
        functions = fcts;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(val + "\n");
        for (Function f : functions) {
            sb.append(f.toString() + "\n");
        }
        return sb.toString();
    }
}

class Function extends Node {
    String name;
    ArrayList<Node> args;
    Block body;

    Function(String n, ArrayList<Node> a, Block b) {
        name = n;
        args = a;
        body = b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Function -> " + name + "(");
        for (Node arg : args) {
            sb.append(arg + ", ");
        }
        sb.append(") {");
        sb.append("\n\t");
        sb.append(body.toString());
        sb.append("}");
        return sb.toString();
    }
}

class Block extends Node {
    ArrayList<Node> statements;

    Block(ArrayList<Node> stmts) {
        statements = stmts;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node st : statements) {
            sb.append(st.toString());
        }
        return sb.toString();
    }
}

class Variable extends Node {
    String name;
    String value;

    Variable(String n, String v) {
        name = n;
        value = v;
    }

    public String toString() {
        return "Variable -> " + name + " = " + value + "\n";
    }
}

class QualifiedIdentifier extends Node {
    String stmt;

    QualifiedIdentifier(String s) {
        stmt = s;
    }

    public String toString() {
        return stmt;
    }
}

class FunctionCall extends Node {
    String name;
    ArrayList<Node> args;

    FunctionCall(String n, ArrayList<Node> a) {
        name = n;
        args = a;
    }

    @Override
    public String toString() {
        return "FunctionCall{" +
                "name='" + name + '\'' +
                ", args=" + args +
                "}\n";
    }
}

class Expression extends Node {
    String val;

    Expression(String s) {
        val = s;
    }

    public String toString() {
        return val;
    }
}

class Return extends Node {
    Node val;

    Return(Node s) {
        val = s;
    }

    public String toString() {
        return "Return -> " + val.toString();
    }
}

class If extends Node {
    Node cond;
    Node body;
    Node elif;

    If(Node c, Node b, Node e) {
        cond = c;
        body = b;
        elif = e;
    }

    public String toString() {
        return "If -> \nif (" + cond.toString() + ") {\n\t" + body.toString() + "\n}" +
                (elif != null ? " else {\n\t" + elif.toString() + "\n}" : "\n");
    }
}

class While extends Node {
    Node cond;
    Node body;

    While(Node c, Node b) {
        cond = c;
        body = b;
    }

    public String toString() {
        return "While -> (" + cond.toString() + ") {\n\t" + body.toString() + "\n}";
    }
}

class NumberLiteral extends Node {
    String val;

    NumberLiteral(String s) {
        val = s;
    }

    public String toString() {
        return val;
    }
}

class StringLiteral extends Node {
    String val;

    StringLiteral(String s) {
        val = s;
    }

    public String toString() {
        return val;
    }
}

class BinaryExpression extends Node {
    Node lhs;
    Node rhs;
    String op;

    public BinaryExpression(Node lhs, Node rhs, String op) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "lhs='" + lhs.toString() + '\'' +
                ", rhs='" + rhs.toString() + '\'' +
                ", op='" + op + '\'' +
                "}\n";
    }
}
