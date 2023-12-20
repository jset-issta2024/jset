#!/bin/bash

function read_dir() {
	for file in 'ls $1'
	echo $1
	do
		if [ -d $1"/"$file ]
		then
			read_dir $1"/"$file
		else
			# if [[ "$file" == *example*test*"java" ]];
			# then
			echo $1"/"$file
			# fi
		fi
	done
}
# read_dir "/home/lmx/Documents/GitHub/jpf8/jdart/src"
