package taintDemo.drive;

import java.util.ArrayList;
import java.util.List;

public class TaintDetectionFile {

    protected static String appPath;
    protected static String classname;
    protected static List<String> sources = new ArrayList<>();
    protected static List<String> sinks = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        setParameters4bloat();
//        setParameters4bmpDecoder();
//        setParameters4bmpimagej();
//        setParameters4ftps_client_java();
//        setParameters4gif_imagej();
//
//        setParameters4image4j();
//        setParameters4jmp123_400_utf8_mini();
//        setParameters4mp3agic();
//        setParameters4pgm_imagej();
//        setParameters4sablecc();
//
//        setParameters4schroeder();
//        setParameters4snakeyaml();
//        setParameters4soot_c();
//        setParameters4yamlbeans();

        FlowDroidDemo flowDroidDemo = new FlowDroidDemo(appPath, classname, sources, sinks);
        flowDroidDemo.run();

    }


    private static void setParameters4yamlbeans() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-yamlbeans";
        classname = "test.TestYamlbeansDriver";

        setSources();
        setSinks();
    }

    private static void setParameters4soot_c() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-soot-c";
        classname = "ca.mcgill.sable.soot.jimple.MainSE";

        String source1 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(java.lang.String,java.lang.String)>";
        sources.add(source1);
        setSinks();
    }

    private static void setParameters4snakeyaml() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-snakeyaml";
        classname = "test.testSnakeyaml";

        setSources();
        setSinks();
    }


    private static void setParameters4schroeder() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-schroeder";
        classname = "schroeder.test.TestWAVEFileRead";

        setSources();
        setSinks();
    }

    private static void setParameters4sablecc() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sablecc";
        classname = "ca.mcgill.sable.sablecc.SableCC";

        setSources();
        setSinks();
    }

    private static void setParameters4pgm_imagej() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        classname = "test.TestPgmDecoderDriver";

        String source1 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicChar(java.lang.String,java.lang.String)>";
        String source2 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(java.lang.String,java.lang.String)>";
        sources.add(source1);
        sources.add(source2);
        setSinks();
    }

    private static void setParameters4mp3agic() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mp3agic";
        classname = "test.testMp3agicDriver";

        setSources();
        setSinks();
    }

    private static void setParameters4jmp123_400_utf8_mini() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jmp123_400_utf8_mini";
        classname = "test.TestJmp123Driver";

        setSources();
        setSinks();
    }

    private static void setParameters4image4j() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-image4j";
        classname = "test.TestImage4jDriver";

        setSources();
        setSinks();
    }

    private static void setParameters4gif_imagej() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        classname = "test.TestGifDecoderDriver";

        setSources();
        setSinks();
    }

    private static void setParameters4ftps_client_java() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ftp";
        classname = "test.ftp.TestFTP";

        String source1 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(java.lang.String,java.lang.String)>";
        sources.add(source1);
        setSinks();
    }

    private static void setParameters4bmpimagej() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        classname = "test.TestBmpDecoderDriver";

        setSources();
        setSinks();

    }

    private static void setParameters4bmpDecoder() {

        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-BMPDecoder";
        classname = "test.TestBMPDecoder";

        String source1 = "<gov.nasa.jpf.jdart.Debug: char makeConcolicChar(java.lang.String,java.lang.String)>";
        String source2 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(java.lang.String,java.lang.String)>";
        sources.add(source1);
        sources.add(source2);
        setSinks();
    }

    private static void setParameters4bloat() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bloat";
        classname = "EDU.purdue.cs.bloat.decorate.ModifiedMainForSE";

        String source1 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(java.lang.String,java.lang.String)>";
        sources.add(source1);
        setSinks();
    }


    private static void setSources() {
        String source1 = "<gov.nasa.jpf.jdart.Debug: char makeConcolicChar(java.lang.String,java.lang.String)>";
        sources.add(source1);
    }

    private static void setSinks() {
        String sink1 = "<featureDetection.InstrumentHelper: void ifBranchHelperInt(int,int)>";
        sinks.add(sink1);
    }

}
