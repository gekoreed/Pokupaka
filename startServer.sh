#!/usr/bin/env bash
mkdir env
rm -rf env/*

mvn package -DskipTests #dependency:copy-dependencies
mv DataServer/target/Server.jar env/Server.jar
#mv DataServer/target/dependency env/dependency
cp -rf DataServer/conf env/conf
cp capture.sh env/capture.sh
mvn clean
cd env
scp -r * ubuntu@ec2-52-29-3-129.eu-central-1.compute.amazonaws.com:/home/ubuntu
ssh ubuntu@ec2-52-29-3-129.eu-central-1.compute.amazonaws.com ./kill.sh


#CREATE USER 'selfach'@'localhost' IDENTIFIED BY 'sel123';
#GRANT ALL PRIVILEGES ON * . * TO 'selfach'@'localhost';
#FLUSH PRIVILEGES;