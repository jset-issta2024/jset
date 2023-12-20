#!/bin/bash

#export JAVA_HOME=/usr/local/jdk1.8
#export JDK_HOME=/usr/local/jdk1.8

#ant coverage-example-javaparser
#ant coverage-example-jsqlparser
#ant coverage-example-MarkdownPapers
#ant coverage-example-sixpath
#ant coverage-example-fastjson-dev
#ant coverage-example-mXparser

ant instrument

#className="test.testActson"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jpf-symbc/expeResultCov/Actson.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testArgo"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Argo.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testAutolinkJava"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/AutolinkJava.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

className="test.testCommonmarkJava"
saveInputFile="/home/lmx/Documents/GitHub/jpf8/IncrementalCoverage/expeResultCoverage/0828/Commonmark.saveInput"
ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testCommonsCsv"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/CommonsCsv.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.expression.ExpressionDriver"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Expression.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testFastCsv"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/FastCsv.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testFlexmark"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Flexmark.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testGalimatias"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Galimatias.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testGson"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Gson.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testHtmlcleaner"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/HtmlCleaner.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testInoJava"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/InoJava.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testJcsv"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Jcsv.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testJsonFlattener"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/JsonFlattener.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testJsoniter"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/IncrementalCoverage/expeResultCoverage/0828/Jsoniter.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testJsonJava"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/JsonJava.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testJsoup"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Jsoup.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testJtidy"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Jtidy.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testMinimalJson"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/MinimalJson.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}


#className="test.testJericho"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Jericho.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testNanojson"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Nanojson.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testSimpleCsv"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/SimpleCsv.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testSuperCsv"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/SuperCsv.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testUnivocity"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Univocity.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testUrlDetector"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/UrlDetector.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testTxtmark"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Txtmark.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testXml"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Xml.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.rhino.RhinoDriver"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Rhino.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.testNanoxml"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/Nanoxml.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

# add by lmx
# ******************************************************************************************************

#className="test.JavaparserDriver"
#saveInputFile="src/example-javaparser/TestJavaparserParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javaparser/TestJavaparserParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javaparser/TestJavaparserParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javaparser/TestJavaparserParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.FastjsonDriver"
#saveInputFile="src/example-fastjson-dev/TestFastJSONDevParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-fastjson-dev/TestFastJSONDevParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-fastjson-dev/TestFastJSONDevParser-TokenSymb-genTokenString-TokenLengthBound5-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-fastjson-dev/TestFastJSONDevParser-TokenSymb-genTokenString-TokenLengthBound5-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.sixpath.SixpathDriver"
#saveInputFile="src/example-sixpath/TestSixpathParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-sixpath/TestSixpathParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-sixpath/TestSixpathParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-sixpath/TestSixpathParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.awkparser.awkDriver"
#saveInputFile="src/example-awkparser/TestAwkParserDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-awkparser/TestAwkParserDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-awkparser/TestAwkParserDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-awkparser/TestAwkParserDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.CurtaDriver"
#saveInputFile="src/example-curta/TestCurtaParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-curta/TestCurtaParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-curta/TestCurtaParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-curta/TestCurtaParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.MarkdownPapersDriver"
#saveInputFile="src/example-MarkdownPapers/TestMDPParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-MarkdownPapers/TestMDPParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-MarkdownPapers/TestMDPParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-MarkdownPapers/TestMDPParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.firstorder.FirstOrderDriver"
#saveInputFile="src/example-firstorderparser/TestFirstOrderParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-firstorderparser/TestFirstOrderParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-firstorderparser/TestFirstOrderParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-firstorderparser/TestFirstOrderParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.HtmlgramDriver"
#saveInputFile="src/example-htmlgramparser/TestHtmlGramParser-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-htmlgramparser/TestHtmlGramParser-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-htmlgramparser/TestHtmlGramParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-htmlgramparser/TestHtmlGramParser-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.FastjsonDriver"
##saveInputFile="src/example-fastjson-dev/grammar-fuzzer.saveinput"
#saveInputFile="/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/Fastjson.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.MarkdownPapersDriver"
#saveInputFile="/home/lmx/Documents/GitHub/jpf8/jdart/expeResultCov/MarkdownPapers.saveInput"
##saveInputFile="src/example-MarkdownPapers/grammar-fuzzer.saveinput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.J2LatexDriver"
#saveInputFile="src/example-j2latex/TestJ2LatexDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-j2latex/TestJ2LatexDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-j2latex/TestJ2LatexDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-j2latex/TestJ2LatexDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.JavaccClojureDriver"
#saveInputFile="src/example-javacc-clojure/TestJavaccClojureDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javacc-clojure/TestJavaccClojureDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javacc-clojure/TestJavaccClojureDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javacc-clojure/TestJavaccClojureDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.javaccgrammarDriver"
#saveInputFile="src/example-javaccgrammar/TestJavaccGrammarDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javaccgrammar/TestJavaccGrammarDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javaccgrammar/TestJavaccGrammarDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-javaccgrammar/TestJavaccGrammarDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="org.mwnorman.json.test.jsonParserMwnormanDriver"
#saveInputFile="src/example-jsonparser-mwnorman/TestJsonParserMwnormanDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-jsonparser-mwnorman/TestJsonParserMwnormanDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-jsonparser-mwnorman/TestJsonParserMwnormanDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-jsonparser-mwnorman/TestJsonParserMwnormanDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.mapl.maplDriver"
#saveInputFile="src/example-mapl/TestMaplDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-mapl/TestMaplDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-mapl/TestMaplDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-mapl/TestMaplDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.oajavaparserDriver"
#saveInputFile="src/example-oajavaparser/TestOaJavaParserDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-oajavaparser/TestOaJavaParserDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-oajavaparser/TestOaJavaParserDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-oajavaparser/TestOaJavaParserDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#
#className="test.sqlparserDriver"
#saveInputFile="src/example-sqlparser/TestSqlParserDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-sqlparser/TestSqlParserDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-sqlparser/TestSqlParserDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-sqlparser/TestSqlParserDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

#className="test.urijavaccDriver"
#saveInputFile="src/example-urijavacc/TestUriJavaccDriver-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-urijavacc/TestUriJavaccDriver-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-urijavacc/TestUriJavaccDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-bfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
#saveInputFile="src/example-urijavacc/TestUriJavaccDriver-TokenSymb-genTokenString-TokenLengthBound3-stage3-dfs.saveInput"
#ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}

