#!/bin/bash

ant instrument

className=$1
saveInputFile=/home/lmx/Documents/GitHub/jpf8/IncrementalCoverage/expeResultCoverage/1212/$2.saveInput
LOG_FILE=/home/lmx/Documents/GitHub/jpf8/IncrementalCoverage/expeCovResult/1212/$2.csv

echo $className
echo $saveInputFile
echo $LOG_FILE

ant GenCoverage -DclassToRun=${className} -DSaveInputFile=${saveInputFile} > $LOG_FILE 2>&1
