package interpreter;

public class Token {
    public int kind = -1;
    public String image = "";

    public static int NUMBER = 1;
    public static int IDENTIFIER = 2;
    public static int LPAREN = 3;
    public static int RPAREN = 4;
    public static int SEMICOLON = 5;
    public static int ERROR = -5;

    public Token(int kind, String image){
        this.kind = kind;
        this.image = image;
    }
}
