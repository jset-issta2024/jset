package taintDemo.drive;

import soot.jimple.infoflow.Infoflow;
import soot.jimple.infoflow.InfoflowConfiguration;
import soot.jimple.infoflow.config.IInfoflowConfig;
import soot.jimple.infoflow.results.DataFlowResult;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.options.Options;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FlowDroidDemoStage2 {
    protected static List<String> sources;
    protected static List<String> sinks;
    protected static String appPath;
    protected static String classname;

    protected static String entry_point;
    protected static String libPath;

    public FlowDroidDemoStage2(String appPath, String classname, List<String> sources, List<String> sinks) {
        this.appPath = appPath;
        this.classname = classname;
        this.sources = sources;
        this.sinks = sinks;
    }

    public void run() throws Exception {

        System.out.println("this analysis for " + appPath);
        System.out.print("stage 1 ------ ");

        libPath = "";
        entry_point = "<" + classname + ": void main(java.lang.String[])>";

        List<String> epoints = new ArrayList<String>();
        epoints.add(entry_point);

        Infoflow infoFlow = getInfoFlow();

        String filename = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/src/flowDroidAnalysis/taintDemo/EasyTaintWrapperSource.txt";
        EasyTaintWrapper easyTaintWrapper = new EasyTaintWrapper(new File(filename));
        infoFlow.setTaintWrapper(easyTaintWrapper);
        infoFlow.computeInfoflow(appPath, libPath, epoints, sources, sinks);

        Set<String> stage1Sink = new HashSet<String>();

        if (infoFlow.isResultAvailable()) {
            InfoflowResults map = infoFlow.getResults();
            if (map.size() > 0) {
                System.out.println(map.getResultSet().size());
                Iterator iterator = map.getResultSet().iterator();
                while (iterator.hasNext()) {
                    DataFlowResult dataFlowResult = (DataFlowResult) iterator.next();
                    System.out.println(dataFlowResult.toString());
                    stage1Sink.add(dataFlowResult.getSink().toString());
                }
            }
        }

        // stage 2

        System.out.print("stage 2 ------ ");

//        for (String s : stage1Sink) {
//            System.out.println(s);
//        }

        sources.clear();
        List<String> tempSource = stage1Sink.stream().collect(Collectors.toList());
        for (String temp : tempSource) {
            String source = "<" + temp.split("<")[1].split(">")[0] + ">";
            sources.add(source);
        }
//        sources.add(source2);
//
        sinks.clear();
        String sink2 = "<featureDetection.InstrumentHelper: void ifBranchHelperInt(int,int)>";
        sinks.add(sink2);
//
        infoFlow.computeInfoflow(appPath, libPath, epoints, sources, sinks);

//        System.out.println(sources);
//        System.out.println(sinks);

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
