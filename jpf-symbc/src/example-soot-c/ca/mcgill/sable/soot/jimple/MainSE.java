/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Jimple, a 3-address code Java(TM) bytecode representation.        *
 * Copyright (C) 1997, 1998 Raja Vallee-Rai (kor@sable.mcgill.ca)    *
 * All rights reserved.                                              *
 *                                                                   *
 * Modifications by Etienne Gagnon (gagnon@sable.mcgill.ca) are      *
 * Copyright (C) 1998 Etienne Gagnon (gagnon@sable.mcgill.ca).  All  *
 * rights reserved.                                                  *
 *                                                                   *
 * Modifications by Patrick Lam (plam@sable.mcgill.ca) are           *
 * Copyright (C) 1999 Patrick Lam.  All rights reserved.             *
 *                                                                   *
 * This work was done as a project of the Sable Research Group,      *
 * School of Computer Science, McGill University, Canada             *
 * (http://www.sable.mcgill.ca/).  It is understood that any         *
 * modification not identified as such is not covered by the         *
 * preceding statement.                                              *
 *                                                                   *
 * This work is free software; you can redistribute it and/or        *
 * modify it under the terms of the GNU Library General Public       *
 * License as published by the Free Software Foundation; either      *
 * version 2 of the License, or (at your option) any later version.  *
 *                                                                   *
 * This work is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU *
 * Library General Public License for more details.                  *
 *                                                                   *
 * You should have received a copy of the GNU Library General Public *
 * License along with this library; if not, write to the             *
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,      *
 * Boston, MA  02111-1307, USA.                                      *
 *                                                                   *
 * Java is a trademark of Sun Microsystems, Inc.                     *
 *                                                                   *
 * To submit a bug report, send a comment, or get the latest news on *
 * this project and other Sable Research Group projects, please      *
 * visit the web site: http://www.sable.mcgill.ca/                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*
 Reference Version
 -----------------
 This is the latest official version on which this file is based.
 The reference version is: $SootVersion: 1.beta.4.dev.7 $

 Change History
 --------------
 A) Notes:

 Please use the following template.  Most recent changes should
 appear at the top of the list.

 - Modified on [date (March 1, 1900)] by [name]. [(*) if appropriate]
   [description of modification].

 Any Modification flagged with "(*)" was done as a project of the
 Sable Research Group, School of Computer Science,
 McGill University, Canada (http://www.sable.mcgill.ca/).

 You should add your copyright, using the following template, at
 the top of this file, along with other copyrights.

 *                                                                   *
 * Modifications by [name] are                                       *
 * Copyright (C) [year(s)] [your name (or company)].  All rights     *
 * reserved.                                                         *
 *                                                                   *

 B) Changes:

 - Modified on February 3, 1999 by Patrick Lam (plam@sable.mcgill.ca) (*)
   Added changes in support of the Grimp intermediate
   representation (with aggregated-expressions).

 - Modified on November 2, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Repackaged all source files and performed extensive modifications.
   First initial release of Soot.

 - Modified on October 4, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Added flag and option to print debugging information.

 - Modified on 12-Sep-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Changed the output options, and redirected the output to files.

 - Modified on 8-Sep-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Corrected the jimplification of several classes.

 - Modified on 1-Sep-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Allows multiple arguments on the command line.  Useful for
   timing the jimplification of several classes.

 - Modified on 28-Aug-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Changed the displayed copyright on program execution.

 - Modified on July 29, 1998 by Etienne Gagnon (gagnon@sable.mcgill.ca). (*)
   Added -nosplitting and -oldtyping parameters.

 - Modified on 23-Jul-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   Minor changes.

 - Modified on July 5, 1998 by Etienne Gagnon (gagnon@sable.mcgill.ca). (*)
   Added jimpleClassPath parameter.
   Updated copyright notice.

 - Modified on 15-Jun-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   First internal release (Version 0.1).
*/

package ca.mcgill.sable.soot.jimple;

import ca.mcgill.sable.soot.*;
import ca.mcgill.sable.util.*;
import ca.mcgill.sable.soot.grimp.*;
import gov.nasa.jpf.symbc.Debug;
//import gov.nasa.jpf.jdart.Debug;

import java.io.*;
import java.text.*;

public class MainSE
{
    static int naiveJimplification;
    static int onlyJimpleOutput;
    public static int isVerbose;
    static int onlyJasminOutput;
    static int isProfilingOptimization;
    static int isSubstractingGC;
    static int oldTyping;
    static int isInDebugMode;
    static int usePackedLive;
    static int usePackedDefs = 1;
    static int isTestingPerformance;
    static int inject_flag = 0;
    
