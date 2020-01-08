#!/bin/bash
#submit calculation
#print variable on a screen
export LC_CTYPE=C
RANDOMDIR=$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 13)
scp -pr webenmr@pbs-enmr.cerm.unifi.it:$1 $RANDOMDIR
ssh webenmr@pbs-enmr.cerm.unifi.it "java -jar pbsrun/target/pbsrun-0.0.1-SNAPSHOT.jar --status=submit --dir=$RANDOMDIR --exec=run.sh"
