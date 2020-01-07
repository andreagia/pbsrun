#!/bin/bash
#submit calculation
STRING="Hello World"
#print variable on a screen
echo $STRING
JOBID=$1
ssh webenmr@pbs-enmr.cerm.unifi.it "java -jar pbsrun/target/pbsrun-0.0.1-SNAPSHOT.jar --status=check --jobid=$JOBID"
