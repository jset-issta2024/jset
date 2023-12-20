#!/usr/bin/python3

import os
import re

IncrementalCoveragePath = "/home/pwy/IdeaProjects/IncrementalCoverage/"
InputDir = "inputs"
TimeMax = 900
LatexMapping = dict()

old_list = ['TestAwkParserDriver', 
 'TestCurtaParser', 
 'TestFastJSONDevParser', 
 'TestFirstOrderParser', 
 'TestHtmlGramParser', 
 'TestJ2LatexDriver', 
 'TestJavaccClojureDriver', 
 'TestJavaccGrammarDriver', 
 'TestJavaparserParser', 
 'TestJsijccDriver', 
 'TestJsonParserMwnormanDriver', 
 'TestMDPParser', 
 'TestMaplDriver', 
 'TestOaJavaParserDriver', 
 'TestSixpathParser', 
 'TestSqlParserDriver', 
 'TestUriJavaccDriver']    

new_list = ['Awk', 
 'Curta'.lower(), 
 'FastJSON', 
 'FirstOrder'.lower(), 
 'HtmlGram'.lower(), 
 'J2Latex', 
 'Clojure', 
 'cmmparser', 
 'Javaparser'.lower(), 
 'Jsijcc', 
 'jsonmwn', 
 'MarkdownP'.lower(), 
 'Mapl', 
 'OaJava', 
 'Sixpath', 
 'Sqlparser', 
 'urijavacc']    

def filterCsv(file):
    rest, ext = os.path.splitext(file)
    if (".csv" in ext ):
        return True
    else:
        return False

def getCsv(dir):
    fileList = os.listdir(dir)
    fileList = [file for file in fileList if filterCsv(file) ]
    return fileList

class InputNameError(Exception):
    def __init__(self, msg):
        self.msg = msg
    
    def __str__(self):
        return self.msg

class UnimplementError(Exception):
    def __init__(self, msg):
        self.msg = msg
    
    def __str__(self):
        return self.msg

def initResMap():
    resMap = {"bfs" : {"baseline" : "", "GoDse" : ""}, 
              "dfs" : {"baseline" : "", "GoDse" : ""},
              "coverageFuzzing" : "",
              "grammarFuzzing" : ""    }
    return resMap

def mapAppend(Map, file):
    parts = re.split( "[\.|-]+", os.path.splitext(file)[0])
    key = parts[0]
    if (key in Map):
        pass
    else:
        Map[key] = initResMap()

    def isStrategy(part):
        if (part in ["bfs", "dfs"]):
            return True
        else:
            return False

    if (isStrategy(parts[1])):
        strategy = parts[1]
        Map[key][strategy]["baseline"] = file
    elif ( "coverage" == parts[1] ):
        Map[key]["coverageFuzzing"] = file
    elif ( "grammar" == parts[1] ):
        Map[key]["grammarFuzzing"] = file
    else:
        strategy = parts[-1]
        if (isStrategy(strategy)):
            Map[key][strategy]["GoDse"] = file
        else:
            raise InputNameError(file)

def csvSplit(csvList):

    resMap = dict()
    for file in csvList:
        try:
            mapAppend(resMap,file)
        except InputNameError as e:
            print(e)
        
    return resMap

def ReadCsv(file):
    import csv
    with open(file, 'r') as f:
        reader = csv.reader(f)
        result = list(reader)
        return result
    raise InputNameError(file)

def getBranchCoverage(file):
    csvList = ReadCsv(file)
    return csvList[-1][0]

def getPathNumber(file):
    csvList = ReadCsv(file)
    return csvList[-1][3]

def getStatementCoverage(file):
    csvList = ReadCsv(file)
    return csvList[-1][2]

def genResultTuplesDetails(csvMap: dict, strategy: str, getCov):
    resultTuples = []
    for value in csvMap.values():
        baseFile = value[strategy]["baseline"]
        baseFile = os.path.join(InputDir,baseFile)
        GoFile = value[strategy]["GoDse"]
        GoFile = os.path.join(InputDir, GoFile)
        elm = ( getCov(baseFile), getCov(GoFile) )
        resultTuples.append(elm)
    return resultTuples

