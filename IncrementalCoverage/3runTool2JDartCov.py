#!/usr/bin/python3

import multiprocessing
import subprocess
import time
import timeit
#line3 'test.TestFlexmark':'Flexmark',
#line7 'test.testJtidy':'Jtidy',

# classes = {'test.testActson': 'Actson', 'test.testArgo':'Argo', 'test.testAutolinkJava':'AutolinkJava',
#            'test.testCommonmarkJava':'CommonmarkJava', 'test.testCommonsCsv':'CommonsCsv', 'TestExpressionParser':'Expression',
#            'test.testFastCsv':'FastCsv', 'TestFastJSONDevParser':'Fastjson',
#            'test.testGalimatias':'Galimatias', 'test.testGson':'Gson', 'test.testHtmlcleaner':'Htmlcleaner',
#            'test.testInoJava':'InoJava', 'test.testJcsv':'Jcsv', 'test.testJericho':'Jericho',
#            'test.testJsonFlattener':'JsonFlattener', 'test.testJsoniter':'Jsoniter', 'test.testJsonJava':'JsonJava',
#            'test.testJsoup':'Jsoup',  'TestMarkdownPapersParser':'MarkdownPapers',
#            'test.testMinimalJson':'MinimalJson', 'test.testNanojson':'Nanojson', 'test.testNanoxml':'Nanoxml',
#            'TestRhino':'Rhino', 'test.testSimpleCsv':'SimpleCsv', 'test.testSuperCsv':'SuperCsv',
#            'test.testTxtmark':'Txtmark', 'test.testUnivocity':'Univocity', 'test.testUrlDetector':'UrlDetector',
#            'test.testXml':'Xml'}

# classes = {'test.testCommonmarkJava':'Commonmark', 
#             'test.testCommonsCsv':'CommonsCsv', 
#             'test.testFastCsv':'fastCsv', 
#             'test.testGson':'Gson', 
#             'test.testHtmlcleaner':'Htmlcleaner', 
#             'test.testInoJava':'InoJava', 
#             'test.testJcsv':'Jcsv', 
#             'TestExpressionParser':'Expression', 
#             'test.testJsoniter':'Jsoniter', 
#             'test.testJsoup':'Jsoup', 
#             'test.testJtidy':'Jtidy', 
#             'TestMarkdownPapersParser':'MarkdownPapers',
#             'test.testMinimalJson':'MinimalJson', 
#             'test.testNanojson':'nanojson', 
#             'test.testNanoxml':'nanoxml', 
#             'test.testXml':'xml', 
#             'TestRhino':'Rhino', 
#             'test.testSimpleCsv':'simpleCsv', 
#             'test.testSuperCsv':'supserCsv', 
#             'test.testUnivocity':'Univocity', 
#             'test.testUrlDetector':'urlDetector' 
#            }

classes = { 'test.testFastCsv':'fastCsv', 
            'test.testSuperCsv':'supserCsv', 
            'test.testUrlDetector':'urlDetector' 
           }


def single_run(className, saveInputFile):
    command = "ant GenCoverage -DclassToRun=%s -DSaveInputFile=./expeResultCoverage/0828/%s.saveInput" %(className, saveInputFile)
    # print(command)
    starttime = time.perf_counter()
    savedRes = "./expeCovResult/0829/" + saveInputFile + ".csv"
    print(savedRes)
    subprocess.run(command, shell=True, stdout = open(savedRes, 'w'), stderr=subprocess.STDOUT)
    print('%s,%i' %(saveInputFile,(time.perf_counter() - starttime)*1000))

if __name__ == '__main__':
    pool = multiprocessing.Pool(processes=4)
    for className in classes.keys():
        ret = pool.apply_async(single_run, (className, classes[className]))
    pool.close()
    pool.join()

