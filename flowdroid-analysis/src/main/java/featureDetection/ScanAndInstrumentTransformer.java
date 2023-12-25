package featureDetection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import soot.*;
import soot.JastAddJ.BranchTargetStmt;
import soot.jimple.*;
import soot.jimple.internal.*;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.jimple.toolkits.annotation.logic.LoopFinder;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.tagkit.Host;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.PostDominatorAnalysis;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;
import soot.util.cfgcmd.AltClassLoader;
import soot.util.cfgcmd.CFGGraphType;
import soot.util.cfgcmd.CFGToDotGraph;

import java.util.*;

public class ScanAndInstrumentTransformer extends SceneTransformer {

    private static String packageName = null;
    private static String packageAnalysisName = null;
    private static int ifId = 0;

    private static SootMethod loopHelperInt;

    private static SootMethod bitwiseHelper;
    private static SootMethod bitwiseHelper0;
    private static SootMethod bitwiseHelper1;
    private static SootMethod bitwiseHelper2;
    private static SootMethod bitwiseHelper3;

    private static SootMethod arrayHelper;
    private static SootMethod arrayHelper2;
    private static SootMethod arrayHelper3;
    private static SootMethod recursionHelper;
    private static SootMethod recursionConditionHelperInt;

    private static SootMethod constraintHelper0;
    private static SootMethod constraintHelper1;
    private static SootMethod constraintHelper2;

    private static SootMethod ifBranchHelper;
    private static SootMethod switchBranchHelper;
    private static SootMethod entryTest;
    private static SootMethod entryMethod;

    private static Map<Unit, SootMethod> arrayStmt = new HashMap<>();

    private static Map<Unit, SootMethod> bitwiseStmt = new HashMap<>();

    private static Map<Stmt, SootMethod> ifBranchReport = new HashMap<>();
    private static Map<Stmt, SootMethod> switchBranchReport = new HashMap<>();
    private static Map<Stmt, Integer> switchBranchCount = new HashMap<>();
    private static Map<Stmt, SootMethod> branchInLoop = new HashMap<>();

    private static Map<Unit, SootMethod> constraintStmt = new HashMap<>();
    
    private static Map<SootMethod, Edge> jniReport = new HashMap<>();

    private static Queue<Unit> seemLoop = new LinkedList<>();
    private static Map<SootMethod, List<Unit>> confirmLoop = new HashMap<>();
    private static Map<Unit, SootMethod> loopStmt = new HashMap<>();

    private static Set<SootMethod> visitedRecu = new HashSet<>();
    private static Set<SootMethod> recursionHelp = new HashSet<>();
    private static Queue<SootMethod> seemRecu = new LinkedList<>();
    private static Set<SootMethod> confirmRecu = new HashSet<>();
    static Multimap<Unit, SootMethod> recursionStmt = HashMultimap.create();
    
    private static Map<SootMethod, Edge> reflectionReport = new HashMap<>();

    private static Map<Unit, Integer> instrumentConditionNodeIdMap = new HashMap<>();
    private static Map<Unit, SootMethod> instrumentConditionNodeMethodMap = new HashMap<>();

    private static Map<Unit, Set<Unit>> switchInstPointsMap = new HashMap<>();

    // store map of ajc instrument stmt and event
    private static Map<Unit, String> instrumentPointNodeEvent = new HashMap<>();

    // store map of condition stmt and event
    private static Map<Unit, Set<String>> instrumentConditionNodeEvent = new HashMap<>();

    Map<Unit, List<Unit>> switchReplace = new LinkedHashMap<Unit, List<Unit>>();

    public ScanAndInstrumentTransformer(InstrumentParameters instrumentParameters,
                                        String packageName, String packageAnalysisName, SootMethod entrySootMethod) {
        this.packageName = packageName;
        this.packageAnalysisName = packageAnalysisName;
        this.entryMethod = entrySootMethod;

        // args for instrument

        this.loopHelperInt = instrumentParameters.getLoopHelperInt();

        this.bitwiseHelper = instrumentParameters.getBitwiseHelper();
        this.bitwiseHelper0 = instrumentParameters.getBitwiseHelper0();
        this.bitwiseHelper1 = instrumentParameters.getBitwiseHelper1();
        this.bitwiseHelper2 = instrumentParameters.getBitwiseHelper2();
        this.bitwiseHelper3 = instrumentParameters.getBitwiseHelper3();

        this.arrayHelper = instrumentParameters.getArrayHelper();
        this.arrayHelper2 = instrumentParameters.getArrayHelper2();
        this.arrayHelper3 = instrumentParameters.getArrayHelper3();

        this.recursionHelper = instrumentParameters.getRecursionHelper();
        this.recursionConditionHelperInt = instrumentParameters.getRecursionConditionHelperInt();

        this.constraintHelper0 = instrumentParameters.getConstraintHelper0();
        this.constraintHelper1 = instrumentParameters.getConstraintHelper1();
        this.constraintHelper2 = instrumentParameters.getConstraintHelper2();

        this.ifBranchHelper = instrumentParameters.getIfBranchHelperInt();
        this.switchBranchHelper = instrumentParameters.getSwitchBranchHelper();
        this.entryTest = instrumentParameters.getEntryTest();

    }

