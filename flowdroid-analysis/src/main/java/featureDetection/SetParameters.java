package featureDetection;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SetParameters {

    public static String inputClassPath;
    public static String entryClass;
    public static String entryMethod;
    public static String packageDriverName;
    public static String packageSourceName;

    public static void main(String[] args) {

        // test for single feature

//        setParameters4Array();
//        setParameters4Bitwise();
//        setParameters4Branch();
//        setParameters4Constraint();
//        setParameters4File();
//        setParameters4Float();
//        setParameters4Jni();
//        setParameters4Loop();
//        setParameters4Recursion();
//        setParameters4Reflection();

        // test for real programs

//        no.1 - no.10
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

//        no.11 - no.20
//        setParameters4calculator();
//        setParameters4clojureparser();
//        setParameters4cmm();
//        setParameters4commonmark();
//        setParameters4commonscodec();
//        setParameters4commonscsv();
//        setParameters4Curta();
//        setParameters4dom4j();
//        setParameters4FastCSV();
//        setParameters4fastjson();

//        no.21 - no.30
//        setParameters4FirstOrderParser();
//        setParameters4flexmarkjava();
//        setParameters4formula4j();
//        setParameters4foxykeep();
//        setParameters4ftpsclientjava();
//        setParameters4galimatias();
//        setParameters4gifimagej();
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

//        setParameters4Colt();
//        setParameters4ColtCD();
//        setParameters4ColtED();
//        setParameters4ColtLU();
//        setParameters4ColtQR();
//        setParameters4ColtRank();
//        setParameters4ColtSolver();
//        setParameters4ColtSVD();
//        setParameters4ColtTVS();

//        setParameters4CholeskyDecompositor();
//        setParameters4EigenDecompositor();
//        setParameters4ForwardBackSubstitutionSolver();
//        setParameters4GaussianSolver();
//        setParameters4GaussJordanInverter();
//
//        setParameters4JacobiSolver();
//        setParameters4LeastSquaresSolver();
//        setParameters4LIA4J();
//        setParameters4LUDecompositor();
//        setParameters4NoPivotGaussInverter();
//
//        setParameters4QRDecompositor();
//        setParameters4SeidelSolver();
//        setParameters4SingularValueDecompositor();
//        setParameters4SquareRootSolver();
//        setParameters4SweepSolver();

        MainDriver mainDriver = new MainDriver();

        if (args.length == 0) {
            System.out.println("length of args is 0");
            String[] arg = new String[5];

            arg[0] = inputClassPath;
            arg[1] = entryClass;
            arg[2] = entryMethod;
            arg[3] = packageDriverName;
            arg[4] = packageSourceName;

            mainDriver.main(arg);
        }
        else {

            String fileName = args[0].split("/")[8];
            String entryName = args[1];
            // Redirect output to a file
            try {
                PrintStream print=new PrintStream("/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/instrument/" + fileName + "-" + entryName + ".log");
                System.setOut(print);
            } catch (FileNotFoundException e) {}

            mainDriver.main(args);
        }

    }

    private static void setParameters4SweepSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestSweepSolver";
        entryMethod = "main";
    }

    private static void setParameters4SquareRootSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestSquareRootSolver";
        entryMethod = "main";
    }

    private static void setParameters4SingularValueDecompositor() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestSingularValueDecompositor";
        entryMethod = "main";
    }

    private static void setParameters4SeidelSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestSeidelSolver";
        entryMethod = "main";
    }

    private static void setParameters4QRDecompositor() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestQRDecompositor";
        entryMethod = "main";
    }

    private static void setParameters4NoPivotGaussInverter() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestNoPivotGaussInverter";
        entryMethod = "main";
    }

    private static void setParameters4LUDecompositor() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestLUDecompositor";
        entryMethod = "main";
    }

    private static void setParameters4LIA4J() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestLIA4J";
        entryMethod = "main";
    }

    private static void setParameters4LeastSquaresSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestLeastSquaresSolver";
        entryMethod = "main";
    }

    private static void setParameters4JacobiSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestJacobiSolver";
        entryMethod = "main";
    }

    private static void setParameters4GaussJordanInverter() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestGaussJordanInverter";
        entryMethod = "main";
    }

    private static void setParameters4GaussianSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestGaussianSolver";
        entryMethod = "main";
    }

    private static void setParameters4ForwardBackSubstitutionSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestForwardBackSubstitutionSolver";
        entryMethod = "main";
    }

    private static void setParameters4EigenDecompositor() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestEigenDecompositor";
        entryMethod = "main";
    }

    private static void setParameters4CholeskyDecompositor() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        packageDriverName = "test";
        packageSourceName = "org";
        entryClass = "test.TestCholeskyDecompositor";
        entryMethod = "main";
    }

    private static void setParameters4ColtTVS() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtTVS";
        entryMethod = "main";
    }

    private static void setParameters4ColtSVD() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtSVD";
        entryMethod = "main";
    }

    private static void setParameters4ColtSolver() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtSolver";
        entryMethod = "main";
    }

    private static void setParameters4ColtRank() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtRank";
        entryMethod = "main";
    }

    private static void setParameters4ColtQR() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtQR";
        entryMethod = "main";
    }

    private static void setParameters4ColtLU() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtLU";
        entryMethod = "main";
    }

    private static void setParameters4ColtED() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtED";
        entryMethod = "main";
    }

    private static void setParameters4ColtCD() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColtCD";
        entryMethod = "main";
    }

    private static void setParameters4Colt() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        packageDriverName = "test";
        packageSourceName = "cern";
        entryClass = "test.TestColt";
        entryMethod = "main";
    }

    private static void setParameters4Reflection() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoReflection";
        packageSourceName = "demoReflection";
        entryClass = "demoReflection.testReflection";
        entryMethod = "main";
    }

    private static void setParameters4Recursion() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoRecursion";
        packageSourceName = "demoRecursion";
        entryClass = "demoRecursion.testRecursion";
        entryMethod = "main";
    }

    private static void setParameters4Loop() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoLoop";
        packageSourceName = "demoLoop";
        entryClass = "demoLoop.testLoop";
        entryMethod = "main";
    }

    private static void setParameters4Jni() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoJni";
        packageSourceName = "demoJni";
        entryClass = "demoJni.testJni";
        entryMethod = "main";
    }

    private static void setParameters4Float() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoFloat";
        packageSourceName = "demoFloat";
        entryClass = "demoFloat.testFloat";
        entryMethod = "main";
    }

    private static void setParameters4File() {
    }

    private static void setParameters4Constraint() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoConstraint";
        packageSourceName = "demoConstraint";
        entryClass = "demoConstraint.testConstraint";
        entryMethod = "main";
    }

    private static void setParameters4Branch() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoBranch";
        packageSourceName = "demoBranch";
