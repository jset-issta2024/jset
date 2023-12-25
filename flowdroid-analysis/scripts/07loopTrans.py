#!/usr/bin/python3

parameters = []

def main():

    appPath = "";
    classname = "";
    intFlag = "0";
    charFlag =  "0";
    floatFlag = "0";

    with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/07loop.txt', 'r') as f:
        for line in f:

            parameter = []
            if line != "":
                if 'appPath = ' in line:
                    appPath = line.split('appPath = ')[1].split('"')[1]
                if 'classname = ' in line:
                    classname = line.split('classname = ')[1].split('"')[1]
                if 'setSources' in line:
                    charFlag = '1'
                if 'setIntegerSources' in line:
                    intFlag = '1'
                if 'setFloatSources' in line:
                    floatFlag = '1'
            if '}' in line:
                parameter.append(appPath)
                parameter.append(classname)
                parameter.append(intFlag)
                parameter.append(charFlag)
                parameter.append(floatFlag)
                parameters.append(parameter)
                intFlag = '0';
                charFlag =  '0';
                floatFlag = '0';
    for para in parameters:
        print(str(para)+',')
#    print(parameters)
    print(len(parameters))
    print(len(parameter))

main()
