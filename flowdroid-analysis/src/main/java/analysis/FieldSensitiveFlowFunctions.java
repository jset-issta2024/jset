package analysis;

//import EDU.purdue.cs.bloat.tree.Assign;

import com.google.common.collect.Sets;
import heros.FlowFunction;
import heros.FlowFunctions;
import heros.InterproceduralCFG;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.*;
import soot.jimple.internal.JInstanceFieldRef;

import java.util.*;

//import com.google.gson.internal.$Gson$Preconditions;

public class FieldSensitiveFlowFunctions implements FlowFunctions<Unit, DataFlowFact, SootMethod> {

    protected InterproceduralCFG<Unit, SootMethod> icfg;
    private final VulnerabilityReporter reporter;
    private String packageName;
    private String packageName2;
    private String source;
    private Set<Unit> sinks;
    private final Map<Local, String> mapLocalTypeArrFie = new HashMap<>();

    public FieldSensitiveFlowFunctions(VulnerabilityReporter reporter) {
        this.reporter = reporter;
    }

    public void setICFG(InterproceduralCFG<Unit, SootMethod> icfg, String source, Set<Unit> sinks, String packageName, String packageName2) {
        this.icfg = icfg;
        this.source = source;
        this.sinks = sinks;
        this.packageName = packageName;
        this.packageName2 = packageName2;
    }

    protected void prettyPrint(Unit stmt, DataFlowFact fact) {
        //todo : check method name need to be changed
        if (icfg.getMethodOf(stmt).toString().contains(packageName) |
                icfg.getMethodOf(stmt).toString().contains(packageName2))
            System.out.println("Method :" + icfg.getMethodOf(stmt) + ", Stmt: " + stmt + ", Fact: " + fact);
    }

    // todo: fact inside callee method should be killed

    @Override
    public FlowFunction<DataFlowFact> getCallFlowFunction(final Unit callSite, final SootMethod callee) {
        return new FlowFunction<DataFlowFact>() {
            @Override
            public Set<DataFlowFact> computeTargets(DataFlowFact fact) {

                if (fact.equals(DataFlowFact.zero()))
                    return Collections.emptySet();

//                System.out.println("From call flow function");
//                prettyPrint(callSite, fact);
//                System.out.println("From call flow function");

                Set<DataFlowFact> out = Sets.newHashSet();

                Stmt callSiteStmt = (Stmt) callSite;

                if (callSiteStmt.getInvokeExpr() != null) {
                    List<Value> args = callSiteStmt.getInvokeExpr().getArgs();

                    // if parameter in facts
                    // check parameter belongs to basic type or reference type
                    // basic type : propagate by call to return
                    // reference type : propagate by call and return

                    if (((Stmt) callSite).getInvokeExpr().getArgs().contains(fact)) {
                        if (mapLocalTypeArrFie.containsKey(fact)) {
                            out.add((fact));
                        }
                    }


                    if (args.size() != 0) {
                        for (int i = 0; i < callee.getParameterCount(); ++i) {
                            Value arg = callSiteStmt.getInvokeExpr().getArg(i);
                            if (fact.getVariable().equals(arg)) {
                                out.add(new DataFlowFact(callee.getActiveBody().getParameterLocal(i)));
                            }
                        }
                    }

                    // fact formal as i0 not (array index)

                }

                // add by mx
                // handle interprocedural data-flows
                // when a taint x reaches a call site of form foo(x)
                // map the variable x to the respective parameter local of the callee method

                return out;
            }

        };
    }

