package interpreter;

public class ParseException  extends Exception{
    public int CurTokenKind = -1;
    public int ExpectTokenKind = -1;
    public ParseException(int curTokenKind, int expectTokenKind){
        super();
        this.CurTokenKind = curTokenKind;
        this.ExpectTokenKind = expectTokenKind;
    }
}