    @Override
    protected void internalTransform(String phaseName, Map options) {

        CallGraph callGraph = Scene.v().getCallGraph();

        // check feature of **recursion**
        checkRecursion(callGraph);
        checkRecursionLast(callGraph);
        for (SootMethod confirmRecuMethod : confirmRecu) {
            Iterator<Edge> edgesInto = callGraph.edgesInto(confirmRecuMethod);
            for (Iterator<Edge> iterator = edgesInto; iterator.hasNext(); ) {
                Edge edge = iterator.next();
                recursionStmt.put(edge.srcStmt(), (SootMethod) edge.getSrc());
            }
        }

        // check condition of recursion done
        HashMap<SootMethod, DirectedGraph<Unit>> cfgMap = new HashMap<>();
        recursionStmt.keySet().stream().forEach(s -> scanConditionOfRecurion(cfgMap, s));

        Iterator<Edge> i = callGraph.iterator();

        // check other feature
        List<SootMethod> scannedMethods = new ArrayList<>();
        while (i.hasNext()) {
            Edge e = i.next();

            if (e.src().getDeclaringClass().getPackageName().startsWith(packageName) ||
                    e.src().getDeclaringClass().getPackageName().startsWith(packageAnalysisName)) {
                if (scannedMethods.contains(e.src()) == false) {

                    checkReflection(e.src(), e);
                    checkJni(e.src(), e);

                    if (!e.src().isPhantom() && !e.src().isNative()) {

                        checkBranchInLoop(e.src());
                        checkLoop(e.src().getActiveBody());
                        checkArrayBitwiseBranchConstraint(e.src().getActiveBody());

                    }
                    scannedMethods.add(e.src());
                }
            }
            if (e.tgt().getDeclaringClass().getPackageName().startsWith(packageName) ||
                    e.tgt().getDeclaringClass().getPackageName().startsWith(packageAnalysisName)) {
                if (scannedMethods.contains(e.tgt()) == false) {

                    checkReflection(e.tgt(), e);
                    checkJni(e.tgt(), e);

                    if (!e.tgt().isPhantom() && !e.tgt().isNative() && e.tgt().hasActiveBody()) {

                        checkBranchInLoop(e.tgt());
                        checkLoop(e.tgt().getActiveBody());
                        checkArrayBitwiseBranchConstraint(e.tgt().getActiveBody());

                    }

                    scannedMethods.add(e.tgt());
                }
            }
        }

        // Finally, we insert the entry test statement in the entry function
        // just for test
        Chain units = entryMethod.getActiveBody().getUnits();
        Iterator stmtIt = units.snapshotIterator();
        if (stmtIt.hasNext()) {
            Stmt stmt = (Stmt) stmtIt.next();
            InvokeExpr storeExprCondition = Jimple.v().newStaticInvokeExpr(entryTest.makeRef());
            Stmt storeStmtConfition = Jimple.v().newInvokeStmt(storeExprCondition);
            units.insertBefore(storeStmtConfition, stmt);
        }

        // output the result of feature detection
        instrumentAndOutputOfDetection();

    }

    private void instrumentAndOutputOfDetection() {

        outputAndInsertArray();
        outputAndInsertBitwise();
        outputAndInsertBranch();
        outputBranchInLoop();
        outputAndInsertConstraint();
        outputAndInsertFile();
        outputAndInsertFloat();
        outputAndInsertJni();
        outputAndInsertLoop();
        outputAndInsertRecursion();
        outputAndInsertReflection();

    }

    private void outputAndInsertReflection() {

        // output the result of feature 10 reflection
        // no instrument, just scan and count
        System.out.print("Reflection Feature Detection Result ------ ");
        System.out.println(reflectionReport.size());
        for (SootMethod m : reflectionReport.keySet()) {
            System.out.println("Method : " + m + ", Edge : " + reflectionReport.get(m));
        }

    }

