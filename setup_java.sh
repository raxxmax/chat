#!/bin/bash
# Script to set up Android Studio's Java environment for Gradle builds

export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

echo "Java environment set up successfully!"
echo "JAVA_HOME: $JAVA_HOME"
java -version