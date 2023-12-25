#!/urs/bin/python3

import os
import math

def main(dir):
	folder_path = dir
	files = os.listdir(folder_path)
	sFiles = sorted(files,key=str.lower);
	count = 0
	countFeature1 = 0
	countFeature2 = 0
	for file in sFiles:
		count += 1
		f = open(dir+'/'+file,'r')
		values = 0
		while True:
			line = f.readline()
			if line:
				if 'stage 1 ------ ' in line:
					values1 = line.split('stage 1 ------ ')[1].strip()
					if values1 == "stage 2 ------":
						values1 = 0
					else:
						countFeature1 += 1
				if 'stage 2 ------ ' in line:
					values2 = line.split('stage 2 ------ ')[1].strip()
					if values2 == "":
						values2 = 0
					else:
						countFeature2 += 1
			else:
				cmd = "echo " + str(count) + " " + file.split('-')[1] + " " + str(values1) + " " + str(values2) + " >> taintData-"+dir.split('/')[9]+".txt"
				os.system(cmd)
				break
	cmd = "echo " + dir.split('/')[9] + " sum1 " + str(countFeature1) + " in programs" + " >> taintData-"+dir.split('/')[9]+".txt"
	os.system(cmd)
	cmd = "echo " + dir.split('/')[9] + " sum2 " + str(countFeature2) + " in programs" + " >> taintData-"+dir.split('/')[9]+".txt"
	os.system(cmd)

if __name__ == "__main__":
	# dir = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/taint/arrayS2"
	# dir = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/taint/bitwiseS2"
	dir = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/taint/constraintS2"
	main(dir)