    public FlowFunction<DataFlowFact> getCallToReturnFlowFunction(final Unit call, final Unit returnSite) {
        return new FlowFunction<DataFlowFact>() {

            @Override
            public Set<DataFlowFact> computeTargets(DataFlowFact val) {

                Set<DataFlowFact> out = Sets.newHashSet();

                // if parameter in facts
                // check parameter belongs to basic type or reference type
                // basic type : propagate by call to return
                // reference type : propagate by call and return

                if (((Stmt) call).getInvokeExpr().getArgs().contains(val)) {
                    if (mapLocalTypeArrFie.containsKey(val)) {
                        return Collections.emptySet();
                    } else {
                        out.add(val);
                    }
                }


//                System.out.println("From Call To Return flow function");
//                prettyPrint(call, val);
//                System.out.println("From Call To Return flow function");

                Stmt callSiteStmt = (Stmt) call;

                if (((Stmt) call).getInvokeExpr() instanceof VirtualInvokeExpr) {
                    out.add(val);
                } else if (((Stmt) call).getInvokeExpr() instanceof SpecialInvokeExpr) {
                    out.add(val);
                } else if (((Stmt) call).getInvokeExpr() instanceof StaticInvokeExpr) {
                    if (call instanceof AssignStmt && (!val.getVariable().equals(((DefinitionStmt) call).getLeftOp()))) {
                        out.add(val);
                    } else if (!(call instanceof AssignStmt)) {
                        out.add(val);
                    }
                }

                // kill ref in parameter

                modelStringOperations(val, out, callSiteStmt);

                modelStreamOperations(val, out, callSiteStmt);

                if (val.equals(DataFlowFact.zero())) {

                    if (call instanceof AssignStmt) {
                        AssignStmt assignStmt = (AssignStmt) call;
                        if (assignStmt.getLeftOp() instanceof Local) {
                            if (val.getVariable().equals(assignStmt.getRightOp())) {
                                Local leftOpLocal = (Local) assignStmt.getLeftOp();
                                out.add(new DataFlowFact(leftOpLocal));
                            }
                            // add taint point
//                            String source = "getParameter";
//                            String source = "secret";
                            if (call.toString().contains(source)) {
                                Local leftOpLocal = (Local) assignStmt.getLeftOp();
                                out.add(new DataFlowFact(leftOpLocal));
                            }
                        }
                    }

                    // add by mx
                    // when the data-flow fact ZERO reaches a call statement
                    // x = getParameter(...)
                    // genrate a data-flow fact that represent x
                    // to generate the data-flow fact
                    // use the appropriate constructor of DataFlowFact

                }

                return out;
            }
        };
    }

    private void modelStringOperations(DataFlowFact fact, Set<DataFlowFact> out,
                                       Stmt callSiteStmt) {
        if (callSiteStmt instanceof AssignStmt && callSiteStmt.toString().contains("java.lang.StringBuilder append(")
                && callSiteStmt.getInvokeExpr() instanceof InstanceInvokeExpr) {
            Value arg0 = callSiteStmt.getInvokeExpr().getArg(0);
            Value base = ((InstanceInvokeExpr) callSiteStmt.getInvokeExpr()).getBase();
            /*Does the propagated value match the first parameter of the append call or the base variable*/
            if (fact.getVariable().equals(arg0) || fact.getVariable().equals(base)) {
                /*Yes, then taint the left side of the assignment*/
                Value leftOp = ((AssignStmt) callSiteStmt).getLeftOp();
                if (leftOp instanceof Local) {
                    out.add(new DataFlowFact((Local) leftOp));
                }
            }
        }

        /*For any call x = var.toString(), if the base variable var is tainted, then x is tainted.*/

//        if(callSiteStmt instanceof AssignStmt && callSiteStmt.toString().contains("toString()")){
//            if(callSiteStmt.getInvokeExpr() instanceof InstanceInvokeExpr){
//                InstanceInvokeExpr instanceInvokeExpr = (InstanceInvokeExpr) callSiteStmt.getInvokeExpr();
//                if(fact.getVariable().equals(instanceInvokeExpr.getBase())){
//                    Value leftOp = ((AssignStmt) callSiteStmt).getLeftOp();
//                    if(leftOp instanceof Local){
//                        out.add(new DataFlowFact((Local) leftOp));
//                    }
//                }
//            }
//        }

        // refactor by bian zheng
        String[] methodList = {"concat(", "substring(", "toString()"};
        if (callSiteStmt instanceof AssignStmt) {
            for (String match : methodList) {
                if (callSiteStmt.toString().contains(match)) {
                    if (callSiteStmt.getInvokeExpr() instanceof InstanceInvokeExpr) {
                        InstanceInvokeExpr instanceInvokeExpr = (InstanceInvokeExpr) callSiteStmt.getInvokeExpr();
                        if (fact.getVariable().equals(instanceInvokeExpr.getBase())) {
                            Value leftOp = ((AssignStmt) callSiteStmt).getLeftOp();
                            if (leftOp instanceof Local) {
                                out.add(new DataFlowFact((Local) leftOp));
                            }
                        }
                    }
                    break;
                }
            }
        }

    }

