package interpreter;

public class Node {
    public Node Parent;
    public Node[] children;

    public String data;

    public Node(){}

    public void setParent(Node parent) {
        Parent = parent;
    }

    public void AddChild(Node n, int i){
        if (children == null){
            children = new Node[i+1];
        }else if (i >= children.length){
            Node c[] = new Node[i+1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }
}
