#!/usr/bin/env bash

mvn package dependency:copy-dependencies
mkdir env
mv target/Server.jar env/Server.jar
mv target/dependency env/dependency
cp -rf conf env/conf
cd env
#java -Xmx512m -server -cp Server.jar:./dependency/*:. com.pokupaka.ServerStart