def getCoverageTypeString(getCov):
    if (getCov is getBranchCoverage):
        return "branch"
    elif (getCov is getStatementCoverage):
        return "statement"
    raise UnimplementError("unimplement " + str(getCov))

def findIndex(value: float, array: list) -> int :
    Stop = False
    for i,val in enumerate(array):
        if (val > value):
            Stop = True
            break
    if not Stop:
        return i
    else:
        return max(i - 1, 1)


def genPlot(tuples: list, strategy, metric):
    global LatexMapping
    def calculateImprove(base: int,new: int):
        return (new - base) / base * 100
        # return new - base

    absList = [ int(elm[1]) - int(elm[0])  for elm in tuples]
    plotList = [ float( '%.2f' % calculateImprove(int(elm[0]) , int(elm[1])) )  for elm in tuples]
    plotList.sort()
    more = len(plotList) - findIndex(0,plotList) - 1
    LatexMapping["more{}{}ProgramNumber".format(strategy,metric)] = more
    LatexMapping["more{}{}ProgramPercent".format(strategy,metric)] = more / len(plotList)

    val = sum(absList) / sum([ float(elm[0]) for elm in tuples]) * 100
    LatexMapping["AverageIncrease{}{}".format(strategy,metric)] = '%.2f' % val
    LatexMapping["increaseLowPercent{}{}".format(strategy,metric)] = plotList[0]
    LatexMapping["increaseUpPercent{}{}".format(strategy,metric)] = plotList[-1]

    plotList = [ str(i+1) + "\t" + str(elm) + "\n" for i,elm in enumerate(plotList) ]
    return plotList

def genFiles(csvMap: dict, genFileFunc):
    strategies = ["bfs", "dfs"]
    getCovs = [getBranchCoverage, getStatementCoverage]
    import itertools
    resultFiles = []
    for strategy, getCov in itertools.product(strategies,getCovs):
        resultTuples = genResultTuplesDetails(csvMap,strategy, getCov)
        coverageTypeStr = getCoverageTypeString(getCov)
        writeFile = genFileFunc(resultTuples, strategy, coverageTypeStr)
        resultFiles.append(writeFile)
    return resultFiles

def genDat(resultTuples, strategy, coverageTypeStr):
    resList = genPlot(resultTuples, strategy, coverageTypeStr)
    writeFile = "fig1-{}-{}.dat"
    writeFile = writeFile.format(strategy, coverageTypeStr)
    with open(writeFile, 'w') as f:
        f.writelines(resList)
    return writeFile

def copyFiles(datList, dstDir):
    import shutil
    for dat in datList:
        shutil.copy(dat, os.path.join(dstDir, dat))

def tableLine(entry):
    keyMap = dict(zip(old_list, new_list))

    key, subDict = entry
    if (not (key in keyMap) ):
        print()

    key = keyMap[key]
    key = "\multirow{2}{*}{" + key +"}"

    files = flattenMap(subDict)
    
    oneLine = [[],[]]
    ind = 0;
    for file in files[:-2]:
        lineList = []
        lineList.append( getStatementCoverage(file) )
        lineList.append( getBranchCoverage(file) )
        lineList.append( getPathNumber( file ) )
        if (ind > 1):
            oneLine[1].extend(lineList)
        else:
            oneLine[0].extend(lineList)

        
        ind = ind + 1

    oneLine = [
        " & ".join(oneLine[0]),
        " & DFS & " + " & ".join(oneLine[1])
    ]

    twoLine = []
    for file in files[-2:]:
        lineList = []
        if len(file) > 0:
            lineList.append( getStatementCoverage(file) )
            lineList.append( getBranchCoverage(file) )
            lineList.append( getPathNumber( file ) )
        else:
            lineList.append( "" )
            lineList.append( "" )
            lineList.append( "" )
        twoLine.extend(lineList)

    twoLine = list(map(lambda s: "\multirow{2}{*}{" + s + "}", twoLine))
    twoLine =" & ".join(twoLine)

    oneLine[0] = oneLine[0] + " & " + twoLine
    oneLine = key + " & BFS & " + "\n \\\\ \cline{2-8} ".join(oneLine)


    return oneLine

