#!/bin/bash

make clean
make
for i in $(seq 1 $1)
do
  echo 'Running test #' $i
  java safe.Sample
done
