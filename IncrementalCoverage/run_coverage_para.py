#!/usr/bin/python3

import subprocess
import time
import timeit
import os
from multiprocessing import Pool

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

classes = {'test.testCommonmarkJava':'Commonmark', 
            'test.testCommonsCsv':'CommonsCsv', 
            'test.testFastCsv':'FastCsv', 
            'test.testGson':'Gson', 
            'test.testHtmlcleaner':'Htmlcleaner', 
            'test.testInoJava':'InoJava', 
            'test.testJcsv':'Jcsv', 
            'TestExpressionParser':'Expression', 
            'test.testJsoniter':'Jsoniter', 
            'test.testJsoup':'Jsoup', 
            'test.testJtidy':'Jtidy', 
            'TestMarkdownPapersParser':'MarkdownPapers',
            'test.testMinimalJson':'MinimalJson', 
            'test.testNanojson':'nanojson', 
            'test.testNanoxml':'nanoxml', 
            'test.testXml':'xml', 
            'TestRhino':'Rhino', 
            'test.testSimpleCsv':'simpleCsv', 
            'test.testSuperCsv':'superCsv', 
            'test.testUnivocity':'Univocity', 
            'test.testUrlDetector':'UrlDetector' 
           }


def single_run(className, saveInputFile):
    command = "ant SpeedTest -DclassToRun=%s -DSaveInputFile=./expeResultCoverage/1212/%s.saveInput > ./expeCovResult/1212/%s.csv" %(className, saveInputFile, saveInputFile)
    print(command)
    starttime = time.perf_counter()
    subprocess.call(command, shell=True, stdout=open('/dev/null', 'w'), stderr=None)

def main():
    os.system("ulimit -s unlimited")
    pool = Pool(processes=4)

    for className in classes.keys():
        pool.apply_async(single_run, (className, classes[className], classes[className]))
    pool.close()
    pool.join()

main()