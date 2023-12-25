package featureDetection;

import soot.*;
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MainDriver {

    /*
    Need to set up information of analysed program
     */
    public static String inputClassPath;
    public static String entryClass;
    public static String entryMethod;
    public static String packageDriverName;
    public static String packageSourceName;

    static LinkedList<String> excludeList;

    public static String target;

    public static void main(String[] args) {

        // set parameters of analysed program
        inputClassPath = args[0];
        entryClass = args[1];
        entryMethod = args[2];
        packageDriverName = args[3];
        packageSourceName = args[4];

        String current_javapath = System.getProperty("java.class.path");
        String path = current_javapath;
        Scene.v().setSootClassPath(path);

        Scene.v().setSootClassPath(
                Scene.v().getSootClassPath()
                        + File.pathSeparator + inputClassPath
        );


        Options.v().set_output_dir(inputClassPath);

        //load and set main class

        //whole program analysis
        Options.v().set_whole_program(true);
        Options.v().set_app(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_no_bodies_for_excluded(true);
        Options.v().set_exclude(excludeList());

        // load instrument help
        InstrumentParameters instrumentParameters = new InstrumentParameters().invoke();

        SootClass appclass = Scene.v().loadClassAndSupport(entryClass);

        ArrayList<SootMethod> entryPoints = new ArrayList<SootMethod>();
        SootMethod entrySootMethod = null;
        for (SootMethod method : appclass.getMethods()) {
            if (method.getName().contains(entryMethod)) {
                entryPoints.add(method);
                entrySootMethod = method;
                break;
            }

        }
        Scene.v().setEntryPoints(entryPoints);
        Scene.v().loadNecessaryClasses();

        Pack wjtp = PackManager.v().getPack("wjtp");
        wjtp.add(new Transform("wjtp.instrumenter",
                new ScanAndInstrumentTransformer(instrumentParameters,
                        packageDriverName, packageSourceName, entrySootMethod)));

        PackManager.v().runPacks();

        /// only output the classes in the packages
        List<SootClass> removeClassList = new ArrayList<>();
        Iterator<SootClass> it = Scene.v().getApplicationClasses().snapshotIterator();
        while (it.hasNext()) {
            SootClass sc = it.next();
            if (!(sc.getPackageName().startsWith(packageDriverName) || sc.getPackageName().startsWith(packageSourceName))) {
                removeClassList.add(sc);
            }
        }
        Scene.v().getApplicationClasses().removeAll(removeClassList);

        /// output the class
        PackManager.v().writeOutput();
    }

    public static LinkedList<String> excludeList() {
        if (excludeList == null) {
            excludeList = new LinkedList<String>();

            excludeList.add("java.*");
            excludeList.add("javax.*");
            excludeList.add("java.lang.*");
            excludeList.add("sun.");
            excludeList.add("sunw.");
            excludeList.add("com.sun.");
            excludeList.add("com.ibm.");
            excludeList.add("com.apple.");
            excludeList.add("apple.awt.");
            excludeList.add("edu.berkeley.");
            excludeList.add("org.junit.");
            excludeList.add("com.pholser.");
            excludeList.add("javax.crypto");
        }
        return excludeList;
    }
}
