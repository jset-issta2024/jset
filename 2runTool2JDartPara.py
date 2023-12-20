#!/usr/bin/python3

import os
from multiprocessing import Pool

run15Min = ['commonmark-java/test/Commonmark', 'commons-csv/test/CommonsCsv', 'fastcsv/test/fastCsv', 'gson/test/Gson', 
				'htmlcleaner/test/Htmlcleaner', 'ino-java/test/InoJava', 'jcsv/test/Jcsv', 'expression/test/Expression', 
				'jsoniter/test/Jsoniter', 'jsoup/test/Jsoup', 'jtidy/test/Jtidy', 'MarkdownPapers/test/MarkdownPapers', 
				'minimal-json/test/MinimalJson', 'nanojson/test/nanojson', 'nanoxml/test/nanoxml', 'xml/test/xml', 
				'rhino/test/Rhino', 'simple-csv/test/simpleCsv', 'super-csv/test/supserCsv', 'univocity/test/Univocity',
				'url-detector/test/urlDetector' ]

def main():

	programs = run15Min
	programAll = sorted(programs,key=str.lower)
	
	os.system("ulimit -s unlimited")
	pool = Pool(processes=4)
	cmd = "./2runTool2JDart.sh {}"

	for program in Cov:
		pool.apply_async(os.system, args=(cmd.format(program),))
		print(cmd)
	pool.close()
	pool.join()

main()
