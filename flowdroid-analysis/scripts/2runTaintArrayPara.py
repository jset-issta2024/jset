#!/usr/bin/python3

import os
from multiprocessing import Pool

parameters = [
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSweepSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSquareRootSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSingularValueDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSeidelSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestQRDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestNoPivotGaussInverter', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestLUDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestLIA4J', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestLeastSquaresSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestJacobiSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestGaussJordanInverter', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestGaussianSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestForwardBackSubstitutionSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestEigenDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestCholeskyDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtTVS', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtSVD', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtRank', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtQR', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtLU', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtED', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtCD', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColt', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-yamlbeans', 'test.TestYamlbeansDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-xerces', 'test.testXerces', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-url-detector', 'test.TestUrlDetector', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-uriparser', 'test.TestUriParser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-univocity', 'test.TestUnivocity', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-txtmark', 'test.TestTxtmark', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-toba', 'toba.translator.Trans', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-super-csv', 'test.testSuperCSVDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sqlparser', 'sql.TestSqlParser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-soot-c', 'ca.mcgill.sable.soot.jimple.MainSE', '1', '0', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-snakeyaml', 'test.testSnakeyaml', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sixpath', 'test.sixpath.TestSixpath', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-simple-csv', 'test.TestSimplecsv', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sie', 'test.TestSie', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-schroeder', 'schroeder.test.TestWAVEFileRead', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-sablecc', 'ca.mcgill.sable.sablecc.SableCC', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-rparser', 'test.TestR', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-rhino', 'test.rhino.TestParser', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA', 'test.TestPgmDecoderDriver', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-open-m3u8', 'test.TestOpenM3u8', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-oajavaparser', 'test.TestOajavaParser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-xml', 'test.TestXml', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ntru', 'test.NTRUExample', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nekohtml', 'test.TestNekohtml', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nanoxml', 'nanoxml.DumpXML', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-nanojson', 'test.TestNanojson', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mXparser', 'test.mxparser.TestmXparser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mp3agic', 'test.testMp3agicDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-minimal-json', 'test.TestDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-MarkdownPapers', 'test.TestMarkdownPapers', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-markdownj', 'test.TestMarkdownj', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-markdown4j', 'test.TestMarkdown4j', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-mapl', 'test.TestMapl', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jurl', 'test.TestJurl', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jtidy', 'test.TestJtidy', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsqlparser', 'test.TestJsqlparserDriver', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsoup', 'test.TestJsoup', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-simple', 'test.TestJsonSimpleDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-raupachz', 'test.TestJsonRaupachz', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsonparser-mwnorman', 'org.mwnorman.json.test.TestJsonParserMwnorman', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsonparser', 'test.TestJsonparser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-java', 'test.TestJsonJavaDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsoniter', 'test.TestJsoniter', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-json-flattener', 'test.TestJsonFlattener', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jsijcc', 'test.TestJsijcc', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jpat', 'jpat.test.JPATTest', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jodatime', 'test.JodatimeExample', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jmp123_400_utf8_mini', 'test.TestJmp123Driver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-JLex', 'JLex.Main', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jieba', 'test.TestJieba', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jericho', 'test.TestHTML', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-expression', 'test.expression.TestExpression', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jdom', 'test.testJdom', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-jcsv', 'test.TestJcsv', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-pobs', 'test.pobs.SimpleCalc', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-javaparser', 'test.TestJavaparser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-javaparser', 'test.TestJavaparser', '0', '0', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-j2html', 'test.TestJ2html', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ino-java', 'test.TestInojava', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-interpreter', 'test.TestInterpreter', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-image4j', 'test.TestImage4jDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlparser2', 'test.html.TestHtmlparser2', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlparser2', 'test.html.TestHtmlparser2', '0', '0', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlgramparser', 'test.TestHtmlGram', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-htmlcleaner', 'test.TestHtmlcleaner', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-html5parser', 'test.TestHtml5parserDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-gson', 'test.TestGson', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-golang', 'test.TestGo', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA', 'test.TestGifDecoderDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-galimatias', 'test.TestGalimatias', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ftp', 'test.ftp.TestFTP', '1', '0', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-foxykeep', 'test.TestFoxykeep', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-formula4j', 'test.TestFormula4j', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-flexmark', 'test.TestFlexmark', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-firstorderparser', 'de.dominicscheurer.fol.test.TestFirstOrder', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-fastjson-dev', 'test.testFastjsonDev', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-fastcsv', 'test.TestFastcsv', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-dom4j', 'test.testDom4j', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-curta', 'test.TestCurta', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-commons-csv', 'test.TestCommonsCsv', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-CommonsCodec', 'test.TestCommonsCodec', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-commonmark-java', 'test.TestCommonmarkJava', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-cmmparser', 'test.TestCmmparser', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-clojure', 'test.TestClojure', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-calculator', 'test.TestCalculator', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-ImageJA', 'test.TestBmpDecoderDriver', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-BMPDecoder', 'test.TestBMPDecoder', '1', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bloat', 'EDU.purdue.cs.bloat.decorate.ModifiedMainForSE', '1', '0', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bling', 'test.TestBling', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-bling', 'test.TestBling', '0', '0', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-autolink', 'test.TestAutolink', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-argo', 'test.TestArgo', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-Antlr', 'test.antlr.TestUDL', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-aejcc', 'test.TestAejcc', '0', '1', '0'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-actson', 'test.TestActson', '0', '1', '0']
]

def main():
    os.system("ulimit -s unlimited")
    pool = Pool(processes=4)
    cmd = "./2runTaintArray.sh {} {} {} {} {}"

    for i in range(len(parameters)):
        pool.apply_async(os.system, args=(cmd.format(parameters[i][0],parameters[i][1],parameters[i][2],parameters[i][3],parameters[i][4]),))
        print(cmd.format(parameters[i][0],parameters[i][1],parameters[i][2],parameters[i][3],parameters[i][4]))
    pool.close()
    pool.join()

main()