//        entryClass = "demoBranch.testBranch";
//        entryClass = "demoBranch.testSwitch";
        entryClass = "demoBranch.testBranchInLoop";
        entryMethod = "main";
    }

    private static void setParameters4Bitwise() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoBitwise";
        packageSourceName = "demoBitwise";
        entryClass = "demoBitwise.testBitwise";
        entryMethod = "main";
    }

    private static void setParameters4Array() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        packageDriverName = "demoArray";
        packageSourceName = "demoArray";
        entryClass = "demoArray.testArray";
        entryMethod = "main";
    }

//    no.91 - no.95

    private static void setParameters4yamlbeans() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-yamlbeans";
        entryClass = "test.TestYamlbeansDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.esotericsoftware.yamlbeans";
    }

    private static void setParameters4xerces() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-xerces";
        entryClass = "test.testXerces";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org";
    }

    private static void setParameters4URL_Detector() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-url-detector";
        entryClass = "test.TestUrlDetector";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.linkedin.urls";
    }

    private static void setParameters4uriparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-uriparser";
        entryClass = "test.TestUriParser";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "uri";
    }

    private static void setParameters4univocity_parsers() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-univocity";
        entryClass = "test.TestUnivocity";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.univocity.parsers";
    }

//    no.81 - no.90

    private static void setParameters4txtmark() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-txtmark";
        entryClass = "test.TestTxtmark";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.github.rjeschke.txtmark";
    }

    private static void setParameters4toba() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-toba";
        entryClass = "toba.translator.Trans";
        entryMethod = "start";
        packageDriverName = "toba";
        packageSourceName = "toba";
    }

    private static void setParameters4super_csv() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-super-csv";
        entryClass = "test.testSuperCSVDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.supercsv";
    }

    private static void setParameters4sqlparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sqlparser";
        entryClass = "sql.TestSqlParser";
        entryMethod = "start";
        packageDriverName = "sql";
        packageSourceName = "sql";
    }

    private static void setParameters4soot_c() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-soot-c";
        entryClass = "ca.mcgill.sable.soot.jimple.MainSE";
        entryMethod = "start";
        packageDriverName = "sootc.net.sf.jazzlib";
        packageSourceName = "ca.mcgill.sable";
    }

    private static void setParameters4snakeyaml() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-snakeyaml";
        entryClass = "test.testSnakeyaml";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.yaml.snakeyaml";
    }

    private static void setParameters4sixpath() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sixpath";
        entryClass = "test.sixpath.TestSixpath";
        entryMethod = "start";
        packageDriverName = "test.sixpath";
        packageSourceName = "de.fzi.XPath";
    }

    private static void setParameters4simplecsv() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-simple-csv";
        entryClass = "test.TestSimplecsv";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "net.quux00.simplecsv";
    }

    private static void setParameters4sie() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sie";
        entryClass = "test.TestSie";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "sie";
    }

    private static void setParameters4schroeder() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-schroeder";
        entryClass = "schroeder.test.TestWAVEFileRead";
        entryMethod = "start";
        packageDriverName = "schroeder";
        packageSourceName = "schroeder.test";
    }

