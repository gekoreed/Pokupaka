#!/usr/bin/env bash
if [ "$#" = "0" ]; then
	echo "you have to pass one of arguments"
	echo "d - deploy"
	echo "b - build"
	echo "pass d as 2 parameter to copy dependencies"
else
	mkdir env
	rm -rf env/*

	COMMAND=$1
	DEPS=$2

	mvn package -DskipTests 
	if [[ "$DEPS" = "d" ]]; then
		mvn dependency:copy-dependencies
		mv DataServer/target/dependency env/dependency
	fi
	mv DataServer/target/Server.jar env/Server.jar
	cp -rf DataServer/conf env/conf
	cp capture.sh env/capture.sh
	mvn clean
	if [[ "$COMMAND" = "d" ]]; then
		cd env
	 	scp -r * ubuntu@selfachserver.gekoreed.com:/home/ubuntu
	 	ssh ubuntu@selfachserver.gekoreed.com ./kill.sh
	fi
fi

#CREATE USER 'selfach'@'localhost' IDENTIFIED BY 'sel123';
#GRANT ALL PRIVILEGES ON * . * TO 'selfach'@'localhost';
#FLUSH PRIVILEGES;