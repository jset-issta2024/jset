package featureDetection;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;

public class InstrumentParameters {
    private SootMethod loopHelperInt;
    private SootMethod bitwiseHelper;
    private SootMethod bitwiseHelper0;
    private SootMethod bitwiseHelper1;
    private SootMethod bitwiseHelper2;
    private SootMethod bitwiseHelper3;
    private SootMethod arrayHelper3;
    private SootMethod arrayHelper2;
    private SootMethod arrayHelper;
    private SootMethod recursionHelper;
    private SootMethod recursionConditionHelperInt;
    private SootMethod constraintHelper0;
    private SootMethod constraintHelper1;
    private SootMethod constraintHelper2;
    private SootMethod ifBranchHelperInt;
    private SootMethod switchBranchHelper;
    private SootMethod entryTest;

    public SootMethod getLoopHelperInt() {
        return loopHelperInt;
    }

    public SootMethod getBitwiseHelper() {
        return bitwiseHelper;
    }

    public SootMethod getBitwiseHelper0() {
        return bitwiseHelper0;
    }

    public SootMethod getBitwiseHelper1() {
        return bitwiseHelper1;
    }

    public SootMethod getBitwiseHelper2() {
        return bitwiseHelper2;
    }

    public SootMethod getBitwiseHelper3() {
        return bitwiseHelper3;
    }

    public SootMethod getArrayHelper3() {
        return arrayHelper3;
    }

    public SootMethod getArrayHelper2() {
        return arrayHelper2;
    }

    public SootMethod getArrayHelper() {
        return arrayHelper;
    }

    public SootMethod getRecursionHelper() {
        return recursionHelper;
    }

    public SootMethod getRecursionConditionHelperInt() {
        return recursionConditionHelperInt;
    }

    public SootMethod getConstraintHelper0() {
        return constraintHelper0;
    }

    public SootMethod getConstraintHelper1() {
        return constraintHelper1;
    }

    public SootMethod getConstraintHelper2() {
        return constraintHelper2;
    }

    public SootMethod getIfBranchHelperInt() {
        return ifBranchHelperInt;
    }

    public SootMethod getSwitchBranchHelper() {
        return switchBranchHelper;
    }

    public SootMethod getEntryTest() {
        return entryTest;
    }

    public InstrumentParameters invoke() {
        SootClass counterClass = Scene.v().loadClassAndSupport("featureDetection.InstrumentHelper");

        SootMethod loopHelper = counterClass.getMethod("void loopHelper(java.lang.Object,java.lang.Object)");
        loopHelperInt = counterClass.getMethod("void loopHelperInt(int,int)");

        bitwiseHelper = counterClass.getMethod("java.lang.Object bitwiseHelper(java.lang.Object,java.lang.Object,java.lang.Object)");
        bitwiseHelper0 = counterClass.getMethod("int bitwiseHelper(int,int,int)");
        bitwiseHelper1 = counterClass.getMethod("long bitwiseHelper(long,long,long)");
        bitwiseHelper2 = counterClass.getMethod("long bitwiseHelper(long,int,long)");
        bitwiseHelper3 = counterClass.getMethod("long bitwiseHelper(long,char,long)");

        arrayHelper3 = counterClass.getMethod("void arrayHelper(char[])");
        arrayHelper2 = counterClass.getMethod("void arrayHelper(char[],int)");
        arrayHelper = counterClass.getMethod("void arrayHelper(java.lang.String)");

        recursionHelper = counterClass.getMethod("void recursionHelper()");
        SootMethod recursionConditionHelper = counterClass.getMethod("void recursionConditionHelper(java.lang.Object,java.lang.Object)");
        recursionConditionHelperInt = counterClass.getMethod("void recursionConditionHelperInt(int,int)");

        SootMethod constraintHelper = counterClass.getMethod("java.lang.Object constraintHelper(java.lang.Object,java.lang.Object,java.lang.Object)");
        constraintHelper0 = counterClass.getMethod("int constraintHelper(int,int,int)");
        constraintHelper1 = counterClass.getMethod("double constraintHelper(double,double,double)");
        constraintHelper2 = counterClass.getMethod("double constraintHelper(double,int,double)");

        SootMethod ifBranchHelper = counterClass.getMethod("void ifBranchHelper(java.lang.Object,java.lang.Object)");
        ifBranchHelperInt = counterClass.getMethod("void ifBranchHelperInt(int,int)");
        switchBranchHelper = counterClass.getMethod("void switchBranchHelper()");
        entryTest = counterClass.getMethod("void entryTest()");
        return this;
    }
}