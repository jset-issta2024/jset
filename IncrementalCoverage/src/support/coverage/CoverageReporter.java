package coverage;

import com.opencsv.CSVWriter;
import org.jacoco.agent.rt.internal_e6e56f0.Agent;
import org.jacoco.agent.rt.internal_e6e56f0.core.runtime.AgentOptions;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.nfunk.jep.function.Str;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CoverageReporter {
    private Agent agent = Agent.getInstance(new AgentOptions());
    private String originalFilePath = "classes";
    private String packagePrefix = "";
    private File targetFile;
    private CSVWriter writer;

    public CoverageReporter(String originalFilePath, String packagePrefix, String coverageFile) {
        this.originalFilePath = originalFilePath;
        this.packagePrefix = packagePrefix;
        targetFile = new File(coverageFile);
        try {
            writer = new CSVWriter(new FileWriter(targetFile));
        } catch (IOException e) {
            System.err.println("Error when create CSVWriter for coverage report");
            e.printStackTrace();
            System.exit(-1);
        }
        agent.reset();
        writer.writeNext(new String[]{"branch","instruction", "line", "path", "time"},false);
    }

    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Coverage writer close error");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private Map<String, Integer> coveredCount = new HashMap<>();
    private Map<String, Integer> totalCount = new HashMap<>();
    public Map<String, Integer> getCoverage(double time, int pathNumber) throws IOException {
        ExecutionDataStore execStore = getExecutionDataStore();
        Collection<IClassCoverage> classes = getCoveredClasses(execStore);
        Map<String, Integer> coveredCount = new HashMap<>();
        Map<String, Integer> totalCount = new HashMap<>();
        for (final IClassCoverage cc : classes){
            if (isInPackage(cc,packagePrefix)){
//                PrintCoverage(cc);
                coveredCount = addCovered2Count(cc,coveredCount);
                totalCount = addTotal2Count(cc,totalCount);
            }
        }
        coveredCount.put("path", pathNumber);
        coveredCount.put("time", (int)time);

        this.coveredCount = coveredCount;
        this.totalCount = totalCount;
        writeCSV(writer,this.coveredCount);

        return this.coveredCount;
    }

    public void writeCSV(CSVWriter writer, Map<String, Integer> map){
        int branches = map.get("Branch");
        int instructions = map.get("Instruction");
        int lines = map.get("Line");
        int paths = map.get("path");
        int time = map.get("time");
        writer.writeNext(new String[]{String.valueOf(branches),String.valueOf(instructions),String.valueOf(lines), String.valueOf(paths),String.valueOf(time)},
                         false);
    }

    public Map<String, Integer> getTotal() throws IOException {
//        this.getCoverage(-1);
        return this.totalCount;
    }

    private Map<String, Integer> addTotal2Count(IClassCoverage cc, Map<String, Integer> count) {
        if (count == null){
            count = new HashMap<>();
        }

        int totalBranches = getBranchTotal(cc);
        addValueInMap(count, totalBranches, "Branch");

        int totalInstructions = getInstructionTotal(cc);
        addValueInMap(count, totalInstructions, "Instruction");

        int totalLines = getLineTotal(cc);
        addValueInMap(count, totalLines, "Line");

        return count;
    }

    private int getLineTotal(IClassCoverage cc) {
        ICounter counter = cc.getLineCounter();
        int total = counter.getTotalCount();
        return total;
    }

    private int getInstructionTotal(IClassCoverage cc) {
        ICounter counter = cc.getInstructionCounter();
        int total = counter.getTotalCount();
        return total;
    }

    private int getBranchTotal(IClassCoverage cc) {
        ICounter counter = cc.getBranchCounter();
        int total = counter.getTotalCount();
        return total;
    }

    public Map<String, Integer> addCovered2Count(IClassCoverage cc, Map<String,Integer> count){
        if (count == null){
            count = new HashMap<>();
        }

        int coveredBranches = getBranchCoverage(cc);
        addValueInMap(count, coveredBranches, "Branch");

        int coveredInstructions = getInstructionCoverage(cc);
        addValueInMap(count, coveredInstructions, "Instruction");

        int coveredLines = getLineCoverage(cc);
        addValueInMap(count, coveredLines, "Line");

        return count;
    }

    private void addValueInMap(Map<String, Integer> map, int value, String key) {
        if (map.containsKey(key)) {
            int addRes = map.get(key);
            addRes += value;
            map.put(key, addRes);
        } else {
            int countCoverage = value;
            map.put(key, countCoverage);
        }
    }

    private int getLineCoverage(IClassCoverage cc) {
        ICounter counter = cc.getLineCounter();
        int covered = counter.getCoveredCount();
        return covered;
    }

    private int getInstructionCoverage(IClassCoverage cc) {
        ICounter counter = cc.getInstructionCounter();
        int covered = counter.getCoveredCount();
        return covered;
    }

    private int getBranchCoverage(IClassCoverage cc) {
        ICounter counter = cc.getBranchCounter();
        int covered = counter.getCoveredCount();
        return covered;
    }

    private void PrintCoverage(IClassCoverage cc) {
        System.out.println("Coverage of class " + cc.getName());
        int missedBranches = cc.getBranchCounter().getMissedCount();
        int totalBranches = cc.getBranchCounter().getTotalCount();
        System.out.printf("%d of %d %s miss\n", missedBranches,totalBranches,"branches");
    }

    private boolean isInPackage(IClassCoverage cc, String packagePrefix) {
        if (packagePrefix.isEmpty()){
            return true;
        }
        packagePrefix = packagePrefix.replace('.','/');
        String packageName = cc.getPackageName();
        boolean res = packageName.startsWith(packagePrefix);
        if (res == false){
            return false;
        }

        int prefixLength = packagePrefix.length();
        if (packageName.length() > prefixLength){
            res = ( packageName.charAt(prefixLength) == '/' );
        }
        return res;
    }

    private ExecutionDataStore getExecutionDataStore() throws IOException {
        byte[] executionData = agent.getExecutionData(false);
        ExecutionDataReader executionDataReader = new ExecutionDataReader(new ByteArrayInputStream(executionData));
        ExecutionDataStore execStore = new ExecutionDataStore();
        SessionInfoStore sessionInfos = new SessionInfoStore();
        executionDataReader.setExecutionDataVisitor(execStore);
        executionDataReader.setSessionInfoVisitor(sessionInfos);
        executionDataReader.read();
        return execStore;
    }

    private Collection<IClassCoverage> getCoveredClasses(ExecutionDataStore execStore) throws IOException {
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(execStore, coverageBuilder);
        analyzer.analyzeAll(originalFilePath,null); // relative path
        return coverageBuilder.getClasses();
    }
}
