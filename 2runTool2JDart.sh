#!/bin/bash

ROOT=/path/jset

ROOT_DIR=$1
pre=${ROOT_DIR##*/}

LOG_FILE=$ROOT/jdart/expeResult/date/$pre.log
ARGS=jdart/src/example-$1.jpf

echo $pre
echo $ARGS
echo $LOG_FILE

jpf-core/bin/jpf $ARGS > $LOG_FILE 2>&1

