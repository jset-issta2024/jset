#!/usr/bin/python3

parameters = []

def main():
    inputClassPath = ""
    packageDriverName = ""
    packageSourceName = ""
    entryClass = ""
    entryMethod = ""

    with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/01parameters.txt', 'r') as f:
        for line in f:
            parameter = []
            if line != "":
                if 'inputClassPath' in line:
                    inputClassPath = line.split('inputClassPath = ')[1].split('"')[1]
                if 'packageDriverName' in line:
                    packageDriverName = line.split('packageDriverName = ')[1].split('"')[1]
                if 'packageSourceName' in line:
                    packageSourceName = line.split('packageSourceName = ')[1].split('"')[1]
                if 'entryClass' in line:
                    entryClass = line.split('entryClass = ')[1].split('"')[1]
                if 'entryMethod' in line:
                    entryMethod = line.split('entryMethod = ')[1].split('"')[1]
            if '}' in line:
                parameter.append(inputClassPath)
                parameter.append(entryClass)
                parameter.append(entryMethod)
                parameter.append(packageDriverName)
                parameter.append(packageSourceName)
                parameters.append(parameter)
    for para in parameters:
        print(str(para)+',')
#    print(parameters)
    print(len(parameters))
    print(len(parameter))

main()
