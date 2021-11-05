#!/bin/sh
#set -x

echo "Running compilation"
mvn clean install source:jar

echo "Starting up game server "
java -jar ./target/fiveinarowchallenge-0.0.1-SNAPSHOT.jar