def getTimeIndex(srcList: list, timeMax: int):
    index = 0
    TimeIndex = []
    for time in range(timeMax + 1):
        if (time == 0):
            TimeIndex.append( (time,-1) )
            continue

        while (     index < len(srcList) 
                and srcList[index] <= time):
            index = index + 1
        TimeIndex.append( (time, index-1) )
    return TimeIndex

def getTimeCoverage(TimeIndex: list, csv: list) -> dict:
    def genCoverList(csvColIndex: int):
        coverList = []
        for time, index in TimeIndex:
            if (index == -1):
                coverList.append( (time, 0) )
            else:
                coverList.append( (time, csv[index][csvColIndex]) )
        return coverList

    branch = genCoverList(0)
    instruction = genCoverList(1)
    statement = genCoverList(2)
    path = genCoverList(3)

    res = {
        "branch" : branch,
        "instruction" : instruction,
        "statement" : statement,
        # "path" : path
    }

    return res

def genCoverageMap(Map: dict, handleCsvFile) -> dict:
    res_dict =dict()
    for key, entry in Map.items():
        if (type(entry) == dict):
            sub_dict = dict()
            for secondKey, entry in entry.items():
                csvFile = os.path.join( InputDir, entry )
                sub_dict[secondKey] = handleCsvFile(csvFile)
            res_dict[key] = sub_dict
        else:
            csvFile = os.path.join( InputDir, entry )
            if (len(entry) > 0):
                res_dict[key] = handleCsvFile(csvFile)
    return res_dict
            
            


def genTimePlot(csvMap: dict) -> dict:
    res_dict = dict()
    for key, Map in csvMap.items():
        res_dict[key] = genCoverageMap(Map, handldTimeCoverage)

    return res_dict

