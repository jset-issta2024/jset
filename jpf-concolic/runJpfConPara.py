#!/usr/bin/python3

import os
from multiprocessing import Pool

allProgs = ['TestActsonParser', 'TestAejccDriver', 'TestAntlrUDL', 'TestArgoParser', 'TestAutolinkParser',
            'TestBlingDriver', 'TestBloat', 'TestBMP', 'TestCalculatorDriver', 'TestClojureDriver',
            'TestCmmparserDriver', 'TestCommonmarkJavaParser', 'TestCommonsCsvParser', 'TestCommonsCodecParser', 'TestCurtaParser',
            'TestDom4jParser', 'TestExpressionParser', 'TestFastcsvParser', 'TestFastJSONDevParser', 'TestFirstOrderParser',
            'TestFlexmarkParser', 'TestFormula4jDriver', 'TestFoxykeepParser', 'TestFTPClient', 'TestGalimatiasParser',
            'TestGolangParser', 'TestGsonParser', 'TestHtml5Parser', 'TestHtmlcleanerParser', 'TestHtmlGramParser',
            'Testhtmlparser2Driver', 'TestImage4jParser', 'TestGifReaderParser', 'TestBmpReaderParser', 'TestPgmReaderParser', 'TestInojavaParser',
            'TestInterpreterParser', 'TestJ2htmlParser', 'TestJavaparserParser', 'TestJCSVParser', 'TestJdomParser',
            'TestJerichoParser', 'TestJiebaParser', 'TestJLex', 'TestJmp123Parser', 'TestJodatime',
            'TestJPAT', 'TestJsijccDriver', 'TestJsonFlattenerParser', 'TestJsonJavaDriver', 'TestJsonRaupachz', 'TestJsqlparserParser', 'TestJsoupParser',
            'TestJsonSimpleDriverParser', 'TestJsoniterParser', 'TestJsonParserMwnormanDriver', 'TestJsonparserDriver', 'TestJtidyParser',
            'TestJurlParser', 'TestMaplDriver', 'TestMarkdown4jParser', 'TestMarkdownjParser', 'TestMDPParser',
            'TestDriverParserMinmalJson', 'TestMp3agicParser', 'TestmXparserParser', 'TestNanojsonParser', 'TestNanoXML',
            'TestNekohtmlDriver', 'TestNTRUExample', 'TestOaJavaParserDriver', 'TestOpenM3u8Parser', 'TestPobsParser',
            'TestRhino', 'TestRDriver', 'TestSableCC', 'TestSchroeder', 'TestSieParser',
            'TestSimplecsvParser', 'TestSixpathParser', 'TestSnakeyamlParser', 'TestSootC', 'TestSqlParserDriver',
            'TestSuperCSVParser', 'TestToba', 'TestTxtmarkParser', 'TestUnivocityParser', 'TestUriParserDriver',
            'TestUrlDetectorParser', 'TestXercesParser', 'TestXmlParser', 'TestYamlbeansParser']

test = ['TestActsonParser', 'TestAejccDriver', 'TestAntlrUDL', 'TestArgoParser']

def main():
    os.system("ulimit -s unlimited")
    pool = Pool(processes=4)
    cmd = "./runExeScript.sh {} >> expPathResult/{}.log"

    for parameter in test:
        pool.apply_async(os.system, args=(cmd.format(parameter,parameter),))
        print(cmd.format(parameter,parameter))
    pool.close()
    pool.join()

main()