//    no.71 - no.80

    private static void setParameters4sablecc() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sablecc";
        entryClass = "ca.mcgill.sable.sablecc.SableCC";
        entryMethod = "start";
        packageDriverName = "ca.mcgill.sable.sablecc";
        packageSourceName = "ca.mcgill.sable.sablecc";
    }

    private static void setParameters4RParser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-rparser";
        entryClass = "test.TestR";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.garcel.parser.r";
    }

    private static void setParameters4rhino() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-rhino";
        entryClass = "test.rhino.TestParser";
        entryMethod = "start";
        packageDriverName = "test.rhino";
        packageSourceName = "mozilla";
    }

    private static void setParameters4pgm_imagej() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        entryClass = "test.TestPgmDecoderDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "ij";
    }

    private static void setParameters4open_m3u8() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-open-m3u8";
        entryClass = "test.TestOpenM3u8";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.iheartradio.m3u8";
    }

    private static void setParameters4oajavaparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-oajavaparser";
        entryClass = "test.TestOajavaParser";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.viaoa.javaparser";
    }

    private static void setParameters4NunniMJAX() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-xml";
        entryClass = "test.TestXml";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "ch.nunnisoft.xml.parser";
    }

    private static void setParameters4ntru() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ntru";
        entryClass = "test.NTRUExample";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "net.sf.ntru";
    }

    private static void setParameters4nekothml() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nekohtml";
        entryClass = "test.TestNekohtml";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.cyberneko.html";
    }

    private static void setParameters4nanoxml() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nanoxml";
        entryClass = "nanoxml.DumpXML";
        entryMethod = "start";
        packageDriverName = "nanoxml";
        packageSourceName = "nanoxml";
    }

//    no.61 - no.70

    private static void setParameters4nanojson() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nanojson";
        entryClass = "test.TestNanojson";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.grack.nanojson";
    }

    private static void setParameters4mXparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mXparser";
        entryClass = "test.mxparser.TestmXparser";
        entryMethod = "start";
        packageDriverName = "test.mxparser";
        packageSourceName = "org.mariuszgromada.math.mxparser";
    }

    private static void setParameters4mp3agic() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mp3agic";
        entryClass = "test.testMp3agicDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.mpatric.mp3agic";
    }

    private static void setParameters4minimal_json() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-minimal-json";
        entryClass = "test.TestDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.eclipsesource.json";
    }

    private static void setParameters4MarkdownPapers() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-MarkdownPapers";
        entryClass = "test.TestMarkdownPapers";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.tautua.markdownpapers";
    }

    private static void setParameters4markdownj() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-markdownj";
        entryClass = "test.TestMarkdownj";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.markdownj";
    }

    private static void setParameters4markdown4j() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-markdown4j";
        entryClass = "test.TestMarkdown4j";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "mark";
    }

    private static void setParameters4Mapl() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mapl";
        entryClass = "test.TestMapl";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "parser";
    }

    private static void setParameters4jurl() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jurl";
        entryClass = "test.TestJurl";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.anthonynsimon.url";
    }

    private static void setParameters4jtidy() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jtidy";
        entryClass = "test.TestJtidy";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.w3c.tidy";
    }

//    no.51 - no.60

    private static void setParameters4JSqlParser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsqlparser";
        entryClass = "test.TestJsqlparserDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "net.sf.jsqlparser";
    }

    private static void setParameters4jsoup() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsoup";
        entryClass = "test.TestJsoup";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.jsoup";
    }

    private static void setParameters4json_simple() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-simple";
        entryClass = "test.TestJsonSimpleDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.json.simple";
    }

    private static void setParameters4json_raupachz() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-raupachz";
        entryClass = "test.TestJsonRaupachz";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "parser";
    }

    private static void setParameters4JSONParser_mwnorman() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsonparser-mwnorman";
        entryClass = "org.mwnorman.json.test.TestJsonParserMwnorman";
        entryMethod = "start";
        packageDriverName = "org.mwnorman.json.test";
        packageSourceName = "org.mwnorman.json";
    }

    private static void setParameters4jsonparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsonparser";
        entryClass = "test.TestJsonparser";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "jsonparser";
    }

    private static void setParameters4JSON_java() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-java";
        entryClass = "test.TestJsonJavaDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.json";
    }

    private static void setParameters4json_iterator() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsoniter";
        entryClass = "test.TestJsoniter";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.jsoniter";
    }

    private static void setParameters4json_flattener() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-flattener";
        entryClass = "test.TestJsonFlattener";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.github.wnameless.json";
    }

    private static void setParameters4JSIJCC() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsijcc";
        entryClass = "test.TestJsijcc";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "javascriptInterpreter";
    }

