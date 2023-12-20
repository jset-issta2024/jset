import com.viaoa.javaparser.JavaParser;
import com.viaoa.javaparser.JavaParserTreeConstants;
import com.viaoa.javaparser.ParseException;
import com.viaoa.javaparser.SimpleNode;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class TestOaJavaParserDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.viaoa.javaparser";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestOaJavaParserDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage3(stage1_2(input));
        }catch (Throwable e){}
    }

    private void stage3(JavaParser parser) {
        SimpleNode root = (SimpleNode) parser.jjtree.rootNode();
        SimpleNode node = root.getChild(JavaParserTreeConstants.JJTCOMPILATIONUNIT);
        node.getChild(JavaParserTreeConstants.JJTPACKAGEDECLARATION);
        node.getChildren(JavaParserTreeConstants.JJTIMPORTDECLARATION);
        if (node == null) {
            return;
        }
        node = node.getChild(JavaParserTreeConstants.JJTTYPEDECLARATION);
        if (node == null) {
            return;
        }
        node = node.getChild(JavaParserTreeConstants.JJTCLASSORINTERFACEDECLARATION);
        if (node == null) {
            return;
        }
        node = node.getChild(JavaParserTreeConstants.JJTCLASSORINTERFACEBODY);

        if (node == null) {
            return;
        }
        SimpleNode[] nodes = node.getChildren(JavaParserTreeConstants.JJTCLASSORINTERFACEBODYDECLARATION);

        LinkedList<SimpleNode> list = new LinkedList<SimpleNode>();
        for (int i = 0; nodes != null && i < nodes.length; i++) {
            for (int j = 0; nodes[i].children != null && j < nodes[i].children.length; j++) {
                if (((SimpleNode) nodes[i].children[j]).getId() == JavaParserTreeConstants.JJTFIELDDECLARATION) {
                    list.add(nodes[i]);
                    break;
                }
            }
        }
        SimpleNode[] ns = new SimpleNode[list.size()];
        list.toArray(ns);
    }

    public JavaParser stage1_2(String s) throws ParseException {
        //	  s = SymbolicString.makeConcolicString(s);
//	  System.out.println(s);
        StringReader reader = new StringReader(s);
        JavaParser parser = new JavaParser(reader);
        parser.CompilationUnit();
        return parser;
    }
}
