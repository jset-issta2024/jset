#!/usr/bin/python3

import subprocess
import time
import timeit
import os
from multiprocessing import Pool

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
#             'test.testFastCsv':'FastCsv',
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
#             'test.testSuperCsv':'superCsv',
#             'test.testUnivocity':'Univocity',
#             'test.testUrlDetector':'UrlDetector'
#            }

# classes = {'test.TestFlexmark':'Flexmark',
#
#            'test.testActson': 'Actson',
#            'test.testArgo':'Argo',
#            'test.testAutolinkJava':'AutolinkJava',
#            'TestExpressionParser':'Expression',
#            'test.testInoJava':'InoJava'}

# classes = {'test.TestFoxy': 'Foxykeep',
#            'test.TestHtml5Parser': 'html5Parser',
#            'test.TestJsonFlattener': 'JsonFlattener',
#            'test.TestMarkdown4j': 'markdown4j',
#            'test.TestSie': 'sie',
#            'test.testSnakeyaml': 'snakeyaml'}

# classes = {'test.TestFlexmark':'Flexmark',
#            'test.testGalimatias':'Galimatias',
#            'test.testGson':'Gson',
#            'test.testJericho':'Jericho',
#            'test.testJsonFlattener':'JsonFlattener',
#            'test.testJsoniter':'Jsoniter',
#            'test.testJtidy':'Jtidy',
#            'TestRhino':'Rhino',
#            'test.testUnivocity':'Univocity',
#            }

# classes = {'test.testCommonmarkJava':'CommonmarkJava',
#            'test.testCommonsCsv':'CommonsCsv',
#            'test.testFastCsv':'FastCsv',
#            'test.testHtmlcleaner':'Htmlcleaner',
#            'test.testNanojson':'Nanojson',
#            'test.testNanoxml':'Nanoxml',
#            'test.testXml':'Xml',
#            'test.testSimpleCsv':'SimpleCsv',
#            'test.testSuperCsv':'SuperCsv'
#            }

# classes = {'test.testNanojson':'Nanojson',
#            'test.testNanoxml':'Nanoxml',
#            'test.testSimpleCsv':'SimpleCsv',
#            'test.testSuperCsv':'SuperCsv',
#            'test.testXml':'Xml'
#            }

# classes = {'test.TestFlexmark':'Flexmark',
#            'test.testGalimatias':'Galimatias',
#            'test.testGson':'Gson',
#            'test.testJericho':'Jericho',
#            'test.testJsonFlattener':'JsonFlattener',
#            'test.testJsoniter':'Jsoniter',
#            'test.testJtidy':'Jtidy',
#            'TestRhino':'Rhino',
#            'test.testUnivocity':'Univocity',
#            }

# classes = {'test.testJpat': 'Jpat',
#            'test.testJurl': 'Jurl',
#            'test.testFlexmark': 'Flexmark'}

# classes = {
#     'test.testCommonmarkJava':'CommonmarkJava',
#     'test.testCommonsCsv':'CommonsCsv',
#     'test.testFastCsv':'FastCsv',
#     'test.testGson':'Gson',
#     'test.testHtmlcleaner':'HtmlCleaner',
#     'test.testInoJava':'InoJava',
#     'test.testJcsv':'Jcsv',
#     'test.testJsoup':'Jsoup',
#     'test.testJtidy':'Jtidy',
#     'test.TestMarkdown4j': 'Markdown4j',
#     'TestMarkdownPapersParser':'MarkdownPapers',
#     'test.testMinimalJson':'MinimalJson',
#     'test.testNanojson':'Nanojson',
#     'test.testNanoxml':'Nanoxml',
#     'TestExpressionParser':'Expression',
#     'test.testSimpleCsv':'SimpleCsv',
#     'test.testSuperCsv':'SuperCsv',
#     'test.testTxtmark':'Txtmark',
#     'test.testUnivocity':'Univocity',
#     'test.testUrlDetector':'UrlDetector',
#     'test.testXml':'Xml'
# }

classes = {
    'test.TestPobs':'Pobs'
}

def main():
    os.system("ulimit -s unlimited")
    pool = Pool(processes=4)
    cmd = "./run_coverage_lmx.sh {} {}"

    for className in classes.keys():
        pool.apply_async(os.system, args=(cmd.format(className, classes[className]),))
        print(cmd)
    pool.close()
    pool.join()

main()