//    no.41 - no.50

    private static void setParameters4jpat() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jpat";
        entryClass = "jpat.test.JPATTest";
        entryMethod = "doDigestTest";
        packageDriverName = "jpat.test";
        packageSourceName = "jpat";
    }

    private static void setParameters4joda_time() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jodatime";
        entryClass = "test.JodatimeExample";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.joda";
    }

    private static void setParameters4jmp123_400_utf8_mini() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jmp123_400_utf8_mini";
        entryClass = "test.TestJmp123Driver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "jmp123";
    }

    private static void setParameters4JLex() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-JLex";
        entryClass = "JLex.Main";
        entryMethod = "startJLex";
        packageDriverName = "JLex";
        packageSourceName = "JLex";
    }

    private static void setParameters4jieba_analysis() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jieba";
        entryClass = "test.TestJieba";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com";
    }

    private static void setParameters4jericho() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jericho";
        entryClass = "test.TestHTML";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "net.htmlparser.jericho";
    }

    private static void setParameters4jepjavagpl() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-expression";
        entryClass = "test.expression.TestExpression";
        entryMethod = "start";
        packageDriverName = "test.expression";
        packageSourceName = "org";
    }

    private static void setParameters4jdom() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jdom";
        entryClass = "test.testJdom";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org";
    }

    private static void setParameters4jcsv() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jcsv";
        entryClass = "test.TestJcsv";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.googlecode.jcsv";
    }

    private static void setParameters4JavaParserObjects() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-pobs";
        entryClass = "test.pobs.SimpleCalc";
        entryMethod = "start";
        packageDriverName = "example";
        packageSourceName = "pobs";
    }

    //        no.31 - no.40

    private static void setParameters4japaparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-javaparser";
        entryClass = "test.TestJavaparser";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "japa.parser";
    }

    private static void setParameters4J2Latex() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-j2latex";
        entryClass = "test.TestJ2Latex";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.github.situx.compiler";
    }

    private static void setParameters4j2html() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-j2html";
        entryClass = "test.TestJ2html";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "j2html";
    }

    private static void setParameters4inojava() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ino-java";
        entryClass = "test.TestInojava";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.amazon.ion";
    }

    private static void setParameters4interpreter() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-interpreter";
        entryClass = "test.TestInterpreter";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "interpreter";
    }

    private static void setParameters4image4j() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-image4j";
        entryClass = "test.TestImage4jDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "net.sf.image4j";
    }

    private static void setParameters4htmlparser2() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlparser2";
        entryClass = "test.html.TestHtmlparser2";
        entryMethod = "start";
        packageDriverName = "test.html";
        packageSourceName = "html.grammarparser";
    }

    private static void setParameters4htmlparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlparser";
        entryClass = "test.TestHtmlParser";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "html.parser";
    }

    private static void setParameters4html_parser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlgramparser";
        entryClass = "test.TestHtmlGram";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "kr.ac.cau.popl.gauthierplm";
    }

    private static void setParameters4htmlcleaner() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlcleaner";
        entryClass = "test.TestHtmlcleaner";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.htmlcleaner";
    }

    //        no.21 - no.30

    private static void setParameters4HTML5Parser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-html5parser";
        entryClass = "test.TestHtml5parserDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com";
    }

    private static void setParameters4gson() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-gson";
        entryClass = "test.TestGson";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.google.gson";
    }

    private static void setParameters4Golang() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-golang";
        entryClass = "test.TestGo";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.bayoog.golang";
    }

    private static void setParameters4gifimagej() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        entryClass = "test.TestGifDecoderDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "ij";
    }

    private static void setParameters4galimatias() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-galimatias";
        entryClass = "test.TestGalimatias";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "io.mola.galimatias";
    }

    private static void setParameters4ftpsclientjava() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ftp";
        entryClass = "test.ftp.TestFTP";
        entryMethod = "start";
        packageDriverName = "test.ftp";
        packageSourceName = "uk.co.marcoratto";
    }

    private static void setParameters4foxykeep() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-foxykeep";
        entryClass = "test.TestFoxykeep";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.foxykeep.cpcodegenerator";
    }

    private static void setParameters4formula4j() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-formula4j";
        entryClass = "test.TestFormula4j";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "hirondelle.formula";
    }

    private static void setParameters4flexmarkjava() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-flexmark";
        entryClass = "test.TestFlexmark";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.vladsch.flexmark";
    }

    private static void setParameters4FirstOrderParser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-firstorderparser";
        entryClass = "de.dominicscheurer.fol.test.TestFirstOrder";
        entryMethod = "start";
        packageDriverName = "de.dominicscheurer.fol.test";
        packageSourceName = "de.dominicscheurer.fol";
    }

    //        no.11 - no.20

    private static void setParameters4fastjson() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-fastjson-dev";
        entryClass = "test.testFastjsonDev";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.alibaba.fastjson";
    }

    private static void setParameters4FastCSV() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-fastcsv";
        entryClass = "test.TestFastcsv";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "de.siegmar.fastcsv";
    }

    private static void setParameters4dom4j() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-dom4j";
        entryClass = "test.testDom4j";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org";
    }

    private static void setParameters4Curta() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-curta";
        entryClass = "test.TestCurta";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "nl.bigo.curta";
    }

    private static void setParameters4commonscsv() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-commons-csv";
        entryClass = "test.TestCommonsCsv";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.apache.commons.csv";
    }

    private static void setParameters4commonscodec() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-CommonsCodec";
        entryClass = "test.TestCommonsCodec";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.apache.commons.codec";
    }

    private static void setParameters4commonmark() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-commonmark-java";
        entryClass = "test.TestCommonmarkJava";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "org.commonmark";
    }

    private static void setParameters4cmm() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-cmmparser";
        entryClass = "test.TestCmmparser";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "rong";
    }

    private static void setParameters4clojureparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-clojure";
        entryClass = "test.TestClojure";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "clojure";
    }

    private static void setParameters4calculator() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-calculator";
        entryClass = "test.TestCalculator";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "com.braxisltd.calculator";
    }

