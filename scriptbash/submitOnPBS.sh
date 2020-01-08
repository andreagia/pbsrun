#!/bin/bash
#submit calculation
#print variable on a screen
RANDOMDIR=$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 13)
scp -pr webenmr@py-enmr.cerm.unifi.it:$1 $RANDOMDIR
java -jar pbsrun/target/pbsrun-0.0.1-SNAPSHOT.jar --status=submit --dir=$RANDOMDIR --exec=run_amber.sh