    private void outputAndInsertRecursion() {

        // output the result of feature 9 recursion
        System.out.print("Recursion Feature Detection Result ------ ");
        System.out.println(instrumentConditionNodeMethodMap.size());

        for (Unit u : recursionStmt.keySet()) {
            if (u instanceof Stmt) {
                for (SootMethod m : recursionStmt.get(u)) {
                    insertRecursionStatement(m.getActiveBody().getUnits(), (Stmt) u);
                }
            }
        }
        for (Unit u : instrumentConditionNodeMethodMap.keySet()) {
            if (u instanceof Stmt) {
                insertComInst(instrumentConditionNodeMethodMap.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }

    }

    private void outputAndInsertLoop() {

        // output the result of feature 8 loop
        System.out.print("Loop Feature Detection Result ------ ");
        System.out.println(loopStmt.size());

        /// Now, we get all the condition nodes before which we need to add instrumentations
        for (Unit u : loopStmt.keySet()) {
            if (u instanceof Stmt) {
                insertLoopStatement(loopStmt.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }

    }

    private void outputAndInsertJni() {

        // output the result of feature 7 jni
        // no instrument, just scan and count
        System.out.print("JNI Feature Detection Result ------ ");
        System.out.println(jniReport.size());
        for (SootMethod m : jniReport.keySet()) {
            System.out.println("Method : " + m + ", Edge : " + jniReport.get(m));
        }

    }

    private void outputAndInsertFloat() {

        // output the result of feature 6 float
        System.out.print("Float Feature Detection Result ------ ");
        System.out.println();
    }

    private void outputAndInsertFile() {

        // output the result of feature 5 file
        System.out.print("File Feature Detection Result ------ ");
        System.out.println();

    }

    private void outputAndInsertConstraint() {

        // output the result of feature 4 constraint
        System.out.print("Constraint Feature Detection Result ------ ");
        System.out.println(constraintStmt.size());

        for (Unit u : constraintStmt.keySet()) {
            if (u instanceof Stmt) {
                insertConstraintStatement(constraintStmt.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }

    }


    private void outputBranchInLoop() {

        // output the result of feature 3 branch
        // output the result of branch
        System.out.println("Branch Feature Detection Result ------ ");
        System.out.println("-----Branch in Loop count : " + branchInLoop.size());

    }

    private void outputAndInsertBranch() {

        // output the result of feature 3 branch
        // output the result of branch
        System.out.println("Branch Feature Detection Result ------ ");
        System.out.println("-----If branch count : " + ifBranchReport.size());
        int switchCount = 0;
        for (Stmt s : switchBranchCount.keySet()) {
            switchCount = switchCount + switchBranchCount.get(s);
        }
        System.out.println("-----Switch branch count : " + switchCount);
        System.out.println("-----Branch count sum: " + (ifBranchReport.size() + switchCount));

        for (Unit u : ifBranchReport.keySet()) {
            if (u instanceof Stmt) {
                insertIfBranchStatement(ifBranchReport.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }

        for (Unit u : switchBranchReport.keySet()) {
            if (u instanceof Stmt) {
                insertSwitchBranchStatement(switchBranchReport.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }

    }

    private void outputAndInsertBitwise() {

        // output the result of feature 2 bitwise
        System.out.print("Bitwise Feature Detection Result ------ ");
        System.out.println(bitwiseStmt.size());

        /// Now, we get all the condition nodes before which we need to add instrumentations
        for (Unit u : bitwiseStmt.keySet()) {
            if (u instanceof Stmt) {
                insertBitwiseStatement(bitwiseStmt.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }
    }

    private void outputAndInsertArray() {

        // output the result of feature 1 array
        System.out.print("Array Feature Detection Result ------ ");
        System.out.println(arrayStmt.size());

        for (Unit u : arrayStmt.keySet()) {
            if (u instanceof Stmt) {
                insertArrayStatement(arrayStmt.get(u).getActiveBody().getUnits(), (Stmt) u);
            }
        }
    }

    private void checkArrayBitwiseBranchConstraint(Body body) {

        Chain units = body.getUnits();
        Iterator stmtIt = units.snapshotIterator();
        SootMethod method = body.getMethod();

        while (stmtIt.hasNext()) {
            Stmt stmt = (Stmt) stmtIt.next();

            if (stmt instanceof AssignStmt) {

                // check array
                if (!(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JVirtualInvokeExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JInterfaceInvokeExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JStaticInvokeExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JNewArrayExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JNewMultiArrayExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JSpecialInvokeExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JCastExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof StringConstant) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JInstanceOfExpr) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof JInstanceFieldRef) &&
                        !(((JAssignStmt) stmt).getLeftOpBox().getValue() instanceof JInstanceFieldRef) &&
                        !(((JAssignStmt) stmt).getRightOpBox().getValue() instanceof StaticFieldRef) &&
                        !(((JAssignStmt) stmt).getLeftOpBox().getValue() instanceof StaticFieldRef)) {

                    if (((AssignStmt) stmt).getLeftOp().toString().contains("[")
                            && ((AssignStmt) stmt).getLeftOp().toString().contains("]")) {
                        // e.g.
                        // $r2[i4] = $c3
                        arrayStmt.put(stmt, body.getMethod());
                    }
                    if (((AssignStmt) stmt).getRightOp().toString().contains("[")
                            && ((AssignStmt) stmt).getRightOp().toString().contains("]")) {
                        // e.g.
                        // $c5 = $r19[i8]
                        // r1 = "[\"abc\"]"
                        arrayStmt.put(stmt, body.getMethod());
                    }
                }

                // check bitwise

                if (((AssignStmt) stmt).getRightOp() instanceof Expr) {
                    Expr expr = (Expr) ((AssignStmt) stmt).getRightOp();
                    if (expr instanceof AndExpr || expr instanceof OrExpr ||
                            expr instanceof XorExpr || expr instanceof NegExpr ||
                            expr instanceof ShrExpr || expr instanceof ShlExpr ||
                            expr instanceof UshrExpr) {
                        bitwiseStmt.put(stmt, method);
                    }
                }

                // check constraint

                if (((JAssignStmt) stmt).getRightOp() instanceof JMulExpr ||
                        ((JAssignStmt) stmt).getRightOp() instanceof JDivExpr) {
                    constraintStmt.put(stmt, body.getMethod());
                }

            }

            // check branch

            if (stmt instanceof IfStmt) {
                ifBranchReport.put(stmt, body.getMethod());
            }
            if (stmt instanceof SwitchStmt) {
                int count = stmt.getUnitBoxes().size();
                switchBranchReport.put(stmt, body.getMethod());
                switchBranchCount.put(stmt, count);
            }

        }
    }


    private void checkBranchInLoop(SootMethod method) {
        Body activeBody = method.getActiveBody();
        LoopFinder loopFinder = new LoopFinder();
        Set<Loop> loops = loopFinder.getLoops(activeBody);
        for (Loop loop : loops) {
            List<Stmt> stmts = loop.getLoopStatements();
            for (Stmt stmt : stmts) {
                if (stmt instanceof IfStmt && !stmt.equals(loop.getHead())) {
                    branchInLoop.put(stmt, method);
                }
            }
        }
    }

    private void checkRecursionLast(CallGraph callGraph) {

        SootMethod srcMethod = null;
        while (!seemRecu.isEmpty()) {
            SootMethod checkMethod = seemRecu.poll();
            boolean flag = false;
            Set<SootMethod> visit = new HashSet<>();
            Queue<SootMethod> queue = new LinkedList<>();
            queue.offer(checkMethod);
            Stmt currentStmt = null;
            while (!queue.isEmpty()) {
                SootMethod nowMethod = queue.poll();
                visit.add(nowMethod);
                for (Iterator<Edge> it = callGraph.edgesOutOf(nowMethod); it.hasNext(); ) {
                    Edge edge = it.next();
                    SootMethod target = edge.tgt();
                    currentStmt = edge.srcStmt();
                    srcMethod = edge.src();
//                    if(target.equals(checkMethod)) {
//                    if (target.equals(checkMethod) && !currentStmt.toString().contains("interfaceinvoke")) {
                    if (target.equals(checkMethod) && !(currentStmt instanceof JInterfaceInvokeExpr)) {
                        flag = true;
                        break;
                    }
                    if (visit.contains(target)) {
                        continue;
                    }
                    queue.offer(target);
                }
                if (flag == true) {
                    confirmRecu.add(checkMethod);
                    recursionStmt.put(currentStmt, srcMethod);

                    break;
                }
            }
        }

    }

    private void checkRecursion(CallGraph callGraph) {

        Iterator<Edge> i = callGraph.iterator();
        while (i.hasNext()) {
            Edge edge = i.next();

            // Detect recursion
            // by detecting whether a method has been called more than once

            if (!edge.getSrc().toString().contains("<java.lang.") && !edge.getTgt().toString().contains("<java.lang.")
                    && !edge.getSrc().toString().contains("<java.io.") && !edge.getTgt().toString().contains("<java.io.")
                    && !edge.getSrc().toString().contains("init") && !edge.getTgt().toString().contains("init")
                    && (edge.getSrc().toString().contains(packageName) || edge.getTgt().toString().contains(packageName))
                    && (edge.getSrc().toString().contains(packageAnalysisName) || edge.getTgt().toString().contains(packageAnalysisName))) {
                if (visitedRecu.contains(edge.getTgt())) {
                    if (!recursionHelp.contains(edge.getTgt())) {
                        seemRecu.offer((SootMethod) edge.getTgt());
                        recursionHelp.add((SootMethod) edge.getTgt());
                    }
                }
                if (!visitedRecu.contains(edge.getTgt())) {
                    visitedRecu.add((SootMethod) edge.getTgt());
                }
            }
        }
    }

    private void checkLoop(Body body) {

        CFGGraphType graphtype = null;
        CFGToDotGraph drawer = null;

        if (drawer == null) {
            drawer = new CFGToDotGraph();
            drawer.setBriefLabels(false);
            drawer.setOnePage(true);
            drawer.setUnexceptionalControlFlowAttr("color", "black");
            drawer.setExceptionalControlFlowAttr("color", "red");
            drawer.setExceptionEdgeAttr("color", "lightgray");
//            drawer.setShowExceptions(true);
            graphtype = CFGGraphType.getGraphType("BriefUnitGraph");

            AltClassLoader.v().setAltClassPath("alt-class-path");
            AltClassLoader.v().setAltClasses(
                    new String[]{"soot.toolkits.graph.ArrayRefBlockGraph", "soot.toolkits.graph.Block",
                            "soot.toolkits.graph.Block$AllMapTo", "soot.toolkits.graph.BlockGraph",
                            "soot.toolkits.graph.BriefBlockGraph", "soot.toolkits.graph.BriefUnitGraph",
                            "soot.toolkits.graph.CompleteBlockGraph", "soot.toolkits.graph.CompleteUnitGraph",
                            "soot.toolkits.graph.TrapUnitGraph", "soot.toolkits.graph.UnitGraph",
                            "soot.toolkits.graph.ZonedBlockGraph"});
        }

        DirectedGraph<Unit> graph = (DirectedGraph<Unit>) graphtype.buildGraph(body);

        Chain units = body.getUnits();

        Iterator stmtIt = units.snapshotIterator();

        while (stmtIt.hasNext()) {
            Stmt stmt = (Stmt) stmtIt.next();
            if (stmt instanceof IfStmt) {
                seemLoop.add(stmt);
            }
        }

        checkLoopBesideBranch(graph, body.getMethod(), body);

    }

    private static void checkLoopBesideBranch(DirectedGraph<Unit> graph, SootMethod method, Body body) {

        while (!seemLoop.isEmpty()) {
            Unit checkNode = seemLoop.poll();
            boolean flag = false;
            Set<Unit> visit = new HashSet<>();
            Queue<Unit> queue = new LinkedList<>();
            queue.offer(checkNode);
            while (!queue.isEmpty()) {
                Unit now_node = queue.poll();
                visit.add(now_node);
                for (Unit target : graph.getSuccsOf(now_node)) {
                    if (target.equals(checkNode)) {
                        flag = true;
                        break;
                    }
                    if (visit.contains(target)) {
                        continue;
                    }
                    queue.offer(target);
                }
                if (flag == true) {
                    List<Unit> units = confirmLoop.get(method);
                    if (units == null) {
                        List<Unit> units1 = new ArrayList<>();
                        if (checkNode instanceof IfStmt) {
                            units1.add(checkNode);
                            confirmLoop.put(method, units1);
                            loopStmt.put(checkNode, method);
                        } else {
                            List<Unit> curret = graph.getPredsOf(checkNode);
                            for (Unit unit : curret) {
                                if (unit instanceof IfStmt) {
                                    units1.add(unit);
                                    confirmLoop.put(method, units1);
                                    loopStmt.put(unit, method);
                                }
                            }
                        }
                    } else {
                        if (checkNode instanceof IfStmt) {
                            units.add(checkNode);
                            confirmLoop.put(method, units);
                            loopStmt.put(checkNode, method);
                        } else {
                            List<Unit> curret = graph.getPredsOf(checkNode);
                            for (Unit unit : curret) {
                                if (unit instanceof IfStmt) {
                                    units.add(unit);
                                    confirmLoop.put(method, units);
                                    loopStmt.put(unit, method);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    protected void checkReflection(SootMethod method, Edge e) {
        if (method.isJavaLibraryMethod()) {
            if (method.toString().contains(("java.lang.reflect."))) {
                reflectionReport.put(method, e);
            }
        }
    }

    private void checkJni(SootMethod method, Edge e) {
        if (method.isNative()) {
            jniReport.put(method, e);
        }
    }

    private void insertLoopStatement(UnitPatchingChain units, Stmt u) {

        if (u instanceof IfStmt) {
            Value v = ((IfStmt) u).getCondition();
            if (v instanceof ConditionExpr) {
                ConditionExpr value = (ConditionExpr) v;

                if ((value.getOp1().getType() instanceof ByteType || value.getOp1().getType() instanceof ShortType ||
                        value.getOp1().getType() instanceof IntType || value.getOp1().getType() instanceof LongType ||
                        value.getOp1().getType() instanceof FloatType || value.getOp1().getType() instanceof DoubleType ||
                        value.getOp1().getType() instanceof BooleanType || value.getOp1().getType() instanceof CharType)
                        && (value.getOp2().getType() instanceof ByteType || value.getOp2().getType() instanceof ShortType ||
                        value.getOp2().getType() instanceof IntType || value.getOp2().getType() instanceof LongType ||
                        value.getOp2().getType() instanceof FloatType || value.getOp2().getType() instanceof DoubleType ||
                        value.getOp2().getType() instanceof BooleanType || value.getOp2().getType() instanceof CharType)) {

                    InvokeExpr loopExpr = Jimple.v().newStaticInvokeExpr(loopHelperInt.makeRef(), value.getOp1(), value.getOp2());
                    Stmt loopStmt = Jimple.v().newInvokeStmt(loopExpr);
                    units.insertBefore(loopStmt, u);
                }
            }
        }

    }

    private void insertRecursionStatement(UnitPatchingChain units, Stmt u) {

        InvokeExpr recursionExpr = Jimple.v().newStaticInvokeExpr(recursionHelper.makeRef());
        Stmt recursionStmt = Jimple.v().newInvokeStmt(recursionExpr);
        units.insertBefore(recursionStmt, u);

    }

    private void insertConstraintStatement(UnitPatchingChain units, Stmt u) {

        if (u instanceof AssignStmt) {
            Value value = ((AssignStmt) u).getRightOp();
            if (value instanceof BinopExpr) {
                if ((((BinopExpr) value).getOp1().getType() instanceof ByteType || ((BinopExpr) value).getOp1().getType() instanceof ShortType ||
                        ((BinopExpr) value).getOp1().getType() instanceof IntType || ((BinopExpr) value).getOp1().getType() instanceof LongType ||
                        ((BinopExpr) value).getOp1().getType() instanceof FloatType || ((BinopExpr) value).getOp1().getType() instanceof DoubleType
                ) && (((BinopExpr) value).getOp2().getType() instanceof ByteType || ((BinopExpr) value).getOp2().getType() instanceof ShortType ||
                        ((BinopExpr) value).getOp2().getType() instanceof IntType || ((BinopExpr) value).getOp2() instanceof LongType ||
                        ((BinopExpr) value).getOp2().getType() instanceof FloatType || ((BinopExpr) value).getOp2().getType() instanceof DoubleType)
                ) {
                    if (((AssignStmt) u).getLeftOp().getType() instanceof DoubleType) {
                        if (((BinopExpr) value).getOp2().getType() instanceof DoubleType) {
                            InvokeExpr constraintExpr = Jimple.v().newStaticInvokeExpr(constraintHelper1.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                    ((AssignStmt) u).getLeftOp());
                            Stmt constraintStmt = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), constraintExpr);
                            units.insertAfter(constraintStmt, u);
                        } else if (((BinopExpr) value).getOp2().getType() instanceof IntType) {
                            InvokeExpr constraintExpr = Jimple.v().newStaticInvokeExpr(constraintHelper2.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                    ((AssignStmt) u).getLeftOp());
                            Stmt constraintStmt = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), constraintExpr);
                            units.insertAfter(constraintStmt, u);
                        }
                    } else {
                        InvokeExpr constraintExpr = Jimple.v().newStaticInvokeExpr(constraintHelper0.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                ((AssignStmt) u).getLeftOp());
                        Stmt constraintStmt = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), constraintExpr);
                        units.insertAfter(constraintStmt, u);
                    }
                }
            }
        }
    }

    private void insertArrayStatement(UnitPatchingChain units, Stmt u) {
        if (!u.toString().contains("[]")) {
            if (((JAssignStmt) u).getRightOp().toString().contains("[") && ((JAssignStmt) u).getRightOp().getUseBoxes().size() == 2) {
                // e.g.
                // $c1 = $r2[i4]
                InvokeExpr arrayExpr2 = Jimple.v().newStaticInvokeExpr(arrayHelper2.makeRef(),
                        ((JAssignStmt) u).getRightOp().getUseBoxes().get(0).getValue(),
                        ((JAssignStmt) u).getRightOp().getUseBoxes().get(1).getValue());
                Stmt arrayStmt2 = Jimple.v().newInvokeStmt(arrayExpr2);
                units.insertAfter(arrayStmt2, u);

            } else if (((JAssignStmt) u).getLeftOp().toString().contains("[") && ((JAssignStmt) u).getLeftOp().getUseBoxes().size() == 2) {
                // e.g.
                // $r4[7] = "("
                // $r12[0] = $r2
                // $r0[14] = "Unknown"
                // $r1[3] = null
                InvokeExpr arrayExpr2 = Jimple.v().newStaticInvokeExpr(arrayHelper2.makeRef(),
                        ((JAssignStmt) u).getLeftOp().getUseBoxes().get(0).getValue(),
                        ((JAssignStmt) u).getLeftOp().getUseBoxes().get(1).getValue());
                Stmt arrayStmt2 = Jimple.v().newInvokeStmt(arrayExpr2);
                units.insertAfter(arrayStmt2, u);

            } else {
                if (u instanceof AssignStmt) {
                    // e.g.
                    // $r4 = newarray (int)[i3]
                    Value value = ((AssignStmt) u).getLeftOp();
                    InvokeExpr arrayExpr3 = Jimple.v().newStaticInvokeExpr(arrayHelper3.makeRef(), ((JAssignStmt) u).getLeftOpBox().getValue());
                    Stmt arrayStmt3 = Jimple.v().newInvokeStmt(arrayExpr3);
                    units.insertAfter(arrayStmt3, u);
                    System.out.println("!!!error111!!!");
                }
            }
        } else {
            if (u instanceof AssignStmt) {
                Value value = ((AssignStmt) u).getLeftOp();

                if (value instanceof StaticFieldRef || value instanceof InstanceFieldRef) {
//                    InvokeExpr arrayExpr3 = Jimple.v().newStaticInvokeExpr(arrayHelper3.makeRef(), ((AssignStmt) u).getLeftOp());
//                    Stmt arrayStmt3 = Jimple.v().newInvokeStmt(arrayExpr3);
//                    units.insertAfter(arrayStmt3, u);
                } else {
                    // e.g.
                    // r17 = newarray (int[])[$i1]
                    // $r2 = staticinvoke <org.apache.commons.codec.net.URLCodec: byte[] encodeUrl(java.util.BitSet,byte[])>($r1, r0)
                    // $r4 = r0.<nl.bigo.curta.CurtaParser: int[] jj_la1>
                    // $i10 = virtualinvoke $r8.<java.io.Reader: int read(char[],int,int)>(r4, 0, $i9)
                    InvokeExpr arrayExpr3 = Jimple.v().newStaticInvokeExpr(arrayHelper3.makeRef(), ((JAssignStmt) u).getLeftOp());
                    Stmt arrayStmt3 = Jimple.v().newInvokeStmt(arrayExpr3);
                    units.insertAfter(arrayStmt3, u);
                    System.out.println("!!!error222!!!");
                }

            }
        }
    }


    private void insertBitwiseStatement(UnitPatchingChain units, Stmt u) {

        if (u instanceof AssignStmt) {
            Value value = ((AssignStmt) u).getRightOp();
            if (value instanceof BinopExpr) {
                if ((((BinopExpr) value).getOp1().getType() instanceof ByteType || ((BinopExpr) value).getOp1().getType() instanceof ShortType
                        || ((BinopExpr) value).getOp1().getType() instanceof IntType || ((BinopExpr) value).getOp1().getType() instanceof LongType
                        || ((BinopExpr) value).getOp1().getType() instanceof CharType || ((BinopExpr) value).getOp1().getType() instanceof BooleanType
                ) && (((BinopExpr) value).getOp2().getType() instanceof ByteType || ((BinopExpr) value).getOp2().getType() instanceof ShortType
                        || ((BinopExpr) value).getOp2().getType() instanceof IntType || ((BinopExpr) value).getOp2().getType() instanceof LongType
                        || ((BinopExpr) value).getOp2().getType() instanceof CharType || ((BinopExpr) value).getOp2().getType() instanceof BooleanType
                )) {
                    if (((AssignStmt) u).getLeftOp().getType() instanceof LongType) {
                        if (((BinopExpr) value).getOp2().getType() instanceof LongType) {
                            InvokeExpr bitwiseExpr2 = Jimple.v().newStaticInvokeExpr(bitwiseHelper1.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                    ((AssignStmt) u).getLeftOp());
                            Stmt bitwiseStmt2 = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), bitwiseExpr2);
                            units.insertAfter(bitwiseStmt2, u);
                        } else if (((BinopExpr) value).getOp2().getType() instanceof IntType) {
                            InvokeExpr bitwiseExpr2 = Jimple.v().newStaticInvokeExpr(bitwiseHelper2.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                    ((AssignStmt) u).getLeftOp());
                            Stmt bitwiseStmt2 = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), bitwiseExpr2);
                            units.insertAfter(bitwiseStmt2, u);
                        } else if (((BinopExpr) value).getOp2().getType() instanceof CharType) {
                            InvokeExpr bitwiseExpr2 = Jimple.v().newStaticInvokeExpr(bitwiseHelper3.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                    ((AssignStmt) u).getLeftOp());
                            Stmt bitwiseStmt2 = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), bitwiseExpr2);
                            units.insertAfter(bitwiseStmt2, u);
                        }
                    } else {
                        InvokeExpr bitwiseExpr2 = Jimple.v().newStaticInvokeExpr(bitwiseHelper0.makeRef(), ((BinopExpr) value).getOp1(), ((BinopExpr) value).getOp2(),
                                ((AssignStmt) u).getLeftOp());
                        Stmt bitwiseStmt2 = Jimple.v().newAssignStmt(((AssignStmt) u).getLeftOp(), bitwiseExpr2);
                        units.insertAfter(bitwiseStmt2, u);
                    }
                }
            }
        }
    }

    private void insertIfBranchStatement(UnitPatchingChain units, Stmt u) {

        if (u instanceof IfStmt) {
            Value v = ((IfStmt) u).getCondition();
            if (v instanceof ConditionExpr) {
                ConditionExpr value = (ConditionExpr) v;

                if ((value.getOp1().getType() instanceof ByteType || value.getOp1().getType() instanceof ShortType ||
                        value.getOp1().getType() instanceof IntType || value.getOp1().getType() instanceof LongType ||
                        value.getOp1().getType() instanceof FloatType || value.getOp1().getType() instanceof DoubleType ||
                        value.getOp1().getType() instanceof BooleanType || value.getOp1().getType() instanceof CharType)
                        && (value.getOp2().getType() instanceof ByteType || value.getOp2().getType() instanceof ShortType ||
                        value.getOp2().getType() instanceof IntType || value.getOp2().getType() instanceof LongType ||
                        value.getOp2().getType() instanceof FloatType || value.getOp2().getType() instanceof DoubleType ||
                        value.getOp2().getType() instanceof BooleanType || value.getOp2().getType() instanceof CharType)) {

                    InvokeExpr ifBranchExpr = Jimple.v().newStaticInvokeExpr(ifBranchHelper.makeRef(), value.getOp1(), value.getOp2());
                    Stmt ifBranchStmt = Jimple.v().newInvokeStmt(ifBranchExpr);
                    units.insertBefore(ifBranchStmt, u);
                }

            }
        }

    }

    private void insertSwitchBranchStatement(UnitPatchingChain units, Stmt u) {
        InvokeExpr switchBranchExpr = Jimple.v().newStaticInvokeExpr(switchBranchHelper.makeRef());
        Stmt switchBranchStmt = Jimple.v().newInvokeStmt(switchBranchExpr);
        units.insertBefore(switchBranchStmt, u);

    }

    private void insertComInst(Chain units, Stmt stmt) {
        /// to be refined later
        if (stmt instanceof IfStmt) {
            Value v = ((IfStmt) stmt).getCondition();
            if (v instanceof ConditionExpr) {
                ConditionExpr value = (ConditionExpr) v;

                if ((value.getOp1().getType() instanceof ByteType || value.getOp1().getType() instanceof ShortType ||
                        value.getOp1().getType() instanceof IntType || value.getOp1().getType() instanceof LongType ||
                        value.getOp1().getType() instanceof FloatType || value.getOp1().getType() instanceof DoubleType ||
                        value.getOp1().getType() instanceof BooleanType || value.getOp1().getType() instanceof CharType)
                        && (value.getOp2().getType() instanceof ByteType || value.getOp2().getType() instanceof ShortType ||
                        value.getOp2().getType() instanceof IntType || value.getOp2().getType() instanceof LongType ||
                        value.getOp2().getType() instanceof FloatType || value.getOp2().getType() instanceof DoubleType ||
                        value.getOp2().getType() instanceof BooleanType || value.getOp2().getType() instanceof CharType)) {
                    InvokeExpr storeExpr = Jimple.v().newStaticInvokeExpr(recursionConditionHelperInt.makeRef(),
                            value.getOp1(), value.getOp2());
                    Stmt storeStmt = Jimple.v().newInvokeStmt(storeExpr);
                    units.insertBefore(storeStmt, stmt);
                }
            }
        }
    }

    public void scanConditionOfRecurion(HashMap<SootMethod, DirectedGraph<Unit>> cfgMap, Unit unit) {

        Collection<SootMethod> sootMethods = recursionStmt.get(unit);
        for (SootMethod method : sootMethods) {

            DirectedGraph<Unit> graph = null;
            if (cfgMap.containsKey(method) == false) {
                Map<String, String> option = new HashMap<String, String>();
                option.put("graph-type", "BriefUnitGraph");
                CFGGraphType graphtype = CFGGraphType.getGraphType(PhaseOptions.getString(option, "graph-type"));
                graph = (DirectedGraph<Unit>) graphtype.buildGraph(method.getActiveBody());
                cfgMap.put(method, graph);
            } else {
                graph = cfgMap.get(method);
            }
            /// we calculate the condition nodes intra-procedurally
            scanConditionNodesInMethod(graph, unit, unit, new ArrayList<Unit>(), unit);

        }

    }

    protected List<Unit> convertSwitch(SwitchStmt s) {
        List<Unit> result = new LinkedList<Unit>();

        List<Expr> cases = new LinkedList<Expr>();
        List<Unit> targets = new LinkedList<Unit>();
        Unit defaultTarget = s.getDefaultTarget();

        if (s instanceof TableSwitchStmt) {
            TableSwitchStmt arg0 = (TableSwitchStmt) s;
            int counter = 0;
            for (int i = arg0.getLowIndex(); i <= arg0.getHighIndex(); i++) {
                cases.add(Jimple.v().newEqExpr(arg0.getKey(), IntConstant.v(i)));
                targets.add(arg0.getTarget(counter));
                counter++;
            }
        } else {
            LookupSwitchStmt arg0 = (LookupSwitchStmt) s;
            for (int i = 0; i < arg0.getTargetCount(); i++) {
                cases.add(Jimple.v().newEqExpr(arg0.getKey(), IntConstant.v(arg0.getLookupValue(i))));
                targets.add(arg0.getTarget(i));
            }
        }

        for (int i = 0; i < cases.size(); i++) {
            // create the ifstmt
            Unit ifstmt = ifStmtFor(cases.get(i), targets.get(i), s);
            result.add(ifstmt);
        }
        if (defaultTarget != null) {
            Unit gotoStmt = gotoStmtFor(defaultTarget, s);
            result.add(gotoStmt);
        }
        return result;
    }

    private void scanConditionNodesInMethod(DirectedGraph<Unit> graph, Unit node, Unit currentNode, List<Unit> visitedNodeList, Unit originalNode) {
        if (visitedNodeList.contains(node)) {
            return;
        }

        visitedNodeList.add(node);

        PostDominatorAnalysis postDominatorAnalysis = new PostDominatorAnalysis((UnitGraph) graph);
        List<Unit> preds = graph.getPredsOf(node);
        if (!preds.isEmpty()) {
            for (Unit n : preds) {
                if (!postDominatorAnalysis.postDominates((Stmt) currentNode, (Stmt) n)) {
                    /// for each pre node, if the node is not postDominate currentNode, we check its type
                    /// and recursively invoke this method
                    Stmt st = (Stmt) n;
                    if (isInterestingType(st)) {
                        instrumentConditionNodeMethodMap.put(st, ((UnitGraph) graph).getBody().getMethod());
                        if (!instrumentConditionNodeIdMap.keySet().contains(st)) {
//                            instrumentConditionNodeIdMap.put(st, ifId++);
                            if (st instanceof IfStmt) {
                                Value v = ((IfStmt) st).getCondition();
                                if (v instanceof ConditionExpr) {
                                    ConditionExpr value = (ConditionExpr) v;
                                    if ((value.getOp1().getType() instanceof IntType || value.getOp1().getType() instanceof BooleanType ||
                                            value.getOp1().getType() instanceof CharType || value.getOp1().getType() instanceof ShortType)
                                            && (value.getOp2().getType() instanceof IntType || value.getOp2().getType() instanceof BooleanType ||
                                            value.getOp2().getType() instanceof CharType || value.getOp2().getType() instanceof ShortType)) {
                                        instrumentConditionNodeIdMap.put(st, ifId++);
                                    }
                                }
                            }
                        }

                        Set<String> stringResult = new HashSet<>();
                        if (instrumentConditionNodeEvent.get(st) != null) {
                            Set<String> stringSet = new HashSet<>();
                            stringSet = instrumentConditionNodeEvent.get(st);
                            stringResult.addAll(stringSet);
                        }
                        if (instrumentConditionNodeEvent.get(currentNode) != null) {
                            Set<String> stringSet = new HashSet<>();
                            stringSet = instrumentConditionNodeEvent.get(currentNode);
                            stringResult.addAll(stringSet);
                        }
                        String string = instrumentPointNodeEvent.get(currentNode);
                        if (string != null) {
                            stringResult.add(string);
                        }
                        instrumentConditionNodeEvent.put(st, stringResult);
                        /// here we record the switch for improving the performance
                        if (st instanceof SwitchStmt) {
                            if (switchInstPointsMap.containsKey(st) == false) {
                                switchInstPointsMap.put(st, new HashSet<Unit>());
                            }
                            switchInstPointsMap.get(st).add(originalNode);
                        }
                    }
                    scanConditionNodesInMethod(graph, n, n, visitedNodeList, originalNode);
                } else {
                    /// Otherwise, we ignore pre and recursively invoke this method
                    /// this step may be optimized
                    scanConditionNodesInMethod(graph, n, currentNode, visitedNodeList, originalNode);
                }
            }
        }
    }

    protected Unit ifStmtFor(Value condition, Unit target, Host createdFrom) {
        IfStmt stmt = Jimple.v().newIfStmt(condition, target);
        stmt.addAllTagsOf(createdFrom);
        return stmt;
    }

    protected Unit gotoStmtFor(Unit target, Host createdFrom) {
        GotoStmt stmt = Jimple.v().newGotoStmt(target);
        stmt.addAllTagsOf(createdFrom);
        return stmt;
    }

    public static boolean isInterestingType(Unit st) {
        if (st instanceof IfStmt || st instanceof BranchTargetStmt || st instanceof SwitchStmt) {
            return true;
        }
        return false;
    }
}
