
FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/arigato-0.0.1-SNAPSHOT.jar

# cd /usr/local/runme
WORKDIR /data

# copy target/find-links.jar /usr/local/runme/app.jar
COPY ${JAR_FILE} app.jar

# copy project dependencies
# cp -rf target/lib/  /usr/local/runme/lib
#ADD ${JAR_LIB_FILE} lib/

# java -jar /usr/local/runme/app.jar
ENTRYPOINT ["java","-jar","app.jar"]