    private void modelStreamOperations(DataFlowFact fact, Set<DataFlowFact> out,
                                       Stmt callSiteStmt) {

        if (callSiteStmt instanceof AssignStmt && callSiteStmt.toString().contains("java.lang.StringBuilder append(")
                && callSiteStmt.getInvokeExpr() instanceof InstanceInvokeExpr) {
            Value arg0 = callSiteStmt.getInvokeExpr().getArg(0);
            Value base = ((InstanceInvokeExpr) callSiteStmt.getInvokeExpr()).getBase();
            /*Does the propagated value match the first parameter of the append call or the base variable*/
            if (fact.getVariable().equals(arg0) || fact.getVariable().equals(base)) {
                /*Yes, then taint the left side of the assignment*/
                Value leftOp = ((AssignStmt) callSiteStmt).getLeftOp();
                if (leftOp instanceof Local) {
                    out.add(new DataFlowFact((Local) leftOp));
                }
            }
        }

        /*For any call x = var.toString(), if the base variable var is tainted, then x is tainted.*/

        // refactor by bian zheng
        String[] methodList = {"concat(", "substring(", "toString()"};
        if (callSiteStmt instanceof AssignStmt) {
            for (String match : methodList) {
                if (callSiteStmt.toString().contains(match)) {
                    if (callSiteStmt.getInvokeExpr() instanceof InstanceInvokeExpr) {
                        InstanceInvokeExpr instanceInvokeExpr = (InstanceInvokeExpr) callSiteStmt.getInvokeExpr();
                        if (fact.getVariable().equals(instanceInvokeExpr.getBase())) {
                            Value leftOp = ((AssignStmt) callSiteStmt).getLeftOp();
                            if (leftOp instanceof Local) {
                                out.add(new DataFlowFact((Local) leftOp));
                            }
                        }
                    }
                    break;
                }
            }
        }

    }

    @Override
    public FlowFunction<DataFlowFact> getNormalFlowFunction(final Unit curr, Unit succ) {
        return new FlowFunction<DataFlowFact>() {
            @Override
            public Set<DataFlowFact> computeTargets(DataFlowFact fact) {

//                System.out.println("From normal flow function");
//                prettyPrint(curr, fact);
//                System.out.println("From normal flow function");

                Set<DataFlowFact> out = Sets.newHashSet();

                if (curr instanceof InvokeStmt) {
                    return Collections.emptySet();
                }

                // e.g. r1 := @parameter0  copy formal parameter
                // e.g. r0 := @this        copy this
                if (curr instanceof IdentityStmt) {
                    out.add(fact);
                }

                // contains ifStmt, GotoStmt, switchStmt, monitorStmt, return Stmt, throwStmt, ...
                if (!(curr instanceof DefinitionStmt)) {
                    out.add(fact);
                }

                if (curr instanceof AssignStmt && ((AssignStmt) curr).getRightOp() instanceof NewExpr) {
                    out.add(fact);
                }


                // two statements are considered here
                // both IdentityStmt and AssignStmt extend DefinitionStmt
                // IdentityStmt = variable ":=" identity_value
                // AssignStmt = variable "=" rvalue

                else if (curr instanceof AssignStmt) {

                    if (!((DefinitionStmt) curr).getLeftOp().toString().equals(fact.toString())) {
                        out.add(fact);
                    }

                    Value leftOp = ((AssignStmt) curr).getLeftOp();
                    Value rightOp = ((AssignStmt) curr).getRightOp();

                    // support assign statement of the form x = y op z
                    // when a local data-flow fact y or z reaches the statement
                    // generate a local data-flow fact for x and add it into the out set

                    if (rightOp instanceof BinopExpr) {
                        Value Op1 = ((BinopExpr) rightOp).getOp1();
                        Value Op2 = ((BinopExpr) rightOp).getOp2();
                        if (fact.getVariable().equals(Op1) | fact.getVariable().equals(Op2)) {
                            out.add(new DataFlowFact((Local) leftOp));
                        }
                    } else {
                        // x = y
                        // x = y.f, x.f = y
                        // x.f = y.f cannot exist, it will become temp = y.f and x.f = temp
                        // x = y[i], x[i] = y
                        // x[i] = y[i] cannot exist, it will become temp = y[i] and x[i] = temp

                        if (((Stmt) curr).containsFieldRef()) {

                            // stmt contain field
                            // x.a = y store stmt
                            // x = y.b load stmt

                            // generate a data-flow fact x.f for a statement x.f = y
                            // when y is tainted

                            FieldRef fieldRef = ((AssignStmt) curr).getFieldRef();

                            if (leftOp.toString().contains(fieldRef.toString())) {
                                // store stmt, i.e., x.a = y
                                if (fact.getVariable().equals(rightOp)) {
                                    if (fieldRef.getUseBoxes().size() > 0) {
                                        out.add(new DataFlowFact((Local) fieldRef.getUseBoxes().get(0).getValue(), fieldRef.getField()));

                                        // delete base
                                        // x.t = y
                                        // save x.t not save x
//                                        out.add(new DataFlowFact((Local) ((JInstanceFieldRef) fieldRef).getBase()));
                                    }

                                }

                            } else if (rightOp.toString().contains(fieldRef.toString())) {

                                if (fieldRef.getUseBoxes().size() > 0) {
                                    mapLocalTypeArrFie.put((Local) fieldRef.getUseBoxes().get(0).getValue(), "Field");
                                    mapLocalTypeArrFie.put((Local) leftOp, "Field");
                                }

                                // load stmt, i.e., x = y.b
                                if (fact.getField() != null) {

                                    if (fact.getField().equals(fieldRef.getField()) &&
                                            fact.getVariable().equals(fieldRef.getUseBoxes().get(0).getValue())) {
                                        out.add(new DataFlowFact((Local) leftOp));
                                    }
//                                    else {
//                                        System.out.println("---------");
//                                        System.out.println("not match");
//                                        System.out.println("---------");
//                                    }
                                }
                            } else {
                                System.out.println("----");
                                System.out.println("exit");
                                System.out.println("----");
                            }
                        } else if (((AssignStmt) curr).containsArrayRef()) {
                            // stmt contain array ref
                            // x[i] = y store stmt
                            // x = y[i] load stmt

                            ArrayRef arrayRef = ((AssignStmt) curr).getArrayRef();
                            if (leftOp.toString().contains(arrayRef.toString())) {
                                // store stmt, i.e., x[i] = y
                                if (fact.getVariable().equals(rightOp)) {
//                                    out.add(new DataFlowFact((Local) arrayRef.getBase(), arrayRef.getIndex()));
                                    out.add(new DataFlowFact((Local) arrayRef.getBase()));
                                }

                            } else if (rightOp.toString().contains(arrayRef.toString())) {
                                mapLocalTypeArrFie.put((Local) arrayRef.getBase(), "Array");
                                mapLocalTypeArrFie.put((Local) leftOp, "Array");
                                // load stmt, i.e., x = y[i]
//                                if(fact.getOffset() != null) {
//                                    if (fact.getOffset().equals(arrayRef.getIndex()) &&
//                                            fact.getVariable().equals(arrayRef.getBase())) {
                                if (fact.getVariable().equals(arrayRef.getBase())) {
                                    out.add(new DataFlowFact((Local) leftOp));
                                }
//                                    else {
//                                        System.out.println("---------");
//                                        System.out.println("not match");
//                                        System.out.println("---------");
//                                    }
//                                }
                            } else {
                                System.out.println("----");
                                System.out.println("exit");
                                System.out.println("----");
                            }
                        } else {
                            // stmt not contain field and not contain array
                            // x = y
                            if (fact.getVariable().equals(rightOp)) {
                                out.add(new DataFlowFact((Local) leftOp));
                            }
                            // support assign statement of the form x = y
                            // when a local data-flow fact y reaches the statement
                            // generate a local data-flow fact for x and add it into the out set
                        }

                    }


                }
                // each type consider three options
                // x y
                // x.f y.f
                // x[i] y[i]

                return out;
            }
        };
    }

