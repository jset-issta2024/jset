#!/usr/bin/python3

# 33
normalRunProgramsNew = ['TestAejccDriver', 'TestAntlrUDL', 'TestAwkParserDriver', 'TestBlingDriver',
                        'TestBloat', 'TestCalculatorDriver', 'TestClojureDriver', 'TestCmmparserDriver',
                        'TestCommonsCodecParser', 'TestCurtaParser', 'TestFirstOrderParser', 'TestFormula4jDriver',
                        'TestFoxykeepParser', 'TestHtml5Parser', 'TestHtmlGramParser', 'Testhtmlparser2Driver',
                        'TestHtmlParserDriver', 'TestInterpreterParser', 'TestJ2LatexDriver', 'TestJavaparserParser',
                        'TestJLex', 'TestJsijccDriver', 'TestJsonparserDriver', 'TestJsonRaupachz',
                        'TestJsqlparserParser', 'TestMaplDriver', 'TestmXparserParser', 'TestOaJavaParserDriver',
                        'TestSixpathParser', 'TestSootC', 'TestSqlParserDriver', 'TestToba',
                        'TestUriParserDriver']

programs = normalRunProgramsNew

programsTest = [[0,1,2,3],[4,5,6,7]]

def main():
    for i in range(len(programsTest)):
        print(programsTest[i][0])
        print(programsTest[i][1])
        print(programsTest[i][2])
        print(programsTest[i][3])
        print("end")

main()
