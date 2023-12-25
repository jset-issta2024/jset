#!/urs/bin/python3

import os
import math

def main(dir):
	folder_path = dir
	files = os.listdir(folder_path)
	sFiles = sorted(files,key=str.lower);
	count = 0
	countFeature = 0
	for file in sFiles:
		count += 1
		f = open(dir+'/'+file,'r')
		values = 0
		while True:
			line = f.readline()
			if line:
				if 'stage 1 ------ ' in line:
					values = line.split('stage 1 ------ ')[1].strip()
					if values == "":
						values = 0
					if values == "1":
						values = 1
					else:
						countFeature += 1
			else:
				cmd = "echo " + str(count) + " " + file + " " + str(values) + " >> taintData-"+dir.split('/')[9]+".txt"
				os.system(cmd)
				break
	cmd = "echo " + dir.split('/')[9] + " sum " + str(countFeature) + " in programs" + " >> taintData-"+dir.split('/')[9]+".txt"
	os.system(cmd)

if __name__ == "__main__":
	dir = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/taint/loop"
	main(dir)