    //static boolean naiveJimplification;
    //static boolean onlyJimpleOutput;    
    //public static boolean isVerbose;
//    static boolean onlyJasminOutput;
//    static boolean isProfilingOptimization;
//    static boolean isSubstractingGC;
//    static boolean oldTyping;
//    static boolean isInDebugMode;
//    static boolean usePackedLive;
//    static boolean usePackedDefs = true;
//    static boolean isTestingPerformance;

    public static String jimpleClassPath;

    static int produceJimpleFile,
        produceJasminFile,
        produceJimpFile = 1;

//    static boolean produceJimpleFile,
//    produceJasminFile,
//    produceJimpFile = true;    

    static int totalFlowNodes,
           totalFlowComputations;
           
    static Timer copiesTimer = new Timer("copies"),
        defsTimer = new Timer("defs"),
        usesTimer = new Timer("uses"),
        liveTimer = new Timer("live"),
        splitTimer = new Timer("split"),
        packTimer = new Timer("pack"),
        cleanup1Timer = new Timer("cleanup1"),
        cleanup2Timer = new Timer("cleanup2"),
        conversionTimer = new Timer("conversionm"),
        cleanupAlgorithmTimer = new Timer("cleanupAlgorithm"),
        graphTimer = new Timer("graphTimer"),
        assignTimer = new Timer("assignTimer"),
        resolveTimer = new Timer("resolveTimer"),
        totalTimer = new Timer("totalTimer"),
        splitPhase1Timer = new Timer("splitPhase1"),
        splitPhase2Timer = new Timer("splitPhase2"),
        usePhase1Timer = new Timer("usePhase1"),
        usePhase2Timer = new Timer("usePhase2"),
        usePhase3Timer = new Timer("usePhase3"),
        defsSetupTimer = new Timer("defsSetup"),
        defsAnalysisTimer = new Timer("defsAnalysis"),
        defsPostTimer = new Timer("defsPost"),
        liveSetupTimer = new Timer("liveSetup"),
        liveAnalysisTimer = new Timer("liveAnalysis"),
        livePostTimer = new Timer("livePost"),
        aggregationTimer = new Timer("aggregation"),
        grimpAggregationTimer = new Timer("grimpAggregation"),
        deadCodeTimer = new Timer("deadCode"),
        propagatorTimer = new Timer("propagator");
        
    static int conversionLocalCount,
        cleanup1LocalCount,
        splitLocalCount,
        assignLocalCount,
        packLocalCount,
        cleanup2LocalCount;

    static int conversionStmtCount,
        cleanup1StmtCount,
        splitStmtCount,
        assignStmtCount,
        packStmtCount,
        cleanup2StmtCount;

    public static void main(String[] args1) throws RuntimeException{
    	start();
    }
    
    public static String[] args = new String[]{"-jimpleClassPath", "build/example-soot-c:src/example-soot-c/rt.jar", "test.Test"};
    