def handldTimeCoverage(csvFile) -> dict :
    csv = ReadCsv(csvFile)
    csv = csv[1:]
    timeList = [ ( int(elm[-1]) // 1000 ) for elm in csv ]
    timeIndex = getTimeIndex( timeList, TimeMax )
    return getTimeCoverage(timeIndex,csv)
            

def flattenMap(Map) -> list:
    strategies = ["bfs", "dfs"]
    methods = ["baseline", "GoDse"]
    import itertools
    files = []
    for strategy, method in itertools.product(strategies, methods):
        file = os.path.join(InputDir, Map[strategy][method])
        files.append(file)

    coverageFuzz = Map["coverageFuzzing"]
    grammarFuzz = Map["grammarFuzzing"]

    if (len(coverageFuzz) > 0 ):
        file = os.path.join( InputDir, coverageFuzz )
        files.append(file)
    else:
        files.append("")

    if (len(grammarFuzz) > 0 ):
        file = os.path.join( InputDir, grammarFuzz )
        files.append(file)
    else:
        files.append("")
    return files
    

def genTableTex(csvMap: dict):
    resultLines = []
    for entry in csvMap.items():
        resultLines.append( tableLine(entry) + '&&&&&&\\\\ \n\cline{1-14} \n' )
    
    fileName = "table.tex"
    with open(fileName, 'w') as f:
        f.writelines(resultLines)
    return fileName

def handleCoverage(filePrefix: str, coverageMap: dict) -> list:
    files = []
    for key,entry in coverageMap.items():
        filename = filePrefix + "-{}.dat".format(key)
        entry = [ (str(first), str(second)) for first,second in entry]
        strList = [ ' '.join(s) + '\n' for s in entry]
        with open(filename,'w') as f:
            f.writelines(strList)
        files.append(filename)
    return files

def genMetricTrend(Map: dict, metric: str):
    BFSBase = Map["bfs"]["baseline"][metric]
    BFSGoDse = Map["bfs"]["GoDse"][metric]
    
    DFSBase = Map["dfs"]["baseline"][metric]
    DFSGoDse = Map["dfs"]["GoDse"][metric]

    CoverageFuzzing = Map["coverageFuzzing"]["branch"]

    MetricList = [  BFSBase, BFSGoDse, DFSBase, DFSGoDse, CoverageFuzzing]
    def getsecond(tupleList: list):
        return [ t[1] for t in tupleList ]
    MetricList = [ getsecond(elm) for elm in MetricList]
    lines = addTimeIndex(MetricList)

    return lines,MetricList

def addTimeIndex(MetricList):
    trendList = [list( 
        str(i) for i in range(TimeMax + 1)
     )]

    trendList.extend(MetricList)
    lines = list(zip(*trendList))
    lines = [' '.join(map(str,line)) + '\n' for line in lines]
    return lines

def listAdder(first: list, second: list) -> list:
    return [ int(a) + int(b) for a,b in zip(first, second)]    
    
def genGnuDat(csvMap: dict) -> list:
    keyMap = dict(zip(old_list, new_list))
    metrics = ["branch", "statement", "instruction",  ]
    files = []
    metricsummaryList = [[],[],[]]
    metricsummaryDict = dict(zip(metrics, metricsummaryList))
    for key, entry in csvMap.items():
        key = keyMap[key]
        for metric in metrics:
            lines,metricList = genMetricTrend(entry, metric)
            filename = "trends-{}-{}.dat".format(key, metric)
            with open(filename,'w') as f:
                f.writelines(lines)
            files.append(filename)

            if (metricList[1][0] > 0):
                print()

            if (len(metricsummaryDict[metric]) == 0):
                metricsummaryDict[metric] = metricList
            else:
                metricsummaryDict[metric] = [ listAdder(a,b)  for a,b in zip(metricsummaryDict[metric], metricList)  ]
    
    for metric in metrics:
        metricList = metricsummaryDict[metric]
        lines = addTimeIndex(metricList)

        filename = "trends-summary-{}.dat".format(metric)
        with open(filename, 'w') as f:
            f.writelines(lines)
        files.append(filename)

    return files

def runCmd(cmd):
    import subprocess
    ret = subprocess.run(cmd, stdout=None, stderr=None, encoding="utf-8")
    return ret

def genResultListDetails(csvMap: dict, strategy: str, method: str, getCov):
    resultList = []
    for value in csvMap.values():
        # if (strategy == "null"):
        #     filename = value[method]
        # else:
        #     filename = value[strategy][method]
        if (   method == "coverageFuzzing" 
            or method == "grammarFuzzing"):
            val = value[method]
        else:
            val = value[strategy][method]

        if (type(val) == str):
            val = os.path.join(InputDir,val)
        else:
            pass

        try:
            resultList.append(getCov(val))
        except IsADirectoryError as e:
            resultList.append(-1)
    return resultList

def chFloatList(array: list) -> list:
    return [float(elm) for elm in array]

def removeByValue(array: list, value: int):
    removeIndex = []
    resList = []
    for i,aa in enumerate(array):
        if (aa == value):
            removeIndex.append(i)
        else:
            resList.append(aa)
    return resList, removeIndex

def removeByIndex(array: list, index: list):
    return [ee for i,ee in enumerate(array) if not (i in index)]

def compareWith(csvMap: dict, method ):
    global LatexMapping
    methodDict = {
        "coverageFuzzing" : "JQF",
        "grammarFuzzing" : "Grammar"
    }
    metrics = {
        "branch" : getBranchCoverage,
        "statement" : getStatementCoverage
    }
    for metric,metricFunc in metrics.items():
        BaseList = genResultListDetails(csvMap,"null",method,metricFunc)
        BaseList = chFloatList(BaseList)
        BaseList, indexList = removeByValue(BaseList,-1)
        strategies = ["bfs", "dfs"]

        for strategy in strategies:
            GodseList = genResultListDetails(csvMap, strategy, "GoDse",  metricFunc)
            GodseList = chFloatList(GodseList)
            GodseList = removeByIndex(GodseList,indexList)
            absList = [ elm[0] - elm[1] for elm in zip(GodseList,BaseList)]
            val = sum(absList) / sum(BaseList) * 100
            LatexMapping["AverageIncrease{}{}{}".format(methodDict[method], strategy, metric)] = '%.2f' % val
            increaseList = [ (elm[0]-elm[1])/elm[1]*100 for elm in zip(GodseList,BaseList)]
            increaseList.sort()
            LatexMapping["increaseLowPercent{}{}{}".format(methodDict[method], strategy,metric)] = '%.2f' % increaseList[0]
            LatexMapping["increaseUpPercent{}{}{}".format(methodDict[method], strategy,metric)] = '%.2f' % increaseList[-1]
            print()


def getBranchTime(file: str):
    csvList = ReadCsv(file)
    branchList = [ float(line[0]) for line in csvList]
    timeList = [ float(line[-1]) for line in csvList]
    return [branchList,timeList]

def getStatementTime(file: str):
    csvList = ReadCsv(file)
    statementList = [ float(line[2]) for line in csvList]
    timeList = [ float(line[-1]) for line in csvList]
    return [statementList,timeList]

def genSummaryTimeList(csvMap: dict, strategy: str, method: str, metric: str):
    resultList = []
    for value in csvMap.values():
        if (   method == "coverageFuzzing" ):
            curList = value[method][metric]
        else:
            curList = value[strategy][method][metric]
        
        curList = [ elm[1] for elm in curList]
        if len(resultList) == 0:
            resultList = curList
        else:
            resultList = listAdder(resultList,curList)
    return resultList


def speedupCoverage(csvMap: dict, method: str):
    global LatexMapping
    methodDict = {
        "coverageFuzzing" : "JQF",
        "baseline" : "Char"
    }
    metrics = {
        "branch" : [getBranchCoverage, getBranchTime],
        "statement" : [getStatementCoverage, getStatementTime]
    }
    for metric,metricFunc in metrics.items():
        strategies = ["bfs", "dfs"]
        for strategy in strategies:
            BaseList = genSummaryTimeList(csvMap,strategy,method,metric)
            GodseList = genSummaryTimeList(csvMap,strategy,"GoDse", metric)
            if method == "baseline":
                LatexMapping["CoveredNumber{}{}{}".format(methodDict[method], strategy, metric)] = BaseList[-1]
            else:
                LatexMapping["CoveredNumber{}{}".format(methodDict[method],metric)] = BaseList[-1]

            seconds = findIndex(BaseList[-1],GodseList)
            LatexMapping["CoveredNumberSeconds{}{}{}".format(methodDict[method], strategy, metric)] = seconds
            LatexMapping["CoveredNumberSpeedup{}{}{}".format(methodDict[method], strategy, metric)] = '%.2f' % (TimeMax / seconds)





if __name__ == "__main__":
    import argparse
    argParser = argparse.ArgumentParser()
    argParser.add_argument('--inputDir', type=str, default=InputDir)
    argParser.add_argument("--paperDir", type=str)
    argParser.add_argument("--brief", action="store_true")
    args = argParser.parse_args()

    InputDir = args.inputDir
    paperDir = args.paperDir
    IncrementalCoveragePath = os.path.dirname(os.path.abspath(InputDir))

    os.chdir(IncrementalCoveragePath)
    csvList = getCsv(InputDir)
    csvMap = csvSplit(csvList)

    # compareWith(csvMap,method="grammarFuzzing")
    # compareWith(csvMap,method="coverageFuzzing")

    # fileList = genFiles(csvMap, genFileFunc=genDat)
    # fileList.append( genTableTex(csvMap) )

    # copyFiles(fileList, paperDir)

    if (not args.brief):
        coverageMap = genTimePlot(csvMap)
        # speedupCoverage(coverageMap,"baseline")
        speedupCoverage(coverageMap,"coverageFuzzing")
        files = genGnuDat(coverageMap)

        runningFile = os.path.join(
            os.path.dirname(__file__),
            "runPlot.py"
        )
        runCmd([runningFile, "--datDir=./"])

        gnuDir = os.path.join(paperDir, "gnuPDF")
        if (os.path.exists(gnuDir)):
            pass
        else:
            os.mkdir(gnuDir)

        files = [ f for f in os.listdir("./") if( "pdf" in f)]
        copyFiles(files,gnuDir)
    
    

