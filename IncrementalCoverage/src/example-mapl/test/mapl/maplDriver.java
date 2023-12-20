package test.mapl;

import coverage.SubjectExecutor;
import mapl.parser.MaplParser;
import mapl.parser.ParseException;
import mapl.staticanalysis.SymbolTable;
import mapl.staticanalysis.SymbolTableBuilder;
import mapl.staticanalysis.TypeChecker;
import mapl.syntaxtree.Program;

import java.io.IOException;
import java.io.StringReader;

public class maplDriver extends SubjectExecutor {

    private Program root;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "mapl";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new maplDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
            stage3();
        }catch (Throwable e){}
    }

    void stage1_2(String s) throws ParseException {
        StringReader reader = new StringReader(s);

        MaplParser parser  = new MaplParser(reader);
        root = parser.nt_Program();

    }

    void stage3() {
        SymbolTableBuilder stvisit = new SymbolTableBuilder();
        root.accept(stvisit);
        SymbolTable symTab = stvisit.getSymTab();
        root.accept(new TypeChecker(symTab));
    }
}
