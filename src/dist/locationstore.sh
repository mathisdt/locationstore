#!/bin/sh
BASE_DIR=$(dirname $(readlink -f "$0"))
JAVA=$(which java)
if [ -z "$JAVA" ]; then
	echo 'Java not found, please download and install the Java Runtime Environment'
	echo 'from here:  =>  http://www.java.com/  <='
else
	$JAVA -Duser.language=de -Duser.country=DE -jar "$BASE_DIR/locationstore.jar" $*
fi
