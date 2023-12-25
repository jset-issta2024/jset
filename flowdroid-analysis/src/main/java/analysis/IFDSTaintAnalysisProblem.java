package analysis;

import com.google.common.collect.Maps;
import heros.FlowFunctions;
import heros.InterproceduralCFG;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.toolkits.ide.DefaultJimpleIFDSTabulationProblem;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class IFDSTaintAnalysisProblem extends DefaultJimpleIFDSTabulationProblem<DataFlowFact, InterproceduralCFG<Unit, SootMethod>> {

    private String methodName;

    private final FieldSensitiveFlowFunctions flowFunctions;

    public IFDSTaintAnalysisProblem(InterproceduralCFG<Unit, SootMethod> icfg, FieldSensitiveFlowFunctions flowFunctions, String methodName) {
        super(icfg);
        this.flowFunctions = flowFunctions;
        this.methodName = methodName;
    }

    @Override
    public Map<Unit, Set<DataFlowFact>> initialSeeds() {
        Map<Unit,Set<DataFlowFact>> res = Maps.newHashMap();
        for(SootClass c : Scene.v().getApplicationClasses()){
            for(SootMethod m : c.getMethods()){
                if(!m.hasActiveBody()){
                    continue;
                }
//                String packageName = "demo.byanalyzed";
//                if(m.getName().equals("doGet")){
//                String methodName = "main";
                if(m.getName().equals(methodName)){
//                if(m.getName().contains(packageName)){
                    //todo check method name need to be changed
                    res.put(m.getActiveBody().getUnits().getFirst(), Collections.singleton(zeroValue()));
                }
            }
        }
        return res;
    }

    @Override
    protected FlowFunctions<Unit, DataFlowFact, SootMethod> createFlowFunctionsFactory() {
        return flowFunctions;
    }

    @Override
    protected DataFlowFact createZeroValue() {
        return DataFlowFact.zero();
    }

    @Override
    public boolean autoAddZero() {
        return true;
    }
}