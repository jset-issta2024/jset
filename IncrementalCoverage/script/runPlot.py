#!/usr/bin/python3

import os

Dir = os.path.dirname(__file__)

def runCmd(cmd):
    import subprocess
    ret = subprocess.run(cmd, stdout=None, stderr=None, encoding="utf-8")
    return ret

def genGnuFile(yMax, xMax, metric, datName:str):
    fileStr = """
set yrange[0:{0}]
set xrange[0:{1}]
set xlabel "Time(s)"
set ylabel "Number of new {2}"
set term post eps color solid enh font 'Helvetica,20'
set output "{3}.eps"
plot '{3}.dat' using 1:2 with p title "baseline bfs" linecolor rgb "#4d4d4d", '{3}.dat' using 1:3 with p title "GoDse bfs" linecolor rgb "#8060ff", '{3}.dat' using 1:4 with p title "baseline dfs" linecolor rgb "#f03232", '{3}.dat' using 1:5 with p title "GoDse dfs" linecolor rgb "#2e8b57", '{3}.dat' using 1:6 with p title "coverage fuzzing" linecolor rgb "#ffa500"
    """.format(yMax,xMax,metric,datName)
    # print(fileStr)
    fileName = os.path.join( 
        Dir, "{}.gnu".format(datName)
     )

    with open(fileName,'w') as f:
        f.write(fileStr)

def readCSV(fileName):
    import csv
    with open(fileName, 'r') as f:
        csvList = list(csv.reader(f))
    return csvList


def handleDatFile():
    fileName = os.path.splitext(f)[0]
    metric = fileName.split('-')[-1]
    csvList = readCSV(os.path.join( Dir, f ) )
    lastLine = csvList[-1][0].split(' ')
    timeMax = int(lastLine[0])
    metricMax = [ int(elm) for elm in lastLine[0:]]
    metricMax.sort()
    metricMax = metricMax[-1]
    genGnuFile(metricMax * 1.4, timeMax + 10, metric, fileName)

if __name__ == "__main__":
    import argparse
    argParser = argparse.ArgumentParser()
    argParser.add_argument('--datDir', type=str)
    args = argParser.parse_args()

    Dir = args.datDir

    files = [ f for f in os.listdir(Dir) if( "trend" in f and "dat" in f)]

    for f in files:
        handleDatFile()

    curDir = os.getcwd()
    os.chdir(Dir)
    files = [f for f in os.listdir(Dir) if ("gnu" in f)]
    for f in files:
        fileName = os.path.join(Dir,f)
        runCmd(
            ["gnuplot",fileName]
        )

    files = [ f for f in os.listdir(Dir) if ("eps" in f)]
    for f in files:
        fileName = os.path.join(Dir, f)
        runCmd(
            ["epspdf", fileName]
        )

    os.chdir(curDir)





