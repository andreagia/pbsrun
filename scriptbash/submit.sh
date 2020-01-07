#!/bin/bash
#submit calculation
STRING="Hello World"
#print variable on a screen
echo $STRING
RANDOMDIR=$1
scp -r $RANDOMDIR  webenmr@pbs-enmr.cerm.unifi.it:$RANDOMDIR
ssh webenmr@pbs-enmr.cerm.unifi.it "java -jar pbsrun/target/pbsrun-0.0.1-SNAPSHOT.jar --status=submit --dir=$RANDOMDIR --exec=run.sh"