    @Override
    public FlowFunction<DataFlowFact> getReturnFlowFunction(final Unit callSite, SootMethod callee, final Unit exitStmt, Unit retSite) {
        return new FlowFunction<DataFlowFact>() {
            @Override
            public Set<DataFlowFact> computeTargets(DataFlowFact fact) {

                if (fact.equals(DataFlowFact.zero()))
                    return Collections.emptySet();


//                System.out.println("From return flow function");
//                prettyPrint(callSite, fact);
//                System.out.println("From return flow function");

                Set<DataFlowFact> out = Sets.newHashSet();

                // if parameter in facts
                // check parameter belongs to basic type or reference type
                // basic type : propagate by call to return
                // reference type : propagate by call and return

                if (((Stmt) callSite).getInvokeExpr().getArgs().contains(fact)) {
                    if (mapLocalTypeArrFie.containsKey(fact)) {
                        out.add((fact));
                    }
                }

                if (exitStmt instanceof ReturnStmt) {
                    ReturnStmt returnStmt = (ReturnStmt) exitStmt;
                    if (returnStmt.getOp().equivTo(fact.getVariable())) {
                        if (callSite instanceof AssignStmt) {
                            AssignStmt assignStmt = (AssignStmt) callSite;
                            out.add(new DataFlowFact((Local) assignStmt.getLeftOp()));
                        }
                    }
                }
                else if (exitStmt instanceof ReturnVoidStmt) {
                    // return directly beside local variable
                    if (fact.toString().contains("this <")) {
                        out.add(fact);
                    }
                }

                return out;
            }
        };
    }

}