    public static void start() throws RuntimeException
    {
        int firstNonOption = 0;
        long stmtCount = 0;
        int buildBodyOptions = 0;

        totalTimer.start();

        SootClassManager cm = new SootClassManager();

        if(args.length == 0)
        {
// $Format: "            System.out.println(\"Jimple version $ProjectVersion$\");"$
            System.out.println("Jimple version 1.beta.4.dev.7");
            System.out.println("Copyright (C) 1997, 1998 Raja Vallee-Rai (kor@sable.mcgill.ca).");
            System.out.println("All rights reserved.");
            System.out.println("");
            System.out.println("Portions copyright (C) 1997 Clark Verbrugge (clump@sable.mcgill.ca).");
            System.out.println("All rights reserved.");
            System.out.println("");
            System.out.println("Modifications are copyright (C) 1997, 1998 by their respective contributors.");
            System.out.println("See individual source files for details.");
            System.out.println("");
            System.out.println("Jimple comes with ABSOLUTELY NO WARRANTY.  This is free software,");
            System.out.println("and you are welcome to redistribute it under certain conditions.");
            System.out.println("See the accompanying file 'COPYING' for details.");
            System.out.println("");
            System.out.println("Syntax: java ca.mcgill.sable.soot.jimple.Main [options] class");
            System.out.println("");
            System.out.println("Classpath Option:");
            System.out.println("    -jimpleClassPath <path>   uses <path> as classpath for finding classes");
            System.out.println("");
            System.out.println("Output Options:");
            System.out.println("    -jimple                   produce .jimple code");
            System.out.println("    -jimp                     produce .jimp (abbreviated .jimple) code [default]");
            System.out.println("    -jasmin                   produce .jasmin code");
            System.out.println("");
            System.out.println("Jimplification Options:");
            System.out.println("    -nocleanup                no constant or copy propagation is performed");
            System.out.println("    -nosplitting              no splitting of variables is performed");
            System.out.println("    -nocleanup                no constant or copy propagation is performed");
            System.out.println("    -oldtyping                use old typing algorithm");
            System.out.println("    -typeless                 do not assign types.  Cannot be used with -jasmin");
            System.out.println("                              or -nolocalpacking ");
            System.out.println("    -nolocalpacking           do not re-use locals after jimplification");
            System.out.println("    -noaggregating            do not perform any Jimple-level aggregation");
            System.out.println("");
            System.out.println("Profiling/Debugging Options:");
            System.out.println("    -timetransform            perform full transformation and print timings");
            System.out.println("    -verbose                  print out jimplification process");
            System.out.println("    -debug                    avoid catching errors during jimplification");
            System.out.println("    -testperf                 jimplify all classes & methods and gather stats");
            System.out.println("                              does not throw exception if error in typing");
            System.exit(0);
        }

        // Handle all the options
            for(int i = 0; i < args.length; i++)
            {
                if(args[i].equals("-jimple"))
                    produceJimpleFile = 1;
                else if(args[i].equals("-jasmin"))
                    produceJasminFile = 1;
                else if(args[i].equals("-jimp"))
                    produceJimpFile = 1;
                else if(args[i].equals("-nocleanup"))
                    buildBodyOptions |= BuildJimpleBodyOption.NO_CLEANUP;
                else if(args[i].equals("-typeless"))
                    buildBodyOptions |= BuildJimpleBodyOption.NO_TYPING;
                else if(args[i].equals("-nolocalpacking"))
                    buildBodyOptions |= BuildJimpleBodyOption.NO_PACKING;
                else if(args[i].equals("-noaggregating"))
                    buildBodyOptions |= BuildJimpleBodyOption.NO_AGGREGATING;
                else if(args[i].equals("-timetransform"))
                    isProfilingOptimization = 1;
                else if(args[i].equals("-substractgc"))
                {
                    Timer.setSubstractingGC(true);
                    isSubstractingGC = 1;
                }    
                else if(args[i].equals("-verbose"))
                    isVerbose = 1;
                else if(args[i].equals("-nosplitting"))
                    buildBodyOptions |= BuildJimpleBodyOption.NO_SPLITTING;
                else if(args[i].equals("-oldtyping"))
                    oldTyping = 1;
                else if(args[i].equals("-usepackedlive"))
                    usePackedLive = 1;
                else if(args[i].equals("-usepackeddefs"))
                    usePackedDefs = 1;    
                else if(args[i].equals("-testperf"))
                {
                    isProfilingOptimization = 1;
                    isTestingPerformance = 1;
                }
                else if(args[i].equals("-jimpleClassPath"))
                {   if(++i < args.length)
                        jimpleClassPath = args[i];
                }
                else if(args[i].equals("-debug"))
                    isInDebugMode = 1;
                else if(args[i].startsWith("-"))
                {
                    System.out.println("Unrecognized option: " + args[i]);
                    System.exit(0);
                }
                else
                    break;

                firstNonOption = i + 1;
            }
        
        makeSymbolicVariables();  // make the input arguments above symbolic!
        
        // Handle all the classes
        {
            int numFailed = 0;
            int numSuccess = 0;

            List listBodies = new ArrayList();

//            for(int i = firstNonOption; i < args.length; i++)
//            {
                //SootClass c = cm.getClass(args[i]);
            	SootClass c = cm.getClass(args[firstNonOption]);
                String postFix;
                PrintWriter writerOut = null;
                FileOutputStream streamOut = null;

                System.out.print("Jimplifying " + c.getName() + "... " );
                System.out.flush();

                // Open output file.
                {
                    if(produceJasminFile > 0) // generate constraints
                        postFix = ".jasmin";
                    else if(produceJimpleFile > 0) //generate constraints
                        postFix = ".jimple";
                    else{
                        postFix = ".jimp";
                    }

                    try { // init operations of the property!
                        streamOut = new FileOutputStream(c.getName() + postFix);
                        writerOut = new PrintWriter(streamOut);
                        writerOut.println("AAA");
                    }
                    catch (IOException e)
                    {
                        System.out.println("Cannot output file " + c.getName() + postFix);
                    }
                }

                if(isTestingPerformance > 0) // can reach sensitive events in the else branch!  generate constraints!
                {
                    Iterator methodIt = c.getMethods().iterator();
                    long localStmtCount = 0;

                    try {
                        while(methodIt.hasNext())
                        {
                            SootMethod m = (SootMethod) methodIt.next();
                            JimpleBody listBody = (JimpleBody) new BuildBody(Jimple.v(), new StoredBody(ClassFile.v())).resolveFor(m);
                            
                            listBodies.add(listBody);
                            localStmtCount += listBody.getStmtList().size();
                        }

                        stmtCount += localStmtCount;

                        System.out.println(localStmtCount + " stmts  ");
                        numSuccess++;
                    }
                    catch(Exception e)
                    {
                        System.out.println("failed due to: " + e);
                        numFailed++;
                    }
                }
                else
                {   
                    // Produce the file
                    {
                        if(isInDebugMode <= 0) // generate constraints! 
                        {                 
                            try {
                                handleClass(c, postFix, writerOut, buildBodyOptions); // can generate sensitive events!
                            }
                            catch(Exception e)
                            {
                                System.out.println("failed due to: " + e);
                            }
                        }
                        else {
                            handleClass(c, postFix, writerOut, buildBodyOptions);
                        }
    
                        try {
                            writerOut.flush();
                            streamOut.close();
                        }
                        catch(IOException e )
                        {
                            System.out.println("Cannot close output file " + c.getName() + postFix);
                        }
    
                        System.out.println();
                    }
                }
//            }
            
            if(isProfilingOptimization > 0) //generate constraints!
            {
                if(isTestingPerformance > 0) // can generate constraints!
                {
                    System.out.println("Successfully jimplified " + numSuccess + " classfiles; failed on " + numFailed + ".");
    
                    // Count number of statements stored
                    {
                        Iterator bodyIt = listBodies.iterator();
                        long storedStmtCount = 0;
    
                        while(bodyIt.hasNext())
                        {
                            JimpleBody listBody = (JimpleBody) bodyIt.next();
                            storedStmtCount += listBody.getStmtList().size();
                        }
    
                        System.out.println("Confirmed " + storedStmtCount + " stored statements.");
                        System.out.println();
                    }
                }
                
                totalTimer.end();
                    
                
                long totalTime = totalTimer.getTime();
                
                System.out.println("Time measurements");
                System.out.println();
                
                System.out.println("      Building graphs: " + toTimeString(graphTimer, totalTime));
                System.out.println("  Computing LocalDefs: " + toTimeString(defsTimer, totalTime));
//                System.out.println("                setup: " + toTimeString(defsSetupTimer, totalTime));
//                System.out.println("             analysis: " + toTimeString(defsAnalysisTimer, totalTime));
//                System.out.println("                 post: " + toTimeString(defsPostTimer, totalTime));
                System.out.println("  Computing LocalUses: " + toTimeString(usesTimer, totalTime));
                System.out.println("            Use phase1: " + toTimeString(usePhase1Timer, totalTime));
                System.out.println("            Use phase2: " + toTimeString(usePhase2Timer, totalTime));
                System.out.println("            Use phase3: " + toTimeString(usePhase3Timer, totalTime));

                System.out.println("     Cleaning up code: " + toTimeString(cleanupAlgorithmTimer, totalTime));
                System.out.println("Computing LocalCopies: " + toTimeString(copiesTimer, totalTime));
                System.out.println(" Computing LiveLocals: " + toTimeString(liveTimer, totalTime));
//                System.out.println("                setup: " + toTimeString(liveSetupTimer, totalTime));
//                System.out.println("             analysis: " + toTimeString(liveAnalysisTimer, totalTime));
//                System.out.println("                 post: " + toTimeString(livePostTimer, totalTime));
                
                System.out.println("Coading coffi structs: " + toTimeString(resolveTimer, totalTime));

                
                System.out.println();

                // Print out time stats.
                {
                    float timeInSecs;

                    System.out.println(" Bytecode -> jimple (naive): " + toTimeString(conversionTimer, totalTime)); 
                    System.out.println("        Splitting variables: " + toTimeString(splitTimer, totalTime));
                    System.out.println("            Assigning types: " + toTimeString(assignTimer, totalTime));
                    System.out.println("  Propagating copies & csts: " + toTimeString(propagatorTimer, totalTime));
                    System.out.println("      Eliminating dead code: " + toTimeString(deadCodeTimer, totalTime));
                    System.out.println("                Aggregation: " + toTimeString(aggregationTimer, totalTime));
                    System.out.println("            Coloring locals: " + toTimeString(packTimer, totalTime));

                                            
//                    System.out.println("           Cleaning up code: " + toTimeString(cleanup1Timer, totalTime) +
//                        "\t" + cleanup1LocalCount + " locals  " + cleanup1StmtCount + " stmts");
                    

                       
                       
//                    System.out.println("               Split phase1: " + toTimeString(splitPhase1Timer, totalTime));
//                    System.out.println("               Split phase2: " + toTimeString(splitPhase2Timer, totalTime));
                    
                        
                
                        /*
                    System.out.println("cleanup2Timer:   " + cleanup2Time +
                        "(" + (cleanup2Time * 100 / totalTime) + "%) " +
                        cleanup2LocalCount + " locals  " + cleanup2StmtCount + " stmts");
*/

                    timeInSecs = (float) totalTime / 1000.0f;
                    float memoryUsed = (float) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000.0f;

                    System.out.println("totalTime:" + toTimeString(totalTimer, totalTime));
                    
                    if(isSubstractingGC > 0)
                    {
                        System.out.println("Garbage collection was substracted from these numbers.");
                        System.out.println("           forcedGC:" + 
                            toTimeString(Timer.forcedGarbageCollectionTimer, totalTime));
                    }
                    System.out.println("totalMemory:" + memoryUsed + "k  ");

                    if(isTestingPerformance > 0)
                    {
                        System.out.println("Time/Space performance");
                        System.out.println();
                        
                        System.out.println(toFormattedString(stmtCount / timeInSecs) + " stmt/s");
                        System.out.println(toFormattedString((float) memoryUsed / stmtCount) + " k/stmt");
                        
                    }
                    
                    System.out.println("totalFlowNodes: " + totalFlowNodes + 
                        " totalFlowComputations: " + totalFlowComputations + " avg: " + 
                        truncatedOf((double) totalFlowComputations / totalFlowNodes, 2));
        
                }
            }
        }
    }
    public static void inject_bug() {
    	if (inject_flag > 0) {
    		PrintWriter o = new PrintWriter(new StringWriter());
    		o.close();
    		o.println("abc");
    	}
    }
    public static void makeSymbolicVariables() {
		// TODO Auto-generated method stub
//    	naiveJimplification = Debug.makeConcolicInteger("sym_naiveJimplification", ""+naiveJimplification);
//    	onlyJimpleOutput = Debug.makeConcolicInteger("sym_onlyJimpleOutput", ""+onlyJimpleOutput);
//    	isVerbose = Debug.makeConcolicInteger("sym_isVerbose", ""+isVerbose);
//    	onlyJasminOutput = Debug.makeConcolicInteger("sym_onlyJasminOutput", ""+onlyJasminOutput);
//    	isProfilingOptimization = Debug.makeConcolicInteger("sym_isProfilingOptimization", ""+isProfilingOptimization);
//    	isSubstractingGC = Debug.makeConcolicInteger("sym_isSubstractingGC", ""+isSubstractingGC);
//    	oldTyping = Debug.makeConcolicInteger("sym_oldTyping", ""+oldTyping);
//    	isInDebugMode = Debug.makeConcolicInteger("sym_isInDebugMode", ""+isInDebugMode);
//    	usePackedLive = Debug.makeConcolicInteger("sym_usePackedLive", ""+usePackedLive);
//    	usePackedDefs = Debug.makeConcolicInteger("sym_usePackedDefs", ""+usePackedDefs);
//    	isTestingPerformance = Debug.makeConcolicInteger("sym_isTestingPerformance", ""+isTestingPerformance);
//    	produceJimpleFile = Debug.makeConcolicInteger("sym_produceJimpleFile", ""+produceJimpleFile);
//    	produceJasminFile = Debug.makeConcolicInteger("sym_produceJasminFile", ""+produceJasminFile);
//    	produceJimpFile = Debug.makeConcolicInteger("sym_produceJimpFile", ""+produceJimpFile);
//    	inject_flag = Debug.makeConcolicInteger("sym_inject_flag", ""+inject_flag);

        naiveJimplification = Debug.makeSymbolicInteger( ""+naiveJimplification);
        onlyJimpleOutput = Debug.makeSymbolicInteger( ""+onlyJimpleOutput);
        isVerbose = Debug.makeSymbolicInteger( ""+isVerbose);
        onlyJasminOutput = Debug.makeSymbolicInteger( ""+onlyJasminOutput);
        isProfilingOptimization = Debug.makeSymbolicInteger( ""+isProfilingOptimization);
        isSubstractingGC = Debug.makeSymbolicInteger( ""+isSubstractingGC);
        oldTyping = Debug.makeSymbolicInteger( ""+oldTyping);
        isInDebugMode = Debug.makeSymbolicInteger( ""+isInDebugMode);
        usePackedLive = Debug.makeSymbolicInteger( ""+usePackedLive);
        usePackedDefs = Debug.makeSymbolicInteger( ""+usePackedDefs);
        isTestingPerformance = Debug.makeSymbolicInteger( ""+isTestingPerformance);
        produceJimpleFile = Debug.makeSymbolicInteger( ""+produceJimpleFile);
        produceJasminFile = Debug.makeSymbolicInteger( ""+produceJasminFile);
        produceJimpFile = Debug.makeSymbolicInteger( ""+produceJimpFile);
        inject_flag = Debug.makeSymbolicInteger( ""+inject_flag);


        System.out.println("======================Print the symbolic values======================");
    	System.out.println("naiveJimplification is : " + naiveJimplification);
    	System.out.println("onlyJimpleOutput is : " + onlyJimpleOutput);
    	System.out.println("isVerbose is : " + isVerbose);
    	System.out.println("onlyJasminOutput is : " + onlyJasminOutput);
    	System.out.println("isProfilingOptimization is : " + isProfilingOptimization);
    	System.out.println("isSubstractingGC is : " + isSubstractingGC);
    	System.out.println("oldTyping is : " + oldTyping);
    	System.out.println("isInDebugMode is : " + isInDebugMode);
    	System.out.println("usePackedLive is : " + usePackedLive);
    	System.out.println("usePackedDefs is : " + usePackedDefs);
    	System.out.println("isTestingPerformance is : " + isTestingPerformance);
    	System.out.println("produceJimpleFile is : " + produceJimpleFile);
    	System.out.println("produceJasminFile is : " + produceJasminFile);
    	System.out.println("produceJimpFile is : " + produceJimpFile);
    	System.out.println("inject_flag is : " + inject_flag);
	}

