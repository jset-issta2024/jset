//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.garcel.parser.r.autogen;

public class SimpleNode implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected Object value;
    protected R parser;
    protected Token firstToken;
    protected Token lastToken;

    public SimpleNode(int i) {
        this.id = i;
    }

    public SimpleNode(R p, int i) {
        this(i);
        this.parser = p;
    }

    public static Node jjtCreate(int id) {
        return new SimpleNode(id);
    }

    public static Node jjtCreate(R p, int id) {
        return new SimpleNode(p, id);
    }

    public void jjtOpen() {
    }

    public void jjtClose() {
    }

    public void jjtSetParent(Node n) {
        this.parent = n;
    }

    public Node jjtGetParent() {
        return this.parent;
    }

    public void jjtAddChild(Node n, int i) {
        if (this.children == null) {
            this.children = new Node[i + 1];
        } else if (i >= this.children.length) {
            Node[] c = new Node[i + 1];
            System.arraycopy(this.children, 0, c, 0, this.children.length);
            this.children = c;
        }

        this.children[i] = n;
    }

    public Node jjtGetChild(int i) {
        return this.children[i];
    }

    public int jjtGetNumChildren() {
        return this.children == null ? 0 : this.children.length;
    }

    public void jjtSetValue(Object value) {
        this.value = value;
    }

    public Object jjtGetValue() {
        return this.value;
    }

    public Token jjtGetFirstToken() {
        return this.firstToken;
    }

    public void jjtSetFirstToken(Token token) {
        this.firstToken = token;
    }

    public Token jjtGetLastToken() {
        return this.lastToken;
    }

    public void jjtSetLastToken(Token token) {
        this.lastToken = token;
    }

    public String toString() {
        return RTreeConstants.jjtNodeName[this.id];
    }

    public String toString(String prefix) {
        return prefix + this.toString();
    }

    public void dump(String prefix) {
        System.out.println(this.toString(prefix));
        if (this.children != null) {
            for(int i = 0; i < this.children.length; ++i) {
                SimpleNode n = (SimpleNode)this.children[i];
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }

    }

    public int getId() {
        return this.id;
    }
}
