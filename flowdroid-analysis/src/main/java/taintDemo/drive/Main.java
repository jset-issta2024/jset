package taintDemo.drive;

import java.util.ArrayList;
import java.util.List;

public class Main {

    protected static String appPath;
    protected static String classname;
    protected static List<String> sources = new ArrayList<>();
    protected static List<String> sinks = new ArrayList<>();

    public static void main(String[] args) throws Exception {

//        testForFlowdroidDemo(); // right

//        setTaintParameters();

        // for feature test
        setTaintParameters4Array();
//        setTaintParameters4Bitwise();
//        setTaintParameters4Branch();
//        setTaintParameters4Constraint();
//        setTaintParameters4Array();
//        setTaintParameters4Array();
//        setTaintParameters4Array();
//        setTaintParameters4Loop();
//        setTaintParameters4Recursion();
//        setTaintParameters4Array();

        FlowDroidDemo flowDroidDemo = new FlowDroidDemo(appPath, classname, sources, sinks);
        flowDroidDemo.run();
    }

    private static void setTaintParameters4Branch() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoBranch.testBranch";

        String source1 = "<demoBranch.testBranch: int makeSymbolicInt(int)>";
        String sink1 = "<featureDetection.InstrumentHelper: void ifBranchHelper(int,int)>";

        sources.add(source1);
        sinks.add(sink1);
    }

    private static void setTaintParameters4Recursion() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoRecursion.testRecursion";

        String source1 = "<demoRecursion.testRecursion: int secret()>";
        String sink1 = "<featureDetection.InstrumentHelper: void recursionConditionHelperInt(int,int)>";

        sources.add(source1);
        sinks.add(sink1);
    }

    private static void setTaintParameters4Loop() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoLoop.testLoop";

        String source1 = "<demoLoop.testLoop: int secret()>";
        String sink1 = "<featureDetection.InstrumentHelper: void loopHelperInt(int,int)>";

        sources.add(source1);
        sinks.add(sink1);
    }

    private static void setTaintParameters4Constraint() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoConstraint.testConstraint";

        String source1 = "<demoConstraint.testConstraint: double makeSymbolic(double)>";
        String sink1 = "<featureDetection.InstrumentHelper: int constraintHelper(int,int,int)>";
        String sink2 = "<featureDetection.InstrumentHelper: double constraintHelper(double,double,double)>";
        String sink3 = "<featureDetection.InstrumentHelper: double constraintHelper(double,int,double)>";

//        String source2 = "<featureDetection.InstrumentHelper: double constraintHelper(double,double,double)>";
//        String sink2 = "<featureDetection.InstrumentHelper: void ifBranchHelper(int,int)>";

        sources.add(source1);
        sinks.add(sink1);
        sinks.add(sink2);
        sinks.add(sink3);
    }

    private static void setTaintParameters4Bitwise() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoBitwise.testBitwise";

        String source1 = "<demoBitwise.testBitwise: int makeConcolicInt(int)>";

//        String sink1 = "<featureDetection.InstrumentHelper: java.lang.Object bitwiseHelper(java.lang.Object,java.lang.Object,java.lang.Object)>";
        String sink1 = "<featureDetection.InstrumentHelper: int bitwiseHelper(int,int,int)>";
        String sink2 = "<featureDetection.InstrumentHelper: long bitwiseHelper(long,long,long)>";
        String sink3 = "<featureDetection.InstrumentHelper: long bitwiseHelper(long,int,long)>";
        String sink4 = "<featureDetection.InstrumentHelper: long bitwiseHelper(long,char,long)>";

//        String source2 = "<featureDetection.InstrumentHelper: int bitwiseHelperTest(int,int,int)>";
//        String sink2 = "<featureDetection.InstrumentHelper: void ifBranchHelper(int,int)>";

        sources.add(source1);
        sinks.add(sink1);
        sinks.add(sink2);
        sinks.add(sink3);
        sinks.add(sink4);
    }

    private static void setTaintParameters4Array() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoArray.testArray";
        String source1 = "<demoArray.testArray: char makeSymbolicChar(char)>";
//        String sink1 = "<featureDetection.InstrumentHelper: void arrayHelper(char[],int)>";
        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
        String sink2 = "<taintDemo.testcase.SourceSink: void sink(java.lang.StringReader)>";
        sources.add(source1);
        sinks.add(sink1);
        sinks.add(sink2);
    }

    private static void setTaintParameters() {

        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-schroeder";
        classname = "schroeder.test.TestWAVEFileRead";

        String source1 = "<gov.nasa.jpf.jdart.Debug: char makeConcolicChar(String,String)>";
        String sink1 = "<featureDetection.InstrumentHelper: double constraintHelperTest(double,double,double)>";
        String source2 = "<featureDetection.InstrumentHelper: double constraintHelperTest(double,double,double)>";
        String sink2 = "<featureDetection.InstrumentHelper: void ifBranchHelper(int,int)>";

        sources.add(source1);
        sinks.add(sink1);

    }

    private static void testForFlowdroidDemo() {

//    protected static final String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
//    protected static final String sink1 = "<taintDemo.testcase.SourceSink: void sink()>";
//    protected static final String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";

//        for TaintInList
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "taintDemo.testcase.TaintInList";
        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
        sources.add(source1);
        sinks.add(sink1);

////        for StringAppend
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
//        classname = "taintDemo.testcase.StringAppend";
//        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
//        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
//        sources.add(source1);
//        sinks.add(sink1);

////        for SimpleTaint
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
//        classname = "taintDemo.testcase.SimpleTaint";
//        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
////        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
//        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String,java.lang.String)>";
//        sources.add(source1);
//        sinks.add(sink1);

////        for OneCallTaint
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
//        classname = "taintDemo.testcase.OneCallTaint";
//        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
//        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
//        sources.add(source1);
//        sinks.add(sink1);

////        for InterTaintTransfer
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
//        classname = "taintDemo.testcase.InterTaintTransfer";
//        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
//        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
//        sources.add(source1);
//        sinks.add(sink1);

////        for BaseToResult
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
//        classname = "taintDemo.testcase.BaseToResult";
//        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
//        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
//        sources.add(source1);
//        sinks.add(sink1);

////        for ArgToResult
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
//        classname = "taintDemo.testcase.ArgToResult";
//        String source1 = "<taintDemo.testcase.SourceSink: java.lang.String source()>";
//        String sink1 = "<taintDemo.testcase.SourceSink: void sink()>";
////        String sink1 = "<taintDemo.testcase.SourceSink: void sink(java.lang.String)>";
//        sources.add(source1);
//        sinks.add(sink1);

    }

//    protected static String classname = "demoLoop.testLoop";
//    protected static final String source1 = "<demoArray.testArray: char makeSymbolicChar(char)>";
//    protected static final String source2 = "<demoArray.testArray: java.lang.String makeSymbolicString(java.lang.String)>";
//    protected static final String sink1 = "<featureDetection.InstrumentHelper: void arrayHelper()>";

////    protected static final String source1 = "<demoBitwise.testBitwise: int makeConcolicInt(int)>";
//    protected static final String source1 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(String,String)>";
//    protected static final String sink1 = "<featureDetection.InstrumentHelper: int bitwiseHelperTest(int,int,int)>";
//    protected static final String source2 = "<featureDetection.InstrumentHelper: int bitwiseHelperTest(int,int,int)>";
//    protected static final String sink2 = "<featureDetection.InstrumentHelper: void ifBranchHelper(int,int)>";

}