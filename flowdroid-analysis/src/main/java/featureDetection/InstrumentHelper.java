package featureDetection;

public class InstrumentHelper {

    // for array

    public static synchronized void arrayHelper(char[] array) {
        System.err.println("there is a array operation occur on " + array + ".");
    }

    public static synchronized void arrayHelper(char[] array, int index) {
        System.err.println("there is a array operation occur in " + index + " on array " + array + ".");
    }

    public static synchronized void arrayHelper(String op) {
        System.err.println("there is a array operation occur in " + op +  ".");
    }

    // for bitwise

    public static synchronized Object bitwiseHelper(Object op1, Object op2, Object op) {
        System.err.println("there is a bitwise operation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized int bitwiseHelper(int op1, int op2, int op) {
        System.err.println("there is a bitwise operation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized long bitwiseHelper(long op1, long op2, long op) {
        System.err.println("there is a bitwise operation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized long bitwiseHelper(long op1, int op2, long op) {
        System.err.println("there is a bitwise operation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized long bitwiseHelper(long op1, char op2, long op) {
        System.err.println("there is a bitwise operation between " + op1 + " and " + op2 + ".");
        return op;
    }

    // for branch

    public static synchronized void ifBranchHelper(Object op1, Object op2) {
        System.err.println("there is a condition in IF branch about " + op1 + " and " + op2 + ".");
    }

    public static synchronized void ifBranchHelperInt(int op1, int op2) {
        System.err.println("there is a condition in IF branch about " + op1 + " and " + op2 + ".");
    }

    public static synchronized void switchBranchHelper() {
        System.err.println("there is a SWITCH branch");
    }

    // for constraint

    public static synchronized Object constraintHelper(Object op1, Object op2, Object op) {
        System.err.println("there is a complex computation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized int constraintHelper(int op1, int op2, int op) {
        System.err.println("there is a complex computation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized double constraintHelper(double op1, double op2, double op) {
        System.err.println("there is a complex computation between " + op1 + " and " + op2 + ".");
        return op;
    }

    public static synchronized double constraintHelper(double op1, int op2, double op) {
        System.err.println("there is a complex computation between " + op1 + " and " + op2 + ".");
        return op;
    }

    // for file

    // for float

    // for jni

    // for loop

    public static synchronized void loopHelper(Object op1, Object op2) {
        System.err.println("there is a loop controlled by " + op1 + " and " + op2 + ".");
    }

    public static synchronized void loopHelperInt(int op1, int op2) {
        System.err.println("there is a loop controlled by " + op1 + " and " + op2 + ".");
    }

    // for recursion

    public static synchronized void recursionHelper() {
        System.err.println("there is a recursion");
    }

    public static synchronized void recursionConditionHelper(Object op1, Object op2) {
        System.err.println("there is a condition in recursion condition about " + op1 + " and " + op2 + ".");
    }

    public static synchronized void recursionConditionHelperInt(int op1, int op2) {
        System.err.println("there is a condition in recursion condition about " + op1 + " and " + op2 + ".");
    }

    // for reflection


    public static synchronized void entryTest() {
        System.err.println("there is an entry");
    }

}

