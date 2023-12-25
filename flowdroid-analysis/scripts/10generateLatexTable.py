#!/urs/bin/python3

import os
import math

ccnDatas = []
clocDatas = []
arrayDatas = []
sumArray = -1
bitwiseData = []
sumBitwise = -1
branchData = []
sumBranch = -1
sumBranchTaint = -1
sumBranchLoop = -1
constraintData = []
sumConstraint = -1
sumFile = -1
floatData = []
fileData = []
sumFloat = -1
sumJni = -1
loopData = []
sumLoop = -1
recursionData = []
sumRecursion = -1
BranchJniReflections = []
sumReflection = -1
typeData = []

floatPrograms = ['colt', 'la4j']

filePrograms = {'bloat':'JAR', 'BMPDecoder':'BMP', 'ftp':'TXT', 'image4j':'BMP', 'ImageJA':'BMP-GIF-PGM',
				'jmp123_400_utf8_mini':'MP3', 'mp3agic':'MP3', 'sablecc':'SCC', 'schroeder':'WAV', 'soot-c':'JAR',
				'yamlbeans':'YAML', 'snakeyaml':'YAML'}

def main():

	with open('/home/lmx/Documents/GitHub/JavaSEBench/script/JavaNCSS/Statistics/data.txt', 'r') as f:
		for line in f:
			ccnData = []
			if ',' in line:
				ccnData.append(line.split(',')[0].strip())
				ccnData.append(line.split(',')[1].strip())
				ccnData.append(line.split(',')[2].strip())
				ccnDatas.append(ccnData)
	print('ccnDatas')
	print(len(ccnDatas))

	with open('/home/lmx/Documents/GitHub/JavaSEBench/script/JavaNCSS/Statistics/clocData.txt', 'r') as f:
		for line in f:
			clocData = []
			if 'program' in line:
				clocData.append(line.split(' ')[2].strip())
				clocData.append(line.split(' ')[3].strip())
				clocDatas.append(clocData)
	print('clocDatas')
	print(len(clocDatas))

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/instrumentData.txt', 'r') as f:
		for line in f:
			BranchJniReflection = []
			if 'programs' not in line:
				BranchJniReflection.append(line.split(' ')[2].strip())
				BranchJniReflection.append(line.split(' ')[3].strip())
				BranchJniReflection.append(line.split(' ')[4].strip())
				BranchJniReflection.append(line.split(' ')[5].strip())
				BranchJniReflections.append(BranchJniReflection)
			if 'branch sum' in line:
				sumBranch = line.split(' ')[2].strip()
			if 'branchLoop sum' in line:
				sumBranchLoop = line.split(' ')[2].strip()
			if 'jni sum' in line:
				sumJni = line.split(' ')[2].strip()
			if 'reflection sum' in line:
				sumReflection = line.split(' ')[2].strip()
	print('BranchJniReflections')
	print(len(BranchJniReflections))

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/taintData-arrayS2.txt', 'r') as f:
		for line in f:
			arrayData = []
			if 'arrayS2' not in line:
				arrayData.append(line.split(' ')[3].strip())
				arrayData.append(line.split(' ')[1].strip())
				arrayDatas.append(arrayData)
			if 'arrayS2 sum2' in line:
				sumArray = line.split(' ')[2].strip()
	print('arrayDatas')
	print(len(arrayDatas))

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/taintData-bitwiseS2.txt', 'r') as f:
		for line in f:
			if 'bitwise' not in line:
				bitwiseData.append(line.split(' ')[3].strip())
			if 'bitwiseS2 sum2' in line:
				sumBitwise = line.split(' ')[2].strip()
	print('bitwiseData')
	print(len(bitwiseData))

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/taintData-branch.txt', 'r') as f:
		for line in f:
			if '.log' in line:
				branchData.append(line.split(' ')[2].strip())
			if 'branch sum' in line:
				sumBranchTaint = line.split(' ')[2].strip()
	print('branchData')
	print(len(branchData))

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/taintData-constraintS2.txt', 'r') as f:
		for line in f:
			if 'constraint' not in line:
				constraintData.append(line.split(' ')[3].strip())
			if 'constraintS2 sum2' in line:
				sumConstraint = line.split(' ')[2].strip()
	print('constraintData')
	print(len(constraintData))

	# with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/test GetData-float.txt', 'r') as f:
	# 	for line in f:
	# 		if '.log' in line:
	# 			floatData.append(line.split(' ')[2].strip())
	# print(floatData)

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/taintData-loop.txt', 'r') as f:
		for line in f:
			if '.log' in line:
				loopData.append(line.split(' ')[2].strip())
			if 'loop sum' in line:
				sumLoop = line.split(' ')[2].strip()
	print('loopData')
	print(len(loopData))

	with open('/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/scripts/taintData-recursion.txt', 'r') as f:
		for line in f:
			if '.log' in line:
				recursionData.append(line.split(' ')[2].strip())
			if 'recursion sum' in line:
				sumRecursion = line.split(' ')[2].strip()
	print('recursionData')
	print(len(recursionData))

	with open('/home/lmx/Documents/GitHub/JavaSEBench/script/JavaNCSS/Statistics/programsType', 'r') as f:
		for line in f:
			if ', ' in line:
				typeData.append(line.split(', ')[1].strip())
	print('typeData')
	print(len(typeData))

	for i in range(len(ccnDatas)):
		if ccnDatas[i][0] in filePrograms:
			fileData.append(filePrograms[ccnDatas[i][0]])
		else:
			fileData.append('-')
		if ccnDatas[i][0] in floatPrograms:
			floatData.append('1')
		else:
			floatData.append('0')


	cmd = "echo "		+	'number'	+	" \& "			+	"Name"	+	" \& "		+	"LoC"	+	" \& "			+	"NCSS" +	" \& "	+	'CCN'			+	" \& "	+ \
		  'array'		+	" \& "		+	'bitwise'		+	" \& "				+	'branch'	+	" \& "				+	'branchTaint'	+	" \& " + \
		  'branchLoop'	+	" \& "		+	'constraint'	+	" \& "				+	'file'		+	" \& "				+	'float'			+	" \& " + \
		  'jni'			+	" \& "		+	'loop'			+	" \& "				+	'recursion'	+	" \& "				+	'reflection'	+	" \& "			+	'type'	+	" >> latexTable"	+	".txt"
	os.system(cmd)
	os.system("echo " + '\\'+'hline'+ " >> latexTable" 	 + ".txt")

	for i in range(len(recursionData)):
		cmd = "echo " + str(i+1)+ " \& " 	 + str(ccnDatas[i][0]) + " \& " + str(clocDatas[i][1]) + " \& " + str(ccnDatas[i][2]) + " \& " + str(ccnDatas[i][1]) +" \& " \
			+ str(arrayDatas[i][0])		 + " \& " + str(bitwiseData[i]) 	 + " \& " + str(BranchJniReflections[i][0])		+ " \& " + str(branchData[i]) + " \& " + \
			  str(BranchJniReflections[i][1]) + " \& " + str(constraintData[i]) + " \& " + str(fileData[i]) 		+ " \& " + str(floatData[i]) 		 + " \& " + \
			  str(BranchJniReflections[i][2])		 + " \& " + str(loopData[i]) 		 + " \& " + str(recursionData[i]) + " \& " + str(BranchJniReflections[i][3])	 + " \& " + str(typeData[i]) + " type >> latexTable" + ".txt"
		os.system(cmd)
		os.system("echo " + '\\'+'hline'+ " >> latexTable" 	 + ".txt")

	cmd = "echo "		+	'Sum'	+	" \& "			+	"Program"	+	" \& "		+	"LOC"	+	" \& "			+	"NCSS" +	" \& "	+	'CCN'			+	" \& "	+ \
			  str(sumArray)		+	" \& "		+	str(sumBitwise)		+	" \& "				+	str(sumBranch)	+	" \& "				+	str(sumBranchTaint)	+	" \& " + \
			  str(sumBranchLoop)	+	" \& "		+	str(sumConstraint)	+	" \& "				+	"14"		+	" \& "				+	"24"			+	" \& " + \
			  str(sumJni)			+	" \& "		+	str(sumLoop)			+	" \& "				+	str(sumRecursion)	+	" \& "				+	str(sumReflection)	+	" \& "			+	'Type'	+	" >> latexTable"	+	".txt"
	os.system(cmd)
	os.system("echo " + '\\'+'hline'+ " >> latexTable" 	 + ".txt")

if __name__ == "__main__":
	main()
