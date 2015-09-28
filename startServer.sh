#!/usr/bin/env bash

mvn package # dependency:copy-dependencies
mkdir env
mv target/Server.jar env/Server.jar
#mv target/dependency env/dependency
#cp -rf conf env/conf
cp capture.sh env/capture.sh
mvn clean
cd env
scp Server.jar root@46.101.160.177:/root
ssh root@46.101.160.177 ./kill.sh
#java -Xmx512m -server -cp Server.jar:./dependency/*:. com.selfach.ServerStart


#CREATE USER 'selfach'@'localhost' IDENTIFIED BY 'sel123';
#GRANT ALL PRIVILEGES ON * . * TO 'selfach'@'localhost';
#FLUSH PRIVILEGES;