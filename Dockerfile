FROM gekoreed/selfach

WORKDIR /data

ADD env /data/
ADD kill.sh /data/

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

EXPOSE 8080
EXPOSE 8083

CMD ["sh","kill.sh"]