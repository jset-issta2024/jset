#!/usr/bin/python3

import os
from multiprocessing import Pool

progAll = ['aejcc/test/aejcc', 'Antlr/test/Antlr', 'autolink/test/Autolink', 'awkparser/test/awkparser', 
			'bling/test/bling', 'bloat/test/bloat', 'calculator/test/calculator', 
			'clojure/test/clojure', 'cmmparser/test/cmmparser', 'CommonsCodec/test/CommonsCodec', 'curta/test/curta', 
			'dom4j/test/dom4j', 'fastjson-dev/test/Fastjson', 'firstorderparser/test/firstorderparser', 
			'flexmark/test/Flexmark', 'formula4j/test/formula4j', 'foxykeep/test/foxykeep', 'ftp/test/ftp', 
			'galimatias/test/Galimatias', 'htmlgramparser/test/htmlgramparser', 
			'htmlparser2/test/htmlpaser2', 'htmlparser/test/htmlparser', 'image4j/test/image4j', 
			'ImageJA/test/GifParser', 'ImageJA/test/PgmParser', 'ino-java/test/InoJava', 'interpreter/test/interpreter', 
			'j2html/test/j2html', 'javaparser/test/javaparser', 'jdom/test/jdom', 
			'jieba/test/jieba', 'JLex/test/jlex', 'jmp123_400_utf8_mini/test/Jmp123', 
			'jodatime/test/jodatime', 'jsijcc/test/jsijcc', 'json-flattener/test/JsonFlattener', 'jsoniter/test/Jsoniter', 
			'jsonparser/test/jsonparser', 'jsonparser-mwnorman/test/jsonparsermwnorman', 'json-raupachz/test/jsonraupachz', 'json-simple/test/jsonsimple', 
			'jurl/test/Jurl', 'mapl/test/mapl', 'markdownj/test/markdownj', 
			'mp3agic/test/mp3agic', 'mXparser/test/mxparser', 'nekohtml/test/nekohtml', 'ntru/test/ntru', 
			'oajavaparser/test/oajavaparser', 'open-m3u8/test/openm3u8', 'sablecc/test/SableCC', 
			'sixpath/test/sixpath', 'snakeyaml/test/snakeyaml', 'soot-c/test/sootc', 
			'sqlparser/test/sqlparser', 'toba/test/toba', 'txtmark/test/Txtmark', 'univocity/test/Univocity', 
			'uriparser/test/uriparser', 'xerces/test/xerces', 'yamlbeans/test/yamlbeans', 'rparser/test/rparser', 
			'golang/test/golang' ]

def main():

	programs = progAll1
	programAll = sorted(programs,key=str.lower)

	os.system("ulimit -s unlimited")
	pool = Pool(processes=4)
	cmd = "./1runTool1SPF.sh {}"

	for program in programAll:
		pool.apply_async(os.system, args=(cmd.format(program),))
		print(cmd.format(program))
	pool.close()
	pool.join()

main()
