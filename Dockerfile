FROM ubuntu:latest

# Install Java.
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN sudo apt-get install software-properties-common -y
RUN add-apt-repository -y ppa:webupd8team/java
RUN add-apt-repository -y ppa:kirillshkrogalev/ffmpeg-next
RUN apt-get update
RUN apt-get install ffmpeg -y
RUN apt-get install -y oracle-java8-installer
RUN rm -rf /var/lib/apt/lists/*
RUN rm -rf /var/cache/oracle-jdk8-installer

WORKDIR /data

ADD env /data/
ADD kill.sh /data/

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

EXPOSE 8080
EXPOSE 8083

CMD ["sh","kill.sh"]