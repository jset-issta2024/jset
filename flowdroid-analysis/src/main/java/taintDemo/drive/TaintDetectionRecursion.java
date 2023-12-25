package taintDemo.drive;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TaintDetectionRecursion {

    protected static String appPath;
    protected static String classname;
    protected static List<String> sources = new ArrayList<>();
    protected static List<String> sinks = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // for feature test
//        setTaintParameters4Recursion();

        // no.1 - no.10
//        setParameters4actson();
//        setParameters4AEJCC();
//        setParameters4antlr();
//        setParameters4argo();
//        setParameters4autolinkjava();
//        setParameters4awkparser();
//        setParameters4bling();
//        setParameters4bloat();
//        setParameters4bmpDecoder();
//        setParameters4bmpimagej();

        // no.11 - no.20
//        setParameters4calculator();
//        setParameters4clojure_parser();
//        setParameters4cmm();
//        setParameters4commonmark();
//        setParameters4commons_codec();
//        setParameters4commons_csv();
//        setParameters4Curta();
//        setParameters4dom4j();
//        setParameters4FastCSV();
//        setParameters4fastjson();

        // no.21 - no.30
//        setParameters4FirstOrderParser();
//        setParameters4flexmark_java();
//        setParameters4formula4j();
//        setParameters4foxykeep();
//        setParameters4ftps_client_java();
//        setParameters4galimatias();
//        setParameters4gif_imagej();
//        setParameters4Golang();
//        setParameters4gson();
//        setParameters4HTML5Parser();

//        no.31 - no.40
//        setParameters4htmlcleaner();
//        setParameters4html_parser();
//        setParameters4htmlparser();
//        setParameters4htmlparser2();
//        setParameters4image4j();
//        setParameters4interpreter();
//        setParameters4inojava();
//        setParameters4j2html();
//        setParameters4J2Latex();
//        setParameters4japaparser();

//        no.41 - no.50
//        setParameters4JavaParserObjects();
//        setParameters4jcsv();
//        setParameters4jdom();
//        setParameters4jepjavagpl();
//        setParameters4jericho();
//        setParameters4jieba_analysis();
//        setParameters4JLex();
//        setParameters4jmp123_400_utf8_mini();
//        setParameters4joda_time();
//        setParameters4jpat();

//        no.51 - no.60
//        setParameters4JSIJCC();
//        setParameters4json_flattener();
//        setParameters4json_iterator();
//        setParameters4JSON_java();
//        setParameters4jsonparser();
//        setParameters4JSONParser_mwnorman();
//        setParameters4json_raupachz();
//        setParameters4json_simple();
//        setParameters4jsoup();
//        setParameters4JSqlParser();

//        no.61 - no.70
//        setParameters4jtidy();
//        setParameters4jurl();
//        setParameters4Mapl();
//        setParameters4markdown4j();
//        setParameters4markdownj();
//        setParameters4MarkdownPapers();
//        setParameters4minimal_json();
//        setParameters4mp3agic();
//        setParameters4mXparser();
//        setParameters4nanojson();

//        no.71 - no.80
//        setParameters4nanoxml();
//        setParameters4nekothml();
//        setParameters4ntru();
//        setParameters4NunniMJAX();
//        setParameters4oajavaparser();
//        setParameters4open_m3u8();
//        setParameters4pgm_imagej();
//        setParameters4rhino();
//        setParameters4RParser();
//        setParameters4sablecc();

//        no.81 - no.90
//        setParameters4schroeder();
//        setParameters4sie();
//        setParameters4simplecsv();
//        setParameters4sixpath();
//        setParameters4snakeyaml();
//        setParameters4soot_c();
//        setParameters4sqlparser();
//        setParameters4super_csv();
//        setParameters4toba();
//        setParameters4txtmark();

//        no.91 - no.100
//        setParameters4univocity_parsers();
//        setParameters4uriparser();
//        setParameters4URL_Detector();
//        setParameters4xerces();
//        setParameters4yamlbeans();

//        setTaintParameters4Colt();
//        setTaintParameters4ColtCD();
//        setTaintParameters4ColtED();
//        setTaintParameters4ColtLU();
//        setTaintParameters4ColtQR();
//        setTaintParameters4ColtRank();
//        setTaintParameters4ColtSolver();
//        setTaintParameters4ColtSVD();
//        setTaintParameters4ColtTVS();
//
//        setTaintParameters4CholeskyDecompositor();
//        setTaintParameters4EigenDecompositor();
//        setTaintParameters4ForwardBackSubstitutionSolver();
//        setTaintParameters4GaussianSolver();
//        setTaintParameters4GaussJordanInverter();
//        setTaintParameters4JacobiSolver();
//        setTaintParameters4LeastSquaresSolver();
//        setTaintParameters4LIA4J();
//        setTaintParameters4LUDecompositor();
//        setTaintParameters4NoPivotGaussInverter();
//        setTaintParameters4QRDecompositor();
//        setTaintParameters4SeidelSolver();
//        setTaintParameters4SingularValueDecompositor();
//        setTaintParameters4SquareRootSolver();
//        setTaintParameters4SweepSolver();

        if (args.length == 0) {
            System.out.println("length of args is 0");

            FlowDroidDemo flowDroidDemo = new FlowDroidDemo(appPath, classname, sources, sinks);
            flowDroidDemo.run();
        } else {

            appPath = args[0];
            classname = args[1];
            String intFlag = args[2];
            String charFlag = args[3];
            String floatFlag = args[4];

            if (charFlag.equals("1")) {
                setSources();
            } else if (intFlag.equals("1")) {
                setIntegerSources();
            } else if (floatFlag.equals("1")) {
                setFloatSources();
            }
            setSinks();

            // Redirect output to a file
            String fileName = appPath.split("/")[8];
            String entryName = classname;
            try {
                PrintStream print = new PrintStream("/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/taint/recursion/" + fileName + "-" + entryName + ".log");
                System.setOut(print);
            } catch (FileNotFoundException e) {
            }

            FlowDroidDemo flowDroidDemo = new FlowDroidDemo(appPath, classname, sources, sinks);
            flowDroidDemo.run();
        }

    }