	private static String toTimeString(Timer timer, long totalTime)
    {
        DecimalFormat format = new DecimalFormat("0.000");
        DecimalFormat percFormat = new DecimalFormat("0.0");
        
        long time = timer.getTime();
        
        String timeString = format.format(time / 1000.0); // paddedLeftOf(new Double(truncatedOf(time / 1000.0, 1)).toString(), 5);
        
        return (timeString + "s" + " (" + percFormat.format(time * 100.0 / totalTime) + "%" + ")");   
    }
    
    private static String toFormattedString(double value)
    {
        return paddedLeftOf(new Double(truncatedOf(value, 2)).toString(), 5);
    }
    
    private static void handleClass(SootClass c, String postFix, PrintWriter writerOut, int buildBodyOptions)
    {
        if(postFix.equals(".jasmin"))
            new JasminClass(c, new BuildBody(Grimp.v(), new StoredBody(ClassFile.v()))).print(writerOut);
        else if(postFix.equals(".jimp"))
        {
            c.printTo(new BuildBody(Jimple.v(), new StoredBody(ClassFile.v()), buildBodyOptions),
                writerOut, PrintJimpleBodyOption.USE_ABBREVIATIONS);
        }
        else
            c.printTo(new BuildBody(Jimple.v(), new StoredBody(ClassFile.v()), buildBodyOptions),
                writerOut);
    }
    
    public static double truncatedOf(double d, int numDigits)
    {
        double multiplier = 1;
        
        for(int i = 0; i < numDigits; i++)
            multiplier *= 10;
            
        return ((long) (d * multiplier)) / multiplier;
    }
    
    public static String paddedLeftOf(String s, int length)
    {
        if(s.length() >= length)
            return s;
        else {
            int diff = length - s.length();
            char[] padding = new char[diff];
            
            for(int i = 0; i < diff; i++)
                padding[i] = ' ';
            
            return new String(padding) + s;
        }    
    }

}
