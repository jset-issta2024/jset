#!/bin/bash

ant instrument

className=$1
saveInputFile=/path/IncrementalCoverage/expeResultCoverage/expTime/$2.saveInput
LOG_FILE=/path/IncrementalCoverage/expeCovResult/expTime/$2.csv

echo $className
echo $saveInputFile
echo $LOG_FILE

ant GenCoverage -DclassToRun=${className} -DSaveInputFile=${saveInputFile} > $LOG_FILE 2>&1
