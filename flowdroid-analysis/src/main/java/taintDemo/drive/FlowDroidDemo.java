package taintDemo.drive;

import soot.jimple.infoflow.Infoflow;
import soot.jimple.infoflow.InfoflowConfiguration;
import soot.jimple.infoflow.config.IInfoflowConfig;
import soot.jimple.infoflow.results.DataFlowResult;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlowDroidDemo {
    protected static List<String> sources;
    protected static List<String> sinks;
    protected static String appPath;
    protected static String classname;

    protected static String entry_point;
    protected static String libPath;

    public FlowDroidDemo(String appPath, String classname, List<String> sources, List<String> sinks) {
        this.appPath = appPath;
        this.classname = classname;
        this.sources = sources;
        this.sinks = sinks;
    }

    public void run() throws Exception {

        System.out.println("this analysis for " + appPath);
        System.out.print("stage 1 ------ ");

//     libPath = System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar";
//        libPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        libPath = "";
        entry_point = "<" + classname + ": void main(java.lang.String[])>";

        List<String> epoints = new ArrayList<String>();
        epoints.add(entry_point);

        Infoflow infoFlow = getInfoFlow();

        String filename = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/src/flowDroidAnalysis/taintDemo/EasyTaintWrapperSource.txt";
        EasyTaintWrapper easyTaintWrapper = new EasyTaintWrapper(new File(filename));
        infoFlow.setTaintWrapper(easyTaintWrapper);
        infoFlow.computeInfoflow(appPath, libPath, epoints, sources, sinks);

        if (infoFlow.isResultAvailable()) {
            InfoflowResults map = infoFlow.getResults();
            if (map.size() > 0) {
                System.out.println(map.getResultSet().size());
                Iterator iterator = map.getResultSet().iterator();
                while (iterator.hasNext()) {
                    DataFlowResult dataFlowResult = (DataFlowResult) iterator.next();
                    System.out.println(dataFlowResult.toString());
                }
            }
        }

//        // stage 2
//
//        System.out.println("stage 2 ------");
//
//        sources.clear();
//        sources.add(source2);
//
//        sinks.clear();
//        sinks.add(sink2);
//
//        infoFlow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
//
//        if (infoFlow.isResultAvailable()) {
//            InfoflowResults map = infoFlow.getResults();
//            if (map.size() > 0) {
//                Iterator iterator = map.getResultSet().iterator();
//                while (iterator.hasNext()) {
//                    DataFlowResult dataFlowResult = (DataFlowResult) iterator.next();
//                    System.out.println(dataFlowResult.toString());
//                }
//            }
//        }
    }

    private static Infoflow getInfoFlow() {
        Infoflow infoFlow = new Infoflow();
        infoFlow.setSootConfig(new IInfoflowConfig() {

            @Override
            public void setSootOptions(Options options, InfoflowConfiguration config) {
                // TODO: set included packages.
                // options.set_include(includeList);
//                options.set_exclude(Utility.excludedPackages());
                options.set_output_format(Options.output_format_none);
            }
        });
        infoFlow.getConfig().setInspectSinks(false);
        infoFlow.getConfig().setInspectSources(false);
        infoFlow.getConfig().setLogSourcesAndSinks(true);
        return infoFlow;
    }

}
