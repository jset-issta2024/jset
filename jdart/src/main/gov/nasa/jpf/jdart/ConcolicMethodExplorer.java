/*
 * Copyright (C) 2015, United States Government, as represented by the 
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 * 
 * The PSYCO: A Predicate-based Symbolic Compositional Reasoning environment 
 * platform is licensed under the Apache License, Version 2.0 (the "License"); you 
 * may not use this file except in compliance with the License. You may obtain a 
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0. 
 * 
 * Unless required by applicable law or agreed to in writing, software distributed 
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations under the License.
 */
package gov.nasa.jpf.jdart;

import gov.nasa.jpf.JPF;
import gov.nasa.jpf.constraints.api.*;
import gov.nasa.jpf.constraints.java.ObjectConstraints;
import gov.nasa.jpf.constraints.parser.ParserUtil;
import gov.nasa.jpf.constraints.types.Type;
import gov.nasa.jpf.jdart.ConcolicUtil.Pair;
import gov.nasa.jpf.jdart.config.*;
import gov.nasa.jpf.jdart.constraints.ConstraintsTree;
import gov.nasa.jpf.jdart.constraints.InternalConstraintsTree;
import gov.nasa.jpf.jdart.constraints.InternalConstraintsTree.BranchEffect;
import gov.nasa.jpf.jdart.constraints.PathResult;
import gov.nasa.jpf.jdart.constraints.PostCondition;
import gov.nasa.jpf.jdart.objects.SymbolicObjectsContext;
import gov.nasa.jpf.util.Global;
import gov.nasa.jpf.util.JPFLogger;
import gov.nasa.jpf.vm.*;
import org.antlr.runtime.RecognitionException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ConcolicMethodExplorer {
  
  private static final class RestoreExploreState {
    public final boolean explore;
    
    public RestoreExploreState(boolean explore) {
      this.explore = explore;
    }
    
    public RestoreExploreState clone() {
      return this;
    }
  }

  public static ConcolicMethodExplorer getCurrentAnalysis(ThreadInfo ti) {
    return ti.getAttr(ConcolicMethodExplorer.class);
  }
  
  
  /**
   * logger
   */
  private final JPFLogger logger = JPF.getLogger("jdart");
  
  /**
   * explored method
   */
  private MethodInfo methodInfo;

  /**
   * concolic config for method
   */
  private final ConcolicMethodConfig methodConfig;
  private final AnalysisConfig anaConf;
  
  /**
   * constraints tree from exploring method
   */
  private InternalConstraintsTree constraintsTree;
  
  /**
   * the original initial valuation for symbolic vars
   */
  private Valuation initValuation;
  
  private Valuation currValuation;
  private Valuation nextValuation;
  
  /**
   * The original values of the parameters
   */
  private Object[] initParams;
  
  private SymbolicObjectsContext symContext;

  /**
   * execution info about method
   */
  
  private final SolverContext solverCtx;
  

  public ConcolicMethodExplorer(ConcolicConfig config, String id, MethodInfo mi) {
    // store method info and config
    this.methodInfo = mi;
    this.methodConfig = config.getMethodConfig(id);
    this.anaConf = methodConfig.getAnalysisConfig();
    
    // get preset values
    ConcolicValues vals = methodConfig.getConcolicValues();
    
    // create a constraints tree
    this.solverCtx = config.getSolver().createContext();    
    this.constraintsTree = new InternalConstraintsTree(solverCtx, anaConf, vals);
  }
  
  public void setExplore(boolean explore) {
    constraintsTree.setExplore(explore);
  }
  
  public String getId() {
    return methodConfig.getId();
  }
  
  public boolean isRootFrame(StackFrame sf) {
    return sf.getPrevious().hasFrameAttr(RootFrame.class);
  }
  
  private PostCondition collectPostCondition(ThreadInfo ti) {
    PostCondition pc = new PostCondition();
    for(SymbolicVariable<?> sv : symContext.getSymbolicVars()) {
      sv.addToPC(pc);
    }
    collectSymbolicReturn(pc, ti);
    
    return pc;
  }
  
  private void collectSymbolicReturn(PostCondition pc, ThreadInfo ti) {
    byte rtc = methodInfo.getReturnTypeCode();
    if(rtc == Types.T_VOID)
      return;
    
    
    StackFrame sf = ti.getTopFrame();
    if(rtc == Types.T_ARRAY || rtc == Types.T_REFERENCE) {
      int ref = sf.peek();
      ElementInfo ei = ti.getHeap().get(ref);
      if(ei == null)
        return;
      
    }
    else { // primitive
      Type<?> type = ConcolicUtil.forTypeCode(rtc);
      Pair<?> cr = ConcolicUtil.peek(sf, type);
      if(!cr.isConcrete())
        pc.setReturn(cr.symb);
    }
  }
      
  public void completePathOk(ThreadInfo ti) {
    PostCondition pc = collectPostCondition(ti);
    PathResult res = PathResult.ok(currValuation, pc);
    constraintsTree.finish(res);
  }
  
  public void completePathError(ThreadInfo ti) {
    ElementInfo exElem = ti.getPendingException().getException();
    StringWriter sw = new StringWriter();
    try(PrintWriter pw = new PrintWriter(sw)) {
      ti.printStackTrace(pw, exElem.getObjectRef());
    }
    String st = sw.toString();
    PathResult res = PathResult.error(currValuation, exElem.getClassInfo().getName(), st);
    constraintsTree.finish(res);
  }

  public boolean hasMoreChoices() {
    // If the initValuation is null, then the initial
    // valuation has not been read; we are *before* the
    // first execution
    if(initValuation == null)
      return true;
    
    // If there is no next valuation, try to find one
    if(nextValuation == null)
      nextValuation = constraintsTree.findNext();
    return (nextValuation != null);
  }
  
  public boolean advanceValuation() {
    if(nextValuation == null)
      nextValuation = constraintsTree.findNext();

    if (nextValuation != null) {
      for (Variable v : currValuation.getVariables()) {
        if (!nextValuation.containsValueFor(v)) {
          nextValuation.addEntry(new ValuationEntry(v, 
                  nextValuation.getValue(v))); // returns the default value for this type
        }
      }
    }
    currValuation = nextValuation;
    nextValuation = null;
    
    return (currValuation != null);
  }
  
  
  /**
   * registers method for concolic execution. Puts symbolic input values onto
   * the stack ...
   * 
   * @param invokeInstruction
   * @param systemState
   * @param ti 
   */
  public void initializeMethod(ThreadInfo ti, StackFrame sf) {
    logger.finest("Initializing concolic execution of " + methodInfo.getFullName());

    // mark root frame
    sf.setFrameAttr(RootFrame.getInstance());
    
    symContext = new SymbolicObjectsContext(ti.getHeap(), anaConf.getSymbolicFieldsExclude(), anaConf.getSymbolicFieldsInclude(), anaConf.getSpecialExclude());
    
    initializeSymbolicStatic(ti);
    initializeSymbolicParams(ti, sf);
    
    List<Variable<?>> vlist = new ArrayList<>();
    logger.finest("Symbolic variables:");
    logger.finest("===================");
    for(SymbolicVariable<?> var : symContext.getSymbolicVars()) {
      logger.finest(var.getVariable().getName());
      vlist.add(var.getVariable());
    }
    logger.finest();
    
    for(String constraintStr : anaConf.getConstraints()) {
      logger.finer("Adding constraint ", constraintStr);
      try {
        Expression<Boolean> constrExpr = ParserUtil.parseLogical(constraintStr,
            ObjectConstraints.getJavaTypes(),
            vlist);
        try {
          solverCtx.add(constrExpr);
        }
        catch(Exception ex) {
          logger.severe("Could not add constraint to solver: ", ex);
        }
      } catch (RecognitionException ex) {
        logger.severe("Could not parse constraint: ", ex);
      }
    }
    
  }
  
  private void initializeSymbolicStatic(ThreadInfo ti) {
    List<String> symbStatics = anaConf.getSymbolicStatics();
    for(String clazz : symbStatics) {
      ClassInfo ci = ClassInfo.getInitializedClassInfo(clazz, ti);
      ElementInfo ei = ci.getStaticElementInfo();
      symContext.processObject(ei, ci.getName());
    }
  }

  private void initializeSymbolicParams(ThreadInfo ti, StackFrame sf) {
    List<ParamConfig> pconfig = methodConfig.getParams();
    int argSize = pconfig.size();
    this.initParams = new Object[argSize];
    int stackIdx = methodInfo.getArgumentsSize();
    
    Heap heap = ti.getHeap();
    
    if(!methodInfo.isStatic()) {
      stackIdx--;
      int thisRef = sf.peek(stackIdx);
      ElementInfo thisEi = heap.get(thisRef);
      symContext.processObject(thisEi, "this", true);
    }
    
    byte[] argTypes = methodInfo.getArgumentTypes();
    
    for(int i = 0; i < argSize; i++) {
      ParamConfig pc = pconfig.get(i);
      String name = pc.getName();
      
      byte tc = argTypes[i];
      stackIdx--;
      if(tc == Types.T_LONG || tc == Types.T_DOUBLE)
        stackIdx--;
      
      this.initParams[i] = getVal(sf, stackIdx, tc);
      
      if(name == null)
        continue; // null name indicates non-symbolic param
      
      if(tc == Types.T_REFERENCE || tc == Types.T_ARRAY) {
        int ref = sf.peek(stackIdx);
        ElementInfo ei = heap.get(ref);
        if(ei != null)
          symContext.processObject(ei, name, true);
      }
      else { // primitive type
        Type<?> t = ConcolicUtil.forTypeCode(tc);
        Variable<?> var = Variable.create(t, name);
        SymbolicParam<?> sp = new SymbolicParam<>(var, stackIdx);
        symContext.addStackVar(sp);
      }
    }
  }
  
  private static Object getVal(StackFrame sf, int offset, byte type) {
    switch(type) {
    case Types.T_LONG:
      return sf.peekLong(offset);
    case Types.T_DOUBLE:
      return sf.peekDouble(offset);
    case Types.T_FLOAT:
      return sf.peekFloat(offset);
    case Types.T_INT:
      default:
      return sf.peek(offset);
    }
  }

  private static Map<String,Double> inputWithTime = new LinkedHashMap<>();

//  private static String programName = "cov";
//  String programName = Global.baseOutputName;

//  private static String programName = "CommonmarkJava";
//  private static String programName = "CommonsCsv";
//  private static String programName = "Expression";
//  private static String programName = "FastCsv";
//  private static String programName = "Gson";
//  private static String programName = "HtmlCleaner";
//  private static String programName = "Jcsv";
//  private static String programName = "JsonJava";
//  private static String programName = "Jsoup";
//  private static String programName = "Jtidy";
//  private static String programName = "MarkdownPapers";
//  private static String programName = "MinimalJson";
//  private static String programName = "Nanoxml";
//  private static String programName = "SimpleCsv";
//  private static String programName = "SuperCsv";
//  private static String programName = "Univocity";
//  private static String programName = "UrlDetector";
//  private static String programName = "Xml";

//  private static String programName = "Txtmark";
//  private static String programName = "Nanojson";
//  private static String programName = "Markdown4j";
  private static String programName = "InoJava";

//  private static String programName = "Jsoniter";

  public void prepareFirstExecution(StackFrame sf) {
    initValuation = new Valuation();
    for(SymbolicVariable<?> sv : symContext.getSymbolicVars())
      sv.readInitial(initValuation, sf);
    currValuation = initValuation;

    // add by lmx ------

    long currentTime = System.currentTimeMillis();
    double time = currentTime - JDart.startTime;
    int length = currValuation.getVariables().size();
    char[] chars = new char[length];
    String name = currValuation.getVariables().iterator().next().getName();
    String[] name1 = name.split("\\[");
    for (int i = 0; i < length; i++) {
      String temp = name1[0]+"["+i+"]";
      char c = (char) currValuation.getValue(temp);
      chars[i] = c;
    }
    String s = new String(chars);

//    long currentTime = System.currentTimeMillis();
//    double time = currentTime - JDart.startTime;
//    int length = currValuation.getVariables().size();
//    byte[] bytes = new byte[length];
//    String name = currValuation.getVariables().iterator().next().getName();
//    String[] name1 = name.split("\\[");
//    for (int i = 0; i < length; i++) {
//      String temp = name1[0]+"["+i+"]";
//      byte c = (byte) currValuation.getValue(temp);
//      bytes[i] = c;
//    }
//    String s = new String(bytes);

    try {
      String fileName = "/home/lmx/Documents/GitHub/jpf8/jdart/expeCovInput/1213Cov/"+programName+".saveInput";
      File file =new File(fileName);
      FileOutputStream out = new FileOutputStream(file);
      ObjectOutputStream objOut=new ObjectOutputStream(out);

      inputWithTime.put(s, time);
      objOut.writeObject(inputWithTime);
      objOut.flush();
      objOut.close();
    }catch (IOException e) {

    }

    // ------ add by lmx
  }
  
  public void prepareReexecution(StackFrame sf) {
    logger.finest("Reexecuting with valuation " + currValuation);

    // add by lmx ------

    long currentTime = System.currentTimeMillis();
    double time = currentTime - JDart.startTime;
    int length = currValuation.getVariables().size();
    char[] chars = new char[length];
    String name = currValuation.getVariables().iterator().next().getName();
    String[] name1 = name.split("\\[");
    for (int i = 0; i < length; i++) {
      String temp = name1[0]+"["+i+"]";
      char c = (char) currValuation.getValue(temp);
      chars[i] = c;
    }
    String s = new String(chars);

//    long currentTime = System.currentTimeMillis();
//    double time = currentTime - JDart.startTime;
//    int length = currValuation.getVariables().size();
//    byte[] bytes = new byte[length];
//    String name = currValuation.getVariables().iterator().next().getName();
//    String[] name1 = name.split("\\[");
//    for (int i = 0; i < length; i++) {
//      String temp = name1[0]+"["+i+"]";
//      byte c = (byte) currValuation.getValue(temp);
//      bytes[i] = c;
//    }
//    String s = new String(bytes);

    try {
      String fileName = "/home/lmx/Documents/GitHub/jpf8/jdart/expeCovInput/1213Cov/"+programName+".saveInput";
      File file =new File(fileName);
      FileOutputStream out = new FileOutputStream(file);
      ObjectOutputStream objOut=new ObjectOutputStream(out);

      inputWithTime.put(s, time);
      objOut.writeObject(inputWithTime);
      objOut.flush();
      objOut.close();
    }catch (IOException e) {

    }

    // ------ add by lmx

    for(SymbolicVariable<?> sv : symContext.getSymbolicVars())
      sv.apply(currValuation, sf);
  }
 
  
  public void uncaughtException(ThreadInfo ti, UncaughtException uex) {
    completePathError(ti);
    ti.clearPendingException();
    ti.breakTransition(true);
  }
  
  public boolean needsDecisions() {
    return constraintsTree.needsDecision();
  }
  
  @SafeVarargs
  public final void decision(ThreadInfo ti, Instruction branchInsn, int chosenIdx, Expression<Boolean> ...expressions) {
    BranchEffect eff = constraintsTree.decision(branchInsn, chosenIdx, expressions);
    switch(eff) {
    case INCONCLUSIVE:
      logger.severe("Aborting current execution due to inconclusive divergence...");
      constraintsTree.failCurrentTarget();
      ti.breakTransition(true);
      break;
    case UNEXPECTED:
      logger.warning("Unexpected divergence in execution of current valuation ...");
      constraintsTree.failCurrentTarget(); // TODO: Here, we could make more effort ...
      break;
    default:
    }
  }
 
    
  public ConcolicMethodConfig getMethodConfig() {
    return this.methodConfig;
  }
  
  public InternalConstraintsTree getInternalConstraintsTree() {
    return this.constraintsTree;
  }
  
  public CompletedAnalysis finish() {
    return new CompletedAnalysis(methodConfig, initValuation, initParams, constraintsTree.toFinalCTree());
  }

  public void newPath(StackFrame sf) {
    if(initValuation == null) { // first execution
      prepareFirstExecution(sf);
    }
    else { // reexecution
      advanceValuation();
      prepareReexecution(sf);
    }
  }
  
  
  public void makeCurrentAnalysis(ThreadInfo ti) {
    ti.setAttr(this);
  }

  public AnalysisConfig getAnalysisConfig() {
    return anaConf;
  }
  
  public void methodExited(ThreadInfo ti, MethodInfo mi) {
    RestoreExploreState r = ti.getTopFrame().getFrameAttr(RestoreExploreState.class);
    if(r != null) {
      constraintsTree.setExplore(r.explore);
      logger.finer("Restored exploration state after leaving method ", mi.getFullName());
    }
  }
  
  public void methodEntered(ThreadInfo ti, MethodInfo mi) {
    boolean explore = constraintsTree.isExplore();
    
    if(explore) {
      if(anaConf.suspendExploration(mi)) {
        ti.getTopFrame().setFrameAttr(new RestoreExploreState(explore));
        logger.finer("Suspending exploration in method " + mi.getFullName());
        constraintsTree.setExplore(false);
      }
    }
    else {
      if(anaConf.resumeExploration(mi)) {
        ti.getTopFrame().setFrameAttr(new RestoreExploreState(explore));
        logger.finer("Resuming exploration in method " + mi.getFullName());
        constraintsTree.setExplore(true);
      }
    }
  }
  
  
  // LEGACY API
  
  @Deprecated
  public ConstraintsTree getConstraintsTree() {
    return constraintsTree.toFinalCTree();
  }
  
  @Deprecated
  public Valuation getOriginalInitialValuation() {
    return initValuation;
  }
}
