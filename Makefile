run:
	docker run -p 8080:8080 -p 8083:8083 -d -v /Users/gekoreed/Desktop/last:/data/last -v /Users/gekoreed/Desktop/pictures:/data/pictures --name selfach gekoreed/selfach
build:
	docker build -t gekoreed/selfach .
push:
	docker push gekoreed/selfach
pull:
	docker pull gekoreed/selfach