//        no.1 - no.10

    private static void setParameters4bmpimagej() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
        entryClass = "test.TestBmpDecoderDriver";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "ij";
    }

    private static void setParameters4bmpDecoder() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-BMPDecoder";
        entryClass = "test.TestBMPDecoder";
        entryMethod = "start";
        packageDriverName = "test";
        packageSourceName = "bmp";
    }

    private static void setParameters4bloat() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bloat";
        packageDriverName = "net.sf.jazzlib";
        packageSourceName = "EDU.purdue.cs.bloat";
        entryClass = "EDU.purdue.cs.bloat.decorate.ModifiedMainForSE";
        entryMethod = "start";
    }

    private static void setParameters4bling() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bling";
        packageDriverName = "test";
        packageSourceName = "com.cloudability.bling.ast";
        entryClass = "test.TestBling";
        entryMethod = "start";
    }

    private static void setParameters4awkparser() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-awkparser";
        packageDriverName = "awk";
        packageSourceName = "awk";
        entryClass = "awk.TestAwkParser";
        entryMethod = "start";
    }

    private static void setParameters4autolinkjava() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-autolink";
        packageDriverName = "test";
        packageSourceName = "org.nibor.autolink";
        entryClass = "test.TestAutolink";
        entryMethod = "start";
    }

    private static void setParameters4argo() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-argo";
        packageDriverName = "test";
        packageSourceName = "argo";
        entryClass = "test.TestArgo";
        entryMethod = "start";
    }

    private static void setParameters4antlr() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-Antlr";
        packageDriverName = "test.antlr";
        packageSourceName = "org.antlr";
        entryClass = "test.antlr.TestUDL";
        entryMethod = "start";
    }

    private static void setParameters4AEJCC() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-aejcc";
        packageDriverName = "test";
        packageSourceName = "ca.ubc.cs411.aejcc";
        entryClass = "test.TestAejcc";
        entryMethod = "start";
    }

    private static void setParameters4actson() {
        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-actson";
        packageDriverName = "test";
        packageSourceName = "de.undercouch.actson";
        entryClass = "test.TestActson";
        entryMethod = "start";
    }

}

//        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-actson";
//        packageName = "test";
//        entryClass = "test.TestActson";
//        entryMethod = "main";

//        // for bmp-imagej
//        inputClassPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA";
//        packageName = "test";
//        entryClass = "test.TestBmpDecoderDriver";
//        entryMethod = "main";
