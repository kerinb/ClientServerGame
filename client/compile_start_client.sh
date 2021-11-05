#!/bin/sh
#set -x

echo "Running compilation"
mvn clean install source:jar

echo "Starting up game client "
java -jar ./target/clientgamechallenge-1.0-SNAPSHOT.jar