//    private static void setParameters4colt() {
//        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
//        classname = "test.TestColt";
//
//        String source1 = "<gov.nasa.jpf.jdart.Debug: double makeConcolicChar(java.lang.String,java.lang.String)>";
//        String sink1 = "<featureDetection.InstrumentHelper: void ifBranchHelper(int,int)>";
//
//        sources.add(source1);
//        sinks.add(sink1);
//    }

    private static void setTaintParameters4SweepSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSweepSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4SquareRootSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSquareRootSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4SingularValueDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSingularValueDecompositor";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4SeidelSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSeidelSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4QRDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestQRDecompositor";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4NoPivotGaussInverter() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestNoPivotGaussInverter";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4LUDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestLUDecompositor";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4LIA4J() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestLIA4J";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4LeastSquaresSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestLeastSquaresSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4JacobiSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestJacobiSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4GaussJordanInverter() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestGaussJordanInverter";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4GaussianSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestGaussianSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ForwardBackSubstitutionSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestForwardBackSubstitutionSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4EigenDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestEigenDecompositor";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4CholeskyDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestCholeskyDecompositor";
        setFloatSources();
        setSinks();
    }

    // ------ ------

    private static void setTaintParameters4ColtTVS() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtTVS";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtSVD() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtSVD";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtSolver";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtRank() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtRank";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtQR() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtQR";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtLU() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtLU";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtED() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtED";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4ColtCD() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtCD";
        setFloatSources();
        setSinks();
    }

    private static void setTaintParameters4Colt() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColt";
        setFloatSources();
        setSinks();
    }

    private static void setParameters4yamlbeans() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-yamlbeans";
        classname = "test.TestYamlbeansDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4xerces() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-xerces";
        classname = "test.testXerces";
        setSources();
        setSinks();
    }

    private static void setParameters4URL_Detector() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-url-detector";
        classname = "test.TestUrlDetector";
        setSources();
        setSinks();
    }

    private static void setParameters4uriparser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-uriparser";
        classname = "test.TestUriParser";
        setSources();
        setSinks();
    }

    private static void setParameters4univocity_parsers() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-univocity";
        classname = "test.TestUnivocity";
        setSources();
        setSinks();
    }

    private static void setParameters4txtmark() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-txtmark";
        classname = "test.TestTxtmark";
        setSources();
        setSinks();
    }


    private static void setParameters4toba() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-toba";
        classname = "toba.translator.Trans";
        setSources();
        setSinks();
    }

    private static void setParameters4super_csv() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-super-csv";
        classname = "test.testSuperCSVDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4sqlparser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sqlparser";
        classname = "sql.TestSqlParser";
        setSources();
        setSinks();
    }

    private static void setParameters4soot_c() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-soot-c";
        classname = "ca.mcgill.sable.soot.jimple.MainSE";
        setIntegerSources();
        setSinks();
    }

    private static void setParameters4snakeyaml() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-snakeyaml";
        classname = "test.testSnakeyaml";
        setSources();
        setSinks();
    }

    private static void setParameters4sixpath() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sixpath";
        classname = "test.sixpath.TestSixpath";
        setSources();
        setSinks();
    }

    private static void setParameters4simplecsv() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-simple-csv";
        classname = "test.TestSimplecsv";
        setSources();
        setSinks();
    }

    private static void setParameters4sie() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sie";
        classname = "test.TestSie";
        setIntegerSources();
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

    private static void setParameters4RParser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-rparser";
        classname = "test.TestR";
        setSources();
        setSinks();
    }

    private static void setParameters4rhino() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-rhino";
        classname = "test.rhino.TestParser";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4pgm_imagej() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        classname = "test.TestPgmDecoderDriver";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4open_m3u8() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-open-m3u8";
        classname = "test.TestOpenM3u8";
        setSources();
        setSinks();
    }

    private static void setParameters4oajavaparser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-oajavaparser";
        classname = "test.TestOajavaParser";
        setSources();
        setSinks();
    }

    private static void setParameters4NunniMJAX() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-xml";
        classname = "test.TestXml";
        setSources();
        setSinks();
    }

    private static void setParameters4ntru() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ntru";
        classname = "test.NTRUExample";
        setSources();
        setSinks();
    }

    private static void setParameters4nekothml() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nekohtml";
        classname = "test.TestNekohtml";
        setSources();
        setSinks();
    }

    private static void setParameters4nanoxml() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nanoxml";
        classname = "nanoxml.DumpXML";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4nanojson() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nanojson";
        classname = "test.TestNanojson";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4mXparser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mXparser";
        classname = "test.mxparser.TestmXparser";
        setSources();
        setSinks();
    }

    private static void setParameters4mp3agic() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mp3agic";
        classname = "test.testMp3agicDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4minimal_json() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-minimal-json";
        classname = "test.TestDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4MarkdownPapers() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-MarkdownPapers";
        classname = "test.TestMarkdownPapers";
        setSources();
        setSinks();
    }

    private static void setParameters4markdownj() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-markdownj";
        classname = "test.TestMarkdownj";
        setSources();
        setSinks();
    }

    private static void setParameters4markdown4j() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-markdown4j";
        classname = "test.TestMarkdown4j";
        setSources();
        setSinks();
    }

    private static void setParameters4Mapl() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mapl";
        classname = "test.TestMapl";
        setSources();
        setSinks();
    }

    private static void setParameters4jurl() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jurl";
        classname = "test.TestJurl";
        setSources();
        setSinks();
    }

    private static void setParameters4jtidy() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jtidy";
        classname = "test.TestJtidy";
        setSources();
        setSinks();
    }

    private static void setParameters4JSqlParser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsqlparser";
        classname = "test.TestJsqlparserDriver";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4jsoup() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsoup";
        classname = "test.TestJsoup";
        setSources();
        setSinks();
    }

    private static void setParameters4json_simple() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-simple";
        classname = "test.TestJsonSimpleDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4json_raupachz() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-raupachz";
        classname = "test.TestJsonRaupachz";
        setSources();
        setSinks();
    }

    private static void setParameters4JSONParser_mwnorman() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsonparser-mwnorman";
        classname = "org.mwnorman.json.test.TestJsonParserMwnorman";
        setSources();
        setSinks();
    }

    private static void setParameters4jsonparser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsonparser";
        classname = "test.TestJsonparser";
        setSources();
        setSinks();
    }

    private static void setParameters4JSON_java() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-java";
        classname = "test.TestJsonJavaDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4json_iterator() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsoniter";
        classname = "test.TestJsoniter";
        setSources();
        setSinks();
    }

    private static void setParameters4json_flattener() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-flattener";
        classname = "test.TestJsonFlattener";
        setSources();
        setSinks();
    }

    private static void setParameters4JSIJCC() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsijcc";
        classname = "test.TestJsijcc";
        setSources();
        setSinks();
    }

    private static void setParameters4jpat() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jpat";
        classname = "jpat.test.JPATTest";
        setSources();
        setSinks();
    }

    private static void setParameters4joda_time() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jodatime";
        classname = "test.JodatimeExample";
        setSources();
        setSinks();
    }

    private static void setParameters4jmp123_400_utf8_mini() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jmp123_400_utf8_mini";
        classname = "test.TestJmp123Driver";
        setSources();
        setSinks();
    }

    private static void setParameters4JLex() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-JLex";
        classname = "JLex.Main";
        setSources();
        setSinks();
    }

    private static void setParameters4jieba_analysis() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jieba";
        classname = "test.TestJieba";
        setSources();
        setSinks();
    }

    private static void setParameters4jericho() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jericho";
        classname = "test.TestHTML";
        setSources();
        setSinks();
    }

    private static void setParameters4jepjavagpl() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-expression";
        classname = "test.expression.TestExpression";
        setSources();
        setSinks();
    }

    private static void setParameters4jdom() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jdom";
        classname = "test.testJdom";
        setSources();
        setSinks();
    }

    private static void setParameters4jcsv() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jcsv";
        classname = "test.TestJcsv";
        setSources();
        setSinks();
    }

    private static void setParameters4JavaParserObjects() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-pobs";
        classname = "test.pobs.SimpleCalc";
        setSources();
        setSinks();
    }

    private static void setParameters4japaparser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-javaparser";
        classname = "test.TestJavaparser";
        setSources();
        setSinks();
    }

    private static void setParameters4J2Latex() {
    }

    private static void setParameters4j2html() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-j2html";
        classname = "test.TestJ2html";
        setSources();
        setSinks();
    }

    private static void setParameters4inojava() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ino-java";
        classname = "test.TestInojava";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4interpreter() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-interpreter";
        classname = "test.TestInterpreter";
        setSources();
        setSinks();
    }

    private static void setParameters4image4j() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-image4j";
        classname = "test.TestImage4jDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4htmlparser2() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlparser2";
        classname = "test.html.TestHtmlparser2";
        setSources();
        setSinks();
    }

    private static void setParameters4htmlparser() {
    }

    private static void setParameters4html_parser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlgramparser";
        classname = "test.TestHtmlGram";
        setSources();
        setSinks();
    }

    private static void setParameters4htmlcleaner() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlcleaner";
        classname = "test.TestHtmlcleaner";
        setSources();
        setSinks();
    }

    private static void setParameters4HTML5Parser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-html5parser";
        classname = "test.TestHtml5parserDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4gson() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-gson";
        classname = "test.TestGson";
        setSources();
        setSinks();
    }

    private static void setParameters4Golang() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-golang";
        classname = "test.TestGo";
        setSources();
        setSinks();
    }

    private static void setParameters4gif_imagej() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        classname = "test.TestGifDecoderDriver";
        setSources();
        setSinks();
    }

    private static void setParameters4galimatias() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-galimatias";
        classname = "test.TestGalimatias";
        setSources();
        setSinks();
    }

    private static void setParameters4ftps_client_java() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ftp";
        classname = "test.ftp.TestFTP";
        setIntegerSources();
        setSinks();
    }

    private static void setParameters4foxykeep() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-foxykeep";
        classname = "test.TestFoxykeep";
        setSources();
        setSinks();
    }

    private static void setParameters4formula4j() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-formula4j";
        classname = "test.TestFormula4j";
        setSources();
        setSinks();
    }

    private static void setParameters4flexmark_java() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-flexmark";
        classname = "test.TestFlexmark";
        setSources();
        setSinks();
    }

    private static void setParameters4FirstOrderParser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-firstorderparser";
        classname = "de.dominicscheurer.fol.test.TestFirstOrder";
        setSources();
        setSinks();
    }

    private static void setParameters4fastjson() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-fastjson-dev";
        classname = "test.testFastjsonDev";
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4FastCSV() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-fastcsv";
        classname = "test.TestFastcsv";
        setSources();
        setSinks();
    }

    private static void setParameters4dom4j() {

        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-dom4j";
        classname = "test.testDom4j";
        setSources();
        setSinks();
    }

    private static void setParameters4Curta() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-curta";
        classname = "test.TestCurta";
        setSources();
        setSinks();
    }

    private static void setParameters4commons_csv() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-commons-csv";
        classname = "test.TestCommonsCsv";
        setSources();
        setSinks();
    }

    private static void setParameters4commons_codec() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-CommonsCodec";
        classname = "test.TestCommonsCodec";
        setSources();
        setSinks();
    }

    private static void setParameters4commonmark() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-commonmark-java";
        classname = "test.TestCommonmarkJava";
        setSources();
        setSinks();
    }

    private static void setParameters4cmm() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-cmmparser";
        classname = "test.TestCmmparser";
        setSources();
        setSinks();
    }

    private static void setParameters4clojure_parser() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-clojure";
        classname = "test.TestClojure";
        setSources();
        setSinks();
    }

    private static void setParameters4calculator() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-calculator";
        classname = "test.TestCalculator";
        setSources();
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
        setIntegerSources();
        setSources();
        setSinks();
    }

    private static void setParameters4bloat() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bloat";
        classname = "EDU.purdue.cs.bloat.decorate.ModifiedMainForSE";
        setIntegerSources();
        setSinks();
    }

    private static void setParameters4bling() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bling";
        classname = "test.TestBling";
        setSources();
        setSinks();
    }

    private static void setParameters4awkparser() {
    }

    private static void setParameters4autolinkjava() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-autolink";
        classname = "test.TestAutolink";
        setSources();
        setSinks();
    }

    private static void setParameters4argo() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-argo";
        classname = "test.TestArgo";
        setSources();
        setSinks();
    }

    private static void setParameters4antlr() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-Antlr";
        classname = "test.antlr.TestUDL";
        setSources();
        setSinks();
    }

    private static void setParameters4AEJCC() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-aejcc";
        classname = "test.TestAejcc";
        setSources();
        setSinks();
    }

    private static void setParameters4actson() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-actson";
        classname = "test.TestActson";
        setSources();
        setSinks();
    }

    private static void setIntegerSources() {
        String source1 = "<gov.nasa.jpf.jdart.Debug: int makeConcolicInteger(java.lang.String,java.lang.String)>";
        sources.add(source1);
    }
    
    private static void setSources() {
        String source1 = "<gov.nasa.jpf.jdart.Debug: char makeConcolicChar(java.lang.String,java.lang.String)>";
        sources.add(source1);
    }
    
    private static void setFloatSources() {
        String source1 = "<gov.nasa.jpf.jdart.Debug: double makeConcolicDouble(java.lang.String,java.lang.String)>";
        sources.add(source1);
    }

    private static void setSinks() {
        String sink1 = "<featureDetection.InstrumentHelper: void recursionConditionHelperInt(int,int)>";
        sinks.add(sink1);
    }

    private static void setTaintParameters4Recursion() {

        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoRecursion.testRecursion";

        String source1 = "<demoRecursion.testRecursion: int secret()>";
        String sink1 = "<featureDetection.InstrumentHelper: void recursionConditionHelperInt(int,int)>";

        sources.add(source1);
        sinks.add(sink1);

    }

}