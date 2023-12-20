#!/usr/bin/python3

import os
import subprocess

inputDir = "inputs"
resultDir = "results"
curWorkDir = os.getcwd()

def settingJDK(jdk_version):
    settting = "/usr/local/jdk1." + str(jdk_version)
    os.environ['JAVA_HOME'] = settting
    os.environ['JDK_HOME'] = settting

def init():
    cwd = os.path.join(os.path.dirname(__file__), "..")
    os.chdir(cwd)
    res = runCmd( ["ant", "instrument"] )
    return res

def finish():
    os.chdir(curWorkDir)

def runCmd(cmd):
    ret = subprocess.run(cmd, stdout=None, stderr=None, encoding="utf-8")
    return ret

def genCov(className, saveInputFile):
    # className="test.JavaparserDriver"
    # saveInputFile="src/example-javaparser/TestJavaparserParser-bfs.saveInput"
    # ant GenCoverage -DclassToRun=${className}  -DSaveInputFile=${saveInputFile}
    cmd = ["ant", "GenCoverage",
            "-DclassToRun={}".format(className), 
            "-DSaveInputFile={}".format(saveInputFile)]

    ret = runCmd(cmd)
    if ret.returncode == 0:
        pass
    else:
        print(ret.stdout)
        print(ret.stderr)

def genBrief(className, saveInputFile):
    cmd = ["ant", "BriefCoverage",
            "-DclassToRun={}".format(className), 
            "-DSaveInputFile={}".format(saveInputFile),
            "-Dbrief={}".format(1)]

    ret = runCmd(cmd)
    if ret.returncode == 0:
        pass
    else:
        print(ret.stdout)
        print(ret.stderr)

def getFiles(dir):
    files = os.listdir(dir)
    res_list = []
    for file in files:
        if( ".saveInput" in os.path.splitext(file)[1] 
        or ".saveinput" in os.path.splitext(file)[1] ):
            res_list.append(file)
    return res_list

def getRunningClass(fileName):
    className = fileName.split("-")[0]
    return className

def genTimeCov():
    global resultDir
    init()

    files = getFiles(inputDir)
    for file in files:
        className = getRunningClass(file)
        genCov(className, os.path.join(inputDir,file))

    resultDir = os.path.abspath(inputDir)
    finish()

def mv_saveInput(srcPath, dstPath):
    import shutil

    if (not os.path.exists(dstPath)):
        os.makedirs(dstPath)
    else:
        shutil.rmtree(dstPath)
        os.makedirs(dstPath)

    fileList = os.listdir(srcPath)
    fileList = [ file for file in fileList if ((".saveInput" in file)
                                                or (".saveinput" in file) )
                ]
    for file in fileList:
        src = os.path.join(srcPath, file)
        dst = os.path.join(dstPath, file)
        shutil.copy(src, dst)

def genBriefCov():
    global resultDir
    init()

    newDir = inputDir + "-brief"
    # mv_saveInput(inputDir, newDir)
    files = getFiles(newDir)
    for file in files:
        className = getRunningClass(file)
        genBrief(className, os.path.join(newDir,file))
    
    resultDir = os.path.abspath(newDir)
    finish()


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument("--brief", action="store_true")
    args = parser.parse_args()
    brief = args.brief

    if brief:
        genBriefCov()
    else:
        genTimeCov()

    


    
