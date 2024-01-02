#!/usr/bin/python3

import subprocess
import time
import timeit
import os
from multiprocessing import Pool

classes = {'test.testActson': 'Actson', 'test.testArgo':'Argo', 'test.testAutolinkJava':'AutolinkJava',
           'test.testCommonmarkJava':'CommonmarkJava', 'test.testCommonsCsv':'CommonsCsv', 'TestExpressionParser':'Expression',
           'test.testFastCsv':'FastCsv', 'TestFastJSONDevParser':'Fastjson',
           'test.testGalimatias':'Galimatias', 'test.testGson':'Gson', 'test.testHtmlcleaner':'Htmlcleaner',
           'test.testInoJava':'InoJava', 'test.testJcsv':'Jcsv', 'test.testJericho':'Jericho',
           'test.testJsonFlattener':'JsonFlattener', 'test.testJsoniter':'Jsoniter', 'test.testJsonJava':'JsonJava',
           'test.testJsoup':'Jsoup',  'TestMarkdownPapersParser':'MarkdownPapers',
           'test.testMinimalJson':'MinimalJson', 'test.testNanojson':'Nanojson', 'test.testNanoxml':'Nanoxml',
           'TestRhino':'Rhino', 'test.testSimpleCsv':'SimpleCsv', 'test.testSuperCsv':'SuperCsv',
           'test.testTxtmark':'Txtmark', 'test.testUnivocity':'Univocity', 'test.testUrlDetector':'UrlDetector',
           'test.testXml':'Xml'}

def main():
    os.system("ulimit -s unlimited")
    pool = Pool(processes=4)
    cmd = "./run_coverage.sh {} {}"

    for className in classes.keys():
        pool.apply_async(os.system, args=(cmd.format(className, classes[className]),))
        print(cmd)
    pool.close()
    pool.join()

main()

