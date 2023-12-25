#!/urs/bin/python3

import os
import math

def main(dir):
	folder_path = dir
	files = os.listdir(folder_path)
	sFiles = sorted(files,key=str.lower);
	print(sFiles)
	count = 0
	countBranch = 0
	countJni = 0
	countReflection = 0
	countBranchLoop = 0
	for file in sFiles:
		count += 1
		f = open(dir+'/'+file,'r')
		if os.path.getsize(dir+'/'+file) == 0:
			print(file)
		BranchValue = 0
		JniValue = 0
		ReflectionValue = 0
		BranchLoopValue = 0
		while True:
			line = f.readline()
			if line:
				if 'Branch count sum: ' in line:
					BranchValue = line.split('Branch count sum: ')[1].strip()
					if BranchValue != '0':
						countBranch += 1
				if 'JNI Feature Detection Result ------ ' in line:
					JniValue = line.split('JNI Feature Detection Result ------ ')[1].strip()
					if JniValue != '0':
						countJni += 1
				if 'Reflection Feature Detection Result ------ ' in line:
					ReflectionValue = line.split('Reflection Feature Detection Result ------ ')[1].strip()
					if ReflectionValue != '0':
						countReflection += 1
				if 'Branch in Loop count : ' in line:
					BranchLoopValue = line.split('Branch in Loop count : ')[1].strip()
					if BranchLoopValue != '0':
						countBranchLoop += 1
			else:
				cmd = "echo " + str(count) + " "+ file + " " + str(BranchValue) + " " + str(BranchLoopValue) + " " + str(JniValue) + " " + str(ReflectionValue) +" >> instrumentData.txt"
				os.system(cmd)
				break
	cmd = "echo " + "branch sum " + str(countBranch) + " in programs" + " >> instrumentData.txt"
	os.system(cmd)
	cmd = "echo " + "branchLoop sum " + str(countBranchLoop) + " in programs" + " >> instrumentData.txt"
	os.system(cmd)
	cmd = "echo " + "jni sum " + str(countJni) + " in programs" + " >> instrumentData.txt"
	os.system(cmd)
	cmd = "echo " + "reflection sum " + str(countReflection) + " in programs" + " >> instrumentData.txt"
	os.system(cmd)

if __name__ == "__main__":
	dir = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/instrument"
	main(dir)
